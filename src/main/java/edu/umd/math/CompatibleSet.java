package edu.umd.math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by brberg on 9/20/15.
 */
public class CompatibleSet {

    private static final Logger logger = LogManager.getLogger(CompatibleSet.class.getName());

    public Set<GammaVertex> vertexSet;

    public CompatibleSet(Set<GammaVertex> vertexSet) {
        this.vertexSet = vertexSet;
    }

    public CompatibleSet(GammaVertex singletonVertex) {
        this.vertexSet = new HashSet<>();
        this.vertexSet.add(singletonVertex);
    }

    public static CompatibleSet buildSuccessorSet(Textile textile, CompatibleSet compatibleSet, List<String> word) {
       if(word.size() > 1) {
           List<String> allButLastLabel = word.subList(0, word.size() - 2);
           logger.debug("Original word: ");
           for(String letter : word) {
               logger.debug("\t" + letter);
           }
           CompatibleSet csForAllButLastLabel = buildSuccessorSet(textile, compatibleSet, allButLastLabel);
           return buildSuccessorSet(textile, csForAllButLastLabel, word.get(word.size() - 1));
       } else if (word.size() == 1) {
           return buildSuccessorSet(textile, compatibleSet, word.get(0));
       } else {
           return null;
       }
    }

    public static CompatibleSet buildSuccessorSet(Textile textile, CompatibleSet compatibleSet, String word) {
        GammaGraph gammaGraph = textile.getGammaGraph();
        Set<GammaVertex> successorSet = new HashSet<GammaVertex>();

        for(GammaVertex gammaVertex : compatibleSet.vertexSet) {
            Set<GammaEdge> outgoingEdges = gammaGraph.outgoingEdgesOf(gammaVertex);
            Optional<GammaEdge> labeledEdge = getLabeledEdge(outgoingEdges,word);
            if(labeledEdge.isPresent()) {
                GammaVertex targetVertex = gammaGraph.getEdgeTarget(labeledEdge.get());
                successorSet.add(targetVertex);
            }
        }

        return new CompatibleSet(successorSet);
    }

    private static Optional<GammaEdge> getLabeledEdge(Set<GammaEdge> outgoingEdges, String word) {
        for(GammaEdge edge : outgoingEdges) {
            if(edge.getName().equals(word)) {
                return Optional.of(edge);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        String output = "Compatible Set : {";
        for(GammaVertex vertex : vertexSet) {
            output.concat(" " + vertex.getName());
        }
        output.concat("}");

        return output;
    }
}
