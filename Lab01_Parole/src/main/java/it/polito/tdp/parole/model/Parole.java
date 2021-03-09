package it.polito.tdp.parole.model;

import java.util.*;


public class Parole {
	
	List <String> parole;
		
	public Parole() {
		parole = new LinkedList <String> ();
	}
	
	public void addParola(String p) {
		parole.add(p);
	}
	
	public List<String> getElenco() {
		List <String> ordinate = parole;
		Collections.sort(ordinate); //alfabetico
		return ordinate;
	}
	
	public void reset() {
		parole.clear();
	}

}
