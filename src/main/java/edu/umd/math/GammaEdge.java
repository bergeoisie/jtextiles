package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GammaEdge extends Edge {
	private static final long serialVersionUID = 6741324465266679631L;
	
	private String pe;
	private String qe;
	

	
	public String getPEHom() {
		return pe;
	}
	
	public String getQEHom() {
		return qe;
	}
	
	public GammaEdge(String s, String t, String p, String q, String n) {
		sName = s;
		tName = t;
		pe = p;
		qe = q;
		name = n;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GEdge))
            return false;
        if (obj == this)
            return true;

        GammaEdge rhs = (GammaEdge) obj;
        return new EqualsBuilder().
            append(name, rhs.name).
            append(sName, rhs.sName).
            append(tName, rhs.tName).
            append(pe, rhs.pe).
            append(qe, rhs.qe).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).append(name)
								.append(sName)
								.append(tName)
								.append(pe)
								.append(qe)
								.toHashCode();
	}
}
