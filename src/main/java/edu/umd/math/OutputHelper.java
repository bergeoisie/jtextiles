package edu.umd.math;

import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

public class OutputHelper {

	public static void printTextile(Textile t) {
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();
		
		
		System.out.println("Printing Gamma Graph");
		
		printGraph(gamma);

		System.out.println("Printing G Graph");
		
		printGraph(g);
		
	}
	
	private static <E extends Edge> String formatEdges(Set<E> edges) {
		StringBuilder str = new StringBuilder();
		
		for(E e : edges) {
			str.append(e.getName())
			.append(" + ");
		}
		if(edges.size() > 0) {
			str.delete(str.length() - 3, str.length());
		} else {
			str.append("0");
		}
		str.append(" ");
		
		return str.toString();
	}
	
	public static <V extends Vertex, E extends Edge> void printGraph(DirectedPseudograph<V,E> graph) {
		Set<V> gammaVertices = graph.vertexSet();

		for(V gammav : gammaVertices) {
			System.out.print(gammav.getName());
			for(V igv : gammaVertices) {
				Set<E> edges = graph.getAllEdges(gammav, igv);
				String formattedEdges = formatEdges(edges);
				System.out.print(formattedEdges);
			}
			System.out.println("");
		}
	}
}
