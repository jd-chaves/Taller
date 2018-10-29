package Cliente;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import mundo.Aerolinea;
import mundo.CatalogoDeVuelos;
import mundo.Ciudad;
import mundo.DayOfWeek;
import mundo.DigraphAlgorithms;

/**
 *
 * @author Samuel S. Salazar, Gustavo Alegria, Fernando De la Rosa
 */
public class Proyecto3Cliente {

	private static Scanner in = new Scanner(System.in);

	private CatalogoDeVuelos mundo;

	/**
	 * *************************************************************************
	 * ************************** OPCIONES DEL MENU
	 * *************************************************************************
	 * ************************** - N: Numero total de opciones - Las opciones
	 * se organizan en un arreglo de entradas que van de 0..N-1 - La opcion i
	 * (0..N-1) corresponde al requerimiento i+1 del menu. A continuacion se
	 * describe cada dato de entrada para cada requerimiento i (1..N-1):
	 * opciones[i][0] = Nombre de la opcion/requerimiento +se define j como un
	 * entero que va de 1...p donde p es el numero de parametros de la opcion. +
	 * Estos parametros (de tipo String) seran pasados en forma de arreglo al
	 * metodo que resuelve el requerimiento + El metodo que resuelve el
	 * requerimiento debe llamarse req<i>(String[] params) opciones[i][j] =
	 * entrada/parametro j de la opcion (con j > 0)
	 * 
	 */
	private static String[][] opciones = {
			// Req 1. Caso 1. En caso que se quiera leer al principio los vuelos
			// del catalogo (definidos en archivo) usar la siguiente definicion:
			{ "req1", "Cargar un catalogo de vuelos desde archivo de datos", "ruta archivo" }, // R1
			// Req 1. Caso 2. En caso que se quiera iniciar el catalogo de
			// vuelos vacio usar la siguiente definicion:
			// {"req1","Crear un catalogo de vuelos (vacio)"}, //R1
			{ "req2", "Agregar una aerolinea al catalogo de vuelos", "Nombre aerolinea" }, // R2
			{ "req3", "Eliminar una aerolinea del catalogo de vuelos", "Nombre aerolinea" }, // R3
			{ "req4", "Agregar y eliminar ciudades autorizadas para realizar vuelos autorizados", "Agregar o Eliminar?",
					"Nombre Ciudad" }, // R4
			{ "req5", "Agregar un vuelo al catalogo de vuelos", "# de vuelo", "Aerolinea", "Ciudad origen",
					"Ciudad destino", "Hora de salida", "Hora de llegada", "Tipo de avion", "Cupo del vuelo",
					"Dias de operacion" }, // R5
			{ "req6", "Calcular y actualizar las tarifas de los vuelos" },
			{ "req7",
					"Informar los conjuntos de ciudades que se pueden comunicar entre si pero que no tienen comunicacion con el resto del pais sin importar las aerolinea" },
			{ "req8",
					"Informar los conjuntos de ciudades que se pueden comunicar entre si pero que no tienen comunicacion con el resto del pais para cada aerolinea" },
			{ "req9",
					"Calcular e imprimir el MST para vuelos nacionales, a partir de una ciudad especifica, utilizando el tiempo del vuelo como peso de los arcos",
					"Ciudad origen" },
			{ "req10",
					"Calcular e imprimir el MST para vuelos nacionales de una aerolinea particular, a partir de una ciudad especifica, utilizando el costo de los vuelos como peso de los arcos",
					"Nombre Aerolinea", "Ciudad origen" },
			{ "req11",
					"Calcular e imprimir el MST  a partir de una ciudad especifica y de un dia particular, sin importar cambios de aerolinea en el viaje",
					"Ciudad origen", "Dia partida" },
			{ "req12", "Calcular e imprimir el itinerario de costo minimo para cada aerolinea", "Ciudad origen",
					"Ciudad destino", "Dia partida" },
			{ "req13", "Calcular e imprimir el itinerario de costo minimo para diferentes aerolineas", "Ciudad origen",
					"Ciudad destino", "Dia partida" },
			{ "req14",
					"Calcular e imprimir la ruta de costo minimo para ir a todas las otras ciudades cubiertas por una aerolinea",
					"Ciudad", "Nombre aerolinea" },
			{ "req15",
					"Calcular e imprimir la ruta de menor tiempo para ir a todas las otras ciudades cubiertas por una aerolinea",
					"Ciudad", "Nombre aerolinea" },

			// TODO Agregar los requerimientos opcionales en caso de querer
			// realizar el bono
			// Nota: los metodos req16, ..., req20 ya aparecen definidos y falta
			// completar segun la documentacion

			{ "exit", "Salir" } };

