package estructuras;

import java.util.Iterator;


/**
 * Estructura lineal indexada.
 * @param <T> Tipo del elemento a guardar.
 */
public class List<T> implements Iterable<T>
{
	// -----------------------------------------------------------------
	// Atributos
	// -----------------------------------------------------------------

	/**
	 * Nodo que determina el principio y el fin de la lista. 
	 * El elemento siguente a �l es el primero y el anterior a �l el �ltimo.
	 */
	private NodeWithPrevious<T> flag;

	/**
	 * Tama�o de la lista.
	 */
	private int size;

	// -----------------------------------------------------------------
	// Constructores
	// -----------------------------------------------------------------

	/**
	 * Crea una lista vac�a.
	 */
	public List() 
	{
		flag = new NodeWithPrevious<>();
		flag.setNext(flag);
		flag.setPrevious(flag);
		size = 0;
	}

	// -----------------------------------------------------------------
	// M�todos
	// -----------------------------------------------------------------

	/**
	 * Retorna el tama�o de la lista.
	 * @return tama�o de la lista.
	 */
	public int size(){
		return size;
	}

	/**
	 * Determina si la lista est� vacia.
	 * @return true si est� vac�a, false de lo contrario.
	 */
	public boolean isEmpty(){
		return size == 0;
	}

	/**
	 * A�ade un elemento al final de la lista.
	 * @param item Elemento a a�adir.
	 */
	public void add(T item)
	{
		NodeWithPrevious<T> last = flag.getPrevious();
		NodeWithPrevious<T> node = new NodeWithPrevious<>(item, flag, last);
		last.setNext(node);
		flag.setPrevious(node);
		size++;
	}

	/**
	 * Retorna el nodo en la posici�n pedida.
	 */
	private NodeWithPrevious<T> getTo(int pos)
	{
		NodeWithPrevious<T> actual = flag;
		if(pos > size/2){
			for(int i = size; i > pos; i--)
				actual = actual.getPrevious();
		}
		else{
			for(int i = -1; i < pos; i++)
				actual = actual.getNext();
		}
		return actual;
	}

	/**
	 * Retorna el elemento en la posici�n pedida.
	 * @param pos posici�n del elemento.
	 */
	public T get(int pos)
	{
		if(pos >= size)
			return null;
		return getTo(pos).getItem();
	}

	/**
	 * Remueve el elemento en la posici�n dada por par�metro.
	 * @param pos posici�n del elemento.
	 * @return elemento removido.
	 */
	public T remove(int pos)
	{
		if(pos >= size)
			return null;
		NodeWithPrevious<T> node = getTo(pos);
		node.getNext().setPrevious(node.getPrevious());
		node.getPrevious().setNext(node.getNext());
		size--;
		return node.getItem();
	}

	/**
	 * A�ade el elemento dado en la posici�n pedida.
	 * @param item elemento a a�adir.
	 * @param i posici�n donde este debe quedar.
	 */
	public void insert(T item, int pos)
	{
		if(pos == size)
			add(item);
		else if(pos < size){
			NodeWithPrevious<T> next = getTo(pos);
			NodeWithPrevious<T> previous = next.getPrevious();
			NodeWithPrevious<T> node = new NodeWithPrevious<>(item, next, previous);
			next.setPrevious(node);
			previous.setNext(node);
			size++;
		}
	}
	
	/**
	 * Cambia dos elementos en la lista.
	 */
	public void swap(int i, int j)
	{
		T temp = remove(i);
		insert(remove(j<i? j : j-1), i);
		insert(temp, j);
	}

	/**
	 * Recorre la lista.
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private NodeWithPrevious<T> actual = flag;

			@Override
			public boolean hasNext() {
				return actual.getNext() != flag;
			}

			@Override
			public T next() {
				actual = actual.getNext();
				return actual.getItem();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	
	@Override
	public String toString() {
		String ans = "[";
		for(T item : this){
			ans += item + ",";
		}
		ans += "]";
		return ans;
	}

}