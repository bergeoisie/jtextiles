package edu.umd.math;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Optional;

public class TypeSet {

	private static final Logger logger = LogManager.getLogger(SpecifiedEquivalenceGenerator.class.getName());
	
	private Set<GammaVertex> vertexSet;
	
	public TypeSet() {
		vertexSet = new HashSet<GammaVertex>();	
	}
	
	public TypeSet(Set<GammaVertex> vertexSet) {
		this.vertexSet = vertexSet;
	}
	
	public int getSize() {
		return vertexSet.size();
	}
	
	public void addVertex(GammaVertex v) {
		vertexSet.add(v);
	}
	
	public TypeSet apply(ProductionRule rule) {
		TypeSet result = new TypeSet();
		for(GammaVertex v : vertexSet) {
			Optional<GammaVertex> ruleResult = rule.applyRule(v);
			if(ruleResult.isPresent()) {
				logger.debug("We have applied " 
							+ rule.toString() + " and received " 
							+ ruleResult.get().getName());
				result.addVertex(ruleResult.get());
			}
		}
		return result;
	}
	
	public void mergeIn(TypeSet t) {
		vertexSet.addAll(t.vertexSet);
	}
	
}
