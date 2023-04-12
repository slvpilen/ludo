package onc;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import onc.backend.GameNameInfo;
import onc.backend.GameNameInfo.GameNameLengthException;
import onc.backend.GameNameInfo.MissingInfoException;

public class CreateGameController {

    private Stage stage; 
    private Scene scene;
    private int numPlayers = 4;

    @FXML
    private Label player1Name, player2Name, player3Name, player4Name;

    @FXML
    private RadioButton num1, num2, num3, num4;

    @FXML 
    private TextField gameName, player1NameField, player2NameField, player3NameField, player4NameField;

    @FXML
    private Label exceptionLabel; 

    /**
     * This method is connected to a radio-button in the createGame-Scene.
     * When this button is clicked, all the text in the textFields other than textField 1 is removed,
     * and those textFields will no longer be visible.
     */
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

    /**
     * This method is connected to a radio-button in the createGame-Scene.
     * When this button is clicked, all the text in the textFields other than textField 1 and 2 is removed,
     * and those textFields will no longer be visible.
     */
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

    /**
     * This method is connected to a radio-button in the createGame-Scene.
     * When this button is clicked, all the text in textField 4 is removed,
     * and the textFields will no longer be visible.
     */
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

    /**
     * This method is connected to a radio-button in the createGame-Scene.
     * When this button is clicked, all the textFields will be visibile.
     */
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

    /**
     * This method takes you from the createGameScreen to the startScreen.
     */
    @FXML
    private void goToStartScreen(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/startScreen.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        // Workaround to get the vBox in the startScreen to update its position.
        double currentWidth = stage.getWidth();
        double currentHeight = stage.getHeight();
        stage.setWidth(currentWidth+0.001);
        stage.setHeight(currentHeight+0.001);
        
    }

    /**
     * This method is run when the submit-button is clicked.
     * It checks if the names of the players and the name of the game satisfies the criteria that must be met.
     * The gameNameInfoClass is responsible for checking if the submitted names are 
     * valid and it creates an instance of the GameNameInfo-class if they are. 
     * The GameNameInfo instance is then passed to the gameSetup() method of the GameFaceController class, 
     * which sets up the game with the specified players and gameName. 
     * Finally, the scene is switched to the gameFace scene so that the game can be played. 
     * If any errors occur during the validation or setup process, an appropriate error message is displayed to the user.
     */
    @FXML
    private void submit(ActionEvent event) throws IOException {
        
        List<String> gameNameInfoAsList = Arrays.asList(gameName.getText(), player1NameField.getText(), player2NameField.getText(),player3NameField.getText(), player4NameField.getText()).stream().filter(name -> !name.equals("")).collect(Collectors.toList());

        try{

            GameNameInfo gameNameInfo = new GameNameInfo(gameNameInfoAsList, numPlayers, true);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/gameFace.fxml"));
            scene = new Scene(loader.load());
            GameFaceController gameFaceController = loader.getController();

            gameFaceController.gameSetup(gameNameInfo);

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
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

}
