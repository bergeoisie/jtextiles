package edu.umd.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

import edu.umd.math.GammaGraph.GammaGraphBuilder;

/**
 * @author brberg
 *
 */
public class TextileBuilder {
	
	static final Logger logger = LogManager.getLogger(TextileBuilder.class.getName());

	public static Textile createDual(Textile t) {
		GammaGraph gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = t.getGGraph();
		

		EdgeFactory<GVertex,GEdge> gEF = 
				new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);

		GammaGraphBuilder ggb = new GammaGraphBuilder();

		DirectedPseudograph<GVertex,GEdge> gT = 
				new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		Set<GammaEdge> gammaEdges = gamma.edgeSet();
		Set<GammaVertex> gammaVertices = gamma.vertexSet();
		
		Set<GEdge> gEdges = g.edgeSet();
		Set<GVertex> gVertices = g.vertexSet();
	
		Map<String,GammaVertex> nameToGammaVertex = new HashMap<String,GammaVertex>();
		
		for(GEdge ge : gEdges) {
			GammaVertex gav = new GammaVertex(g.getEdgeSource(ge),g.getEdgeTarget(ge),ge.getName());
			ggb.addVertex(gav);
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
			ggb.addEdge(sv, tv, e);
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
		
		GammaGraph gammaT = ggb.build();
		
		return new Textile(gammaT,gT);
	}
	
	public static Textile createInverse(Textile t) {
		GammaGraph gamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> g = 
				(DirectedPseudograph<GVertex, GEdge>) t.getGGraph().clone();

		GammaGraphBuilder ggb = new GammaGraphBuilder();
		
		
		Set<GammaEdge> gammaEdges = gamma.edgeSet();
		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Map<String,GammaVertex> nameToGammaVertex = new HashMap<String,GammaVertex>();
		
		for(GammaVertex gammaV : gammaVertices) {
			GammaVertex inverseGammaV = new GammaVertex(gammaV.getPVHom(),
														gammaV.getQVHom(),
														gammaV.getName());
			ggb.addVertex(inverseGammaV);
			nameToGammaVertex.put(gammaV.getName(), inverseGammaV);
		}
		
		for(GammaEdge gammaE : gammaEdges) {
			GammaEdge inverseGammaE = new GammaEdge(gammaE.getSourceName(),
													gammaE.getTargetName(),
													gammaE.getQEHom(),
													gammaE.getPEHom(),
													gammaE.getName());
			ggb.addEdge(nameToGammaVertex.get(gammaE.getSourceName()), 
									nameToGammaVertex.get(gammaE.getTargetName()), 
									inverseGammaE);
		}
		
		GammaGraph inverseGamma = ggb.build();
		
		return new Textile(inverseGamma,g);
	}
	
	
	
