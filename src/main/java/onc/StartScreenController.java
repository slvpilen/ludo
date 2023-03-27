package onc;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class StartScreenController {

    private Scene scene;

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
    private void pauseMusic() throws IOException {
        App.pauseMusic();
    }

    @FXML
    private void resumeMusic() throws IOException {
        App.resumeMusic();
    }


}
