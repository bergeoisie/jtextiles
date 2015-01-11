package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EquivEntry {

	static final Logger logger = LogManager.getLogger(EquivEntry.class.getName());
	
	private GEdge a;
	private GEdge b;
	private GEdge aprime;
	private GEdge bprime;
	
	public EquivEntry(GEdge aa, GEdge bb, GEdge aaprime, GEdge bbprime) {
		a = aa;
		b = bb;
		aprime = aaprime;
		bprime = bbprime;
	}
	
	public GEdge getA() {
		return a;
	}
	
	public GEdge getAPrime() {
		return aprime;
	}
	
	public GEdge getB() {
		return b;
	}
	
	public GEdge getBPrime() {
		return bprime;
	}
	
	public String getName() {
		return a.getName() + bprime.getName() + b.getName() + aprime.getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EquivEntry))
            return false;
        if (obj == this)
            return true;

        EquivEntry rhs = (EquivEntry) obj;
        return new EqualsBuilder().
            append(a.getName(), rhs.a.getName()).
            append(b.getName(), rhs.b.getName()).
            append(aprime.getName(), rhs.aprime.getName()).
            append(bprime.getName(), rhs.bprime.getName()).
            isEquals();
    }
	
	public void printEntry() {
		logger.info("a = " + a.getName() + " b = " + b.getName() 
				+ " aprime = " + aprime.getName() + " bprime = " + bprime.getName());
	}
}
