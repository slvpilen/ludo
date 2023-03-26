package onc;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StartScreenController {

    @FXML
    private void createGame() throws IOException {
        App.setRoot("createGame");
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