	public static Textile createProductTextile(Textile t, Textile s) throws Exception {
		
		GammaGraph tGamma = t.getGammaGraph();
		DirectedPseudograph<GVertex,GEdge> tG = t.getGGraph();

		GammaGraph sGamma = s.getGammaGraph();		
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
		
	
		Map<String,GammaVertex> prodGammaVertexMap = new HashMap<String,GammaVertex>();

		GammaGraphBuilder prodGammaBuilder = new GammaGraphBuilder();
		
		for(GammaVertex tGammaVertex : tGammaVertices) {
			prodGammaBuilder.addVertex(tGammaVertex);
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
					prodGammaBuilder.addEdge(prodGammaVertexMap.get(tGammaVertex.getName()),
									prodGammaVertexMap.get(tgvoeTargetVertex.getName()),
									prodEdge);
				}
			}
		}
		
		GammaGraph prodGamma = prodGammaBuilder.build();
		
		return new Textile(prodGamma, prodG);
	}
	
	public static Textile createHigherBlockTextile(Textile T, Integer n) {
		DirectedPseudograph<GVertex,GEdge> higherG =  createHigherBlockGraph(T.getGGraph(),n);
		GammaGraph higherGamma;
		try{
			higherGamma = createHigherBlockGammaGraph(T.getGammaGraph(),higherG,n);
		}
		catch(Exception e) {
			return T;
		}
		return new Textile(higherGamma,higherG);
	}
	
	private static GammaGraph createHigherBlockGammaGraph(GammaGraph gamma,
														DirectedPseudograph<GVertex,GEdge> gHigherBlock,
														Integer n) throws Exception {

		GammaGraphBuilder ggb = new GammaGraphBuilder();
	
		// Implement list to edge constructor for edges and vertices
		Set<GammaEdge> eSet = gamma.edgeSet();
		
		Stack<List<GammaEdge>> pathStack = new Stack<List<GammaEdge>>();  
		
		for(GammaEdge e : eSet ) {
			List<GammaEdge> seedList = new ArrayList<GammaEdge>();
			seedList.add(e);
			pathStack.push(seedList);
			logger.debug("Adding " + e.getName() + " to the path stack.");
		}
		
		Map<String,GammaVertex> gammaVertexNameMap = new HashMap<String,GammaVertex>();
		Map<String,GVertex> gVertexNameMap = new HashMap<String,GVertex>();
		
		for(GVertex g : gHigherBlock.vertexSet()) {
			gVertexNameMap.put(g.getName(), g);
		}
		
		
		while(!pathStack.empty()) {
			List<GammaEdge> currentList = pathStack.pop();
			logger.debug("Popping " +listToName(currentList));
			if(currentList.size() == n) {
				String sourceName = listToName(currentList.subList(0, n-1));
				String targetName = listToName(currentList.subList(1, n));
				
				GammaVertex s = gammaVertexNameMap.get(sourceName);
				
				if(s == null) {
					GVertex sourceP = gVertexNameMap.get(listToPName(currentList.subList(0, n-1)));
					GVertex sourceQ = gVertexNameMap.get(listToQName(currentList.subList(0, n-1)));
					if(sourceP == null || sourceQ == null) {
						throw new Exception("Unlying G Vertex not found");
					}
					s = new GammaVertex(sourceP,sourceQ,sourceName);
					ggb.addVertex(s);
					gammaVertexNameMap.put(sourceName, s);
				}
				
				GammaVertex t = gammaVertexNameMap.get(targetName);
				
				if(t == null) {
					GVertex targetP = gVertexNameMap.get(listToPName(currentList.subList(1, n)));
					GVertex targetQ = gVertexNameMap.get(listToQName(currentList.subList(1, n)));
					if(targetP == null || targetQ == null) {
						throw new Exception("Unlying G Vertex not found");
					}
					t = new GammaVertex(targetP,targetQ,targetName);
					ggb.addVertex(t);
					gammaVertexNameMap.put(targetName, t);
				}
				
				GammaEdge higherBlockGammaEdge = new GammaEdge(s.getName(), t.getName(),
						listToPName(currentList),listToQName(currentList),listToName(currentList));
				ggb.addEdge(s,t,higherBlockGammaEdge);
			}
			else {
				GammaEdge last = currentList.get(currentList.size()-1);
				Set<GammaEdge> outLast = gamma.outgoingEdgesOf(gamma.getEdgeTarget(last));
				for(GammaEdge e : outLast) {
					List<GammaEdge> newList = new ArrayList<GammaEdge>(currentList);
					newList.add(e);
					pathStack.push(newList);
				}
			}
		}
		return ggb.build();
	}
	
	private static DirectedPseudograph<GVertex,GEdge> createHigherBlockGraph(DirectedPseudograph<GVertex,GEdge> g, Integer n) {
		EdgeFactory<GVertex,GEdge> gEF = 
				new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		DirectedPseudograph<GVertex,GEdge> higherBlockG = 
				new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		// Implement list to edge constructor for edges and vertices
		Set<GEdge> eSet = g.edgeSet();
		
		Stack<List<GEdge>> pathStack = new Stack<List<GEdge>>();  
		
		for(GEdge e : eSet ) {
			List<GEdge> seedList = new ArrayList<GEdge>();
			seedList.add(e);
			pathStack.push(seedList);
		}
		
		Map<String,GVertex> gVertexNameMap = new HashMap<String,GVertex>();
		
		while(!pathStack.empty()) {
			List<GEdge> currentList = pathStack.pop();
			logger.debug("Popping " +listToName(currentList));
			if(currentList.size() == n) {
				logger.debug("Our list is of the appropriate length, adding new edge");
				String sourceName = listToName(currentList.subList(0, n-1));
				String targetName = listToName(currentList.subList(1, n));
				
				logger.debug("Source name is " + sourceName + " and target name is " + targetName);
				
				GVertex s = gVertexNameMap.get(sourceName);

				
				
				if(s == null) {
					logger.debug("Could not find existing vertex, adding " + sourceName);
					s = new GVertex(sourceName);
					higherBlockG.addVertex(s);
					gVertexNameMap.put(sourceName, s);
				}

				GVertex t = gVertexNameMap.get(targetName);
				
				if(t == null) {
					logger.debug("Could not find existing vertex, adding " + targetName);
					t = new GVertex(targetName);
					higherBlockG.addVertex(t);
					gVertexNameMap.put(targetName, t);
				}
				
				GEdge higherBlockGEdge = new GEdge(s.getName(), t.getName(),listToName(currentList));
				higherBlockG.addEdge(s,t,higherBlockGEdge);
			}
			else {
				GEdge last = currentList.get(currentList.size()-1);
				logger.debug("Building new path using " + last.getName());
				Set<GEdge> outLast = g.outgoingEdgesOf(g.getEdgeTarget(last));
				logger.debug("There are " + outLast.size() + " outgoing edges");
				for(GEdge e : outLast) {
					List<GEdge> newList = new ArrayList<GEdge>(currentList);
					newList.add(e);
					pathStack.push(newList);
				}
			}
		}
		return higherBlockG;
	}
	

	private static <E extends Edge> String listToName(List<E> l) {
		
		StringBuilder sb = new StringBuilder();
		for(E edge : l) {
			sb.append(edge.getName());
		}
		return sb.toString();
	}
	
	private static String listToPName(List<GammaEdge> l) {
		
		StringBuilder sb = new StringBuilder();
		for(GammaEdge edge : l) {
			sb.append(edge.getPEHom());
		}
		return sb.toString();
	}

	private static String listToQName(List<GammaEdge> l) {
		
		StringBuilder sb = new StringBuilder();
		for(GammaEdge edge : l) {
			sb.append(edge.getQEHom());
		}
		return sb.toString();
	}
	
	public static Textile inducedRightResolvingP(Textile T) {
		return T;
	}
	
}