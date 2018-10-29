package mundo;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import estructuras.Arco;
import estructuras.ChainingHashTable;
import estructuras.Digraph;
import estructuras.List;
import estructuras.Stack;

public class CatalogoDeVuelos {

	public static final String RUTA_EXCEL = "./data/ItinerarioAeroCivil-v2.xlsx";

	private Digraph<Integer, Vuelo> catalogo;
	private List<Aerolinea> aerolineas;
	private ChainingHashTable<String, Integer> tablaNombreId;
	private DigraphAlgorithms algoritmos;

	public CatalogoDeVuelos() {
		catalogo = new Digraph<>();
		aerolineas = new List<>();
		tablaNombreId = new ChainingHashTable<>();
		algoritmos = new DigraphAlgorithms(catalogo);
	}

	public List<Aerolinea> darAerolineas() {
		return aerolineas;
	}

	public int tamCiudad()
	{
		return catalogo.sizeV();
	}
	public void agregarAerolinea(String nombreAerolinea) {
		try(XSSFWorkbook wb = new XSSFWorkbook(new File(RUTA_EXCEL))){
			XSSFSheet sheet = wb.getSheetAt(1);
			Iterator<Row> rowIt = sheet.rowIterator();
			rowIt.next();
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				String nombre = row.getCell(0).getStringCellValue();
				if(nombre.equals(nombreAerolinea)){
					double valorMin = Double.parseDouble(row.getCell(1).getStringCellValue().split(" ")[0]);
					int numSillas = (int) row.getCell(2).getNumericCellValue();
					aerolineas.add(new Aerolinea(nombre, valorMin, numSillas));
					return;
				}
			}
			aerolineas.add(new Aerolinea(nombreAerolinea, 1, 100));
		} catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	public void eliminarAerolinea(String nombre) {
		int tam = aerolineas.size();
		for (int i = 0; i < tam; i++) {
			if (aerolineas.get(i).darNombre().equals(nombre))
			{
				aerolineas.remove(i);
				break;
			}
		}
	}

	public void agregarVuelo(int numero, Aerolinea aerolinea, Ciudad ciudadOrigen, Ciudad ciudadDestino,
			Calendar horaDeSalida, Calendar horaDeLlegada, String tipoDeAvion, int cupo, DayOfWeek[] diasDeOperacion) {
		Vuelo vuelo = new Vuelo(numero, aerolinea, ciudadOrigen, ciudadDestino, horaDeSalida, horaDeLlegada,
				tipoDeAvion, cupo, diasDeOperacion);
		Ciudad destino = vuelo.darCiudadDestino();
		Ciudad origen = vuelo.darCiudadOrigen();

		long costo = vuelo.darDuracion();
		catalogo.agregarArco(origen.darId(), destino.darId(), costo, vuelo);
	}

	public static double calcularTarifa(Vuelo vuelo, DayOfWeek dia) {
		double tm = vuelo.darAerolinea().darTarifaPorMinuto();
		long td = vuelo.darDuracion();
		int ts = vuelo.darCupo();
		int tsMax = vuelo.darAerolinea().darMaxSillas();

		double tarifa = tm * td * tsMax / ts * td;

		return (dia.equals(DayOfWeek.VIERNES) || dia.equals(DayOfWeek.SABADO) || dia.equals(DayOfWeek.DOMINGO))
				? (tarifa * 1.3) : (tarifa);

	}

	public Ciudad darCiudad(String nombre) {
		return (Ciudad) catalogo.buscarNodo(tablaNombreId.get(nombre));
	}

	public void agregarCiudad(String nombre) {
		int id = catalogo.sizeV();
		catalogo.agregarNodo(new Ciudad(nombre, id));
		tablaNombreId.put(nombre, id);
	}

