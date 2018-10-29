package mundo;

import java.util.Comparator;

import estructuras.*;

public class DigraphAlgorithms {
	
	public static final int COSTO = 1;
	public static final int TIEMPO = 2;
	public static final int TIEMPO_CON_ESCALA = 3;
	
	public double darCosto(Arco<Integer, Vuelo> a, int peso, DayOfWeek dia){
		long escala = 0;
		if(edgeTo != null && edgeTo[a.darNodoInicio().darId()] != null)
			escala = Vuelo.darEscala(edgeTo[a.darNodoInicio().darId()].darInformacion(), a.darInformacion(), dia);
		
		if(peso == COSTO)
			return CatalogoDeVuelos.calcularTarifa(a.darInformacion(), dia);
		else if(peso == TIEMPO)
			return a.darCosto();
		else{
			return a.darCosto() + escala;
		}
	}


	// -----------------------------------------------------------------
	// Inicializaci�n
	// -----------------------------------------------------------------

	private Digraph<Integer, Vuelo> grafo;

	private Digraph<Integer, Vuelo> reverseGrafo;
	private boolean[] visited;
	private Arco<Integer, Vuelo>[] edgeTo;

	public DigraphAlgorithms(Digraph<Integer, Vuelo> grafo) {
		this.grafo = grafo;

	}

	public Arco<Integer, Vuelo>[] getEdgeTo() {
		return edgeTo;
	}

	// -----------------------------------------------------------------
	// Dijkstra
	// -----------------------------------------------------------------

	private double[] distTo;
	private Heap<Integer> pq;

