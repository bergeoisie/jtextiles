package edu.umd.math;

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
}
