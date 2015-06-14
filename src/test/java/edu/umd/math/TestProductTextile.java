package edu.umd.math;

import org.jgrapht.graph.DirectedPseudograph;
import org.junit.Test;

public class TestProductTextile {

	@Test
	public void testCreateDual() {
		Textile t = TestTextileUtils.generateNasu();
		Textile s = TestTextileUtils.generateNasu();
		
		try {
			Textile prod = TextileBuilder.createProductTextile(t, s);
			DirectedPseudograph<GVertex,GEdge> prodG = prod.getGGraph();
			GammaGraph prodGamma = prod.getGammaGraph();
			
			
			OutputHelper.printTextile(prod);
			
			org.junit.Assert.assertEquals("failure - g vertexset sizes not equal", 2, prodG.vertexSet().size());
			org.junit.Assert.assertEquals("failure - gamma vertexset sizes not equal", 3, prodGamma.vertexSet().size());
		
		
		}
		catch(Exception e) {
			System.out.println("Caught " + e.getMessage());
		}
		
	}
	
}
