package onc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onc.backend.GameInfo;
import onc.backend.GameInfo.GameNameLengthException;

public class CreateGameController {

    private Parent root;
    private Stage stage;
    private Scene scene;


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
        player2NameField.getText(),player3NameField.getText(), player4NameField.getText());

        try{
            
            GameInfo gameInfo = new GameInfo(gameInfoAsList);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/gameFace.fxml"));
            root = loader.load();
            GameFaceController gameFaceController = loader.getController();

            gameFaceController.setGameInfo(gameInfo);
            IntStream.range(0, gameInfoAsList.size()).filter(c -> !gameInfoAsList.get(c).equals(""))
                                                                    .forEach(index -> gameFaceController
                                                                    .setName(gameInfoAsList.get(index), index));
            
            gameFaceController.gameSetup();


            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);

            //GameFaceController gameFaceController = loader.getController();
            // App.setRoot("gameFace");
        }

        catch (GameNameLengthException e) {
            exceptionLabel.setText("Maximum game name length is 25");
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
