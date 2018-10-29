package estructuras;

/**
 * Representa un Arco con peso en un grafo.
 * Cada arco consta de un Nodo inicio, un Nodo fin y un costo. Adicionalmente, el Arco puede
 * guardar información adicional en un objeto (tipo E).
 * @param K tipo del identificador de los vertices (comparable)
 * @param E tipo de la informacion asociada a los arcos
 */
public class Arco<K extends Comparable<K>,E> implements Comparable<Arco<K, E>>{

	@Override
	public String toString() {
		return inicio.darId()+" -> "+fin.darId();
	}

	/**
	 * Costo de ir de nodo inicio a nodo fin
	 */
	private double costo;

	/**
	 * Informacion adicional que se puede guardar en el arco
	 */
	//actualizacion
	private E obj;

	/**
	 * Nodo inicio 
	 */
	private Nodo<K> inicio;

	/**
	 * Nodo fin
	 */
	private Nodo<K> fin;


	/**
	 * Construye un nuevo arco desde un nodo inicio hasta un nodo fin
	 * con un peso dado e información adicional. 
	 * @param inicio el nodo inicial del arco
	 * @param fin el nodo final del arco
	 * @param costo Costo del arco
	 * @param obj Información adicional que se desea guardar
	 */
	public Arco(Nodo<K> inicio, Nodo<K> fin, double costo, E obj) {
		this.costo = costo;
		this.obj = obj;
		this.inicio = inicio;
		this.fin = fin;
	}

	/**
	 * Construye un nuevo arco desde un nodo inicio hasta un nodo fin.
	 * @param inicio Nodo inicial del arco.
	 * @param fin Nodo final del arco.
	 * @param costo Costo del arco. 
	 */
	public Arco(Nodo<K> inicio, Nodo<K> fin, double costo) {
		this.costo = costo;
		this.inicio = inicio;
		this.fin = fin;
	}


	/**
	 * Devuelve el nodo inicio del arco
	 * @return Nodo inicio
	 */
	public Nodo<K> darNodoInicio() {
		return inicio;
	}

	/**
	 * Devuelve el nodo final del arco
	 * @return Nodo fin
	 */
	public Nodo<K> darNodoFin() 
	{
		return fin;
	}

	/**
	 * Devuelve el costo del arco
	 * @return costo
	 */
	public double darCosto() {
		return costo;
	}

	/**
	 * Asigna un objeto como informacion adicional asociada al arco
	 * @param info Objeto (tipo E) que se desea guardar como información adicional
	 * @return este arco con la información adicional asignada
	 */
	public  Arco<K,E> asignarInformacion(E info) {
		obj = info; 
		return this;
	}

	/**
	 * Devuelve la información adicional asociada al arco
	 * @return objeto (tipo E) asociado como información adicional
	 */
	public 	E darInformacion() {
		return obj;
	}

	public int compareTo(Arco<K,E> o) 
	{
		int comp = Double.compare(o.costo, costo);
		if(comp==0)
			comp+=	o.inicio.compareTo(inicio) +  o.fin.compareTo(fin) ;
		return comp;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		if (fin == null) {
			if (other.fin != null)
				return false;
		} else if (!fin.equals(other.fin))
			return false;
		if (inicio == null) {
			if (other.inicio != null)
				return false;
		} else if (!inicio.equals(other.inicio))
			return false;
		return true;
	}
	
	
}