package onc;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import javafx.stage.Stage;

public class StartScreenController implements Initializable {

    private Scene scene;

    @FXML
    public CheckBox soundOn;

    
    /**
     * This methods takes you from the startScreen to the createGameScreen.
     */
    @FXML
    private void createGame(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/createGame.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    /**
     * This method is not yet implemented.
     */
    @FXML
    private void loadGame() throws IOException {
        
    }

    /**
     * This method plays the superMarioBros music if it isn't already playing.
     * If the music is playing, and the method is run, then the music will be paused.
     */
    @FXML
    public void startStopMusic() throws IOException {
        
        if (soundOn.isSelected()) {
            App.resumeMusic();
        } 
        
        else {
            App.pauseMusic();
        }
    }
    
    /**
     * This method tells java what should happen when you enter the 
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundOn.setSelected(true);
    }  


}
