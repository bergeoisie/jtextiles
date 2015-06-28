package edu.umd.math;

import com.google.common.base.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProductionRule {

    private static final Logger logger = LogManager.getLogger(ProductionRule.class.getName());

	private GammaVertex from;
	private GammaVertex to;
	
	public ProductionRule(GammaVertex from, GammaVertex to) {
		this.from = from;
		this.to = to;
	}
	
	public Optional<GammaVertex> applyRule(GammaVertex f) {
		GammaVertex gv;
        logger.debug("Applying rule: " + this.toString() + " to " + f.toString());
		if(f.equals(from)) {
            logger.debug("We match. Setting output to: " + to.toString());
			gv = to;
		}
		else {
            logger.debug("We do not match. from is : " + from.toString());
			gv = null;
		}
		
		return Optional.fromNullable(gv);
	}
	
	@Override
	public String toString() {
		return "ProductionRule, from: " + from.getName() + " to: " + to.getName();
	}
	
}
