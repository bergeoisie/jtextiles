package edu.umd.math;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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
		Multimap<String,EquivEntry> orphanedTargetVertices = ArrayListMultimap.create();

		for(EquivEntry ee : seArray) {
			GammaVertex s = createGammaVertexFromEquivEntry(ee);
            if(!textileGammaMap.containsKey(s.getName())) {
                textileGammaBuilder.addVertex(s);
                textileGammaMap.put(s.getName(),s);
                buildOrphanLinks(s,ee,textileGammaMap,orphanedTargetVertices,textileGammaBuilder);
            }

            if(textileGammaMap.containsKey(ee.getBPrime().getName())) {
                GammaVertex t = textileGammaMap.get(ee.getBPrime().getName());
                GammaEdge gammaE = new GammaEdge(s.getName(),t.getName(),ee.getA().getName(),ee.getAPrime().getName(),ee.getName());
                textileGammaBuilder.addEdge(s, t, gammaE);
            } else {
                orphanedTargetVertices.put(ee.getBPrime().getName(),ee);
            }
		}
		
		GammaGraph textileGamma = textileGammaBuilder.build();
		
		return new Textile(textileGamma,g);
	}

	private GammaVertex createGammaVertexFromEquivEntry(EquivEntry ee) {
        GammaVertex gv = new GammaVertex(g.getEdgeSource(ee.getA()),
                g.getEdgeSource(ee.getAPrime()),
                ee.getB().getTargetName());

        return gv;
    }

    private void buildOrphanLinks(GammaVertex vertex,
                                  EquivEntry equivEntry,
                                  Map<String,GammaVertex> textileGammaMap,
                                  Multimap<String, EquivEntry> orphanedTargetVertices,
                                  GammaGraphBuilder textileGammaBuilder) {
        for(EquivEntry ee : orphanedTargetVertices.get(vertex.getName())) {
            GammaVertex s = textileGammaMap.get(ee.getB().getName());
            GammaEdge gammaE = new GammaEdge(s.getName(),vertex.getName(),ee.getA().getName(),ee.getAPrime().getName(),ee.getName());
            textileGammaBuilder.addEdge(s,vertex,gammaE);
        }
    }

}
