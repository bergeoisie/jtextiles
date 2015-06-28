package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GEdge extends Edge {

	private static final long serialVersionUID = -3345887409347580717L;

	public GEdge(String s, String t, String n) {
		sName = s;
		tName = t;
		name = n;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GEdge))
            return false;
        if (obj == this)
            return true;

        GEdge rhs = (GEdge) obj;
        return new EqualsBuilder().
            append(name, rhs.name).
            append(sName, rhs.sName).
            append(tName, rhs.tName).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).append(name)
								.append(sName)
								.append(tName)
								.toHashCode();
	}
	
}
