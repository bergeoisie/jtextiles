package edu.umd.math;

public class LabeledEdge extends Edge {

	private final String le;
	
	public String getEdgeHom() {
		return le;
	}
	
	public LabeledEdge(String s, String t, String l, String n) {
		super(n,s,t);
		le = l;
	}
}
