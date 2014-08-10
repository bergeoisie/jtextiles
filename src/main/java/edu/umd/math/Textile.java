package edu.umd.math;

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
}
