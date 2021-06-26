package it.polito.tdp.parole;

import javafx.application.Application;
import static javafx.application.Application.launch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Team;
import it.polito.tdp.food.db.DBConnect;
import it.polito.tdp.food.db.FoodDao;
import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Event;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Simulatore;
import it.polito.tdp.food.model.Stazione;
import it.polito.tdp.food.model.Event.EventType;
import it.polito.tdp.imdb.model.Actor;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Model {
	
	/******************* CONTROLLER ********************/
	
	//CREA GRAFO
	
	txtResult.clear();
	Integer n=0;
	
	try {
		n=Integer.parseInt(txtPorzioni.getText());
		if(n==null) {
			txtResult.setText("Inserisci un numero intero di ...");
			return;
		}
	}catch(NumberFormatException nfe) {
		txtResult.setText("Inserisci un numero intero di ...");
		return;
	}catch(NullPointerException npe) {
		txtResult.setText("Inserisci un numero intero di ...");
		return;
	}
	
	model.creaGrafo(porzioni);
	txtResult.appendText("Caratteristiche del grafo:\n#VERTICI = "+model.getNVertici()+"\n#ARCHI = "+model.getNArchi());
	
	,,,,,,.getItems().addAll(model.getVertici());
	
	
	//CONTROLLA SE ESISTE GRAFO
	if(model.getGraph()==null) {
		txtResult.setText("Devi prima creare il grafo!");
		return;
	}
	
	
	/******************* MODEL ****************************/
	private SimpleWeightedGraph<  , DefaultWeightedEdge>graph;
	private Map <Integer,   > idMap;
	private DAO dao;
	private Simulatore sim;
	
	public Model() {
		idMap= new HashMap <Integer,  >();
		dao=new FoodDao();
	}
	
	public void creaGrafo(Integer n) {
		graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		dao.loadAllVertici(idMap, ..);
		Graphs.addAllVertices(graph, idMap.values());
		
		for(Arco a : dao.listArchi(idMap))
			Graphs.addEdge(graph, ,,,,.);
		
		
	}
	
	public Integer getNVertici() {
		return graph.vertexSet().size();
	}
	
	public Integer getNArchi() {
		return graph.edgeSet().size();
	}
	
	public List <> getVertici(){
		List <> vertici = new ArrayList <>();
		for(     : graph.vertexSet())
			vertici.add(  );
		Collections.sort(vertici);
		return vertici;
	}

	public SimpleWeightedGraph<  , DefaultWeightedEdge> getGraph() {
		return graph;
	}
	
	
	// GET VERTICI RAGGIUNGIBILI ///////////
	public List<Actor> getVicini(Actor scelto) {
		if(!graph.vertexSet().contains(scelto))
			throw new RuntimeException("L'attore scelto non fa parte del grafo");
		List <Actor> vicini = new ArrayList <Actor>();
		
		//BreadthFirstIterator
		
				GraphIterator<Actor,DefaultWeightedEdge> bfi = new BreadthFirstIterator <Actor,DefaultWeightedEdge> (graph,scelto);
				while(bfi.hasNext()) 
						vicini.add(bfi.next());
				
				return vicini;
	} ////NON STAMPARE IL VERTICE ORIGINARIO --> sennò significa che può raggiungere se stesso!!!
	
	
	/****************** DAO ******************************/
	
	public void loadAllVertici(Map <Integer, Vertici> idMap, Integer n){
		
	
		String sql = "" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, n);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getInt(""))) {
					
					idMap.put(res.getInt(""), );
					System.out.println();
				}//if
				
			}//while
			
			conn.close();
			return  ;

		} catch (SQLException e) {
			e.printStackTrace();
			return  ;
		}

	}


public List <Arco> listArchi(Map <Integer,  Match > idMap, Integer MIN){
	String sql = "SELECT m1.MatchID as match1, m2.MatchID as match2, COUNT(DISTINCT a1.PlayerID) as peso "
			+ "FROM Matches as m1,Matches as m2, Actions as a1, Actions as a2 "
			+ "WHERE m1.MatchID<>m2.MatchID and m2.MatchID=a2.MatchID and m1.MatchID=a1.MatchID and a1.PlayerID=a2.PlayerID and a1.TimePlayed>? "
			+ "GROUP BY  m1.MatchID, m2.MatchID";

	Connection conn = DBConnect.getConnection();
	List <Arco> archi = new ArrayList <Arco>();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, MIN);
		ResultSet res = st.executeQuery();
		while (res.next()) {

			Match m1 = idMap.get(res.getInt("match1"));
			Match m2 = idMap.get(res.getInt("match2"));
			
			if(m1!=null & m2!=null & res.getInt("peso")>0) {
				Arco a = new Arco(m1,m2,res.getInt("peso"));
				archi.add(a);
				System.out.println(a);
			}
		

		}
		conn.close();
		return archi;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
}


