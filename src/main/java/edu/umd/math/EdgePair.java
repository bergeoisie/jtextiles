package edu.umd.math;

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
	}
