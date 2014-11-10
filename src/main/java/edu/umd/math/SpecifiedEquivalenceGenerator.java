package edu.umd.math;

import java.util.List;

import org.jgrapht.graph.DirectedPseudograph;

public class SpecifiedEquivalenceGenerator {

	private DirectedPseudograph<GVertex,GEdge> g;
	private DirectedPseudograph<GVertex,GEdge> h;
	private List<SpecifiedEquivalence> currentList;
	
	
	public SpecifiedEquivalenceGenerator(DirectedPseudograph<GVertex,GEdge> gg, DirectedPseudograph<GVertex,GEdge> hh) {
		g = gg;
		h = hh;
		currentList = new ArrayList<SpecifiedEquivalence>();
		createAllSpecifiedEquivalences();
	}
	
	private void createAllSpecifiedEquivalences() {
		
	}
	
}
