package edu.umd.math;

import java.lang.reflect.Type;
import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.jgrapht.Graph;
import org.jgrapht.alg.cycle.SzwarcfiterLauerSimpleCycles;
import org.jgrapht.graph.DirectedPseudograph;

public class TextileChecks {



	public static <V,E> boolean checkNondegeneracy(Graph<V,E> g) {	 
		Set<V> inits = new HashSet<V>();
		Set<V> tails = new HashSet<V>();

		inits.addAll(g.vertexSet());
		tails.addAll(g.vertexSet());

		for(E e : g.edgeSet()) {
			inits.remove(g.getEdgeSource(e));
			tails.remove(g.getEdgeTarget(e));
		}

		return (inits.isEmpty() && tails.isEmpty());

	}



	public static boolean checkHomomorphisms(Textile t) {

		Map<String,GEdge> edgeMap = new HashMap<String,GEdge>();

		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GEdge> gEdges = g.edgeSet();
		Set<GammaEdge> gammaEdges = gamma.edgeSet();

		for(GEdge e : gEdges) {
			edgeMap.put(e.getName(), e);
		}

		for(GammaEdge e : gammaEdges) {
			GEdge pe = edgeMap.get(e.getPEHom());
			GEdge qe = edgeMap.get(e.getQEHom());

			if( !gamma.getEdgeSource(e).getPVHomName().equals(pe.getSourceName()) ||
					!gamma.getEdgeSource(e).getQVHomName().equals(qe.getSourceName()) ||
					!gamma.getEdgeTarget(e).getPVHomName().equals(pe.getTargetName()) ||
					!gamma.getEdgeTarget(e).getQVHomName().equals(qe.getTargetName()) ) {
				System.out.println("There is a problem with " + e.getName() + " from " +
						e.getSourceName() + " to " + e.getTargetName() );

				return false;
			}
		}

		return true;

	}

	public static boolean isLR(Textile t) {

		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}

			for(GEdge ge : g.outgoingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}					
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}

	public static boolean isQLeftResolving(Textile t) {
		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}
		}
		return true;
	}

	public static boolean isQRightResolving(Textile t) {

		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge : g.outgoingEdgesOf(gammav.getQVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getQEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}

	public static boolean isPLeftResolving(Textile t) {

		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge :  g.incomingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.incomingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;
			}
		}
		return true;
	}

	public static boolean isPRightResolving(Textile t) {

		GammaGraph gamma = t.getGammaGraph();
		GGraph g = t.getGGraph();

		Set<GammaVertex> gammaVertices = gamma.vertexSet();

		Integer matches = 0;

		for(GammaVertex gammav : gammaVertices) {	
			for(GEdge ge : g.outgoingEdgesOf(gammav.getPVHom())) {
				for(GammaEdge gammae : gamma.outgoingEdgesOf(gammav)) {
					if(gammae.getPEHom().equals(ge.getName())) {
						matches++;
					}
				}
				if(!matches.equals(1)) {
					return false;
				}
				matches = 0;

			}			
		}

		return true;
	}

	public static boolean isPRightDefinite(Textile t) {
		
		ProductionRuleMachine prm = createProductionRuleMachineForTextile(t);
		Multimap<Integer, TypeSet> matrix = initializeMatrix(t, x -> x.getPVHom());
		
		DirectedPseudograph<TypeSet,Edge> graph = generateGraphFromHighestLevel(prm, matrix);
		
		// Check for cycles
		SzwarcfiterLauerSimpleCycles<TypeSet,Edge> simpleCycleFinder = new SzwarcfiterLauerSimpleCycles<>(graph);
		List<List<TypeSet> > cycles= simpleCycleFinder.findSimpleCycles();

		return cycles.size() > 0;
	}
	
	private static ProductionRuleMachine createProductionRuleMachineForTextile(Textile t) {
	    GammaGraph gamma = t.getGammaGraph();

        ProductionRuleMachine prm = new ProductionRuleMachine();

        for(GammaEdge e : gamma.edgeSet()) {
            ProductionRule rule = new ProductionRule(gamma.getEdgeSource(e), gamma.getEdgeTarget(e));
            prm.addRule(rule);
        }

        return prm;
	}

	private static Multimap<Integer,TypeSet> initializeMatrix(Textile t, HomomorphismFunction homFunction) {

		Multimap<Integer, TypeSet> matrix =  ArrayListMultimap.create();

		for(GVertex g : t.getGGraph().vertexSet()) {
			TypeSet typeSet = new TypeSet();

			for(GammaVertex gamma : t.getGammaGraph().vertexSet()) {
				if(homFunction.getVHom(gamma).equals(g)) {
					typeSet.addVertex(gamma);
				}
			}

			matrix.put(typeSet.getSize(), typeSet);
		}

		return matrix;
	}

	private static DirectedPseudograph<TypeSet,Edge> generateGraphFromHighestLevel(ProductionRuleMachine prm,
																				   Multimap<Integer,TypeSet> matrix) {
		DirectedPseudograph<TypeSet,Edge> graph = new DirectedPseudograph<TypeSet, Edge>(Edge.class);

		Integer maximumM = Collections.max(matrix.keySet());
		Collection<TypeSet> listOfMaximalTypeSets = matrix.get(maximumM);

		for(TypeSet maximalTypeSet : listOfMaximalTypeSets) {
			graph.addVertex(maximalTypeSet);
		}

		for(TypeSet maximalTypeSet : listOfMaximalTypeSets) {

			TypeSet afterApplication = prm.applyRules(maximalTypeSet);
			if(afterApplication.getSize() == maximumM && listOfMaximalTypeSets.contains(afterApplication)) {
				graph.addEdge(maximalTypeSet, afterApplication);
			}
		}

		return graph;
	}

	private interface HomomorphismFunction {
		GVertex getVHom(GammaVertex gammaVertex);
	}
}
