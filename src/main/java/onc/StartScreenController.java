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

    
    @FXML
    private void createGame(ActionEvent event) throws IOException {
        // App.setRoot("createGame");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/createGame.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

    }

    @FXML
    private void loadGame() throws IOException {
        App.setRoot("gameFace");
    }

    @FXML
    public void startStopMusic() throws IOException {
        if (soundOn.isSelected()) {
            App.resumeMusic();
        } else {
            App.pauseMusic();
        }
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        soundOn.setSelected(true);
    }  


}
