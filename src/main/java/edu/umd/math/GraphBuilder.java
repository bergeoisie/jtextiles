package edu.umd.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

public class GraphBuilder {
	
	public static DirectedPseudograph<GVertex,GEdge> createProductGraph(DirectedPseudograph<GVertex,GEdge> g,
			DirectedPseudograph<GVertex,GEdge> h) {
		
		Set<GVertex> gVertices = g.vertexSet();
		Set<GVertex> hVertices = h.vertexSet();
	
		
		EdgeFactory<GVertex,GEdge> gEF = 
				new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		DirectedPseudograph<GVertex,GEdge> prodG = 
				new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		Map<String,GVertex> gVertexMap = new HashMap<String,GVertex>();
		Map<String,GVertex> hVertexMap = new HashMap<String,GVertex>();
		
		
		for(GVertex gVertex : gVertices) {
			prodG.addVertex(gVertex);
			gVertexMap.put(gVertex.getName(), gVertex);
		}
		
		for(GVertex hVertex : hVertices) {
			hVertexMap.put(hVertex.getName(), hVertex);
		}
		
		for(GVertex tGVertex : gVertices) {
			Set<GEdge> tGVertexOutEdges = g.outgoingEdgesOf(tGVertex);
			for(GEdge tGVertexOutEdge : tGVertexOutEdges) {
				GVertex tgvoeTargetVertex = hVertexMap.get(g.getEdgeTarget(tGVertexOutEdge).getName());
				Set<GEdge> tgvoeTargetVertexOutEdges = h.outgoingEdgesOf(tgvoeTargetVertex);
				for(GEdge tgvoeTargetVertexOutEdge : tgvoeTargetVertexOutEdges) {
					GEdge prodEdge = new GEdge(tGVertex.getName(),
												tgvoeTargetVertex.getName(),
												tGVertexOutEdge.getName() + tgvoeTargetVertexOutEdge.getName());
					prodG.addEdge(gVertexMap.get(tGVertex.getName()),
									gVertexMap.get(tgvoeTargetVertex.getName()),
									prodEdge);
				}
			}
		}
		
		return prodG;
	}
}
