package mundo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Vuelo {
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Número del vuelo.
	 */
	private int numero;

	/**
	 * Aerolinea a la cual pertenece el vuelo.
	 */
	private Aerolinea aerolinea;

	/**
	 * Ciudad de la cual proviene el vuelo.
	 */
	private Ciudad ciudadOrigen;

	/**
	 * Ciudad a la cual se dirige el vuelo.
	 */
	private Ciudad ciudadDestino;

	/**
	 * Hora de salida del vuelo.
	 */
	private Calendar horaDeSalida;

	/**
	 * Hora de llegada del vuelo.
	 */
	private Calendar horaDeLlegada;

	/**
	 * Tipo de avión del vuelo.
	 */
	private String tipoDeAvion;

	/**
	 * Cupo del vuelo a realizarse.
	 */
	private int cupo;

	/**
	 * Días de la semana en los que se realiza el vuelo.
	 */
	private DayOfWeek[] diasDeOperacion;

	/**
	 * Construye un nuevo vuelo.
	 */
	public Vuelo(int numero, Aerolinea aerolinea, Ciudad ciudadOrigen,
			Ciudad ciudadDestino, Calendar horaDeSalida,
			Calendar horaDeLlegada, String tipoDeAvion, int cupo,
			DayOfWeek[] diasDeOperacion) {
		super();
		this.numero = numero;
		this.aerolinea = aerolinea;
		this.ciudadOrigen = ciudadOrigen;
		this.ciudadDestino = ciudadDestino;
		this.horaDeSalida = horaDeSalida;
		this.horaDeLlegada = horaDeLlegada;
		this.tipoDeAvion = tipoDeAvion;
		this.cupo = cupo;
		this.diasDeOperacion = diasDeOperacion;
	}

	/**
	 * Retorna el número del vuelo.
	 */
	public int darNumero() {
		return numero;
	}

	/**
	 * Retorna la aerolinea a la cual pertenece el vuelo.
	 */
	public Aerolinea darAerolinea() {
		return aerolinea;
	}

	/**
	 * Retorna la ciudad de la cual proviene el vuelo.
	 */
	public Ciudad darCiudadOrigen() {
		return ciudadOrigen;
	}

	/**
	 * Retorna la ciudad a la cual se dirige el vuelo.
	 */
	public Ciudad darCiudadDestino() {
		return ciudadDestino;
	}

	/**
	 * Retorna la hora de salida del vuelo.
	 */
	public Calendar darHoraDeSalida() {
		return horaDeSalida;
	}

	/**
	 * Retorna la hora de llegada del vuelo.
	 */
	public Calendar darHoraDeLlegada() {
		return horaDeLlegada;
	}

	/**
	 * Retorna el tipo de avión del vuelo.
	 */
	public String darTipoDeAvion() {
		return tipoDeAvion;
	}

	/**
	 * Retorna el cupo del vuelo a realizarse.
	 */
	public int darCupo() {
		return cupo;
	}

	public long darDuracion() {
		long temp = horaDeLlegada.getTimeInMillis()
				- horaDeSalida.getTimeInMillis();

		return TimeUnit.MILLISECONDS.toMinutes(temp);
	}

	private DayOfWeek dia;

	public static long darEscala(Vuelo v1, Vuelo v2, DayOfWeek dia){
		long temp = v2.horaDeLlegada.getTimeInMillis() - v1.horaDeSalida.getTimeInMillis();
		temp = TimeUnit.MILLISECONDS.toMinutes(temp);

		boolean contains = false;
		while(!contains){
			for(DayOfWeek d : v2.diasDeOperacion){
				if(d == dia){
					contains = true;
					break;
				}
			}
			if(!contains){
				temp += 24*60;
				dia = DayOfWeek.next(dia);
			}
		}
		v2.dia = dia;
		return temp;
	}

	public DayOfWeek darDia(){
		return dia;
	}

	/**
	 * Retorna los días de la semana en los que se realiza el vuelo.
	 */
	public DayOfWeek[] getDiasDeOperacion() {
		return diasDeOperacion;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vuelo other = (Vuelo) obj;
		if (numero != other.numero)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ciudadOrigen + ", " + ciudadDestino + ", " + aerolinea + ", "
				+ numero + ", " + SimpleDateFormat.getTimeInstance().format(horaDeSalida.getTime()) + ", " + darDuracion();
	}
}
