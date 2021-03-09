package it.polito.tdp.parole;

import it.polito.tdp.parole.model.Parole;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Parole elenco ; //struttura dati su cui lavoro

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtParola;

    @FXML
    private Button btnInserisci;

    @FXML
    private TextArea txtResult;
    
    @FXML
    private TextArea txtTime;
    
    @FXML
    private Button btnClear;

    @FXML
    private Button btnReset;

    @FXML
    void doInsert(ActionEvent event) {
    	String word = txtParola.getText();
    	double time = elenco.addParola(word); //la aggiungo all'elenco
    	
    	txtParola.clear(); //pulisco lo spazio di inserimento
    	
    	String result = ""; //la lista ordinate di parole diventa una string
    	for(String si: elenco.getElenco())
    		result+=si+"\n";
    	txtResult.setText(result);
    	
    	txtTime.setText("Tempo di ADD: "+time+" nanosecondi");
    	
    }
    
    @FXML
    void doClear(ActionEvent event) {
    	String selected = txtResult.getSelectedText();
    	
    	//toglie quella parola dall'elenco
    	double time=0.0;
    	for(String si: elenco.getElenco())
    		if(si.compareTo(selected)==0) 
    			 time = elenco.clear(selected);	
    		
    	
    	//cancello vecchia area di testo
    	//stampo il nuovo elenco
    	txtResult.clear();
    	
    	String result = ""; 
    	for(String si: elenco.getElenco())
    		result+=si+"\n";
    	txtResult.setText(result);
    	
    	txtTime.setText("Tempo di CLEAR: "+time+" nanosecondi");
    			
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtResult.clear();
    	elenco.reset();
    }

    @FXML
    void initialize() {
        assert txtParola != null : "fx:id=\"txtParola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnInserisci != null : "fx:id=\"btnInserisci\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

        elenco = new Parole() ;
    }
}
