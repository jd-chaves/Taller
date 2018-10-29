package testEstructuras;

import estructuras.Digraph;
import junit.framework.TestCase;
import mundo.Ciudad;

public class DigraphTest extends TestCase{
	private Digraph<Integer, Ciudad> grafo;
	private void setUpEscenario1()
	{
		grafo = new Digraph<>();
	}

	public void testGrafo()
	{
		setUpEscenario1();
		
		Ciudad bogota = new Ciudad("bogota",1);
		Ciudad medellin = new Ciudad("Medellin", 2);
		Ciudad cartagena = new Ciudad("Cartagena",3);
		Ciudad armenia = new Ciudad("Armenia",4);
		Ciudad barranquilla = new Ciudad("Barranquilla",5);
		
		grafo.agregarNodo(bogota);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darNodos().length);
		grafo.agregarNodo(medellin);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darNodos().length);
		grafo.agregarNodo(cartagena);
		assertEquals("Los tamanhos no coinciden", 3, grafo.darNodos().length);
		grafo.agregarNodo(armenia);
		assertEquals("Los tamanhos no coinciden", 4, grafo.darNodos().length);
		grafo.agregarNodo(barranquilla);
		assertEquals("Los tamanhos no coinciden", 5, grafo.darNodos().length);
		
		grafo.agregarArco(1, 2, 4);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosDestino(2).length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosOrigen(1).length);
		grafo.agregarArco(2, 4, 1);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosDestino(4).length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosOrigen(2).length);
		grafo.agregarArco(5, 3, 2);
		assertEquals("Los tamanhos no coinciden", 3, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosDestino(3).length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosOrigen(5).length);
		grafo.agregarArco(5, 4, 3);
		assertEquals("Los tamanhos no coinciden", 4, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darArcosDestino(4).length);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darArcosOrigen(5).length);
		grafo.agregarArco(1, 3, 1);
		assertEquals("Los tamanhos no coinciden", 5, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darArcosDestino(3).length);
		assertEquals("Los tamanhos no coinciden", 2, grafo.darArcosOrigen(1).length);
		
		grafo.eliminarArco(1, 2);
		assertEquals("Los tamanhos no coinciden", 4, grafo.darArcos().length);
		assertEquals("Los tamanhos no coinciden", 0, grafo.darArcosDestino(2).length);
		assertEquals("Los tamanhos no coinciden", 1, grafo.darArcosOrigen(1).length);
		
		grafo.eliminarNodo(3);
		assertEquals("Los tamanhos no coinciden", 4, grafo.darNodos().length);
		assertEquals("Los tamanhos no coinciden", 0, grafo.darArcosDestino(3).length);
		grafo.eliminarNodo(1);
		assertEquals("Los tamanhos no coinciden", 3, grafo.darNodos().length);
		assertEquals("Los tamanhos no coinciden", 0, grafo.darArcosOrigen(1).length);
	}
	
	
	
	
}