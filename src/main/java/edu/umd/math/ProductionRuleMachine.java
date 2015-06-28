package edu.umd.math;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductionRuleMachine {

	private static final Logger logger = LogManager.getLogger(ProductionRuleMachine.class.getName());
	
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
			logger.debug("Applying rule: " + rule.toString());
			TypeSet resultSet = ts.apply(rule);
			allResults.mergeIn(resultSet);
		}
		
		return allResults;
	}
	
	
	
}
