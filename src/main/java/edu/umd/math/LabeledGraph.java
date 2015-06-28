package edu.umd.math;

import org.jgrapht.graph.DirectedPseudograph;

public class LabeledGraph {
	private DirectedPseudograph<LabeledVertex, LabeledEdge> labeledGraph;

	private LabeledGraph(DirectedPseudograph<LabeledVertex, LabeledEdge> labeledGraph) {
		this.labeledGraph = labeledGraph;
	}
	
	
}