	/**
	 * Constructor del cliente que prueba los requerimientos del proyecto 3
	 */
	public Proyecto3Cliente() {
		mundo = new CatalogoDeVuelos();
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) throws Exception {
		Proyecto3Cliente cli = new Proyecto3Cliente();

		// Ciclo de interaccion x consola: despliegue opciones, seleccion de
		// opcion, lectura de datos necesarios, ejecucion del metodo respectivo,
		// tiempo de ejecucion
		while (true) {
			String[] opc = menu();
			if (opc != null) {
				try {
					long start = System.currentTimeMillis();
					Method metodo = cli.getClass().getMethod(opc[0], String[].class);
					metodo.invoke(cli, new Object[] { Arrays.copyOfRange(opc, 2, opc.length) });
					System.out.println(
							String.format(">>Tiempo de ejecucion: %d ms ", System.currentTimeMillis() - start));
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(">>Ocurrio un error\n>>Detalles: " + e.getMessage());
				}
			}
		}
	}

	/**
	 * Despliega las opciones del API, lee una opcion y sus datos de entrada
	 * (tipo String)
	 * 
	 * @return String compuesto con el nombre del metodo que resuelve el
	 *         requerimiento y sus datos de entreda
	 */
	private static String[] menu() {
		System.out.println(
				"---------------------------------------------------------------------------------------------");
		System.out.println(
				"------------------------------ API AERONAUTICA CIVIL ----------------------------------------");
		System.out.println(
				"---------------------------------------------------------------------------------------------");

		for (int i = 0; i < opciones.length; i++) {
			System.out.println(String.format("[%d] %s", i + 1, opciones[i][1]));

		}
		int opc = in.nextInt();
		// Revision de opcion invalida
		if (opc < 1 || opc > opciones.length) {
			System.out.println(">> Opcion invalida");
			return null;
		}

		// definir la informacion del metodo que resuelve el requerimiento con
		// sus datos de entrada (parametros)
		String[] rta = new String[opciones[opc - 1].length];
		rta[0] = opciones[opc - 1][0]; // identificador metodo del requerimiento
		rta[1] = Integer.toString(opc); // numero de la opcion seleccionada
		in.nextLine();
		// lectura de los parametros necesarios para el requerimiento
		for (int i = 2; i < opciones[opc - 1].length; i++) {
			System.out.println(">>" + opciones[opc - 1][i]);
			rta[i] = in.nextLine();
		}
		return rta;
	}

	/**
	 * Metodo de terminacion del cliente
	 * 
	 * @param params
	 *            (ninguno)
	 */
	public void exit(String[] params) {
		System.out.println("exit: parametros" + Arrays.toString(params));
		System.out.println(">> Adios");
		System.exit(0);
	}

	/**
	 * Metodo encargar de crear (caso 1) o cargar (caso 2) un catalogo de vuelos
	 * 
	 * @param params
	 */
	public void req1(String[] params) {
		System.out.println("Req1: parametros" + Arrays.toString(params));
		String ruta = params[0];
		mundo.cargarArchivo(ruta);
	}

	/**
	 * Agrega una aerolinea al catalogo
	 * 
	 * @param params
	 *            params[0] = Nombre de la aerolinea
	 */
	public void req2(String[] params) {
		System.out.println("Req2: parametros" + Arrays.toString(params));
		String nombreAerolinea = params[0];
		mundo.agregarAerolinea(nombreAerolinea);

		int nAerolineas = mundo.darAerolineas().size();
		System.out.println("Numero de aerolineas: " + nAerolineas);

	}

	/**
	 * Elimina una aerolinea del catalogo
	 * 
	 * @param params
	 *            params[0] = Nombre de la aerolinea
	 */
	public void req3(String[] params) {
		System.out.println("Req3: parametros" + Arrays.toString(params));
		String id = params[0];
		mundo.eliminarAerolinea(id);
		int nAerolineas = mundo.darAerolineas().size();
		System.out.println("Numero de aerolineas: " + nAerolineas);

	}

