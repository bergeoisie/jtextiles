package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GammaEdge extends Edge {
	private static final long serialVersionUID = 6741324465266679631L;
	
	private final String pe;
	private final String qe;
	

	
	public String getPEHom() {
		return pe;
	}
	
	public String getQEHom() {
		return qe;
	}
	
	public GammaEdge(String s, String t, String p, String q, String n) {
		super(n,s,t);
		pe = p;
		qe = q;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GEdge))
            return false;
        if (obj == this)
            return true;

        GammaEdge rhs = (GammaEdge) obj;
        return new EqualsBuilder().
            append(this.getName(), rhs.getName()).
            append(this.getSourceName(), rhs.getSourceName()).
            append(this.getTargetName(), rhs.getTargetName()).
            append(pe, rhs.pe).
            append(qe, rhs.qe).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).append(this.getName())
								.append(this.getSourceName())
								.append(this.getTargetName())
								.append(pe)
								.append(qe)
								.toHashCode();
	}
}
