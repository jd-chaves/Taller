package mundo;
/**
 * Clase que contiene la informaci�n de una aerolinea.
 */
public class Aerolinea 
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

	/**
	 * Nombre de la aerolinea.
	 */
	private String nombre;
	
	/**
	 * Valor de un minuto de vuelo.
	 */
	private double tarifaPorMinuto;
	
	/**
	 * N�mero m�ximo de sillas de referencia para el c�lculo de la tarifa.
	 */
	private int maxSillas;

	
    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

	/**
	 * Construye una nueva aerolinea
	 * @param nombre Nombre de la aerolinea.
	 * @param tarifaPorMinuto Tarifa por minuto.
	 * @param maxSillas N�mero m�ximo de sillas.
	 */
	public Aerolinea(String nombre, double tarifaPorMinuto, int maxSillas) {
		super();
		this.nombre = nombre;
		this.tarifaPorMinuto = tarifaPorMinuto;
		this.maxSillas = maxSillas;
	}
	
    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------
	
	/**
	 * Retorna el nombre de la aerolinea.
	 * @return Nombre de la aerolinea.
	 */
	public String darNombre() {
		return nombre;
	}
	
	/**
	 * Retorna la tarifa por minuto de la aerolinea.
	 * @return Tarifa por minuto.
	 */	
	public double darTarifaPorMinuto() {
		return tarifaPorMinuto;
	}
	
	/**
	 * Retorna el n�mero m�ximo de sillas de la aerolinea.
	 * @return N�mero m�ximo de sillas.
	 */
	public int darMaxSillas() {
		return maxSillas;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aerolinea other = (Aerolinea) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
