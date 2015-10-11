package edu.umd.math;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

	private static final long serialVersionUID = 3147205887399516360L;

	protected String name;
	
	protected String sName;
	protected String tName;
	
	public String getName() {
		return name;
	}
	
	public String getSourceName() {
		return sName;
	}
	
	public String getTargetName() {
		return tName;
	}
	
	
}
