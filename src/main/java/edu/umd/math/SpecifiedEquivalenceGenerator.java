package edu.umd.math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.graph.DirectedPseudograph;

public class SpecifiedEquivalenceGenerator {

	private DirectedPseudograph<GVertex,GEdge> g;
	private DirectedPseudograph<GVertex,GEdge> h;
	private DirectedPseudograph<GVertex,GEdge> gh;
	private DirectedPseudograph<GVertex,GEdge> hg;

	private List<SpecifiedEquivalence> specEquivList;
	
	private PriorityQueue<SpecHelper> specHelperPQueue;
	
	
	static final Logger logger = LogManager.getLogger(SpecifiedEquivalenceGenerator.class.getName());

	
	public SpecifiedEquivalenceGenerator(DirectedPseudograph<GVertex,GEdge> gg, DirectedPseudograph<GVertex,GEdge> hh) {
		g = gg;
		h = hh;
		gh = GraphBuilder.createProductGraph(gg, hh);
		hg = GraphBuilder.createProductGraph(hh, gg);
		specEquivList = new ArrayList<SpecifiedEquivalence>();
		specHelperPQueue = new PriorityQueue<SpecHelper>(10, new Comparator<SpecHelper>() {
	        public int compare(SpecHelper sh1, SpecHelper sh2) {
	            return  (Integer.valueOf(sh1.getLength()).compareTo(sh2.getLength()));
	        }
	    });
		createAllSpecifiedEquivalences();
	}
	
	private void createAllSpecifiedEquivalences() {
		
		Set<GVertex> hVertices = h.vertexSet();
		Map<String,GVertex> hVertexMap = new HashMap<String,GVertex>();
		for(GVertex hVertex : hVertices) {
			hVertexMap.put(hVertex.getName(),hVertex);
		}
		
		Set<GVertex> gVertices = g.vertexSet();
		Map<String,GVertex> gVertexMap = new HashMap<String,GVertex>();
		for(GVertex gVertex : gVertices) {
			gVertexMap.put(gVertex.getName(),gVertex);
		}

		// start pqueue with all valid initial configurations for a chosen ab'
		GEdge aEdge = g.edgeSet().iterator().next();
		String intVertexName = g.getEdgeTarget(aEdge).getName();
		GVertex intVertexInH = hVertexMap.get(intVertexName);
		Set<GEdge> outIntEdges = h.outgoingEdgesOf(intVertexInH);
		GEdge bPrimeEdge = outIntEdges.iterator().next();
		EdgePair aBPrimePair = new EdgePair(aEdge,bPrimeEdge);

		Set<EdgePair> initialPossibilities = getAllPossibleEdgePairs(aBPrimePair, gVertexMap, hVertexMap);	

		for(EdgePair bAprimePair : initialPossibilities) {
			EquivEntry ee = new EquivEntry(aEdge,bAprimePair.getFirst(),bAprimePair.getSecond(),bPrimeEdge);
			SpecHelper sh = new SpecHelper(ee);
			specHelperPQueue.add(sh);
		}

		// iterate through the pqueue, if it's a full equiv entry, convert it to a SE and put it in the list, otherwise
		// generate all the new spec helpers.
		while(!specHelperPQueue.isEmpty()) {
			SpecHelper currentSpecHelper = specHelperPQueue.remove();
			
			if(currentSpecHelper.getLength() == gh.edgeSet().size()) {
				logger.info("Our current spec helper is of length " +
							currentSpecHelper.getLength() + 
							" so it will be transformed into a SE and added to the list");
				SpecifiedEquivalence se = new SpecifiedEquivalence(currentSpecHelper,g,h);
				specEquivList.add(se);
			}
			
			EdgePair nextEP = currentSpecHelper.nextEP(g,h,hVertexMap);
			if(nextEP != null) {
				logger.info("Found a non-null EP, getting possible associations");
				Set<EdgePair> nextPairs = getAllPossibleEdgePairs(nextEP,gVertexMap,hVertexMap);
				for(EdgePair np : nextPairs) {
					logger.info("Checking edge pair with b = " +
								np.getFirst().getName() + 
								" and aPrime = " + np.getSecond().getName());
					if(!currentSpecHelper.hasSeenBAprimePair(np)) {
						SpecHelper newSpecHelper = new SpecHelper(currentSpecHelper);
						EquivEntry createdEquivEntry = new EquivEntry(nextEP.getFirst(),np.getFirst(),np.getSecond(),nextEP.getSecond());
						newSpecHelper.addEquivEntry(createdEquivEntry);
						specHelperPQueue.add(newSpecHelper);
					}
				}
			} else {
				logger.error("We ran out of edge pairs. This shouldn't happen");
			}
		}

	}

	private Set<EdgePair> getAllPossibleEdgePairs(EdgePair ep, Map<String,GVertex> gVertexMap, Map<String,GVertex> hVertexMap){

		GEdge aEdge = ep.getFirst();
		GEdge bPrimeEdge = ep.getSecond();

		Set<EdgePair> bAprimePairs = new HashSet<EdgePair>();

		String sourceName = g.getEdgeSource(aEdge).getName();
		String targetName = h.getEdgeTarget(bPrimeEdge).getName();

		GVertex hSourceVertex = hVertexMap.get(sourceName);
		GVertex gTargetVertex = gVertexMap.get(targetName);

		Set<GEdge> bEdges = h.outgoingEdgesOf(hSourceVertex);
		Set<GEdge> aPrimeEdges = g.incomingEdgesOf(gTargetVertex);

		for(GEdge bEdge : bEdges) {
			for(GEdge aPrimeEdge : aPrimeEdges) {
				String rhsIntNameInH = h.getEdgeTarget(bEdge).getName();
				String rhsIntNameInG = g.getEdgeSource(aPrimeEdge).getName();
				if(rhsIntNameInH.equals(rhsIntNameInG)) {
					EdgePair bAprime = new EdgePair(bEdge,aPrimeEdge); 
					bAprimePairs.add(bAprime);
				}
			}
		}

		return bAprimePairs;
	}
	
	public List<SpecifiedEquivalence> getAllSpecEquivs() {
		return specEquivList;
	}
	
}
