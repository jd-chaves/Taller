package estructuras;

public class NodeWithPrevious<T> extends Node<T> 
{
	protected NodeWithPrevious<T> previous;
	
  
	public NodeWithPrevious() {
		super();
		previous = null;
	}


	public NodeWithPrevious(T item, NodeWithPrevious<T> next, NodeWithPrevious<T> previous) 
	{
		super(item, next);
		this.previous = previous;
	}

 
	public NodeWithPrevious<T> getPrevious() {
		return previous;
	}

	public void setPrevious(NodeWithPrevious<T> previous) {
		this.previous = previous;
	}
	
	@Override
	public NodeWithPrevious<T> getNext(){
		return (NodeWithPrevious<T>) super.getNext();
	}
	
	@Override
	public void setNext(Node<T> next) {
		if(next instanceof NodeWithPrevious)
			super.setNext(next);
	}
}