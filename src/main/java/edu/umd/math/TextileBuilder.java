package edu.umd.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

/**
 * @author brberg
 *
 */
public class TextileBuilder {

	public static Textile createDual(Textile t) {
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();
		
		EdgeFactory<GammaVertex,GammaEdge> gammaEF = 
				new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		EdgeFactory<GVertex,GEdge> gEF = 
				new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		
		DirectedPseudograph<GammaVertex,GammaEdge> gammaT = 
				new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		DirectedPseudograph<GVertex,GEdge> gT = 
				new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		Set<GammaEdge> gammaEdges = gamma.edgeSet();
		Set<GammaVertex> gammaVertices = gamma.vertexSet();
		
		Set<GEdge> gEdges = g.edgeSet();
		Set<GVertex> gVertices = g.vertexSet();
	
		Map<String,GammaVertex> nameToGammaVertex = new HashMap<String,GammaVertex>();
		
		for(GEdge ge : gEdges) {
			GammaVertex gav = new GammaVertex(g.getEdgeSource(ge),g.getEdgeTarget(ge),ge.getName());
			gammaT.addVertex(gav);
			nameToGammaVertex.put(ge.getName(),gav);
		}
		
		for(GammaEdge gammae : gammaEdges) {
			GammaVertex sv = nameToGammaVertex.get(gammae.getPEHom());
			GammaVertex tv = nameToGammaVertex.get(gammae.getQEHom());
			GammaEdge e = new GammaEdge(sv.getName(),
										tv.getName(),
										gammae.getSourceName(),
										gammae.getTargetName(),
										gammae.getName());
			gammaT.addEdge(sv, tv, e);
		}
		
		Map<String,GVertex> nameToGVertex = new HashMap<String,GVertex>();
		
		for(GVertex gv : gVertices) {
			GVertex gtv = new GVertex(gv.getName());
			gT.addVertex(gtv);
			nameToGVertex.put(gtv.getName(), gtv);
		}
		
		for(GammaVertex gammav : gammaVertices) {
			GVertex sv = nameToGVertex.get(gammav.getPVHomName());
			GVertex tv = nameToGVertex.get(gammav.getQVHomName());
			GEdge e = new GEdge(sv.getName(),tv.getName(),gammav.getName());
			gT.addEdge(sv,tv,e);
		}
		
		return new Textile(gammaT,gT);
	}
	
	public static Textile createInverse(Textile t) {
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = 
				(DirectedPseudograph<GVertex, GEdge>) t.getGGraph().clone();

		EdgeFactory<GammaVertex,GammaEdge> gammaEF = 
				new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		DirectedPseudograph<GammaVertex,GammaEdge> inverseGamma = 
				new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		
		Set<GammaEdge> gammaEdges = gamma.edgeSet();
		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Map<String,GammaVertex> nameToGammaVertex = new HashMap<String,GammaVertex>();
		
		for(GammaVertex gammaV : gammaVertices) {
			GammaVertex inverseGammaV = new GammaVertex(gammaV.getPVHom(),
														gammaV.getQVHom(),
														gammaV.getName());
			inverseGamma.addVertex(inverseGammaV);
			nameToGammaVertex.put(gammaV.getName(), inverseGammaV);
		}
		
		for(GammaEdge gammaE : gammaEdges) {
			GammaEdge inverseGammaE = new GammaEdge(gammaE.getSourceName(),
													gammaE.getTargetName(),
													gammaE.getQEHom(),
													gammaE.getPEHom(),
													gammaE.getName());
			inverseGamma.addEdge(nameToGammaVertex.get(gammaE.getSourceName()), 
									nameToGammaVertex.get(gammaE.getTargetName()), 
									inverseGammaE);
		}
		
		return new Textile(inverseGamma,g);
	}
	
	
	
