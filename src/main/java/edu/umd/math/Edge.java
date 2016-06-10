package edu.umd.math;

import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge {

	private static final long serialVersionUID = 3147205887399516360L;

	private String name;
	
	private String sName;
	private String tName;

	public Edge(String name, String sName, String tName) {
		this.name = name;
		this.sName = sName;
		this.tName = tName;
	}

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
