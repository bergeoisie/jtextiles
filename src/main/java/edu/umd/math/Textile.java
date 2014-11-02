package edu.umd.math;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.graph.DirectedPseudograph;

import com.google.common.collect.Sets;

public class Textile {
	
	static final Logger logger = LogManager.getLogger(Textile.class.getName());
	
	private DirectedPseudograph<GammaVertex,GammaEdge> gammaGraph;
	private DirectedPseudograph<GVertex,GEdge> gGraph;
	
	public Textile(DirectedPseudograph<GammaVertex,GammaEdge> gamma,
			DirectedPseudograph<GVertex,GEdge> g) {
		gammaGraph = gamma;
		gGraph = g;
	}
	
	public DirectedPseudograph<GammaVertex,GammaEdge> getGammaGraph() {
		return gammaGraph;
	}
	
	public DirectedPseudograph<GVertex,GEdge> getGGraph() {
		return gGraph;
	}
	
	public void trim() {
		
		boolean stable = false;
		
		do {
			stable = true;
			Set<GEdge> pSet = new HashSet<GEdge>();
			Set<GEdge> qSet = new HashSet<GEdge>();

			Map<String,GEdge> gEdgeMap = new HashMap<String, GEdge>();

			for(GEdge gEdge : gGraph.edgeSet()) {
				gEdgeMap.put(gEdge.getName(), gEdge);
			}

			Set<GammaEdge> gammaEdges = gammaGraph.edgeSet();

			for(GammaEdge gammaEdge : gammaEdges) {
				pSet.add(gEdgeMap.get(gammaEdge.getPEHom()));
				qSet.add(gEdgeMap.get(gammaEdge.getQEHom()));
			}

			// Take the symmetric difference of pSet and qSet. We can throw out all of these vertices
			Sets.SetView<GEdge> symDiff = Sets.symmetricDifference(pSet,qSet);

			Set<String> namesInSymDiff = new HashSet<String>();

			for(GEdge symDiffElem : symDiff) {
				namesInSymDiff.add(symDiffElem.getName());
			}

			Set<GammaEdge> gammaToRemove = new HashSet<GammaEdge>();

			for(GammaEdge gammaEdge : gammaEdges) {
				if(namesInSymDiff.contains(gammaEdge.getPEHom()) || namesInSymDiff.contains(gammaEdge.getQEHom())) {
					gammaToRemove.add(gammaEdge);
					logger.debug("Added " + gammaEdge.getName() + " to the symm set");

				}
			}

			if(gammaToRemove.size() > 0 || symDiff.size() > 0) {
				stable = false;
			}
			
			gammaGraph.removeAllEdges(gammaToRemove);
			gGraph.removeAllEdges(symDiff);

			Set<GammaVertex> gammaVertices = gammaGraph.vertexSet();
			Set<GVertex> gVertices = gGraph.vertexSet();

			Set<GVertex> gSinkOrSource = new HashSet<GVertex>();
			Set<GammaVertex> gammaSinkOrSource = new HashSet<GammaVertex>();

			for(GammaVertex gammaVertex : gammaVertices) {
				if(gammaGraph.inDegreeOf(gammaVertex) == 0 || gammaGraph.outDegreeOf(gammaVertex) == 0) {
					gammaSinkOrSource.add(gammaVertex);
					logger.debug("Added " + gammaVertex.getName() + " to the sink or source set");
					
				}
			}

			for(GVertex gVertex : gVertices) {
				if(gGraph.inDegreeOf(gVertex) == 0 || gGraph.outDegreeOf(gVertex) == 0) {
					gSinkOrSource.add(gVertex);
				}
			}

			if(gammaSinkOrSource.size() > 0 || gSinkOrSource.size() > 0) {
				stable = false;
			}
			
			gammaGraph.removeAllVertices(gammaSinkOrSource);
			gGraph.removeAllVertices(gSinkOrSource);
		} while(!stable);
	}
}
