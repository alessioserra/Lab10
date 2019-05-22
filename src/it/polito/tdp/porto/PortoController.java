package it.polito.tdp.porto;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	Model model = new Model();
	List<Author> lista;
	
	public void setModel(Model model) {
		
		this.model=model;
		
		this.lista = this.model.getAllAutori();
		Collections.sort(lista);
		
		boxPrimo.getItems().addAll(lista);
		
		this.model.creaGrafo();
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {

    	txtResult.clear();
    	
    	Author autore = boxPrimo.getValue();
    	
    	if (autore!=null) {
    	List<Author> vicini = model.getVicini(autore);
    	
    	for (Author a : vicini) {
    	    txtResult.appendText(a.toString()+"\n");
    	    }
    	
    	//Aggiungo i NON co-autori al box 2
    	List<Author> lista2 = new ArrayList<>();
    	for (Author a: this.lista) {
    		if ( !vicini.contains(a) ) lista2.add(a);
    	}
    	boxSecondo.getItems().clear();
    	boxSecondo.getItems().addAll(lista2);
    	
    	}
    	else txtResult.appendText("Selezionare un autore!\n");
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Author a1 = boxPrimo.getValue();
    	Author a2 = boxSecondo.getValue();
    	
    	if ( a1!= null && a2!=null)  {
    		List<Paper> list = model.getCamminoMinimo(a1, a2);
    	
    	
    	for (Paper p : list) txtResult.appendText(p.toString()+"\n");
    	
    	}
    	else txtResult.appendText("Selezionare 2 autori!");
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
}
