package edu.umd.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

public class Textile {
	private DirectedPseudograph<GammaVertex,GammaEdge> gammaGraph;
	private DirectedPseudograph<GVertex,GEdge> gGraph;
	
	public Textile(DirectedPseudograph<GammaVertex,GammaEdge> gamma,
			DirectedPseudograph<GVertex,GEdge> g) {
		gammaGraph = gamma;
		gGraph = g;
	}
	
	public DirectedPseudograph<GammaVertex,GammaEdge> getGammaGraph() {
		return gammaGraph;
	}
	
	public DirectedPseudograph<GVertex,GEdge> getGGraph() {
		return gGraph;
	}
	
	public void trim() {
		
		
		Set<GEdge> pSet = new HashSet<GEdge>();
		Set<GEdge> qSet = new HashSet<GEdge>();
		
		Map<String,GEdge> gEdgeMap = new HashMap<String, GEdge>();
		
		for(GEdge gEdge : gGraph.edgeSet()) {
			gEdgeMap.put(gEdge.getName(), gEdge);
		}
		
		Set<GammaEdge> gammaEdges = gammaGraph.edgeSet();
		
		for(GammaEdge gammaEdge : gammaEdges) {
			pSet.add(gEdgeMap.get(gammaEdge.getPEHom()));
			qSet.add(gEdgeMap.get(gammaEdge.getQEHom()));
		}
		
		
		
		
	}
}
