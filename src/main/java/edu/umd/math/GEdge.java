package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GEdge extends Edge {

	private static final long serialVersionUID = -3345887409347580717L;

	public GEdge(String s, String t, String n) {
		super(n,s,t);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GEdge))
            return false;
        if (obj == this)
            return true;

        GEdge rhs = (GEdge) obj;
        return new EqualsBuilder().
            append(this.getName(), rhs.getName()).
            append(this.getSourceName(), rhs.getSourceName()).
            append(this.getTargetName(), rhs.getTargetName()).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).append(this.getName())
								.append(this.getSourceName())
								.append(this.getTargetName())
								.toHashCode();
	}
	
}
