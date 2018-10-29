package estructuras;

import java.util.Iterator;

/**
 * Estructura lineal con política LIFO.
 * @param <T> Tipo del elemento a guardar.
 */
public class Stack<T> implements Iterable<T>
{
    // -----------------------------------------------------------------
    // Atributos.
    // -----------------------------------------------------------------
	
	/**
	 * Tope de la pila.
	 */
	private Node<T> top;
	
	/**
	 * Tamaño de la pila.
	 */
	private int size;
	
    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------
	
	/**
	 * Construye una pila vacía.
	 */
	public Stack() 
	{
		top = null;
		size = 0;
	}
	
    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------
	
	/**
	 * Retorna el tamaño de la pila.
	 * @return Tamaño de la pila.
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Determina si la pila está vacía.
	 * @return true si está vacía, false si no.
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	/**
	 * Añade un elemento al tope de pila.
	 * @param item elemento a añadir.
	 */
	public void push(T item)
	{
		size++;
		top = new Node<T>(item, top);
	}
	
	/**
	 * Muestra el elemento en el tope de la pila.
	 * @return elemento tope.
	 */
	public T peek()
	{
		if(top == null)
			return null;
		return top.getItem();
	}
	
	/**
	 * Remueve el elemento al tope de la pila.
	 * @return elemento removido.
	 */
	public T pop()
	{
		if(top == null)
			return null;
		Node<T> temp = top;
		top = top.getNext();
		size--;
		return temp.getItem();
	}

	/**
	 * Recorre la lista de arriba hacia abajo.
	 */
	public Iterator<T> iterator() 
	{
		return new Iterator<T>() {
			private Node<T> actual = top;
			
			public boolean hasNext() {
				return actual != null;
			}

			public T next() {
				T temp = actual.getItem();
				actual = actual.getNext();
				return temp;
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
