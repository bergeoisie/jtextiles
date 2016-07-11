package edu.umd.math;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

import edu.umd.math.GammaGraph.GammaGraphBuilder;

public class TestTextileUtils {

	public static Textile generateNasu() {
		

		GammaGraphBuilder gammaBuilder = new GammaGraphBuilder();
		GGraph.GGraphBuilder gBuilder = new GGraph.GGraphBuilder();

		GVertex gv1 = new GVertex("0");
		GVertex gv2 = new GVertex("1");

		gBuilder.addVertex(gv1);
		gBuilder.addVertex(gv2);
		
		GEdge ge1 = new GEdge("0","0","a");
		GEdge ge2 = new GEdge("0","0","b");
		GEdge ge3 = new GEdge("0","1","c");
		GEdge ge4 = new GEdge("1","0","d");
		GEdge ge5 = new GEdge("1","1","e");
		
		gBuilder.addEdge(gv1, gv1, ge1);
		gBuilder.addEdge(gv1, gv1, ge2);
		gBuilder.addEdge(gv1, gv2, ge3);
		gBuilder.addEdge(gv2, gv1, ge4);
		gBuilder.addEdge(gv2, gv2, ge5);
		
		GammaVertex gammav1 = new GammaVertex(gv1,gv1,"u");
		GammaVertex gammav2 = new GammaVertex(gv1,gv2,"v");
		GammaVertex gammav3 = new GammaVertex(gv2,gv1,"w");
		
		gammaBuilder.addVertex(gammav1);
		gammaBuilder.addVertex(gammav2);
		gammaBuilder.addVertex(gammav3);
		
		GammaEdge gammae1 = new GammaEdge("u","u","a","a","A");
		GammaEdge gammae2 = new GammaEdge("u","v","a","c","B");
		GammaEdge gammae3 = new GammaEdge("u","w","c","b","C");
		GammaEdge gammae4 = new GammaEdge("v","u","b","d","D");
		GammaEdge gammae5 = new GammaEdge("v","v","b","e","E");
		GammaEdge gammae6 = new GammaEdge("w","u","d","b","F");
		GammaEdge gammae7 = new GammaEdge("w","v","d","c","G");
		GammaEdge gammae8 = new GammaEdge("w","w","e","a","H");

		gammaBuilder.addEdge(gammav1, gammav1, gammae1);
		gammaBuilder.addEdge(gammav1, gammav2, gammae2);
		gammaBuilder.addEdge(gammav1, gammav3, gammae3);
		gammaBuilder.addEdge(gammav2, gammav1, gammae4);
		gammaBuilder.addEdge(gammav2, gammav2, gammae5);
		gammaBuilder.addEdge(gammav3, gammav1, gammae6);
		gammaBuilder.addEdge(gammav3, gammav2, gammae7);
		gammaBuilder.addEdge(gammav3, gammav3, gammae8);
		
		GGraph g = gBuilder.build();
		GammaGraph gamma = gammaBuilder.build();


		return new Textile(gamma,g);
	}

	public static Textile generateNasuTNaught() {
		GammaGraphBuilder gammaBuilder = new GammaGraphBuilder();
		GGraph.GGraphBuilder gBuilder = new GGraph.GGraphBuilder();

		GVertex gv1 = new GVertex("0");
		GVertex gv2 = new GVertex("1");

		gBuilder.addVertex(gv1);
		gBuilder.addVertex(gv2);

		GEdge ge1 = new GEdge("0","0","a");
		GEdge ge2 = new GEdge("0","0","b");
		GEdge ge3 = new GEdge("0","1","c");
		GEdge ge4 = new GEdge("1","0","d");
		GEdge ge5 = new GEdge("1","1","e");

		gBuilder.addEdge(gv1, gv1, ge1);
		gBuilder.addEdge(gv1, gv1, ge2);
		gBuilder.addEdge(gv1, gv2, ge3);
		gBuilder.addEdge(gv2, gv1, ge4);
		gBuilder.addEdge(gv2, gv2, ge5);

		GammaVertex gammav1 = new GammaVertex(gv1,gv1,"u");
		GammaVertex gammav2 = new GammaVertex(gv1,gv2,"v");
		GammaVertex gammav3 = new GammaVertex(gv2,gv1,"w");

		gammaBuilder.addVertex(gammav1);
		gammaBuilder.addVertex(gammav2);
		gammaBuilder.addVertex(gammav3);

		GammaEdge gammae1 = new GammaEdge("u","u","a","a","A");
		GammaEdge gammae2 = new GammaEdge("u","v","a","c","B");
		GammaEdge gammae3 = new GammaEdge("u","w","c","b","C");
		GammaEdge gammae4 = new GammaEdge("v","u","b","d","D");
		GammaEdge gammae5 = new GammaEdge("v","v","b","e","E");
		GammaEdge gammae6 = new GammaEdge("w","u","d","a","F");
		GammaEdge gammae7 = new GammaEdge("w","v","d","c","G");
		GammaEdge gammae8 = new GammaEdge("w","w","e","b","H");

		gammaBuilder.addEdge(gammav1, gammav1, gammae1);
		gammaBuilder.addEdge(gammav1, gammav2, gammae2);
		gammaBuilder.addEdge(gammav1, gammav3, gammae3);
		gammaBuilder.addEdge(gammav2, gammav1, gammae4);
		gammaBuilder.addEdge(gammav2, gammav2, gammae5);
		gammaBuilder.addEdge(gammav3, gammav1, gammae6);
		gammaBuilder.addEdge(gammav3, gammav2, gammae7);
		gammaBuilder.addEdge(gammav3, gammav3, gammae8);

		GGraph g = gBuilder.build();
		GammaGraph gamma = gammaBuilder.build();


		return new Textile(gamma,g);
	}

	public static GGraph createUVWGraph() {
        GGraph.GGraphBuilder gBuilder = new GGraph.GGraphBuilder();

        GVertex gv1 = new GVertex("0");
        GVertex gv2 = new GVertex("1");

        gBuilder.addVertex(gv1);
        gBuilder.addVertex(gv2);

        GEdge ge1 = new GEdge("0","0","u");
        GEdge ge2 = new GEdge("0","1","v");
        GEdge ge3 = new GEdge("1","0","w");

        return gBuilder.build();
    }

    public static GGraph createXYZGraph() {
        GGraph.GGraphBuilder gBuilder = new GGraph.GGraphBuilder();

        GVertex gv1 = new GVertex("0");
        GVertex gv2 = new GVertex("1");

        gBuilder.addVertex(gv1);
        gBuilder.addVertex(gv2);

        GEdge ge1 = new GEdge("0","0","x");
        GEdge ge2 = new GEdge("0","1","y");
        GEdge ge3 = new GEdge("1","0","z");

        return gBuilder.build();
    }

}
