package edu.umd.math;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.graph.DirectedPseudograph;

import edu.umd.math.EdgePair;

class SpecHelper {
	
	static final Logger logger = LogManager.getLogger(SpecHelper.class.getName());

	
	private List<EquivEntry> currentEntryList;
	private Set<EdgePair> seenABPrimePairs;
	private Set<EdgePair> seenBAPrimePairs;

	/*
	 * TODO: Add in copy constructor and add EquivEntry function
	 */
	public SpecHelper(SpecHelper sh) {
		currentEntryList = new ArrayList<EquivEntry>(sh.getCurrentEntryList());
		seenABPrimePairs = new HashSet<EdgePair>(sh.getSeenABPrimePairs());
		seenBAPrimePairs = new HashSet<EdgePair>(sh.getSeenBAPrimePairs());
		generateSeenPairs();
	}

	public SpecHelper() {
		currentEntryList = new ArrayList<EquivEntry>();
		seenABPrimePairs = new HashSet<EdgePair>();
		seenBAPrimePairs = new HashSet<EdgePair>();
	}

	public SpecHelper(EquivEntry a) {
		this();
		this.addEquivEntry(a);
	}

	public void addEquivEntry(EquivEntry a) {
		currentEntryList.add(a);
		EdgePair aBPrime = new EdgePair(a.getA(),a.getBPrime());
		seenABPrimePairs.add(aBPrime);
		EdgePair bAPrime = new EdgePair(a.getB(),a.getAPrime());			
		seenBAPrimePairs.add(bAPrime);
	}

	public int getLength() {
		return currentEntryList.size();
	}
	
	public int getSeenABPrimeSize() {
		return seenABPrimePairs.size();
	}
	
	public int getSeenBAPrimeSize() {
		return seenBAPrimePairs.size();
	}

	public List<EquivEntry> getCurrentEntryList() {
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

	public EdgePair nextEP(GGraph g, GGraph h, Map<String,GVertex> hVertexMap) {
		for(GEdge aEdge : g.edgeSet()) {
			String intVertexName = g.getEdgeTarget(aEdge).getName();
			GVertex intVertexInH = hVertexMap.get(intVertexName);
			Set<GEdge> outIntEdges = h.outgoingEdgesOf(intVertexInH);
			for(GEdge bPrimeEdge : outIntEdges) {
				EdgePair aBPrimePair = new EdgePair(aEdge,bPrimeEdge);
				if(!seenABPrimePairs.contains(aBPrimePair)) {
					logger.info("The pair with a =  " +
								aEdge.getName() + " and bPrime = " +
								bPrimeEdge.getName() + " has not been seen and will be returned");
					return aBPrimePair;
				}
			}
		}
		return null;
	}

	public Set<EdgePair> getSeenABPrimePairs() {
		return seenABPrimePairs;
	}
	
	public Set<EdgePair> getSeenBAPrimePairs() {
		return seenBAPrimePairs;
	}
	
	public boolean hasSeenBAprimePair(EdgePair ep) {
		return seenBAPrimePairs.contains(ep);
	}
	
	public void printHelper() {
		for(EquivEntry e : currentEntryList) {
			e.printEntry();
		}
	}

}