package edu.umd.math;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

public class SpecifiedEquivalence {

	private DirectedPseudograph<GVertex,GEdge> g;
	private DirectedPseudograph<GVertex,GEdge> h;
	
	List<EquivEntry> seArray;
	
	

	public SpecifiedEquivalence(DirectedPseudograph<GVertex,GEdge> gGraph,
								DirectedPseudograph<GVertex,GEdge> hGraph,
								List<EquivEntry> sm) {
		g = gGraph;
		h = hGraph;
		seArray = sm;
	}
	
	public Textile toTextile() {
		
		EdgeFactory<GammaVertex,GammaEdge> gammaEF = 
				new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		DirectedPseudograph<GammaVertex,GammaEdge> textileGamma = new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		
		DirectedPseudograph<GVertex,GEdge> ghProduct = GraphBuilder.createProductGraph(g, h);
		DirectedPseudograph<GVertex,GEdge> hgProduct = GraphBuilder.createProductGraph(h, g);
		
		Map<String,GammaVertex> textileGammaMap = new HashMap<String,GammaVertex>();
		Map<String,GEdge> gEdgeMap = new HashMap<String,GEdge>();

		
		Set<GVertex> ghVertices = ghProduct.vertexSet();
		Set<GEdge> hEdges = h.edgeSet();
		Set<GEdge> gEdges = g.edgeSet();
		
		for(GEdge hEdge : hEdges) {
			GammaVertex gammaV = new GammaVertex(hEdge.getName());
			textileGammaMap.put(gammaV.getName(), gammaV);
			textileGamma.addVertex(gammaV);
		}
				
		
		for(EquivEntry ee : seArray) {
			GammaVertex s = textileGammaMap.get(ee.getB().getName());
			if(s.getPVHom() == null) {
				GVertex pv = g.getEdgeSource(ee.getA());
				s.setPVHom(pv);
				GVertex qv = g.getEdgeSource(ee.getAPrime());
				s.setQVHom(qv);
			}
			GammaVertex t = textileGammaMap.get(ee.getBPrime().getName());
			GammaEdge gammaE = new GammaEdge(s.getName(),t.getName(),ee.getA().getName(),ee.getAPrime().getName(),ee.getName());
			textileGamma.addEdge(s, t, gammaE);
		}
		
		
		
		
		return new Textile(textileGamma,g);
	}
	
}
