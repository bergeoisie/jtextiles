package edu.umd.math;

import java.util.Set;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;
import org.junit.Test;

public class TestSpecHelper {

	@Test
	public void testAddToSpecHelper() {

		EdgeFactory<GVertex,GEdge> gEF = new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		
		DirectedPseudograph<GVertex,GEdge> g = new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		GVertex gv1 = new GVertex("0");
		GVertex gv2 = new GVertex("1");

		g.addVertex(gv1);
		g.addVertex(gv2);
		
		GEdge ge1 = new GEdge("0","0","a");
		GEdge ge2 = new GEdge("0","1","b");
		GEdge ge3 = new GEdge("1","0","c");
		GEdge ge4 = new GEdge("1","0","d");
		
		g.addEdge(gv1, gv1, ge1);
		g.addEdge(gv1, gv2, ge2);
		g.addEdge(gv2, gv1, ge3);
		g.addEdge(gv2, gv2, ge4);
		
		SpecHelper sh = new SpecHelper(); 
		
		EquivEntry ee = new EquivEntry(ge1,ge2,ge3,ge4);
		
		EdgePair ep1 = new EdgePair(ge1,ge4);
		EdgePair ep2 = new EdgePair(ge2,ge3);
		
		sh.addEquivEntry(ee);
		
		Set<EdgePair> sabp = sh.getSeenABPrimePairs();
		Set<EdgePair> sbap = sh.getSeenBAPrimePairs();
		
		org.junit.Assert.assertTrue(sabp.contains(ep1));
		org.junit.Assert.assertTrue(sbap.contains(ep2));
		
	}
	
}
