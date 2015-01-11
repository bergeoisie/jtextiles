package edu.umd.math;

import org.apache.commons.lang3.builder.EqualsBuilder;

class EdgePair {
		GEdge first;
		GEdge second;
		
		public EdgePair(GEdge f, GEdge s) {
			first = f;
			second = s;
		}
		
		public GEdge getFirst() {
			return first;
		}
		
		public GEdge getSecond() {
			return second;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof EdgePair))
	            return false;
	        if (obj == this)
	            return true;

	        EdgePair rhs = (EdgePair) obj;
	        return new EqualsBuilder().
	            append(first.getName(), rhs.first.getName()).
	            append(second.getName(), rhs.second.getName()).
	            isEquals();
	    }
	}
