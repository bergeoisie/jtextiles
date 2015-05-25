package edu.umd.math;

import java.util.Set;

public class ProductionRuleMachine {

	private Set<ProductionRule> rules;
	
	public TypeSet applyRules(TypeSet ts) {
		
		TypeSet allResults = new TypeSet();
		
		for(ProductionRule rule : rules) {
			TypeSet resultSet = ts.apply(rule);
			allResults.mergeIn(resultSet);
		}
		
		return allResults;
	}
	
}