	@SuppressWarnings("unchecked")
	public void Dijsktra(int s, Aerolinea a, int peso, DayOfWeek dia) {
		edgeTo = new Arco[grafo.sizeV()];
		distTo = new double[grafo.sizeV()];
		visited = new boolean[grafo.sizeV()];
		pq = new Heap<>(new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return -Double.compare(distTo[o1], distTo[o2]);
			}
		});

		for (int v = 0; v < grafo.sizeV(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0;

		pq.add(s);
		while (!pq.isEmpty()) {
			int v = pq.poll();
			relax(v, a, peso, edgeTo[v] == null || edgeTo[v].darInformacion().darDia() == null? dia : edgeTo[v].darInformacion().darDia());
		}
		
	}

	private void relax(int v, Aerolinea a, int peso, DayOfWeek dia) {
		for (Arco<Integer, Vuelo> e : grafo.darArcosOrigen(v)) {
			if (a == null || e.darInformacion().darAerolinea().equals(a)) {
				int w = e.darNodoFin().darId();
				if (visited[w])
					continue;
				if (distTo[w] > distTo[v] + darCosto(e, peso, dia)) {
					distTo[w] = distTo[v] + darCosto(e, peso, dia);
					edgeTo[w] = e;
					pq.recalculate(w);
				}
			}

		}
		visited[v] = true;
	}

	// -----------------------------------------------------------------
	// Kosaraju
	// -----------------------------------------------------------------

	private int[] components;

	public int[] getComponents() {
		return components;
	}

	private Stack<Integer> reversePost;

	public Stack<Integer> getTopologicalOrder() {
		return reversePost;
	}

	public void topoSort(Aerolinea a) {
		reversePost = new Stack<Integer>();
		visited = new boolean[grafo.sizeV()];
		for (int v = 0; v < grafo.sizeV(); v++)
			if (!visited[v])
				dfsTopo(v, a);
	}

	private void dfsTopo(int v, Aerolinea a) {
		visited[v] = true;
		for (Arco<Integer, Vuelo> e : grafo.darArcosOrigen(v)) {
			if (a == null || e.darInformacion().darAerolinea().equals(a)) {
				int w = e.darNodoFin().darId();
				if (!visited[w])
					dfsTopo(w, a);
			}
		}
		reversePost.push(v);
	}

	public void Kosaraju(Aerolinea a) {
		topoSort(a);
		this.reverseGrafo = grafo.reverse();
		visited = new boolean[grafo.sizeV()];
		components = new int[grafo.sizeV()];
		int comp = 0;
		for (int v : reversePost) {
			if (!visited[v])
				dfsKJ(v, comp++, a);
		}
	}

	private void dfsKJ(int v, int comp, Aerolinea a) {
		visited[v] = true;
		components[v] = comp;
		if(reverseGrafo==null|| reverseGrafo.darArcosOrigen(v)==null)
			return;
		for (Arco<Integer, Vuelo> e : reverseGrafo.darArcosOrigen(v)) {
			if (a == null || e.darInformacion().darAerolinea().equals(a)) {
				int w = e.darNodoFin().darId();
				if (!visited[w])
					dfsKJ(w, comp, a);
			}
		}
	}

	// -----------------------------------------------------------------
	// Prim
	// -----------------------------------------------------------------
	/*
	 * private Heap<Arco<Integer, Vuelo>> pqArc;
	 * 
	 * @SuppressWarnings("unchecked") public void Prim() { edgeTo = new
	 * Arco[grafo.sizeV()]; visited = new boolean[grafo.sizeV()]; pqArc = new
	 * Heap<>(new Comparator<Arco<Integer, Vuelo>>() { public int
	 * compare(Arco<Integer, Vuelo> o1, Arco<Integer, Vuelo> o2) { return
	 * -Double.compare(o1.darCosto(), o2.darCosto()); } });
	 * 
	 * visit(0); while (!pqArc.isEmpty()) { Arco<Integer, Vuelo> e =
	 * pqArc.poll(); int w = e.darNodoFin().darId(); if (!visited[w]) {
	 * edgeTo[w] = e; visit(w); } } }
	 * 
	 * private void visit(int v) { visited[v] = true; for (Arco<Integer, Vuelo>
	 * e : grafo.darArcosOrigen(v)) { if (!visited[e.darNodoFin().darId()])
	 * pqArc.add(e); }
	 * 
	 * }
	 */
	// -----------------------------------------------------------------
	// Edmonds
	// -----------------------------------------------------------------

	public ChainingHashTable<Integer, Arco<Integer, Vuelo>> MST(Aerolinea a,
			int v, int peso, DayOfWeek dia) {
		Digraph<Integer, Vuelo> grafo2 = new Digraph<>();
		Kosaraju(a);
		int comp = components[v];
		for (Nodo<Integer> nodo : grafo.darNodos()) {
			if (components[nodo.darId()] == comp) {
				grafo2.agregarNodo(nodo);
			}
		}
		for (Nodo<Integer> nodo : grafo.darNodos()) {
			if (components[nodo.darId()] == comp) {
				Arco<Integer, Vuelo>[] arcos = grafo.darArcosOrigen(nodo
						.darId());
				for (Arco<Integer, Vuelo> arco : arcos) {
					if (components[arco.darNodoFin().darId()] == comp
							&& (a == null || arco.darInformacion()
									.darAerolinea().equals(a)))
						grafo2.agregarArco(arco.darNodoInicio().darId(), arco
								.darNodoFin().darId(), darCosto(arco, peso, dia), arco
								.darInformacion());
				}
			}
		}
		return Edmonds(grafo2, v);
	}

	private ChainingHashTable<Integer, Arco<Integer, Vuelo>> Edmonds(
			Digraph<Integer, Vuelo> grafo2, int r) {
		ChainingHashTable<Integer, Arco<Integer, Vuelo>> arbol = new ChainingHashTable<>();
		grafo2.simplificar();
		for (Nodo<Integer> nodo : grafo2.darNodos()) {
			Arco<Integer, Vuelo>[] arcos = grafo2.darArcosDestino(nodo.darId());
			if (nodo.darId() == r) {
				for (Arco<Integer, Vuelo> arco : arcos)
					grafo2.eliminarArco(arco.darNodoInicio().darId(), r);
			} else {
				double min = Double.POSITIVE_INFINITY;
				Arco<Integer, Vuelo> resp = null;
				for (Arco<Integer, Vuelo> arco : arcos) {
					if (arco.darCosto() < min) {
						min = arco.darCosto();
						resp = arco;
					}
				}
				if (resp == null)
					System.out.println();

				arbol.put(nodo.darId(), resp);
			}
		}
		ChainingHashTable<Integer, Nodo<Integer>> ciclo = darCiclo(grafo2,
				arbol);
		if (ciclo == null)
			return arbol;

		Digraph<Integer, Vuelo> nuevo = new Digraph<>();
		for (Nodo<Integer> nodo : grafo2.darNodos()) {
			if (ciclo.get(nodo.darId()) == null)
				nuevo.agregarNodo(nodo);
		}
		Nodo<Integer> nodoC = new Ciudad("ciclo",
				ciclo.getKeys(new Integer[0])[0]);
		nuevo.agregarNodo(nodoC);

		for (Arco<Integer, Vuelo> arco : grafo2.darArcos()) {
			int origen = arco.darNodoInicio().darId(), destino = arco
					.darNodoFin().darId();
			if (ciclo.get(origen) == null && ciclo.get(destino) != null) {
				nuevo.agregarArco(origen, nodoC.darId(), arco.darCosto()
						- arbol.get(destino).darCosto(), arco.darInformacion());
			} else if (ciclo.get(origen) != null && ciclo.get(destino) == null) {
				nuevo.agregarArco(nodoC.darId(), destino, arco.darCosto(),
						arco.darInformacion());
			} else if (ciclo.get(origen) == null && ciclo.get(destino) == null) {
				nuevo.agregarArco(origen, destino, arco.darCosto(),
						arco.darInformacion());
			}
		}
		ChainingHashTable<Integer, Arco<Integer, Vuelo>> arbol2 = Edmonds(
				nuevo, r);
		Arco<Integer, Vuelo> arco = darPredescesor(grafo2,
				arbol2.get(nodoC.darId()));
		int v = arco.darNodoFin().darId();
		for (Nodo<Integer> nodo : grafo2.darNodos()) {
			Integer id = nodo.darId();
			if (id == r)
				continue;
			if (id == v)
				arbol.put(id, arco);
			else if (ciclo.get(id) == null)
				arbol.put(id, darPredescesor(grafo2, arbol2.get(id)));
		}
		return arbol;
	}

	private Arco<Integer, Vuelo> darPredescesor(Digraph<Integer, Vuelo> grafo2,
			Arco<Integer, Vuelo> arco) {
		if (!((Ciudad) arco.darNodoInicio()).darNombre().equals("ciclo")) {
			for (Arco<Integer, Vuelo> arco2 : grafo2.darArcosOrigen(arco
					.darNodoInicio().darId())) {
				if (arco2.darInformacion().equals(arco.darInformacion()))
					return arco2;
			}
		} else {
			for (Arco<Integer, Vuelo> arco2 : grafo2.darArcos()) {
				if (arco2.darInformacion().equals(arco.darInformacion()))
					return arco2;
			}
		}
		return null;
	}

	private ChainingHashTable<Integer, Nodo<Integer>> darCiclo(
			Digraph<Integer, Vuelo> grafo2,
			ChainingHashTable<Integer, Arco<Integer, Vuelo>> arbol) {
		for (Nodo<Integer> nodo : grafo2.darNodos()) {
			visited = new boolean[grafo.sizeV()];
			visited[nodo.darId()] = true;
			ChainingHashTable<Integer, Nodo<Integer>> ans = new ChainingHashTable<>();
			ans.put(nodo.darId(), nodo);
			Arco<Integer, Vuelo> arco = arbol.get(nodo.darId());
			while (arco != null) {
				Integer id = arco.darNodoInicio().darId();
				if (id == nodo.darId())
					return ans;

				if (visited[id])
					break;
				visited[id] = true;
				ans.put(id, arco.darNodoInicio());
				arco = arbol.get(id);
			}
		}
		return null;
	}

}
