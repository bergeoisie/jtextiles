package edu.umd.math;

import java.util.ArrayList;

public class Codex {

	private ArrayList<String> entries;
	
	public Codex() {
		entries = new ArrayList<String>();
	}
	
	public void addEntry(String s) {
		entries.add(s);
	}

	public ArrayList<String> getEntries() {
		return entries;
	}
}
