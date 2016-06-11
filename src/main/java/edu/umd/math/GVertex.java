package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class GVertex extends Vertex {
	
	public GVertex(String n) {
		super(n);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GEdge))
            return false;
        if (obj == this)
            return true;

        GVertex rhs = (GVertex) obj;
        return new EqualsBuilder().
            append(this.getName(), rhs.getName()).
            isEquals();
    }
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(21,41).append(this.getName())
								.toHashCode();
	}

}
