package edu.umd.math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brendan on 6/11/2016.
 */
public class TestSpecifiedEquivalence {

    private static final Logger logger = LogManager.getLogger(TestSpecifiedEquivalence.class);

    @Test
    public void testSpecifiedEquivalenceToTextile() {

        List<EquivEntry> kEquivEntryList = new ArrayList<>();

        GGraph p = TestTextileUtils.createUVWGraph();
        GGraph q = TestTextileUtils.createXYZGraph();

        GVertex pZeroVertex = p.getVertexByName("0").get();
        GVertex pOneVertex = p.getVertexByName("1").get();

        GVertex qZeroVertex = q.getVertexByName("0").get();
        GVertex qOneVertex = q.getVertexByName("1").get();

        GEdge uEdge = p.getEdge(pZeroVertex, pZeroVertex);
        GEdge vEdge = p.getEdge(pZeroVertex, pOneVertex);
        GEdge wEdge = p.getEdge(pOneVertex, pZeroVertex);

        GEdge xEdge = q.getEdge(qZeroVertex, qZeroVertex);
        GEdge yEdge = q.getEdge(qZeroVertex, qOneVertex);
        GEdge zEdge = q.getEdge(qOneVertex, qZeroVertex);


        EquivEntry xuux = new EquivEntry(xEdge,uEdge,uEdge,xEdge);
        EquivEntry ywvz = new EquivEntry(yEdge,wEdge,vEdge,zEdge);
        EquivEntry xvuy = new EquivEntry(xEdge,vEdge,uEdge,yEdge);
        EquivEntry zuwx = new EquivEntry(zEdge,uEdge,wEdge,xEdge);
        EquivEntry zvwy = new EquivEntry(zEdge,vEdge,wEdge,yEdge);

        kEquivEntryList.add(xuux);
        kEquivEntryList.add(ywvz);
        kEquivEntryList.add(xvuy);
        kEquivEntryList.add(zuwx);
        kEquivEntryList.add(zvwy);

        kEquivEntryList.forEach(entry -> entry.printEntry());

        SpecifiedEquivalence kSpecEquiv = new SpecifiedEquivalence(p,q,kEquivEntryList);
        Textile t = kSpecEquiv.toTextile();

    }
}
