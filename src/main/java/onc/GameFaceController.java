package onc;



import java.io.IOException;
import java.net.URL;
import java.nio.channels.NotYetBoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
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
    
    @FXML
    private Text gameName;
    

    
    @FXML
    private Text player1Name, player2Name, player3Name, player4Name, playerTurn;

    @FXML
    private GridPane gameGrid;

    @FXML
    private ImageView diceView;

    @FXML private VBox startMessage;

    private boolean startMessageHidden;

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @FXML
    public void rollDice() throws IOException {

        if (!startMessageHidden) {
            startMessage.setVisible(false);
            startMessageHidden = true;
        }


        updatePlayerTurn();
        gameEngine.rollDice();
        updateImageOfDice(gameEngine.getDice());
        
        
    }
    


    @FXML
    private void goToStartScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/onc/startScreen.fxml"));
        scene = new Scene(loader.load());
        Stage stage = (Stage) gameGrid.getScene().getWindow();
        stage.setScene(scene);
    }

    public void updateImageOfDice(int latestDice) {
        if (latestDice < 1 || latestDice > 6)
            throw new IllegalArgumentException("Dice must be between 1-6");

        Image sourceimage1 = new Image("file:src/main/resources/dicesImages/dice" + Integer.toString(latestDice) + ".png");
        diceView.setImage(sourceimage1);
    }

    public void updatePlayerTurn() {
        
        String playerName = gameEngine.getCurrentPlayer().getUsername();
        playerTurn.setText(playerName + " must move!");
        
        int currentHousNumber = gameEngine.getCurrentPlayer().getHouseNumber();
        if (currentHousNumber == 1) {
            playerTurn.setFill(Color.valueOf("#00ff00"));
        }

        else if (currentHousNumber == 2) {
            playerTurn.setFill(Color.valueOf("#ffd700"));
        }

        else if (currentHousNumber == 3) {
            playerTurn.setFill(Color.valueOf("#4968bc"));
        }

        else if (currentHousNumber == 4) {
            playerTurn.setFill(Color.valueOf("#EE4B2B"));
        }

    }

    public void updatePlayerThrow() {
        
        String p1Name = player1Name.getText();
        String p2Name = player2Name.getText();
        String p3Name = player3Name.getText();
        String p4Name = player4Name.getText();
        
        if (gameEngine.getCurrentPlayer().getHouseNumber() == 1) {
            if (p1Name.charAt(p1Name.length()-1) == 's' || p1Name.charAt(p1Name.length()-1) == 'x' || p1Name.charAt(p1Name.length()-1) == 'z' ) {
                playerTurn.setText(p1Name + "' throw");
                playerTurn.setFill(Color.valueOf("#00FF00"));
            }
            else {
                playerTurn.setText(p1Name + "s throw");
                playerTurn.setFill(Color.valueOf("#00FF00"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 2) {
            if (p2Name.charAt(p2Name.length()-1) == 's' || p2Name.charAt(p2Name.length()-1) == 'x' || p2Name.charAt(p2Name.length()-1) == 'z' ) {
                playerTurn.setText(p2Name + "' throw");
                playerTurn.setFill(Color.valueOf("#FFD700"));
            }
            else {
                playerTurn.setText(p2Name + "s throw");
                playerTurn.setFill(Color.valueOf("#FFD700"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 3) {
            if (p3Name.charAt(p3Name.length()-1) == 's' || p3Name.charAt(p3Name.length()-1) == 'x' || p3Name.charAt(p3Name.length()-1) == 'z' ) {
                playerTurn.setText(p3Name + "' throw");
                playerTurn.setFill(Color.valueOf("#4968bc"));
            }
            else {
                playerTurn.setText(p3Name + "s throw");
                playerTurn.setFill(Color.valueOf("#4968bc"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 4) {
            if (p4Name.charAt(p4Name.length()-1) == 's' || p4Name.charAt(p4Name.length()-1) == 'x' || p4Name.charAt(p4Name.length()-1) == 'z' ) {
                playerTurn.setText(p4Name + "' throw");
                playerTurn.setFill(Color.valueOf("#EE4B2B"));
            }
            else {
                playerTurn.setText(p4Name + "s throw");
                playerTurn.setFill(Color.valueOf("#EE4B2B"));
            }
        }

    }

    // This method is used during initialization of new game. Used in CreateGameController.
    public void setName(String name, int textBox) {
        
        if (textBox == 0) {
            gameName.setText(gameName.getText() + name);
        }

        if (textBox == 1) {
            player1Name.setText(player1Name.getText() + name);
        }

        if (textBox == 2) {
            player2Name.setText(player2Name.getText() + name);
        }
        
        if (textBox == 3) {
            player3Name.setText(player3Name.getText() + name);
        }
        
        if (textBox == 4) {
            player4Name.setText(player4Name.getText() + name);
        }

    }


    // This method is used by CreateGameController to initialize the GameFace after clicking submit (creating new game).
    public void gameSetup(int numPlayers) throws IOException {


        if (numPlayers < 1 || numPlayers > 4) {
            throw new IllegalArgumentException("Number of players is incorrect!");
        }

        Settings settings = new Settings();
        
        ArrayList<Player> players = new ArrayList<>();
        
        Player player1, player2, player3, player4;

        if (numPlayers == 4) {
            if (!player1Name.getText().equals("")) {
                player1 = new Player(player1Name.getText(), 1, gameGrid);   
                players.add(player1);
            }
            if (!player2Name.getText().equals("")) {
                player2 = new Player(player2Name.getText(), 2, gameGrid);
                players.add(player2);
    
            }
            if (!player3Name.getText().equals("")) {
                player3 = new Player(player3Name.getText(), 3, gameGrid);
                players.add(player3);
    
            }
            if (!player4Name.getText().equals("")) {
                player4 = new Player(player4Name.getText(), 4, gameGrid);
                players.add(player4);
            }
        }

        else if (numPlayers == 3) {
            if (!player1Name.getText().equals("")) {
                player1 = new Player(player1Name.getText(), 1, gameGrid);   
                players.add(player1);
            }
            if (!player2Name.getText().equals("")) {
                player2 = new Player(player2Name.getText(), 2, gameGrid);
                players.add(player2);
    
            }
            if (!player3Name.getText().equals("")) {
                player3 = new Player(player3Name.getText(), 3, gameGrid);
                players.add(player3);
    
            }
            if (!player4Name.getText().equals("")) {
                player4 = new RobotPlayer(player4Name.getText(), 4, gameGrid);
                players.add(player4);
            }
        }

        else if (numPlayers == 2) {
            if (!player1Name.getText().equals("")) {
                player1 = new Player(player1Name.getText(), 1, gameGrid);   
                players.add(player1);
            }
            if (!player2Name.getText().equals("")) {
                player2 = new RobotPlayer(player2Name.getText(), 2, gameGrid);
                players.add(player2);
    
            }
            if (!player3Name.getText().equals("")) {
                player3 = new Player(player3Name.getText(), 3, gameGrid);
                players.add(player3);
    
            }
            if (!player4Name.getText().equals("")) {
                player4 = new RobotPlayer(player4Name.getText(), 4, gameGrid);
                players.add(player4);
            }
        }

        else {
            if (!player1Name.getText().equals("")) {
                player1 = new Player(player1Name.getText(), 1, gameGrid);   
                players.add(player1);
            }
            if (!player2Name.getText().equals("")) {
                player2 = new RobotPlayer(player2Name.getText(), 2, gameGrid);
                players.add(player2);
    
            }
            if (!player3Name.getText().equals("")) {
                player3 = new RobotPlayer(player3Name.getText(), 3, gameGrid);
                players.add(player3);
    
            }
            if (!player4Name.getText().equals("")) {
                player4 = new RobotPlayer(player4Name.getText(), 4, gameGrid);
                players.add(player4);
            }
        }
    

        this.gameEngine = new GameEngine(settings, players);
        gameEngine.addListener(this);

        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // This setting is crucial
        updateImageOfDice(1); // setting dice to be 1 as default

        
        
        
        
        
        
        // This is made to test the robotplayers.
        
        // Settings settings = new Settings();
        
        // ArrayList<Player> players = new ArrayList<>();
        
        // Player player1;
        // Player player2;
        // Player player3;
        // Player player4;
  
        // player1 = new Player("Christian", 1, gameGrid);   
        // player2 = new RobotPlayer("Roberto", 2, gameGrid);
        // player3 = new RobotPlayer("Rotobert", 3, gameGrid);
        // player4 = new RobotPlayer("Robby", 4, gameGrid);
           
        // players.add(player1);
        // players.add(player2);
        // players.add(player3);
        // players.add(player4);

        // setName("Robo rampage", 0);
        // setName("Christian", 1);
        // setName("Roberto", 2);
        // setName("Rotobert", 3);
        // setName("Robby", 4);
        
        // this.gameEngine = new GameEngine(settings, players);
        // gameEngine.addListener(this);
        
        //End of robotplayer-setup.
        
    }

    @Override
    public void currentPlayerChanged() {
        updatePlayerThrow();
    }

    @Override public void playerWon(String winnerName) {
        showWinPopup(winnerName);
    }


    // This is more of a temporary solution, didn't think it would work at first, but it did.
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


    public void robotRolledDice() {
        
        try {
            rollDice();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
    

}