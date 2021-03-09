package it.polito.tdp.parole.model;

import java.util.*;


public class Parole {
	
	List <String> parole;
		
	public Parole() {
		parole = new ArrayList <String> (); //so qual è l'indice da cui cancellare
	}
	
	public void addParola(String p) {
		parole.add(p);
	}
	
	public List<String> getElenco() {
		List <String> ordinate = parole;
		Collections.sort(ordinate); //alfabetico
		return ordinate;
	}
	
	public void clear(String selected) {
		parole.remove(selected);
	}
	
	public void reset() {
		parole.clear();
	}

}
