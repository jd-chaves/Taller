package estructuras;
public class Node<T> 
{
   
	private T item;
	

	protected Node<T> next;
	
 
	public Node(){
		item = null;
		next = null;
	}

	public Node(T item, Node<T> next) 
	{
		this.item = item;
		this.next = next;
	}
	
    
	
	public T getItem() {
		return item;
	}
	

	public Node<T> getNext() {
		return next;
	}
	

	public void setNext(Node<T> next) {
		this.next = next;
	}

}