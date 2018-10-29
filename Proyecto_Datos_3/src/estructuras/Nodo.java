package estructuras;

/**
 * Interface que representa un nodo de un grafo.
 * @param K tipo del identificador de los vertices
 *
 */
public interface Nodo<K extends Comparable<K>> extends Comparable<Nodo<K>> {
	
	/**
	 * Identificador único del nodo
	 * @return id
	 */
	public K darId();
	

}
