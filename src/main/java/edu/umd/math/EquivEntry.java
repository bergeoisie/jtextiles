package edu.umd.math;

public class EquivEntry {

	private GEdge a;
	private GEdge b;
	private GEdge aprime;
	private GEdge bprime;
	
	public EquivEntry(GEdge aa, GEdge bb, GEdge aaprime, GEdge bbprime) {
		a = aa;
		b = bb;
		aprime = aaprime;
		bprime = bbprime;
	}
	
	public GEdge getA() {
		return a;
	}
	
	public GEdge getAPrime() {
		return aprime;
	}
	
	public GEdge getB() {
		return b;
	}
	
	public GEdge getBPrime() {
		return bprime;
	}
	
	public String getName() {
		return a.getName() + bprime.getName() + b.getName() + aprime.getName();
	}
}
