package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LabeledEdge extends Edge {

	private final String le;
	
	public String getEdgeHom() {
		return le;
	}
	
	public LabeledEdge(String s, String t, String l, String n) {
		super(n,s,t);
		le = l;
	}

	@Override
	public boolean equals(Object obj) {
        if (!(obj instanceof LabeledEdge))
            return false;
        if (obj == this)
            return true;

        LabeledEdge rhs = (LabeledEdge) obj;
        return new EqualsBuilder().
                append(this.getName(), rhs.getName()).
                append(this.getSourceName(), rhs.getSourceName()).
                append(this.getTargetName(), rhs.getTargetName()).
                append(this.getEdgeHom(), rhs.getEdgeHom()).
                isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17,31).append(this.getName())
				.append(this.getSourceName())
				.append(this.getTargetName())
                .append(this.getEdgeHom())
				.toHashCode();

	}
}
