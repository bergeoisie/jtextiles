package edu.umd.math;

import java.util.Collection;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

public class GammaGraph {
	private DirectedPseudograph<GammaVertex, GammaEdge> gammaGraph;

	private GammaGraph(DirectedPseudograph<GammaVertex, GammaEdge> gammaGraph) {
		this.gammaGraph = gammaGraph;
	}
	
	public int inDegreeOf(GammaVertex v) {
		return gammaGraph.inDegreeOf(v);
	}
	
	public int outDegreeOf(GammaVertex v) {
		return gammaGraph.outDegreeOf(v);
	}
	
	public boolean removeAllVertices(Collection<GammaVertex> toRemove) {
		return gammaGraph.removeAllVertices(toRemove);
	}
	
	public boolean removeAllEdges(Collection<GammaEdge> toRemove) {
		return gammaGraph.removeAllEdges(toRemove);
	}
	
	public Set<GammaVertex> vertexSet() {
		return gammaGraph.vertexSet();
	}
	
	public Set<GammaEdge> edgeSet() {
		return gammaGraph.edgeSet();
	}
	
	public static class GammaGraphBuilder {
		
		private DirectedPseudograph<GammaVertex, GammaEdge> nestedGammaGraph;
		
		public GammaGraphBuilder() {
			EdgeFactory<GammaVertex,GammaEdge> gammaEF = 
					new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
			
			nestedGammaGraph = new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		}
		
		public GammaGraphBuilder addVertex(GammaVertex vertex) {
			nestedGammaGraph.addVertex(vertex);
			return this;
		}
		
		public GammaGraphBuilder addEdge(GammaVertex source, GammaVertex target, GammaEdge edge) {
			nestedGammaGraph.addEdge(source, target, edge);
			return this;
		}
		
		public GammaGraph build() {
			return new GammaGraph(nestedGammaGraph);
		}
	}

	public Set<GammaEdge> incomingEdgesOf(GammaVertex vertex) {
		return gammaGraph.incomingEdgesOf(vertex);
	}
	
	public Set<GammaEdge> outgoingEdgesOf(GammaVertex vertex) {
		return gammaGraph.outgoingEdgesOf(vertex);
	}

	public Set<GammaEdge> getAllEdges(GammaVertex source, GammaVertex target) {
		return gammaGraph.getAllEdges(source, target);
	}

	public GammaVertex getEdgeTarget(GammaEdge edge) {
		return gammaGraph.getEdgeTarget(edge);
	}

	public GammaVertex getEdgeSource(GammaEdge edge) {
		return gammaGraph.getEdgeSource(edge);
	}
	
}