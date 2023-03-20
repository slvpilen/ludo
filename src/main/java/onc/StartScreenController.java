package onc;

import java.io.IOException;
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
}
