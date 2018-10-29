package estructuras;


/**
 * Clase que representa un grafo no dirigido con pesos en los arcos.
 * 
 * @param K
 *            tipo del identificador de los vertices (Comparable)
 * @param E
 *            tipo de la informacion asociada a los arcos
 */
public class Digraph<K extends Comparable<K>, E> implements Grafo<K, E> {

	/**
	 * Nodos del grafo
	 */
	private ChainingHashTable<K, Nodo<K>> nodos;

	/**
	 * Lista de adyacencia
	 */
	private ChainingHashTable<K, List<Arco<K, E>>> adj;

	/**
	 * Construye un grafo no dirigido vacio.
	 */
	public Digraph() {
		adj = new ChainingHashTable<K, List<Arco<K, E>>>();
		nodos = new ChainingHashTable<>();
	}

	public int darCantidadArcos()
	{
		return adj.size();
	}
	public Digraph<K, E> reverse() {
		Arco<K, E>[] temp = darArcos();
		Digraph<K, E> ans = new Digraph<K, E>();
		Nodo<K>[] temp1 = darNodos();

		for (Nodo<K> n : temp1) {
			if (n != null)
				ans.agregarNodo(n);
		}
		for (Arco<K, E> a : temp) {
			ans.agregarArco(a.darNodoFin().darId(), a.darNodoInicio().darId(),
					a.darCosto(), a.darInformacion());
		}
		return ans;
	}

	@Override
	public void agregarNodo(Nodo<K> nodo) {
		nodos.put(nodo.darId(), nodo);
		adj.put(nodo.darId(), new List<Arco<K, E>>());
	}

	@Override
	public boolean eliminarNodo(K id) {
		if (nodos.delete(id) == null)
			return false;
		List<Arco<K, E>> temp = adj.get(id);
		if (temp != null) {
			for (int i = 0; i < temp.size(); i++) {

				if (temp.get(i).darNodoInicio().darId().equals(id)) {
					temp.remove(i);
					i--;
				}
			}
		}

		NodoHash<K, List<Arco<K, E>>>[] temp1 = adj.getEntries();
		for (NodoHash<K, List<Arco<K, E>>> n : temp1) {
			List<Arco<K, E>> temp2 = n.getValor();
			for (int i = 0; i < temp2.size(); i++) {

				if (temp2.get(i).darNodoFin().darId().equals(id)) {
					temp2.remove(i);
					i--;
				}
			}

		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Arco<K, E>[] darArcos() {
		List<List<Arco<K, E>>> temp = adj.getValues();
		int tam = 0;

		for (List<Arco<K, E>> l : temp) {
			tam += l.size();
		}

		Arco<K, E>[] resp = new Arco[tam];
		int cont = 0;
		for (List<Arco<K, E>> l : temp) {
			for (Arco<K, E> a : l) {
				resp[cont++] = a;
			}
		}
		return resp;
	}

	private Arco<K, E> crearArco(K inicio, K fin, double costo, E e) {
		Nodo<K> nodoI = buscarNodo(inicio);
		Nodo<K> nodoF = buscarNodo(fin);
		if (nodoI != null && nodoF != null) {
			return new Arco<K, E>(nodoI, nodoF, costo, e);
		}
		{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Nodo<K>[] darNodos() {
		List<Nodo<K>> resp = nodos.getValues();
		Nodo<K>[] resp1 = new Nodo[resp.size()];
		for (int i = 0; i < resp1.length; i++) {
			resp1[i] = resp.get(i);
		}
		return resp1;
	}

	@Override
	public boolean agregarArco(K inicio, K fin, double costo, E obj) {
		Arco<K, E> arco = crearArco(inicio, fin, costo, obj);
		if (arco == null)
			return false;
		List<Arco<K, E>> list = adj.get(arco.darNodoInicio().darId());
		if (list == null) {
			List<Arco<K, E>> list1 = new List<>();
			list1.add(arco);
			adj.put(arco.darNodoInicio().darId(), list1);
			return true;
		}
		adj.get(arco.darNodoInicio().darId()).add(arco);
		return true;
	}

	@Override
	public boolean agregarArco(K inicio, K fin, double costo) {
		return agregarArco(inicio, fin, costo, null);
	}

	@Override
	public Arco<K, E> eliminarArco(K inicio, K fin) {
		Arco<K, E> arco = crearArco(inicio, fin, 0, null);
		List<Arco<K, E>> list = adj.get((K) arco.darNodoInicio().darId());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(arco)) {
				return list.remove(i);
			}
		}
		return null;
	}

	@Override
	public Nodo<K> buscarNodo(K id) {
		return nodos.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Arco<K, E>[] darArcosOrigen(K id) {
		List<Arco<K, E>> temp = adj.get(id);
		if(temp == null)
			return null;
		Arco<K, E>[] resp = new Arco[temp.size()];
		for (int i = 0; i < temp.size(); i++) {
			resp[i] = temp.get(i);
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public Arco<K, E>[] darArcosDestino(K id) {
		List<Arco<K, E>> resp = new List<Arco<K,E>>();
		Arco<K,E>[] temp = darArcos();
		for(Arco<K, E> a:temp)
		{
			if(a.darNodoFin().darId().equals(id))
				resp.add(a);
		}
		temp = new Arco[resp.size()];
		for (int i = 0; i < resp.size(); i++) {
			temp[i] = resp.get(i);
		}
		return temp;
	}

	public int sizeV() {
		return nodos.size();
	}

	public void simplificar() {
		for (Nodo<K> nodo : darNodos()) {
			List<Arco<K, E>> arcos = adj.get(nodo.darId());
			ChainingHashTable<K, List<Arco<K, E>>> porDestino = new ChainingHashTable<>();
			for (Arco<K, E> arco : arcos) {
				if (porDestino.get(arco.darNodoFin().darId()) == null) {
					List<Arco<K, E>> temp = new List<>();
					temp.add(arco);
					porDestino.put(arco.darNodoFin().darId(), temp);
				} else {
					porDestino.get(arco.darNodoFin().darId()).add(arco);
				}
			}
			List<Arco<K, E>> nuevos = new List<>();
			for (List<Arco<K, E>> list : porDestino.getValues()) {
				Arco<K, E> resp = null;
				double minCosto = Double.POSITIVE_INFINITY;
				for (Arco<K, E> arco : list) {
					if (arco.darCosto() < minCosto) {
						minCosto = arco.darCosto();
						resp = arco;
					}
				}
				nuevos.add(resp);
			}
			adj.put(nodo.darId(), nuevos);
		}
	}
}