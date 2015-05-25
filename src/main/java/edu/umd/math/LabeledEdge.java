package edu.umd.math;

public class LabeledEdge extends Edge {

	private String le;
	

	
	public String getEdgeHom() {
		return le;
	}
	
	public LabeledEdge(String s, String t, String l, String n) {
		sName = s;
		tName = t;
		le = l;
		name = n;
	}
}
