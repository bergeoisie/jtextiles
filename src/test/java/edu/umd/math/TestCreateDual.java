package edu.umd.math;

import static org.junit.Assert.*;

import org.jgrapht.graph.DirectedPseudograph;
import org.junit.Test;

public class TestCreateDual {

	@Test
	public void testCreateDual() {
		Textile t = TestTextileUtils.generateNasu();
		
		Textile td = TextileBuilder.createDual(t);

		GGraph tdG = td.getGGraph();
		GammaGraph tdGamma = td.getGammaGraph();
			
		org.junit.Assert.assertEquals("failure - g edgeset sizes not equal", 3, tdG.edgeSet().size());
		org.junit.Assert.assertEquals("failure - gamma edgeset sizes not equal", 8, tdGamma.edgeSet().size());
		
		
		
	}
	

}
