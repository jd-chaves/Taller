package mundo;

import estructuras.Nodo;

public class Ciudad implements Nodo<Integer>

{
	/**
	 * Nombre de la ciudad.
	 */
	private String nombre;

	/**
	 * Identificador de la ciudad en el sistema.
	 */
	private int id;

	public Ciudad(String nombre, int id) {
		this.nombre = nombre;
		this.id = id;
	}

	/**
	 * Retorna el nombre de la ciudad.
	 * 
	 * @return Nombre de la ciudad.
	 */
	public String darNombre() {
		return nombre;
	}

	public int compareTo(Nodo<Integer> o) {
		return Integer.compare(id, o.darId());
	}

	public Integer darId() {
		return id;
	}

	public void cambiarId(int id) {
		this.id = id;
	}

	public String toString() {
		return nombre;
	}

}
