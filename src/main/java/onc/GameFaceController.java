package onc;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import onc.backend.GameEngine;
import onc.backend.GameInfo;
import onc.backend.InterfaceGameEngineListener;
import onc.backend.Player;
import onc.backend.RobotPlayer;
import onc.backend.Settings;


public class GameFaceController implements Initializable, InterfaceGameEngineListener {

    private Scene scene;
    private GameEngine gameEngine;
    private GameInfo gameInfo;
    private boolean startMessageHidden;
    
    @FXML private Text gameName, player1Name, player2Name, player3Name, player4Name, playerTurn;

    @FXML private GridPane gameGrid;

    @FXML private ImageView diceView;

    @FXML private VBox startMessage;


    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @FXML
    public void rollDice() throws IOException {

        if (!startMessageHidden) {
            startMessage.setVisible(false);
            startMessageHidden = true;
        }

        gameEngine.rollDice();      
    
    }

    @FXML
    private void goToStartScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/startScreen.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.setScene(scene);
    }

    @Override
    public void updateImageOfDice(int latestDice) {
        if (latestDice < 1 || latestDice > 6)
            throw new IllegalArgumentException("Dice must be between 1-6");

        Image sourceimage1 = new Image("file:src/main/resources/dicesImages/dice" + Integer.toString(latestDice) + ".png");
        diceView.setImage(sourceimage1);
    }

    /**
     * This method sets the image of the dice to be a black dice.
     * This image is always presented before a player has rolled the dice.
     */
    public void blackDice() {
        Image blackDice = new Image("file:src/main/resources/dicesImages/SvartTerning.jpg");
        diceView.setImage(blackDice);
    }

    @Override
    public void updatePlayerText(String text) {
        String playerName = gameEngine.getCurrentPlayer().getUsername();
        playerTurn.setText(playerName + text);
    
        int currentHouseNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        switch (currentHouseNumber) {
            case 1:
                playerTurn.setFill(Color.valueOf("#00ff00"));
                break;
            case 2:
                playerTurn.setFill(Color.valueOf("#ffd700"));
                break;
            case 3:
                playerTurn.setFill(Color.valueOf("#4968bc"));
                break;
            case 4:
                playerTurn.setFill(Color.valueOf("#EE4B2B"));
                break;
        }
    }
    

    /**
     * This method changes the in-game-text and color to show which player's turn it is
     */
    public void updatePlayerThrowText() {
        
        String playerName = gameEngine.getCurrentPlayer().getUsername();
        int currentHouseNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        
        String text;
        Color color = new Color(0, 0, 0, 1);
    
        if (playerName.charAt(playerName.length() - 1) == 's' || playerName.charAt(playerName.length() - 1) == 'x' || playerName.charAt(playerName.length() - 1) == 'z') {
            text = playerName + "' throw";
        } 
        
        else {
            text = playerName + "s throw";
        }
    
        switch (currentHouseNumber) {
            case 1:
                color = Color.valueOf("#00ff00");
                break;
            case 2:
                color = Color.valueOf("#ffd700");
                break;
            case 3:
                color = Color.valueOf("#4968bc");
                break;
            case 4:
                color = Color.valueOf("#EE4B2B");
                break;
        }
        
        playerTurn.setText(text);
        playerTurn.setFill(color);
    }

    /**  
     * This method is used during initialization of new game. Used in CreateGameController to set the name of the players in the game scene.
     */
    public void setName(String name, int textBox) {

        Text[] texts = {gameName, player1Name, player2Name, player3Name, player4Name};
        texts[textBox].setText(texts[textBox].getText() + name);

    }
    

    /** 
     * This method is used by CreateGameController to initialize the game scene after clicking submit.
     * Specifically, the gameEngine gets initialized with a set of players and some settings.
     * At this point, custom settings have not yet been implemented, so default settings are applied.
     */
    public void gameSetup(int numPlayers) throws IOException {
        
        Settings settings = new Settings();
        ArrayList<Player> players = new ArrayList<>();
    
        players.add(new Player(player1Name.getText(), 1, gameGrid));
        
        if (numPlayers == 1) {
            players.add(new RobotPlayer(player2Name.getText(), 2, gameGrid));
            players.add(new RobotPlayer(player3Name.getText(), 3, gameGrid));
            players.add(new RobotPlayer(player4Name.getText(), 4, gameGrid));
        }

        else {
            players.add(numPlayers == 2 ? new RobotPlayer(player2Name.getText(), 2, gameGrid) : new Player(player2Name.getText(), 2, gameGrid));
            players.add(new Player(player3Name.getText(), 3, gameGrid));
            players.add(numPlayers == 4 ? new Player(player4Name.getText(), 4, gameGrid) : new RobotPlayer(player4Name.getText(), 4, gameGrid));
        }

        gameEngine = new GameEngine(settings, players);
        gameEngine.addListener(this);
    }   

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        blackDice(); // setting dice to be 1 as default
    }

    @Override
    public void currentPlayerChanged() {
        updatePlayerThrowText();
    }

    @Override 
    public void playerWon(String winnerName) {
        showWinPopup(winnerName);
    }


    /**
     * This method ensures that a pop-up window appears when a player has won the game.
     * Clicking the ok-button which appears will take you to the home screen.
     */
    private void showWinPopup(String playerName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(playerName + " has won the game!");
        alert.setContentText("Click OK to return to the home screen.");
    
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                goToStartScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void robotRolledDice() {
        
        try {
            rollDice();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playerMadeMove() {
        blackDice();
    }
    
    @Override
    public void diceClickable(boolean arg) {
        diceView.setDisable(!arg);
    }


}