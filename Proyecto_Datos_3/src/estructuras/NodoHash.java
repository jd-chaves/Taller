package estructuras;

public class NodoHash<K,V> {

	private K llave;
	private V valor;
	private NodoHash<K,V> next;
	
	public NodoHash(K llave, V valor) {
		super();
		this.llave = llave;
		this.valor = valor;
		this.next = null;
	}
	
	public NodoHash<K, V> getNext() {
		return next;
	}

	public void setNext(NodoHash<K, V> next) {
		this.next = next;
	}

	public K getLlave() {
		return llave;
	}
	
	public void setLlave(K llave) {
		this.llave = llave;
	}
	
	public V getValor() {
		return valor;
	}
	
	public void setValor(V valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "{" + llave + " : " + valor + "}"  + (next != null? "-> " + next : "");
	}
	
	
}