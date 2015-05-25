package edu.umd.math;

import java.util.HashSet;
import java.util.Set;

public class ProductionRuleMachine {

	private Set<ProductionRule> rules;
	
	ProductionRuleMachine(Set<ProductionRule> rules) {
		this.rules = rules;
	}
	
	ProductionRuleMachine() {
		this.rules = new HashSet<ProductionRule>();
	}
	
	public void addRule(ProductionRule p) {
		rules.add(p);
	}
	
	public TypeSet applyRules(TypeSet ts) {
		
		TypeSet allResults = new TypeSet();
		
		for(ProductionRule rule : rules) {
			TypeSet resultSet = ts.apply(rule);
			allResults.mergeIn(resultSet);
		}
		
		return allResults;
	}
	
}
