package estructuras;

import java.util.ArrayList;
import java.util.Arrays;

public class ChainingHashTable<K, V> {

	/**
	 * Factor de carga actual de la tabla (porcentaje utilizado).
	 */
	private double factorCarga;

	/**
	 * Factor de carga maximo que soporta la tabla.
	 */
	private double factorCargaMax;

	/**
	 * Estructura que soporta la tabla.
	 */
	private NodoHash<K, V>[] entries;

	/**
	 * La cuenta de elementos actuales.
	 */
	private int size;

	/**
	 * La capacidad actual de la tabla. Tama�o del arreglo fijo.
	 */
	private int capacidad;

	// Constructores

	public ChainingHashTable() {
		this(17, 3);
	}

	@SuppressWarnings("unchecked")
	public ChainingHashTable(int capacidad, float factorCargaMax) {
		this.size = 0;
		this.factorCarga = 0;
		this.factorCargaMax = factorCargaMax;
		this.capacidad = capacidad;
		this.entries = new NodoHash[capacidad];
	}

	@SuppressWarnings("unchecked")
	public void put(K llave, V valor) {
		int index = hash(llave);
		NodoHash<K, V> nodo = entries[index];
		if (nodo == null) {
			entries[index] = new NodoHash<>(llave, valor);
		} else {
			while (nodo.getNext() != null) {
				if (nodo.getLlave().equals(llave)) {
					nodo.setValor(valor);
					return;
				}
				nodo = nodo.getNext();
			}
			if (nodo.getLlave().equals(llave)) {
				nodo.setValor(valor);
				return;
			}
			nodo.setNext(new NodoHash<>(llave, valor));
		}
		size++;
		factorCarga = ((double) size) / capacidad;

		if (factorCarga > factorCargaMax) {
			K[] keys = getKeys((K[]) new Object[0]);
			ArrayList<V> values = new ArrayList<>();
			for (K key : keys)
				values.add(delete(key));

			capacidad = 7 * capacidad + 2;
			factorCarga = ((double) size) / capacidad;
			entries = new NodoHash[capacidad];
			for (int i = 0; i < keys.length; i++) {
				put(keys[i], values.get(i));
			}
		}

	}

	public K[] getKeys(K[] dummy) {
		K[] keys = Arrays.copyOf(dummy, size);
		int i = 0;
		for (NodoHash<K, V> node : entries) {
			while (node != null) {
				keys[i++] = node.getLlave();
				node = node.getNext();
			}
		}
		return keys;
	}

	public V get(K llave) {
		if (llave == null)
			return null;
		NodoHash<K, V> nodo = entries[hash(llave)];
		while (nodo != null && !nodo.getLlave().equals(llave))
			nodo = nodo.getNext();
		return nodo != null ? nodo.getValor() : null;
	}

	public V delete(K llave) {
		if (llave == null)
			return null;
		int pos = hash(llave);
		NodoHash<K, V> nodo = entries[pos];
		if (nodo == null)
			return null;
		V resp = null;
		if (nodo.getLlave().equals(llave)) {
			resp = nodo.getValor();
			entries[pos] = nodo.getNext();
		} else {
			while (nodo.getNext() != null
					&& !nodo.getNext().getLlave().equals(llave))
				nodo = nodo.getNext();
			if (nodo.getNext() == null)
				return null;
			resp = nodo.getNext().getValor();
			nodo.setNext(nodo.getNext().getNext());
		}
		size--;
		factorCarga = ((double) size) / capacidad;
		return resp;
	}

	public int size() {
		return size;
	}

	@SuppressWarnings("unchecked")
	public NodoHash<K, V>[] getEntries() {
		ArrayList<NodoHash<K, V>> resp = new ArrayList<>();
		for (int i = 0; i < entries.length; i++) {
			if (entries[i] != null) {
				resp.add(entries[i]);
			}
		}
		NodoHash<K, V>[] resp1 = new NodoHash[resp.size()];
		for (int i = 0; i < resp1.length; i++) {
			resp1[i] = resp.get(i);
		}

		return resp1;
	}

	public List<V> getValues() {
		List<V> resp = new List<>();
		for (int i = 0; i < entries.length; i++) {
			NodoHash<K, V> temp = entries[i];
			if (temp != null) {
				do {
					resp.add(temp.getValor());
					temp = temp.getNext();
				} while (temp != null);
			}
		}
		return resp;
	}

	// Hash
	private int hash(K llave) {
		return (llave.hashCode() & 0x7fffffff) % capacidad;
	}

	@Override
	public String toString() {
		return Arrays.toString(entries);
	}

}