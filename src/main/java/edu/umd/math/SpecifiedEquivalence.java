package edu.umd.math;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.umd.math.GammaGraph.GammaGraphBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SpecifiedEquivalence {

	private GGraph g;
	private GGraph h;

	private List<EquivEntry> seArray;

    private static final Logger logger = LogManager.getLogger(SpecifiedEquivalence.class);

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
		
		Map<String,GammaVertex> textileGammaMap = new HashMap<>();

		Multimap<String,EquivEntry> orphanedTargetVertices = ArrayListMultimap.create();

		for(EquivEntry ee : seArray) {
            logger.error("Creating gamma vertex for " + ee.toString());
			GammaVertex s = createGammaVertexFromEquivEntry(ee);
            if(!textileGammaMap.containsKey(s.getName())) {
                textileGammaBuilder.addVertex(s);
                textileGammaMap.put(s.getName(),s);
                buildOrphanLinks(s,textileGammaMap,orphanedTargetVertices,textileGammaBuilder);
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
        return new GammaVertex(g.getEdgeSource(ee.getA()),
                g.getEdgeSource(ee.getAPrime()),
                ee.getB().getTargetName());
    }

    private void buildOrphanLinks(GammaVertex vertex,
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
