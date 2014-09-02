package edu.umd.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedPseudograph;

public class TextileChecks {



	public static <V,E> boolean checkNondegeneracy(Graph<V,E> g) {	 
		Set<V> inits = new HashSet<V>();
		Set<V> tails = new HashSet<V>();

		inits.addAll(g.vertexSet());
		tails.addAll(g.vertexSet());

		for(E e : g.edgeSet()) {
			inits.remove(g.getEdgeSource(e));
			tails.remove(g.getEdgeTarget(e));
		}

		return (inits.isEmpty() && tails.isEmpty());

	}



	public static boolean checkHomomorphisms(Textile t) {

		Map<String,GEdge> edgeMap = new HashMap<String,GEdge>();

		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GEdge> gEdges = g.edgeSet();
		Set<GammaEdge> gammaEdges = gamma.edgeSet();

		for(GEdge e : gEdges) {
			edgeMap.put(e.getName(), e);
		}

		for(GammaEdge e : gammaEdges) {
			GEdge pe = edgeMap.get(e.getPEHom());
			GEdge qe = edgeMap.get(e.getQEHom());

			if( !gamma.getEdgeSource(e).getPVHomName().equals(pe.getSourceName()) ||
					!gamma.getEdgeSource(e).getQVHomName().equals(qe.getSourceName()) ||
					!gamma.getEdgeTarget(e).getPVHomName().equals(pe.getTargetName()) ||
					!gamma.getEdgeTarget(e).getQVHomName().equals(qe.getTargetName()) ) {
				System.out.println("There is a problem with " + e.getName() + " from " +
						e.getSourceName() + " to " + e.getTargetName() );

				return false;
			}
		}

		return true;

	}

	public static boolean isLR(Textile t) {

		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}

			for(GEdge ge : g.outgoingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}					
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}

	public static boolean isQLeftResolving(Textile t) {
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}
		}
		return true;
	}

	public static boolean isQRightResolving(Textile t) {

		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge : g.outgoingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}

	public static boolean isPLeftResolving(Textile t) {

		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}
		}
		return true;
	}

	public static boolean isPRightResolving(Textile t) {

		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge : g.outgoingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}


}