	public static Textile createProductTextile(Textile t, Textile s) throws Exception {
		
		DirectedPseudograph<GammaVertex,GammaEdge> tGamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> tG = t.getGGraph();

		DirectedPseudograph<GammaVertex,GammaEdge> sGamma = s.getGammaGraph();		
		DirectedPseudograph<GVertex,GEdge> sG = s.getGGraph();		

		Set<GammaVertex> sGammaVertices = sGamma.vertexSet();

		Set<GammaVertex> tGammaVertices = tGamma.vertexSet();

		Set<GVertex> tGVertices = tG.vertexSet();
		
		Set<GEdge> sGEdges = sG.edgeSet();
		Set<GVertex> sGVertices = sG.vertexSet();
		
		Map<String,GammaVertex> sGammaVertexMap = new HashMap<String,GammaVertex>();
		Map<String,GVertex> sGVertexMap = new HashMap<String,GVertex>();
		Map<String,GVertex> tGVertexMap = new HashMap<String,GVertex>();
		
		for(GammaVertex sGammaVertex : sGammaVertices) {
			sGammaVertexMap.put(sGammaVertex.getName(), sGammaVertex);
		}
		
		for(GVertex sGVertex : sGVertices) {
			sGVertexMap.put(sGVertex.getName(), sGVertex);
		}
		
		for(GVertex tGVertex : tGVertices) {
			tGVertexMap.put(tGVertex.getName(), tGVertex);
		}
		
		// Check to make sure the graphs are compatible
		if(sGVertices.size() != tGVertices.size()) {
			throw new Exception("Unequal number of vertices between G graphs");
		}
		if(sGEdges.size() != sGEdges.size()) {
			throw new Exception("Unequal number of edges betweeen G graphs");
		}
		// Pick a vertex, find it's counterpart, check name
		// TODO: switch to using the map to find gamma vertices
		Set<GVertex> sTestSet = new HashSet<GVertex>(sGVertices);
		for(GVertex tGVertex : tGVertices) {
			boolean found = false;
			for(GVertex sGVertex : sTestSet) {
				if(tGVertex.getName().equals(sGVertex.getName())) {
					found = true;
					sTestSet.remove(sGVertex);
					break;
				}
			}
			if(!found) {
				throw new Exception("Did not find g vertex with name " + tGVertex.getName());
			}
		}
		Set<GammaVertex> sGammaTestSet = new HashSet<GammaVertex>(sGammaVertices);
		for(GammaVertex tGammaVertex : tGammaVertices) {
			boolean found = false;
			for(GammaVertex sGammaVertex : sGammaTestSet) {
				if(tGammaVertex.getName().equals(sGammaVertex.getName())) {
					found = true;
					if(!tGammaVertex.getPVHomName().equals(sGammaVertex.getPVHomName()) || 
						!tGammaVertex.getQVHomName().equals(sGammaVertex.getQVHomName())) {
						throw new Exception("Vertex homomorphisms are not equal");
					}
					sGammaTestSet.remove(sGammaVertex);
					break;
				}
			}
			if(!found) {
				throw new Exception("Did not find gamma vertex with name " + tGammaVertex.getName());
			}
		}
		
		// TODO: Create G1G2 graph
		
		EdgeFactory<GVertex,GEdge> gEF = 
				new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		DirectedPseudograph<GVertex,GEdge> prodG = 
				new DirectedPseudograph<GVertex,GEdge>(gEF);
		Map<String,GVertex> prodGVertexMap = new HashMap<String,GVertex>();
		
		for(GVertex tGVertex : tGVertices) {
			prodG.addVertex(tGVertex);
			prodGVertexMap.put(tGVertex.getName(), tGVertex);
		}
		
		
		for(GVertex tGVertex : tGVertices) {
			Set<GEdge> tGVertexOutEdges = tG.outgoingEdgesOf(tGVertex);
			for(GEdge tGVertexOutEdge : tGVertexOutEdges) {
				GVertex tgvoeTargetVertex = sGVertexMap.get(tG.getEdgeTarget(tGVertexOutEdge).getName());
				Set<GEdge> tgvoeTargetVertexOutEdges = sG.outgoingEdgesOf(tgvoeTargetVertex);
				for(GEdge tgvoeTargetVertexOutEdge : tgvoeTargetVertexOutEdges) {
					GEdge prodEdge = new GEdge(tGVertex.getName(),
												tgvoeTargetVertex.getName(),
												tGVertexOutEdge.getName() + tgvoeTargetVertexOutEdge.getName());
					prodG.addEdge(prodGVertexMap.get(tGVertex.getName()),
									prodGVertexMap.get(tgvoeTargetVertex.getName()),
									prodEdge);
				}
			}
		}
		
		EdgeFactory<GammaVertex,GammaEdge> gammaEF = 
				new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		DirectedPseudograph<GammaVertex,GammaEdge> prodGamma = 
				new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		Map<String,GammaVertex> prodGammaVertexMap = new HashMap<String,GammaVertex>();

		
		
		for(GammaVertex tGammaVertex : tGammaVertices) {
			prodGamma.addVertex(tGammaVertex);
			prodGammaVertexMap.put(tGammaVertex.getName(), tGammaVertex);
		}
		
		
		for(GammaVertex tGammaVertex : tGammaVertices) {
			Set<GammaEdge> tGammaVertexOutEdges = tGamma.outgoingEdgesOf(tGammaVertex);
			for(GammaEdge tGammaVertexOutEdge : tGammaVertexOutEdges) {
				GammaVertex tgvoeTargetVertex = sGammaVertexMap.get(tGamma.getEdgeTarget(tGammaVertexOutEdge).getName());
				Set<GammaEdge> tgvoeTargetVertexOutEdges = sGamma.outgoingEdgesOf(tgvoeTargetVertex);
				for(GammaEdge tgvoeTargetVertexOutEdge : tgvoeTargetVertexOutEdges) {
					GammaEdge prodEdge = new GammaEdge(tGammaVertex.getName(),
														tgvoeTargetVertex.getName(),
														tGammaVertexOutEdge.getPEHom() + tgvoeTargetVertexOutEdge.getPEHom(),
														tGammaVertexOutEdge.getQEHom() + tgvoeTargetVertexOutEdge.getQEHom(),
														tGammaVertexOutEdge.getName() + tgvoeTargetVertexOutEdge.getName());
					prodGamma.addEdge(prodGammaVertexMap.get(tGammaVertex.getName()),
									prodGammaVertexMap.get(tgvoeTargetVertex.getName()),
									prodEdge);
				}
			}
		}
		
		
		return new Textile(prodGamma, prodG);
	}
	
	public static Textile createHigherBlockTextile(Textile T, Integer n) {	
		return new Textile(createHigherBlockGraph(T.getGammaGraph(),n), createHigherBlockGraph(T.getGGraph(),n));
	}
	
	private static DirectedPseudograph<GammaVertex,GammaEdge> createHigherBlockGammaGraph(DirectedPseudograph<GammaVertex,GammaEdge> gamma, DirectedPseudograph<GVertex,GEdge> gHigherBlock, Integer n) {
		
		// Implement list to edge constructor for edges and vertices
		Set<GammaEdge> eSet = gamma.edgeSet();
		
		Stack<List<GammaEdge>> pathStack = new Stack<List<GammaEdge>>();  
		
		for(GammaEdge e : eSet ) {
			List<GammaEdge> seedList = new ArrayList<GammaEdge>();
			seedList.add(e);
			pathStack.push(seedList);
		}
		
		while(!pathStack.empty()) {
			List<GammaEdge> currentList = pathStack.pop();
			if(currentList.size() == n) {
			//	V source = new V(currentList.subList(0,n-2));
			}
		}
		
		
		
		return gamma;
	}
	
}
