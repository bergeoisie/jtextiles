package edu.umd.math;

public class GammaVertex extends Vertex{

	private GVertex pv;
	private GVertex qv;
	
	
	public String getPVHomName() {
		return pv.getName();
	}
	
	public String getQVHomName() {
		return qv.getName();
	}
	
	public GVertex getPVHom() {
		return pv;
	}
	
	public GVertex getQVHom() {
		return qv;
	}
	
	public GammaVertex(GVertex p, GVertex q, String n) {
		pv = p;
		qv = q;
		name = n;
	}
	
}
