package edu.umd.math;

public class GammaVertex extends Vertex{

	private String pv;
	private String qv;
	
	
	public String getPVHom() {
		return pv;
	}
	
	public String getQVHom() {
		return qv;
	}
	
	public GammaVertex(String p, String q, String n) {
		pv = p;
		qv = q;
		name = n;
	}
	
}
