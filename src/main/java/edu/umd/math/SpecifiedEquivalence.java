package edu.umd.math;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

import edu.umd.math.GammaGraph.GammaGraphBuilder;

public class SpecifiedEquivalence {

	private GGraph g;
	private GGraph h;
	
	List<EquivEntry> seArray;
	
	

	public SpecifiedEquivalence(GGraph gGraph,
								GGraph hGraph,
								List<EquivEntry> sm) {
		g = gGraph;
		h = hGraph;
		seArray = sm;
	}
	
	public SpecifiedEquivalence(SpecHelper sh, GGraph gg, GGraph hh) {
		g = gg;
		h = hh;
		seArray = sh.getCurrentEntryList();
	}
	
	public Textile toTextile() {
						
		GammaGraphBuilder textileGammaBuilder = new GammaGraphBuilder();
		
		Map<String,GammaVertex> textileGammaMap = new HashMap<String,GammaVertex>();

		
		Set<GEdge> hEdges = h.edgeSet();
		
		for(GEdge hEdge : hEdges) {
			GammaVertex gammaV = new GammaVertex(hEdge.getName());
			textileGammaMap.put(gammaV.getName(), gammaV);
			textileGammaBuilder.addVertex(gammaV);
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
			textileGammaBuilder.addEdge(s, t, gammaE);
		}
		
		GammaGraph textileGamma = textileGammaBuilder.build();
		
		return new Textile(textileGamma,g);
	}
	
}
