package estructuras;

/**
 * Representa las funcionalidades basicas que debe implementar un grafo.
 * @param K tipo del identificador de los vertices (Comparable)
 * @param E tipo de la informacion asociada a los arcos
 */
public interface Grafo<K extends Comparable<K>, E>
{
	/**
	 * Devuelve el nodo cuyo id coincide con el dado por parametro.
	 * @param id El identificador unico del nodo que se desea buscar
	 * @return Nodo que coincide con el id  o null si no hay coincidencias
	 */
	public Nodo<K> buscarNodo(K id);
	
	/**
	 * Retorna todos los Arcos salientes (sucesores) del nodo con id dado.  
	 * @param id Identificador unico del nodo
	 * @return Arreglo con los arcos con los nodos adyacentes al nodo con id dado.
	 */
	public Arco<K,E>[] darArcosOrigen(K id);
	
	/**
	 * Retorna todos los Arcos entrantes (predecesores) del nodo con id dado.  
	 * @param id Identificador unico del nodo
	 * @return Arreglo con los arcos entrantes al nodo con id dado.
	 */
	public Arco<K,E>[] darArcosDestino(K id);
	
	/**
	 * Agrega un nodo al grafo
	 * @param nodo Nodo que se quiere agregar
	 */
	public void agregarNodo(Nodo<K> nodo);
	
	/**
	 * Agrega un arco desde un nodo inicio a un nodo fin con un costo dado 
	 * y un objeto como información adicional asociada
	 * @param inicio Id del nodo inicio
	 * @param fin Id del nodo final
	 * @param costo Costo de ir del nodo inicio al nodo fin
	 * @param obj Informacion adicional asociada al arco
	 * @return true si el arco fue agregado, false de lo contrario.
	 */
	public boolean agregarArco(K inicio, K fin, double costo, E obj);
	
	/**
	 * Agrega un arco desde un nodo inicio a un nodo fin con un costo dado.
	 * @param inicio Id del nodo inicio
	 * @param fin Id del nodo final
	 * @param costo Costo de ir del nodo inicio al nodo fin
	 * @return true si el arco fue agregado, false de lo contrario.
	 */
	public boolean agregarArco(K inicio, K fin, double costo);
	
	/**
	 * Elimina el nodo con el identificador dado y todos los arcos relacionados con el nodo
	 * @param id El identificador del nodo a eliminar
	 * @return True si el nodo fue eliminado, false de lo contrario
	 */
	public boolean eliminarNodo(K id);
	
	/**
	 * Elimina el arco que conecta el nodo inicio con el nodo fin
	 * @param inicio Id del nodo inicial
	 * @param fin Id del nodo final
	 * @return El arco que fue eliminado, null si no se elimino ninguno
	 */
	public Arco<K,E> eliminarArco(K inicio, K fin);
	
	/**
	 * Devuelve un arreglo con todos los arcos del grafo
	 * @return Arreglo de arcos
	 */
	public Arco<K,E>[] darArcos();
	
	/**
	 * Devuelve un arreglo con todos los nodos del grafo
	 * @return Arreglo de nodos
	 */
	public Nodo<K>[] darNodos();
}