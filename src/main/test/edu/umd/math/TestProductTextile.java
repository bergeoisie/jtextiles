package edu.umd.math;

import org.jgrapht.graph.DirectedPseudograph;
import org.junit.Test;

public class TestProductTextile {

	@Test
	public void testCreateDual() {
		Textile t = Utils.generateNasu();
		Textile s = Utils.generateNasu();
		
		try {
			Textile prod = TextileBuilder.createProductTextile(t, s);
			DirectedPseudograph<GVertex,GEdge> prodG = prod.getGGraph();
			DirectedPseudograph<GammaVertex,GammaEdge> prodGamma = prod.getGammaGraph();
			
			org.junit.Assert.assertEquals("failure - g vertexset sizes not equal", 2, prodG.vertexSet().size());
			org.junit.Assert.assertEquals("failure - gamma vertexset sizes not equal", 3, prodGamma.vertexSet().size());
		
		
		}
		catch(Exception e) {
			System.out.println("Caught " + e.getMessage());
		}
		
	}
	
}
