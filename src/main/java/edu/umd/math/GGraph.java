package edu.umd.math;

import org.jgrapht.EdgeFactory;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedPseudograph;

import java.util.Collection;
import java.util.Set;

/**
 * Created by brberg on 6/28/15.
 */
public class GGraph implements Cloneable {
    private DirectedPseudograph<GVertex, GEdge> gGraph;

    public GGraph(DirectedPseudograph<GVertex, GEdge> gGraph) {
        this.gGraph = gGraph;
    }

    public int inDegreeOf(GVertex v) {
        return gGraph.inDegreeOf(v);
    }

    public int outDegreeOf(GVertex v) {
        return gGraph.outDegreeOf(v);
    }

    public boolean removeAllVertices(Collection<GVertex> toRemove) {
        return gGraph.removeAllVertices(toRemove);
    }

    public boolean removeAllEdges(Collection<GEdge> toRemove) {
        return gGraph.removeAllEdges(toRemove);
    }

    public Set<GVertex> vertexSet() {
        return gGraph.vertexSet();
    }

    public Set<GEdge> edgeSet() {
        return gGraph.edgeSet();
    }

    public static class GGraphBuilder {

        private DirectedPseudograph<GVertex, GEdge> nestedGGraph;

        public GGraphBuilder() {
            EdgeFactory<GVertex,GEdge> gammaEF =
                    new ClassBasedEdgeFactory<GVertex,GEdge>(GEdge.class);

            nestedGGraph = new DirectedPseudograph<GVertex,GEdge>(gammaEF);
        }

        public GGraphBuilder addVertex(GVertex vertex) {
            nestedGGraph.addVertex(vertex);
            return this;
        }

        public GGraphBuilder addEdge(GVertex source, GVertex target, GEdge edge) {
            nestedGGraph.addEdge(source, target, edge);
            return this;
        }

        public GGraph build() {
            return new GGraph(nestedGGraph);
        }
    }

    public Set<GEdge> incomingEdgesOf(GVertex vertex) {
        return gGraph.incomingEdgesOf(vertex);
    }

    public Set<GEdge> outgoingEdgesOf(GVertex vertex) {
        return gGraph.outgoingEdgesOf(vertex);
    }

    public Set<GEdge> getAllEdges(GVertex source, GVertex target) {
        return gGraph.getAllEdges(source, target);
    }

    public GVertex getEdgeTarget(GEdge edge) {
        return gGraph.getEdgeTarget(edge);
    }

    public GVertex getEdgeSource(GEdge edge) {
        return gGraph.getEdgeSource(edge);
    }


}
