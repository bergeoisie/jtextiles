package edu.umd.math;

public class LabeledVertex extends Vertex {
	private GVertex lv;
	
	
	public String getHomName() {
		return lv.getName();
	}
	
	public GVertex getHom() {
		return lv;
	}
	
	
	public LabeledVertex(GVertex l, String n) {
		lv = l;
		name = n;
	}
	
	public LabeledVertex(String n) {
		lv = null;
		name = n;
	}
	
	protected void setVHom(GVertex l) {
		lv = l;
	}
	

}