	/**
	 * Agregar y eliminar ciudades autorizadas para realizar vuelos autorizados
	 * 
	 * @param params
	 *            params[0] = Agregar o eliminar una ciudad params[1]= Nombre
	 *            ciudad autorizada
	 */
	public void req4(String[] params) {
		System.out.println("Req4: parametros" + Arrays.toString(params));
		String opcion = params[0];
		String nombreCiudad = params[1];
		if (opcion.equalsIgnoreCase("Agregar"))
			mundo.agregarCiudad(nombreCiudad);
		else if (opcion.equalsIgnoreCase("Eliminar"))
			mundo.eliminarCiudad(nombreCiudad);
		else
			System.out.println("Ingrese una opcion válida");

		int nCiudades = 0;
		System.out.println("Numero de ciudades: " + mundo.tamCiudad());

	}

	/**
	 * Agregar un vuelo al catalogo de vuelos
	 * 
	 * @param params
	 *            params[0] = # de vuelo params[1]= Aerolinea params[2]= Ciudad
	 *            origen params[3]= Ciudad destino params[4] = Hora de salida
	 *            params[5] = Hora de llegada params[6] = Tipo de avion
	 *            params[7] = Cupo del vuelo params[8] = Dias de operacion
	 */
	public void req5(String[] params) {
		System.out.println("Req5: parametros" + Arrays.toString(params));
		int numVuelo = Integer.parseInt(params[0]);
		Aerolinea aerolinea = mundo.darAerolinea(params[1]);
		if (aerolinea == null) {
			System.out.println("la aerolinea no es válida");
			return;
		}

		String origen = params[2];
		Ciudad or = mundo.darCiudad(origen);
		String destino = params[3];
		Ciudad des = mundo.darCiudad(destino);

		if (or == null) {
			mundo.agregarCiudad(params[1]);
			or = mundo.darCiudad(origen);
		}
		if (des == null) {
			mundo.agregarCiudad(params[2]);
			des = mundo.darCiudad(destino);
		}
		String horaSalida = params[4];
		int a = Integer.parseInt(horaSalida.split(":")[0]);
		int b = Integer.parseInt(horaSalida.split(":")[1]);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, a);
		c.set(Calendar.MINUTE, b);
		String horaLlegada = params[5];
		a = Integer.parseInt(horaLlegada.split(":")[0]);
		b = Integer.parseInt(horaLlegada.split(":")[1]);
		Calendar d = Calendar.getInstance();
		d.set(Calendar.HOUR_OF_DAY, a);
		d.set(Calendar.MINUTE, b);
		String tipoAvion = params[6];
		int cupoVuelo = Integer.parseInt(params[7]);
		String[] diasOperacion = params[8].split(",");
		DayOfWeek[] days = new DayOfWeek[diasOperacion.length];
		for (int i = 0; i < days.length; i++)
			days[i] = DayOfWeek.name(diasOperacion[i]);

		mundo.agregarVuelo(numVuelo, aerolinea, or, des, c, d, tipoAvion, cupoVuelo, days);

