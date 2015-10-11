package edu.umd.math;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Brendan on 9/12/2015.
 */
public class TestInducedResolvingMaps {

    @Test
    public void testInducedRightResolving() {

    }

    @Test
    public void testInducedLeftResolving() {

    }

    @Test
    public void testRightResolvingCheck() {
        Textile tNaught = TestTextileUtils.generateNasuTNaught();
        Textile t = TestTextileUtils.generateNasu();

        Assert.assertFalse(TextileChecks.isPRightResolving(tNaught));
        Assert.assertFalse(TextileChecks.isPRightResolving(t));
        Assert.assertTrue(TextileChecks.isQRightResolving(tNaught));
        Assert.assertTrue(TextileChecks.isQRightResolving(t));
    }

    @Test
    public void testLeftResolvingCheck() {
        Textile tNaught = TestTextileUtils.generateNasuTNaught();
        Textile t = TestTextileUtils.generateNasu();

        Assert.assertFalse(TextileChecks.isQLeftResolving(tNaught));
        Assert.assertFalse(TextileChecks.isQLeftResolving(t));
        Assert.assertTrue(TextileChecks.isPLeftResolving(tNaught));
        Assert.assertTrue(TextileChecks.isPLeftResolving(t));
    }
}
