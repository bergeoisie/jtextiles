package edu.umd.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by brberg on 8/14/16.
 */
public class TestGGraph {
    @Test
    public void TestUVWGraphCreation() {
        GGraph g = TestTextileUtils.createUVWGraph();

        Assert.assertEquals(g.vertexSet().size(),2);
        Assert.assertEquals(g.edgeSet().size(),3);

        Assert.assertTrue(g.getVertexByName("0").isPresent());
        Assert.assertTrue(g.getVertexByName("1").isPresent());

        GVertex zeroVertex = g.getVertexByName("0").get();
        GVertex oneVertex = g.getVertexByName("1").get();

        GEdge uEdge = g.getEdge(zeroVertex, zeroVertex);
        GEdge vEdge = g.getEdge(zeroVertex, oneVertex);
        GEdge wEdge = g.getEdge(oneVertex, zeroVertex);

        Assert.assertEquals(g.getAllEdges(zeroVertex,zeroVertex).size(),1);
        Assert.assertEquals(uEdge.getName(),"u");

    }
}
