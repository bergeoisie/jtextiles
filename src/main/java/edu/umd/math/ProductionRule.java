package edu.umd.math;

import com.google.common.base.Optional;

public class ProductionRule {

	private GammaVertex from;
	private GammaVertex to;
	
	public ProductionRule(GammaVertex from, GammaVertex to) {
		this.from = from;
		this.to = to;
	}
	
	public Optional<GammaVertex> applyRule(GammaVertex f) {
		GammaVertex gv;
		if(f.equals(from)) {
			gv = to;
		}
		else {
			gv = null;
		}
		
		return Optional.fromNullable(gv);
	}
	
}