//per VERTICI == STRING
public void loadAllVertici(List <String> vertici, String cat, Integer year){
	
	
	String sql = "SELECT DISTINCT offense_type_id as reato "
			+ "FROM events "
			+ "WHERE offense_category_id=? and YEAR(reported_date)=?" ;
	try {
		Connection conn = DBConnect.getConnection() ;

		PreparedStatement st = conn.prepareStatement(sql) ;
		st.setString(1, cat);
		st.setInt(2, year);
		
		ResultSet res = st.executeQuery() ;
		
		while(res.next()) {
			if(!vertici.contains(res.getString("reato"))) {
				
				vertici.add(res.getString("reato"));
				System.out.println();
			}//if
			
		}//while
		
		conn.close();
		return  ;

	} catch (SQLException e) {
		e.printStackTrace();
		return  ;
	}

}


public List <Arco> listArchi(List <String>vertici, String cat, Integer year){
String sql = "SELECT e1.offense_type_id as id1, e2.offense_type_id as id2, COUNT(DISTINCT e1.district_id) as peso "
		+ "FROM events as e1, events as e2 "
		+ "WHERE e1.offense_type_id > e2.offense_type_id and e1.offense_category_id=? and e1.offense_category_id=e2.offense_category_id and YEAR(e1.reported_date)=? and YEAR(e1.reported_date)=YEAR(e2.reported_date) and e1.district_id=e2.district_id "
		+ "GROUP BY e1.offense_type_id,e2.offense_type_id ";

Connection conn = DBConnect.getConnection();
List <Arco> archi = new ArrayList <Arco>();

try {
	PreparedStatement st = conn.prepareStatement(sql);
	st.setString(1, cat);
	st.setInt(2, year);
	ResultSet res = st.executeQuery();
	while (res.next()) {
		
		if(vertici.contains(res.getString("id1")) & vertici.contains(res.getString("id2")) & res.getInt("peso")>0) {
			Arco a = new Arco(res.getString("id1"),res.getString("id2"),res.getInt("peso"));
			archi.add(a);
			System.out.println(a);
		}
	

	}
	conn.close();
	return archi;
	
} catch (SQLException e) {
	e.printStackTrace();
	return null;
}
}


	
	/****************** SIMULATORE *********************************/

	private PriorityQueue<Event> queue;
	private SimpleWeightedGraph<Food, DefaultWeightedEdge>graph;
	
	private List <>list;
	
	
	public Simulatore( SimpleWeightedGraph<Food, DefaultWeightedEdge>graph) {
		queue = new PriorityQueue<Event> ();
		this.graph=graph;
		
		cucinati = new LinkedList<>();
		
		//PERSONE,STAZIONI...
		for(int i=1;i<=K;i++)
			stazioni.add(new Stazione(i));
		
		//INIZIALIZZAZIONE
	
		for(Food f: Graphs.neighborListOf(graph, scelto)) {
		
		
	}
	
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = this.queue.poll();
			processEvent(e);
		}//while
	}
	
	
	private void processEvent(Event e) {
		switch(e.getType()) {
				
			case FINE: //EVENTO (crimine, preparazione di qualcosa, gestione di qualcosa...)
				,,,,,,
				
				//Creo evento GESTITO
				queue.add(e2);
				
				break;
				
			case GESTITO: //setto l'omino/l'operatore/il cuoco/la stazione a FREE
				break;
				
			default:
				break;
		}
	}
	
	
	
	///////////////// SENZA EVENTI e PRIORITY QUEUE ////////////////////////
	
	private SimpleWeightedGraph<Actor, DefaultWeightedEdge>graph;
	
	private List <Actor>intervistati;
	private List <Actor>daIntervistare;
	private int pause=0;
	private int n;//giorni di interviste
	private int gg=0; //gg di interviste già fatti
	
	
	public Simulatore( SimpleWeightedGraph<Actor, DefaultWeightedEdge>graph, Integer n) {
		this.n=n;
		this.graph=graph;
		
		intervistati = new ArrayList<Actor>();
		daIntervistare = new ArrayList<Actor>();
		for(Actor a: graph.vertexSet())
			daIntervistare.add(a);
		
		Actor a = this.scegliCasualmente();
		intervistati.add(a);
		gg++;
		
	}
	
	public void run() {
		//se gg==5 vuol dire che ha già intervistato per 5 gg
		//se gg==4 --> deve iniziare 5° gg
		while(gg<n) {
			double probability= Math.random();
			
			if(probability<=0.6) {
				Actor a = this.scegliCasualmente();
				intervistati.add(a);
				gg++;
			} else {
				Actor a = this.consiglio();
				intervistati.add(a);
				gg++;
			} //40%
			
			if(gg<n) {
				Actor ultimo=intervistati.get(intervistati.size()-1);
				Actor penultimo=intervistati.get(intervistati.size()-2);
				if(ultimo.getGender().equals(penultimo.getGender())) {
					double p=Math.random();
					if(p<=0.9) {//pausa
						pause++;
						gg++; //salto un giorno
						//gg dopo riparto casualmente
						if(gg<n) {
							Actor a = this.scegliCasualmente();
							intervistati.add(a);
							gg++;
						}
					}
				}
			}
				
		}//while
	}
		
	
	private Actor scegliCasualmente() {
		int prob = (int) (Math.random()*(daIntervistare.size()));
		Actor scelto = daIntervistare.remove(prob);
		return scelto;
		
	}
	
	/**
	 * attore sotto consiglio dell'ultimo intervistato
	 * @param e
	 */
	public Actor  consiglio() {
		Actor ultimo = intervistati.get(intervistati.size()-1);
		//se non ha vicini --> casuale
		if(Graphs.neighborListOf(graph, ultimo)==null)
			return this.scegliCasualmente();
		//se ha vicini
		Actor intervistato = this.getVicinoGradoMAX(ultimo);
		//lo tolgo da "daIntervistare"
		for(int i=0;i<daIntervistare.size();i++){
			if(daIntervistare.get(i).equals(intervistato))
				daIntervistare.remove(i);
		}
		
		return intervistato;
		
	}
	
	/**
	 * potrebbero esserci più vicini di grado max
	 * in quel caso scelgo a caso tra loro
	 * @param ultimo
	 * @return
	 */
	private Actor getVicinoGradoMAX(Actor ultimo) {
		double pesoMax=0.0;
		List <Actor> max = new ArrayList <Actor>();
		//trovo grado max
		for(DefaultWeightedEdge e: graph.incomingEdgesOf(ultimo))
			if(graph.getEdgeWeight(e)>pesoMax)
				pesoMax=graph.getEdgeWeight(e);
		//trovo tutti gli attori con quel peso
		System.out.println("\n\nIncoming = "+graph.incomingEdgesOf(ultimo).size()+"\nOutgoing = "+graph.outgoingEdgesOf(ultimo).size());
		for(DefaultWeightedEdge e: graph.incomingEdgesOf(ultimo))
			if(graph.getEdgeWeight(e)==pesoMax) {
				Actor vicino = Graphs.getOppositeVertex(graph, e, ultimo);
				max.add(vicino);
			}
		System.out.println("\nN° di vicini max "+max.size());
		//ne scelgo uno a caso tra i max
		int p = (int) (Math.random()*(max.size()));
		System.out.println("\n p= "+p);
		return max.get(p);
				
	}
	
	
	/******************** RICORSIONE *******************/
	
	public List <String> trovaPercorso(String partenza, Integer N){
		this.N=N;
		best=new ArrayList <String>();
		List <String> parziale = new ArrayList <String>();
		parziale.add(partenza);
		cerca(parziale);
		return best;
	}
	
	private void cerca(List <String> parziale) {
		if(parziale.size()==N) {
			if(best.size()==0 || calcolaPeso(parziale)>pesoMax) {
				best=new ArrayList <String> (parziale);
				pesoMax=calcolaPeso(parziale);
				return;
			}
		}
		
		for(String p: Graphs.neighborListOf(graph, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}
	
	
	private Integer calcolaPeso(List <String> lista) {
		Integer peso=0;
		
		for(int i=0;i<lista.size()-1;i++) {
			String p1=lista.get(i);
			String p2=lista.get(i+1);
			DefaultWeightedEdge e=graph.getEdge(p1, p2);
			peso+=(int)graph.getEdgeWeight(e);
		}
		return peso;
	}
	
	
	/*********************** MESI *************************/
	String[] mesi = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"};
	
	String meseString;
	Integer mese=0;
	try {
		meseString=cmbMese.getValue();
	}catch(NullPointerException npe) {
		txtResult.appendText("Scegli un mese");
		return;
	}
	
	switch(meseString) {
		case "Gennaio":
			mese=1;
			break;
		case "Febbraio":
			mese=2;
			break;
		case "Marzo":
			mese=3;
			break;
		case "Aprile":
			mese=4;
			break;
		case "Maggio":
			mese=5;
			break;
		case "Giugno":
			mese=6;
			break;
		case "Luglio":
			mese=7;
			break;
		case "Agosto":
			mese=8;
			break;
		case "Settembre":
			mese=9;
			break;
		case "Ottobre":
			mese=10;
			break;
		case "Novembre":
			mese=11;
			break;
		case "Dicembre":
			mese=12;
			break;
		default:
			mese=0;
			break;
	}
	
	
	//COMPARATORE //////////////
	public class ComparatoreDiTeams implements Comparator <Team>{
		public int compare (Team t1, Team t2) {
			return t2.getPunti()-t1.getPunti();
		}
	}
	
	

}
