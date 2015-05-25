package edu.umd.math;

import java.util.Set;

import com.google.common.base.Optional;

public class TypeSet {

	Set<GammaVertex> vertexSet;
	
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
				result.addVertex(v);
			}
		}
		return result;
	}
	
	public void mergeIn(TypeSet t) {
		vertexSet.addAll(t.vertexSet);
	}
	
}
