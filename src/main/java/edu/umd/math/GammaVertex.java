package edu.umd.math;

import java.util.List;

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
	
	public GammaVertex(List<GammaVertex> list) {
		StringBuilder nameSb = new StringBuilder();
		for(GammaVertex v : list) {
			nameSb.append(v.getName());
		}
		name = nameSb.toString();
	}
	
}
