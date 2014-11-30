package edu.umd.math;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.graph.DirectedPseudograph;

public class SpecifiedEquivalenceGenerator {

	private DirectedPseudograph<GVertex,GEdge> g;
	private DirectedPseudograph<GVertex,GEdge> h;
	private DirectedPseudograph<GVertex,GEdge> gh;
	private DirectedPseudograph<GVertex,GEdge> hg;

	private List<SpecifiedEquivalence> specEquivList;
	
	private PriorityQueue<SpecHelper> specHelperPQueue;
	
	
	public SpecifiedEquivalenceGenerator(DirectedPseudograph<GVertex,GEdge> gg, DirectedPseudograph<GVertex,GEdge> hh) {
		g = gg;
		h = hh;
		gh = GraphBuilder.createProductGraph(gg, hh);
		gh = GraphBuilder.createProductGraph(hh, gg);
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
			EdgePair nextEP = currentSpecHelper.nextEP(g,h,hVertexMap);
			if(nextEP != null) {
				Set<EdgePair> nextPairs = getAllPossibleEdgePairs(nextEP,gVertexMap,hVertexMap);
				
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
	
	
	class SpecHelper {
		private List<EquivEntry> currentEntryList;
		private Set<EdgePair> seenABPrimePairs;
		private Set<EdgePair> seenBAPrimePairs;
		
		/*
		 * TODO: Add in copy constructor and add EquivEntry function
		 */
		public SpecHelper(SpecHelper sh) {
			currentEntryList = new ArrayList<EquivEntry>(sh.getCurrentEntryList());
			seenABPrimePairs = new HashSet<EdgePair>();
			seenBAPrimePairs = new HashSet<EdgePair>();
			generateSeenPairs();
		}
		
		public SpecHelper() {
			currentEntryList = new ArrayList<EquivEntry>();
			seenABPrimePairs = new HashSet<EdgePair>();
			seenBAPrimePairs = new HashSet<EdgePair>();
		}
		
		public SpecHelper(EquivEntry a) {
			currentEntryList = new ArrayList<EquivEntry>();
			seenABPrimePairs = new HashSet<EdgePair>();
			seenBAPrimePairs = new HashSet<EdgePair>();
			this.addEquivEntry(a);
		}
		
		public void addEquivEntry(EquivEntry a) {
			currentEntryList.add(a);
			EdgePair ep = new EdgePair(a.getA(),a.getBPrime());
			seenABPrimePairs.add(ep);
		}
		
		public int getLength() {
			return currentEntryList.size();
		}
		
		protected List<EquivEntry> getCurrentEntryList() {
			return currentEntryList;
		}
		
		private void generateSeenPairs() {
			
			for(EquivEntry ee : currentEntryList) {
				EdgePair abprime = new EdgePair(ee.getA(),ee.getBPrime());
				seenABPrimePairs.add(abprime);
				EdgePair baprime = new EdgePair(ee.getB(),ee.getAPrime());
				seenBAPrimePairs.add(baprime);
			}
						
		}
		
		public EdgePair nextEP(DirectedPseudograph<GVertex,GEdge> g, DirectedPseudograph<GVertex,GEdge> h, Map<String,GVertex> hVertexMap) {
			for(GEdge aEdge : g.edgeSet()) {
				String intVertexName = g.getEdgeTarget(aEdge).getName();
				GVertex intVertexInH = hVertexMap.get(intVertexName);
				Set<GEdge> outIntEdges = h.outgoingEdgesOf(intVertexInH);
				for(GEdge bPrimeEdge : outIntEdges ) {
					EdgePair aBPrimePair = new EdgePair(aEdge,bPrimeEdge);
					if(seenABPrimePairs.contains(aBPrimePair)) {
						return aBPrimePair;
					}
				}
			}
			return null;
		}
	}
	
	class EdgePair {
		GEdge first;
		GEdge second;
		
		public EdgePair(GEdge f, GEdge s) {
			first = f;
			second = s;
		}
		
		public GEdge getFirst() {
			return first;
		}
		
		public GEdge getSecond() {
			return second;
		}
	}
}
