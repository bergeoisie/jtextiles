package edu.umd.math;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

public class TextileBuilder {

	public static Textile createDual(Textile t) {
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();
		
		EdgeFactory<GammaVertex,GammaEdge> gammaEF = new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		EdgeFactory<GVertex,GEdge> gEF = new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		
		DirectedPseudograph<GammaVertex,GammaEdge> gammaT = new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		DirectedPseudograph<GVertex,GEdge> gT = new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		Set<GammaEdge> gammaEdges = gamma.edgeSet();
		Set<GammaVertex> gammaVertices = gamma.vertexSet();
		
		Set<GEdge> gEdges = g.edgeSet();
		Set<GVertex> gVertices = g.vertexSet();
	
		Map<String,GammaVertex> nameToGammaVertex = new HashMap<String,GammaVertex>();
		
		for(GEdge ge : gEdges) {
			GammaVertex gav = new GammaVertex(ge.getSourceName(),ge.getTargetName(),ge.getName());
			gammaT.addVertex(gav);
			nameToGammaVertex.put(ge.getName(),gav);
		}
		
		for(GammaEdge gammae : gammaEdges) {
			GammaVertex sv = nameToGammaVertex.get(gammae.getPEHom());
			GammaVertex tv = nameToGammaVertex.get(gammae.getQEHom());
			GammaEdge e = new GammaEdge(sv.getName(),tv.getName(),gammae.getSourceName(),gammae.getTargetName(),gammae.getName());
			gammaT.addEdge(sv, tv, e);
		}
		
		Map<String,GVertex> nameToGVertex = new HashMap<String,GVertex>();
		
		for(GVertex gv : gVertices) {
			GVertex gtv = new GVertex(gv.getName());
			gT.addVertex(gtv);
			nameToGVertex.put(gtv.getName(), gtv);
		}
		
		for(GammaVertex gammav : gammaVertices) {
			GVertex sv = nameToGVertex.get(gammav.getPVHom());
			GVertex tv = nameToGVertex.get(gammav.getQVHom());
			GEdge e = new GEdge(sv.getName(),tv.getName(),gammav.getName());
			gT.addEdge(sv,tv,e);
		}
		
		return new Textile(gammaT,gT);
	}
	/*
	public static Textile createInverse(Textile t) {
		
	}
	
	public static Textile createProductTextile(Textile s, Textile t) {
	
	}
	
	
	
	*/
}
