package onc;

import java.io.IOException;
import javafx.fxml.FXML;

public class CreateGameController {

    @FXML
    private void goToStartScreen() throws IOException {
        App.setRoot("startScreen");
    }
}
