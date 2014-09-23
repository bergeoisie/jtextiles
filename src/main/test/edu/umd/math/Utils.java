package edu.umd.math;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

public class Utils {

	public static Textile generateNasu() {
		
		EdgeFactory<GammaVertex,GammaEdge> gammaEF = new ClassBasedEdgeFactory<GammaVertex,GammaEdge>(GammaEdge.class);
		EdgeFactory<GVertex,GEdge> gEF = new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);
		
		DirectedPseudograph<GammaVertex,GammaEdge> gamma = new DirectedPseudograph<GammaVertex,GammaEdge>(gammaEF);
		DirectedPseudograph<GVertex,GEdge> g = new DirectedPseudograph<GVertex,GEdge>(gEF);
		
		GVertex gv1 = new GVertex("0");
		GVertex gv2 = new GVertex("1");

		g.addVertex(gv1);
		g.addVertex(gv2);
		
		GEdge ge1 = new GEdge("0","0","a");
		GEdge ge2 = new GEdge("0","0","b");
		GEdge ge3 = new GEdge("0","1","c");
		GEdge ge4 = new GEdge("1","0","d");
		GEdge ge5 = new GEdge("1","1","e");
		
		g.addEdge(gv1, gv1, ge1);
		g.addEdge(gv1, gv1, ge2);
		g.addEdge(gv1, gv2, ge3);
		g.addEdge(gv2, gv1, ge4);
		g.addEdge(gv2, gv2, ge5);
		
		GammaVertex gammav1 = new GammaVertex(gv1,gv1,"u");
		GammaVertex gammav2 = new GammaVertex(gv1,gv2,"v");
		GammaVertex gammav3 = new GammaVertex(gv2,gv1,"w");
		
		gamma.addVertex(gammav1);
		gamma.addVertex(gammav2);
		gamma.addVertex(gammav3);
		
		GammaEdge gammae1 = new GammaEdge("u","u","a","a","A");
		GammaEdge gammae2 = new GammaEdge("u","v","a","c","B");
		GammaEdge gammae3 = new GammaEdge("u","w","c","b","C");
		GammaEdge gammae4 = new GammaEdge("v","u","b","d","D");
		GammaEdge gammae5 = new GammaEdge("v","v","b","e","E");
		GammaEdge gammae6 = new GammaEdge("w","u","d","a","F");
		GammaEdge gammae7 = new GammaEdge("w","v","d","c","G");
		GammaEdge gammae8 = new GammaEdge("w","w","e","b","H");

		gamma.addEdge(gammav1, gammav1, gammae1);
		gamma.addEdge(gammav1, gammav2, gammae2);
		gamma.addEdge(gammav1, gammav3, gammae3);
		gamma.addEdge(gammav2, gammav1, gammae4);
		gamma.addEdge(gammav2, gammav2, gammae5);
		gamma.addEdge(gammav3, gammav1, gammae6);
		gamma.addEdge(gammav3, gammav2, gammae7);
		gamma.addEdge(gammav3, gammav3, gammae8);
		
		return new Textile(gamma,g);
	}
	
}
