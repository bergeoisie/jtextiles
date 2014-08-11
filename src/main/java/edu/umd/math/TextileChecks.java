package edu.umd.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

public class TextileChecks {

	/*
	 
	 public static boolean checkNondegeneracy(Graph<V,E> g) {
	 
	 }
	
	 */
	 
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
			 
			 if( !gamma.getEdgeSource(e).getPVHom().equals(pe.getSourceName()) ||
					 !gamma.getEdgeSource(e).getQVHom().equals(qe.getSourceName()) ||
					 !gamma.getEdgeTarget(e).getPVHom().equals(pe.getTargetName()) ||
					 !gamma.getEdgeTarget(e).getQVHom().equals(qe.getTargetName()) ) {
				 System.out.println("There is a problem with " + e.getName() + " from " +
					 e.getSourceName() + " to " + e.getTargetName() );
				 
				 return false;
			 }
		 }
		 
		 return true;
	 
	 }
	 
	 
	
	
}
