package onc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onc.backend.GameInfo;
import onc.backend.GameInfo.GameNameLengthException;
import onc.backend.GameInfo.MissingInfoException;

public class CreateGameController {

    private Parent root;
    private Stage stage; 
    private Scene scene;
    private int numPlayers = 4;

    @FXML
    private Label player1Name, player2Name, player3Name, player4Name;

    @FXML
    private RadioButton num1, num2, num3, num4;

    @FXML 
    private TextField player1NameField, player2NameField, player3NameField, player4NameField;

    @FXML 
    private TextField gameName; 

    @FXML
    private Label exceptionLabel; 


    @FXML
    private void radioButton1() {

        player2NameField.setVisible(false);
        player2Name.setVisible(false);

        player3NameField.setVisible(false);
        player3Name.setVisible(false);

        player4NameField.setVisible(false);
        player4Name.setVisible(false);
        numPlayers = 1;

        player2NameField.setText("");
        player3NameField.setText("");
        player4NameField.setText("");
    }


    @FXML
    private void radioButton2() {

        player2NameField.setVisible(true);
        player2Name.setVisible(true);

        player3NameField.setVisible(false);
        player3Name.setVisible(false);

        player4NameField.setVisible(false);
        player4Name.setVisible(false);
        numPlayers = 2;

        player3NameField.setText("");
        player4NameField.setText("");
    }

    @FXML
    private void radioButton3() {

        player2NameField.setVisible(true);
        player2Name.setVisible(true);

        player3NameField.setVisible(true);
        player3Name.setVisible(true);

        player4NameField.setVisible(false);
        player4Name.setVisible(false);
        numPlayers = 3;

        player4NameField.setText("");
    }

    @FXML
    private void radioButton4() {

        player2NameField.setVisible(true);
        player2Name.setVisible(true);

        player3NameField.setVisible(true);
        player3Name.setVisible(true);

        player4NameField.setVisible(true);
        player4Name.setVisible(true);
        numPlayers = 4;
    }

    @FXML
    private void goToStartScreen(ActionEvent event) throws IOException {
        //nullstillExceptionLabel();
        // App.setRoot("startScreen");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/startScreen.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void submit(ActionEvent event) throws IOException {
        
        List<String> gameInfoAsList = Arrays.asList(gameName.getText(), player1NameField.getText(), 
        player2NameField.getText(),player3NameField.getText(), player4NameField.getText()).stream().
        filter(name -> !name.equals("")).collect(Collectors.toList());

        
        try{
            GameInfo gameInfo = new GameInfo(gameInfoAsList, numPlayers);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/gameFace.fxml"));
            root = loader.load();
            GameFaceController gameFaceController = loader.getController();

            gameFaceController.setGameInfo(gameInfo); //Det ser ikke ut til at denne brukes

            List<String> validatedGameInfoAsList = gameInfo.getGameInfoAsList();

            IntStream.range(0, validatedGameInfoAsList.size()).forEach(index -> gameFaceController.setName(validatedGameInfoAsList.get(index), index));
            
            gameFaceController.gameSetup(numPlayers);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            //GameFaceController gameFaceController = loader.getController();
            // App.setRoot("gameFace");
        }

        catch (GameNameLengthException e) {
            exceptionLabel.setText("Maximum game name length is 25");
        }
        catch (MissingInfoException e) {
            exceptionLabel.setText("Missing info about game");
        }
        catch (IllegalStateException e){
            exceptionLabel.setText("Fill out every field!");
        }
        catch (IllegalArgumentException e){
            exceptionLabel.setText("The players must have different names!");
        }
        catch (IllegalMonitorStateException e) {
            exceptionLabel.setText("Maximum name length is 9!");
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
