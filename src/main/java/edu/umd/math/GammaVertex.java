package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class GammaVertex extends Vertex{

	private final GVertex pv;
	private final GVertex qv;
	
	
	public String getPVHomName() {
		return pv.getName();
	}
	
	public String getQVHomName() {
		return qv.getName();
	}
	
	public GVertex getPVHom() {
		return pv;
	}
	
	public GVertex getQVHom() {
		return qv;
	}
	
	public GammaVertex(GVertex p, GVertex q, String n) {
		super(n);
		pv = p;
		qv = q;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GammaVertex))
            return false;
        if (obj == this)
            return true;

        GammaVertex rhs = (GammaVertex) obj;
        return new EqualsBuilder().
            append(this.getName(), rhs.getName()).
            append(pv.getName(), rhs.pv.getName()).
            append(qv.getName(), rhs.qv.getName()).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(19,39).append(this.getName())
								.append(pv.getName())
								.append(qv.getName())
								.toHashCode();
	}

	@Override
    public String toString() {
        return "GammaVertex: " + this.getName() + " p: " + pv.getName() + " q: " + qv.getName();
    }
}
