package edu.umd.math;

import java.util.List;

public abstract class Vertex {
	private final String name;
	
	public String getName() {
		return name;
	}
	
	public Vertex(List<Vertex> list) {
		StringBuilder sb = new StringBuilder();
		for(Vertex v : list) {
			sb.append(v.getName());
		}
		name = sb.toString();
	}
	
	public Vertex(String name) {
		this.name = name;
	}
}
