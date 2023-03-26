package onc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import onc.backend.GameInfo;

public class CreateGameController {

    @FXML 
    private TextField player1NameField;
    @FXML 
    private TextField player2NameField;
    @FXML 
    private TextField player3NameField;
    @FXML 
    private TextField player4NameField;
    @FXML 
    private TextField gameName; 
    @FXML
    private Label exceptionLabel; 


    @FXML
    private void goToStartScreen() throws IOException {
        //nullstillExceptionLabel();
        App.setRoot("startScreen");
    }

    @FXML
    private void submit() throws IOException {
        List<String> gameInfoAsList = Arrays.asList(gameName.getText(), player1NameField.getText(), 
        player2NameField.getText(),player3NameField.getText(), player4NameField.getText());

        try{
            GameInfo gameInfo = new GameInfo(gameInfoAsList);
            //GameFaceController gameFaceController = loader.getController();
            App.setRoot("gameFace");
        }
        catch (IllegalStateException e){
            exceptionLabel.setText("Fill out every field!");
        }
        catch (IllegalArgumentException e){
            exceptionLabel.setText("Cant have the same name!");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @FXML
    private void nullstillExceptionLabel() throws IOException {
        System.out.println("hei");
        exceptionLabel.setText("");
    }

/*     @FXML
    private void loadGame() throws IOException {
        App.setRoot("gameFace");
    } */

}
