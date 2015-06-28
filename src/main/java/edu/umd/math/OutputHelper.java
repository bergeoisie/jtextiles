package edu.umd.math;

import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

public class OutputHelper {

	public static void printTextile(Textile t) {
		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();
		
		
		System.out.println("Printing Gamma Graph");
		
		System.out.println("Gamma Graph has " + gamma.vertexSet().size() + " vertices and " + gamma.edgeSet().size() + " edges");
		
		printHomomorphisms(gamma);
		
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
	
	private static String formatHomomorphisms(Set<GammaEdge> edges) {
		StringBuilder str = new StringBuilder();
		
		for(GammaEdge e : edges) {
			str.append(e.getPEHom())
			.append("/")
			.append(e.getQEHom())
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
	
	public static void printGraph(GGraph graph) {
		Set<GVertex> vertices = graph.vertexSet();

		for(GVertex gv : vertices) {
			System.out.print(gv.getName() + " ");
			for(GVertex igv : vertices) {
				Set<GEdge> edges = graph.getAllEdges(gv, igv);
				String formattedEdges = formatEdges(edges);
				System.out.print(formattedEdges);
			}
			System.out.println("");
		}
	}

	public static void printGraph(GammaGraph graph) {
		Set<GammaVertex> vertices = graph.vertexSet();

		for(GammaVertex gammav : vertices) {
			System.out.print(gammav.getName() + " ");
			for(GammaVertex igv : vertices) {
				Set<GammaEdge> edges = graph.getAllEdges(gammav, igv);
				String formattedEdges = formatEdges(edges);
				System.out.print(formattedEdges);
			}
			System.out.println("");
		}
	}
	
	
	public static void printHomomorphisms(GammaGraph graph) {
		Set<GammaVertex> gammaVertices = graph.vertexSet();

		for(GammaVertex gammav : gammaVertices) {
			System.out.print(gammav.getName() + " ");
			for(GammaVertex igv : gammaVertices) {
				Set<GammaEdge> edges = graph.getAllEdges(gammav, igv);
				String formattedHomomorphisms = formatHomomorphisms(edges);
				System.out.print(formattedHomomorphisms);
			}
			System.out.println("");
		}
	}
	

}
