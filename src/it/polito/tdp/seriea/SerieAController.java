/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.util.*;
import java.net.URL;
import java.util.ResourceBundle;
import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;
import it.polito.tdp.seriea.model.TeamAndScore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSeason"
    private ChoiceBox<Season> boxSeason; // Value injected by FXMLLoader

    @FXML // fx:id="boxTeam"
    private ChoiceBox<Team> boxTeam; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCarica(ActionEvent event) {

    	Season s= boxSeason.getValue();
    	
    	if(s ==null){
    		txtResult.appendText("Scegliere una stagione dall'elenco\n");
    		return;
    	}
    	boxTeam.getItems().addAll(model.getTeamsForSeason(s));
    	model.createGraph(s);
    	
    	List<TeamAndScore> classifica = model.getClassificaCampionato(s);
    	
    	txtResult.appendText("Classifica Campionato per la stagione "+s.getDescription()+":\n");
    	for(TeamAndScore t: classifica){
    		txtResult.appendText(t.toString());
    	}
    }

    @FXML
    void handleDomino(ActionEvent event) {
    	/*CONTROLLI DI INPUT DA STRINGA A NUMERICO
    	 * 
    	String s= txtInput.getText();
    	int num;
    	if(s.length()==0){
    		txtResult.appendText("Errore: devi inserire qalcosa!\n");
    		return;
    	}
    	
    	try{
    		num = Integer.parseInt(s);
    	} catch(NumberFormatException exception){
    		txtResult.appendText("Inserire caratteri numerici");
    		return;
    	}
    	
    	if(num<1){
    		txtResult.appendText("Inserire numero >0\n");
    		return;
    	}
    	
    	*/
    	model.RiduciGrafo();
    	model.getLongestPath();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSeason != null : "fx:id=\"boxSeason\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxTeam != null : "fx:id=\"boxTeam\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";
    }

	public void setModel(Model model) {
		this.model=model;
		this.boxSeason.getItems().addAll(model.getSeasons());
	}
}
