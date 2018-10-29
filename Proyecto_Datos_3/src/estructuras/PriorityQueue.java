package estructuras;
/**
 * Interfaz de un heap binario genérico.
 * @author Diego
 */
public interface PriorityQueue<T> {

	/**
	 * Agrega un elemento al heap
	 */
	public void add(T elemento);
	
	/**
	 * Retorna pero no remueve el elemento máximo/mínimo del heap.
	 * @return T elemento 
	 */
	public T peek();
	
	/**
	 * Retorna el elemento máximo/mínimo luego de removerlo del heap.
	 * @return T El elemento máximo/mínimo del heap
	 */
	public T poll();
	
	/**
	 * Retorna el número de elementos en el heap
	 * @return size Número de elementos en el heap
	 */
	public int size();
	
	/**
	 * Retorna true si el heap no tiene elementos; false de lo contrario.
	 * @return
	 */
	public boolean isEmpty();
}