	public void eliminarCiudad(String nombre) {
		int id = tablaNombreId.delete(nombre);
		catalogo.eliminarNodo(id);
		Ciudad temp = (Ciudad) catalogo.buscarNodo(catalogo.sizeV() - 1);
		temp.cambiarId(id);
		catalogo.eliminarNodo(catalogo.sizeV() - 1);
		catalogo.agregarNodo(temp);
		tablaNombreId.put(nombre, id);
	}

	public void cargarArchivo(String ruta) {
		File f = new File(ruta);
		try (XSSFWorkbook workbook = new XSSFWorkbook(f)) {
			cargarAerolineas(workbook.getSheetAt(1));
			cargarVuelos(workbook.getSheetAt(0));
		} catch (IOException | InvalidFormatException e) {
			e.printStackTrace();
		}
	}

	private void cargarAerolineas(XSSFSheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator();
		rowIt.next();
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			String nombre = row.getCell(0).getStringCellValue();
			double valorMin = Double.parseDouble(row.getCell(1).getStringCellValue().split(" ")[0]);
			int numSillas = (int) row.getCell(2).getNumericCellValue();
			aerolineas.add(new Aerolinea(nombre, valorMin, numSillas));
		}
	}

	private void cargarVuelos(XSSFSheet sheet) {
		Iterator<Row> rowIt = sheet.rowIterator();
		rowIt.next();
		rowIt.next();
		String nombreM = "", origenM = "", destinoM = "";
		int numVueloM = 0;
		Date horaM = null, horaM2 = null;
		while (rowIt.hasNext()) {
			Row row = rowIt.next();
			String nombre = row.getCell(0).getStringCellValue();
			if (nombre.equals(""))
				nombre = nombreM;
			else
				nombreM = nombre;

			Aerolinea aerolinea = darAerolinea(nombre);

			int numVuelo = (int) row.getCell(1).getNumericCellValue();
			if (numVuelo == 0)
				numVuelo = numVueloM;
			else
				numVueloM = numVuelo;

			String origen = row.getCell(2).getStringCellValue();
			if (origen.equals(""))
				origen = origenM;
			else
				origenM = origen;
			if (darCiudad(origen) == null)
				agregarCiudad(origen);

			String destino = row.getCell(3).getStringCellValue();
			if (destino.equals(""))
				destino = destinoM;
			else
				destinoM = destino;
			if (darCiudad(destino) == null)
				agregarCiudad(destino);

			Date hora = row.getCell(4).getDateCellValue();
			if (hora == null)
				hora = horaM;
			else
				horaM = hora;

			Calendar horaSalida = Calendar.getInstance();
			horaSalida.setTime(hora);

			hora = row.getCell(5).getDateCellValue();
			if (hora == null)
				hora = horaM2;
			else
				horaM2 = hora;

			Calendar horaLlegada = Calendar.getInstance();
			horaLlegada.setTime(hora);

			String tipo = row.getCell(6).getStringCellValue();
			int numSillas = (int) row.getCell(7).getNumericCellValue();

			List<DayOfWeek> dias = new List<>();
			for (int i = 1; i < 8; i++)
				if ("X".equals(row.getCell(7 + i).getStringCellValue()))
					dias.add(DayOfWeek.of(i));

			DayOfWeek[] arrDias = new DayOfWeek[dias.size()];
			for (int i = 0; i < dias.size(); i++)
				arrDias[i] = dias.get(i);

			agregarVuelo(numVuelo, aerolinea, darCiudad(origen), darCiudad(destino), horaSalida, horaLlegada, tipo,
					numSillas, arrDias);
		}

	}

	public String darComponentes() {
		algoritmos.Kosaraju(null);
		return imprimirConectados(algoritmos.getComponents());
	}

	public String darComponentesAerolinea(Aerolinea a) {
		algoritmos.Kosaraju(a);
		return imprimirConectados(algoritmos.getComponents());
	}

	public Aerolinea darAerolinea(String s) {
		for (Aerolinea a : aerolineas) {
			if (a.darNombre().equals(s))
				return a;
		}
		return null;
	}

	public String darMSTCiudad(String ciudad, int peso, DayOfWeek dia) {
		String ans = "";
		List<Arco<Integer, Vuelo>> mst = algoritmos.MST(null, darCiudad(ciudad).darId(), peso, dia).getValues();
		for (Arco<Integer, Vuelo> a : mst)
			ans += a.darInformacion() + "\n";
		return ans;
	}

	public String darMSTAerolinea(String aerolinea, String ciudad, int peso) {
		String ans = "";
		List<Arco<Integer, Vuelo>> mst = algoritmos
				.MST(darAerolinea(aerolinea), darCiudad(ciudad).darId(), peso, DayOfWeek.DOMINGO).getValues();
		for (Arco<Integer, Vuelo> a : mst)
			ans += a.darInformacion() + "\n";
		return ans;
	}

	public String darMenorItinerarioTodasLasAerolineas(String origen, String destino, DayOfWeek dia, int peso) {
		int size = aerolineas.size();
		String[] respuestas = new String[size];
		double[] costos = new double[size];
		for (int i = 0; i < size; i++) {
			respuestas[i] = aerolineas.get(i) + " "
					+ darMenorItinerarioAerolinea(origen, destino, dia, peso, aerolineas.get(i));
			costos[i] = Double.parseDouble(respuestas[i].split("}, ")[1]);
		}

		for (int i = 1; i < size; i++) {
			for (int j = i; j > 0 && costos[j] < costos[j - 1]; j--) {
				String temp1 = respuestas[j];
				respuestas[j] = respuestas[j - 1];
				respuestas[j - 1] = temp1;
				double temp2 = costos[j];
				costos[j] = costos[j - 1];
				costos[j - 1] = temp2;
			}
		}

		String ans = "";
		for (int i = 0; i < size; i++)
			ans += respuestas[i] + "\n";
		return ans;
	}

	private String darMenorItinerarioAerolinea(String origen, String destino, DayOfWeek dia, int peso, Aerolinea a) {
		algoritmos.Dijsktra(darCiudad(origen).darId(), a, peso, dia);
		int idDest = tablaNombreId.get(destino);
		int idOri = tablaNombreId.get(origen);
		Stack<Arco<Integer, Vuelo>> temp = new Stack<>();
		while (true) {
			if (idDest == idOri)
				break;

			Arco<Integer, Vuelo> arco = algoritmos.getEdgeTo()[idDest];
			if(arco == null)
				return "no existe un camino entre estas dos ciudades";
			temp.push(arco);
			idDest = arco.darNodoInicio().darId();
		}

		String ans = "{";
		double costo = 0;
		while (!temp.isEmpty()) {
			Arco<Integer, Vuelo> temp1 = temp.pop();
			Vuelo temp2 = temp1.darInformacion();
			costo += algoritmos.darCosto(temp1, peso, dia);
			if (temp2.darCiudadOrigen().darNombre().equals(origen))
				ans += temp2.darCiudadOrigen();
			String salida = (String) ((temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY) < 10
					? ("0" + temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY))
					: temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY))
					+ ":"
					+ (temp2.darHoraDeSalida().get(Calendar.MINUTE) < 10
							? ("0" + temp2.darHoraDeSalida().get(Calendar.MINUTE))
							: temp2.darHoraDeSalida().get(Calendar.MINUTE)));
			String dias = "";
			if(temp2.darDia() != null)
				dias = temp2.darDia().toString();
				ans += "-" + temp2.darNumero() + "-" + dias + "-"
					+ salida+ "-"
					+ temp2.darCiudadDestino() + "-" + algoritmos.darCosto(temp1, peso, dia);
			if (temp.size() == 0) {
				ans += "}, " + costo;
				break;
			} else
				ans += "-";
		}
		return ans;
	}

	public String darMenorItinerario(String origen, String destino, DayOfWeek dia, int peso) {
		algoritmos.Dijsktra(darCiudad(origen).darId(), null, peso, dia);
		int idDest = tablaNombreId.get(destino);
		int idOri = tablaNombreId.get(origen);
		Stack<Arco<Integer, Vuelo>> temp = new Stack<>();
		while (true) {
			if (idDest == idOri)
				break;
			Arco<Integer, Vuelo> arco = algoritmos.getEdgeTo()[idDest];
			if(arco == null)
				return "no existe un camino entre estas dos ciudades";
			temp.push(arco);
			idDest = arco.darNodoInicio().darId();
		}

		String ans = "{	";
		double costo = 0;
		while (!temp.isEmpty()) {
			Arco<Integer, Vuelo> temp1 = temp.pop();
			Vuelo temp2 = temp1.darInformacion();
			costo += algoritmos.darCosto(temp1, peso, dia);
			if (temp2.darCiudadOrigen().darNombre().equals(origen))
				ans += temp2.darCiudadOrigen();
			String salida = (String) ((temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY)<10?("0"+temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY)):temp2.darHoraDeSalida().get(Calendar.HOUR_OF_DAY))+":"+(temp2.darHoraDeSalida().get(Calendar.MINUTE)<10?("0"+temp2.darHoraDeSalida().get(Calendar.MINUTE)):temp2.darHoraDeSalida().get(Calendar.MINUTE)));
			ans += "-" + temp2.darAerolinea() + "-" + temp2.darNumero() + "-" + temp2.darDia() + "-"
					+ salida+"-" + temp2.darCiudadDestino() + "-"
					+ algoritmos.darCosto(temp1, peso, dia);
			if (temp.size() == 0) {
				ans += "}, " + costo;
				break;
			} else
				ans += "-";
		}
		return ans;
	}

	public String darMenorRutaTodaCiudad(String ciudad, String aerolinea, int peso) {
		String ans = "";
		algoritmos.Dijsktra(darCiudad(ciudad).darId(), darAerolinea(aerolinea), peso, DayOfWeek.LUNES);
		Arco<Integer, Vuelo>[] array = algoritmos.getEdgeTo();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				ans += darMenorItinerarioAerolinea(ciudad, catalogo.buscarNodo(i).toString(), DayOfWeek.LUNES, peso,
						darAerolinea(aerolinea)) + "\n";
			}
		}
		return ans;
	}

	@SuppressWarnings("unchecked")
	public String imprimirConectados(int[] resp) {
		String ans = "";
		List<String>[] temp = new List[catalogo.sizeV()];
		for (int i = 0; i < resp.length; i++) {
			if (temp[resp[i]] == null)
				temp[resp[i]] = new List<String>();

			temp[resp[i]].add(catalogo.buscarNodo(i).toString());
		}
		for (List<String> s : temp) {
			if (s != null && s.size() > 1) {
				ans += "{";
				for (int i = 0; i < s.size(); i++) {
					if (i != s.size() - 1)
						ans += s.get(i) + ", ";
					else
						ans += s.get(i) + "}\n";
				}

			}
		}
		return ans;
	}

	public int darCantidadVuelos() {
		return catalogo.darCantidadArcos();
	}

	/*
	 * public static void main(String[] args) { CatalogoDeVuelos cat = new
	 * CatalogoDeVuelos(); cat.cargarArchivo(RUTA_EXCEL);
	 * cat.algoritmos.Kosaraju(null);
	 * System.out.println(cat.imprimirConectados(cat.algoritmos
	 * .getComponents())); for (Aerolinea a : cat.aerolineas) {
	 * cat.algoritmos.Kosaraju(a);
	 * System.out.println(cat.imprimirConectados(cat.algoritmos
	 * .getComponents()));
	 * 
	 * }
	 * 
	 * cat.algoritmos.Dijsktra(2, cat.aerolineas.get(27));
	 * System.out.println(Arrays.toString(cat.algoritmos.getEdgeTo()));
	 * 
	 * }
	 */

}
