package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LabeledVertex extends Vertex {
	private GVertex lv;

	public String getHomName() {
		return lv.getName();
	}
	
	public GVertex getHom() {
		return lv;
	}

	public LabeledVertex(GVertex l, String n) {
		super(n);
		lv = l;
	}

	@Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LabeledVertex))
            return false;
        if (obj == this)
            return true;

        LabeledVertex rhs = (LabeledVertex) obj;
        return new EqualsBuilder().
                append(this.getName(), rhs.getName()).
                append(this.getHomName(), rhs.getHomName()).
                isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(21,31).append(this.getName())
                .append(this.getHomName())
                .toHashCode();

    }
}
