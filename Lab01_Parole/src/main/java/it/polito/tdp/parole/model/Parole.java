package it.polito.tdp.parole.model;

import java.util.*;


public class Parole {
	
	List <String> parole;
		
	public Parole() { /**SCELGO TIPO DI LIST*/
		//parole = new ArrayList <String> (); //so qual è l'indice da cui cancellare
		parole = new LinkedList <String> ();
	}
	
	public double addParola(String p) {
		double start = System.nanoTime();
		parole.add(p);
		double end = System.nanoTime();
		return end-start;
		
		//ARRAY LIST --> media 600-1000 nanosecondi
		//LINKED LIST --> 2000-7000 nanosec
	}
	
	public List<String> getElenco() {
		List <String> ordinate = parole;
		Collections.sort(ordinate); //alfabetico
		return ordinate;
	}
	
	public double clear(String selected) {
		double start = System.nanoTime();
		parole.remove(selected);
		double end = System.nanoTime();
		return end-start;
		
		//LINKED LIST --> 1000-3000 nano sec
		//ARRAY LIST --> 1000-4000 nano sec
		
	}
	
	public void reset() {
		parole.clear();
	}

}