		System.out.println("Numero de vuelos: " + mundo.darCantidadVuelos());

	}

	/**
	 * Calcular y actualizar las tarifas de los vuelos
	 * 
	 * @param params
	 */
	public void req6(String[] params) {
		System.out.println("Req6: parametros" + Arrays.toString(params));
	}

	/**
	 * Informar los conjuntos de ciudades que se pueden comunicar entre si pero
	 * que no tienen comunicacion con el resto del pais sin importar las
	 * aerolinea
	 * 
	 * @param params
	 */
	public void req7(String[] params) {
		System.out.println("Req7: parametros" + Arrays.toString(params));
		System.out.println(mundo.darComponentes());
	}

	/**
	 * Informar los conjuntos de ciudades que se pueden comunicar entre si pero
	 * que no tienen comunicacion con el resto del pais para cada aerolinea
	 * 
	 * @param params
	 */
	public void req8(String[] params) {
		System.out.println("Req8: parametros" + Arrays.toString(params));
		for (Aerolinea a : mundo.darAerolineas()) {
			System.out.println(a + ": " + mundo.darComponentesAerolinea(a));
		}
	}

	/**
	 * Calcular e imprimir el MST para vuelos nacionales, a partir de una ciudad
	 * especifica, utilizando como peso de los arcos el tiempo del vuelo
	 * 
	 * @param params
	 *            params[0]= Ciudad origen
	 */
	public void req9(String[] params) {
		System.out.println("Req9: parametros" + Arrays.toString(params));
		String origen = params[0];
		System.out.println(mundo.darMSTCiudad(origen, DigraphAlgorithms.TIEMPO, DayOfWeek.LUNES));
	}

	/**
	 * Calcular e imprimir el MST para vuelos nacionales de una aerolinea
	 * particular, a partir de una ciudad especifica, utilizando como peso de
	 * los arcos el costo de los vuelos
	 * 
	 * @param params
	 *            params[0]= Nombre aerolinea params[1]= Ciudad origen
	 */
	public void req10(String[] params) {
		System.out.println("Req10: parametros" + Arrays.toString(params));
		String aerolinea = params[0];
		String origen = params[1];
		System.out.println(mundo.darMSTAerolinea(aerolinea, origen, DigraphAlgorithms.COSTO));
	}

	/**
	 * Calcular e imprimir el MST a partir de una ciudad especifica y de un dia
	 * particular, sin importar cambios de aerolinea en el viaje
	 * 
	 * @param params
	 *            params[0]= Ciudad origen params[1]= Dia de partida
	 */
	public void req11(String[] params) {
		System.out.println("Req11: parametros" + Arrays.toString(params));
		String origen = params[0];
		String dia = params[1];
		System.out.println(mundo.darMSTCiudad(origen, DigraphAlgorithms.TIEMPO, DayOfWeek.name(dia)));
	}

	/**
	 * Calcular e imprimir el itinerario de costo minimo para cada aerolinea
	 * 
	 * @param params
	 *            params[0]= Ciudad origen params[1]= Ciudad destino params[2]=
	 *            Dia de partida
	 * 
	 */
	public void req12(String[] params) {
		System.out.println("Req12: parametros" + Arrays.toString(params));
		String origen = params[0];
		String destino = params[1];
		String dia = params[2];
		System.out.println(mundo.darMenorItinerarioTodasLasAerolineas(origen, destino, DayOfWeek.name(dia),
				DigraphAlgorithms.COSTO));
	}

	/**
	 * Calcular e imprimir el itinerario de costo minimo para diferentes
	 * aerolineas
	 * 
	 * @param params
	 *            params[0]= Ciudad origen params[1]= Ciudad destino params[2]=
	 *            Dia de partida
	 */
	public void req13(String[] params) {
		System.out.println("Req13: parametros" + Arrays.toString(params));
		String origen = params[0];
		String destino = params[1];
		String dia = params[2];
		System.out.println(mundo.darMenorItinerario(origen, destino, DayOfWeek.name(dia), DigraphAlgorithms.COSTO));
	}

	/**
	 * Calcular e imprimir la ruta de costo minimo para ir a todas las otras
	 * ciudades cubiertas por una aerolinea
	 * 
	 * @param params
	 *            params[0]= Nombre ciudad params[1]= Nombre aerolinea
	 * 
	 */
	public void req14(String[] params) {
		System.out.println("Req14: parametros" + Arrays.toString(params));
		String ciudad = params[0];
		String aerolinea = params[1];
		System.out.println(mundo.darMenorRutaTodaCiudad(ciudad, aerolinea, DigraphAlgorithms.COSTO));
	}

	/**
	 * Calcular e imprimir la ruta de menor tiempo para ir a todas las otras
	 * ciudades cubiertas por una aerolinea
	 * 
	 * @param params
	 *            params[0]= Nombre ciudad params[1]= Nombre aerolinea
	 */
	public void req15(String[] params) {
		System.out.println("Req15: parametros" + Arrays.toString(params));
		String ciudad = params[0];
		String aerolinea = params[1];
		System.out.println(mundo.darMenorRutaTodaCiudad(ciudad, aerolinea, DigraphAlgorithms.TIEMPO_CON_ESCALA));
	}

	/**
	 * Calcular e imprimir la ruta de minimo precio para visitar todas las otras
	 * ciudades cubiertas por una aerolinea
	 * 
	 * @param params
	 *            params[0]= Nombre ciudad params[1]= Dia de la semana
	 *            params[2]= Nombre aerolinea
	 */
	public void req16(String[] params) {
		System.out.println("Req16: parametros" + Arrays.toString(params));
		String ciudad = params[0];
		String dia = params[1];
		String aerolinea = params[2];
		// TODO
		// Completar segun documentacion del requerimiento
		// Mostrar la ruta como {<Ciudad Origen>-<Vuelo>-<Dia>-<Hora>-<Ciudad
		// A>-<Tiempo-Origen-A>, ...,
		// <Ciudad ?>-<Vuelo>-<Dia>-<Hora>-<Ciudad X>-<Tiempo-Origen-X>}

	}

	/**
	 * Buscar la aerolinea, ciudad, dia de la semana y hora para iniciar un
	 * viaje que permita a un viajero visitar la mayor cantidad de ciudades a
	 * minimo costo, en vuelos de una misma aerolinea.
	 * 
	 * @param params
	 */
	public void req17(String[] params) {
		System.out.println("Req17: parametros" + Arrays.toString(params));
		// TODO
		// Completar segun documentacion del requerimiento
		// Mostrar <Aerolinea>, <Ciudad Origen>, <Dia de semana> y <Hora Salida>
		// inicial del viaje
		// Mostrar la ruta como {<Ciudad Origen>-<Vuelo>-<Dia>-<Hora>-<Ciudad
		// A>-<Costo-Origen-A>,
		// <Ciudad A>-<Vuelo>-<Dia>-<Hora>-<Ciudad B>-<Costo-A-B>, ...,
		// <Ciudad X>-<Vuelo>-<Dia>-<Hora>-<Ciudad Ultima>-<Costo-X-Ultima>}
		// Mostrar <Costo total viaje>

	}

	/**
	 * Buscar el dia de la semana y hora para iniciar un viaje que permita a un
	 * viajero visitar la mayor cantidad de ciudades, en vuelos de una aerolinea
	 * preferida.
	 * 
	 * @param params
	 *            params[0]= Nombre aerolinea
	 */
	public void req18(String[] params) {
		System.out.println("Req18: parametros" + Arrays.toString(params));
		String aerolinea = params[0];
		// TODO
		// Completar segun documentacion del requerimiento
		// Mostrar <Ciudad Origen>, <Dia de semana> y <Hora Salida> inicial del
		// viaje
		// Mostrar la ruta como {<Ciudad Origen>-<Vuelo>-<Dia>-<Hora>-<Ciudad
		// A>,
		// <Ciudad A>-<Vuelo>-<Dia>-<Hora>-<Ciudad B>, ...,
		// <Ciudad X>-<Vuelo>-<Dia>-<Hora>-<Ciudad Ultima>}
		// Mostrar <Numero ciudades visitas>

	}

	/**
	 * Buscar el dia de la semana y hora para iniciar un viaje que permita a un
	 * viajero visitar la mayor cantidad de ciudades, con posibilidad de cambios
	 * de aerolinea.
	 * 
	 * @param params
	 */
	public void req19(String[] params) {
		System.out.println("Req19: parametros" + Arrays.toString(params));
		// TODO
		// Completar segun documentacion del requerimiento
		// Mostrar <Ciudad Origen>, <Dia de semana> y <Hora Salida> inicial del
		// viaje
		// Mostrar la ruta como {<Ciudad
		// Origen>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad A>,
		// <Ciudad A>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad B>, ...,
		// <Ciudad X>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad Ultima>}
		// Mostrar <Numero ciudades visitas>, <Costo total viaje>, <Tiempo total
		// en vuelos>

	}

	/**
	 * Buscar la ruta para visitar un conjunto de ciudades dadas bajo las
	 * restricciones definidas. Nota: No se requiere visitar las ciudades en el
	 * orden de ingreso en los parametros
	 * 
	 * @param params
	 *            String ciudad = params[0]; String dia = params[1]; String hora
	 *            = params[2]; String ciudad intermedia 1 = params[3]; ...
	 *            String ciudad intermedia N = params[3+N-1];
	 */
	public void req20(String[] params) {
		System.out.println("Req20: parametros" + Arrays.toString(params));
		String ciudad_Origen = params[0];
		String dia = params[1];
		String hora = params[2];
		// A continuacion deben venir los nombres de las ciudades intermedias a
		// visitar (N)
		String ciudad_I1 = params[3]; // Nombre de ciudad intermedia a visitar
		// ...
		// String ciudad_IN = params[3+N-1]; // Nombre de ciudad intermedia a
		// visitar
		// TODO
		// Completar segun documentacion del requerimiento
		// Mostrar <Ciudad Origen>, <Dia de semana> y <Hora Salida> inicial del
		// viaje
		// Mostrar la ruta como {<Ciudad
		// Origen>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad Intermedia X>,
		// <Ciudad Intermedia X>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad
		// Intermedia ¿?>, ...,
		// <Ciudad Intermedia ¿?>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad
		// intermedia Ultima>,
		// <Ciudad Intermedia Ultima>-<Vuelo>-<Dia>-<Hora>-<Aerolinea>-<Ciudad
		// Origen>}

	}
}
