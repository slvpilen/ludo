package onc;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import onc.backend.GameEngine;
import onc.backend.GameInfo;
import onc.backend.Piece;
import onc.backend.Player;
import onc.backend.Settings;

public class GameFaceController implements Initializable {

    private GameEngine gameEngine;
    private GameInfo gameInfo;
    
    @FXML
    private Text gameName;
    
    @FXML
    private Text player1Name;
    @FXML
    private Text player2Name;
    @FXML
    private Text player3Name;
    @FXML
    private Text player4Name;
    @FXML
    private Text playerTurn;

    @FXML
    private GridPane gameGrid;
    //@FXML
    //private Text diceText;
    @FXML
    private ImageView diceView;

    @FXML private VBox startMessage;

    private boolean startMessageHidden;

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @FXML
    public void rollDice(MouseEvent event) throws IOException {

        if (!startMessageHidden) {
            startMessage.setVisible(false);
            startMessageHidden = true;
        }

        updatePlayerTurn();
        gameEngine.rollDice();
        updateImageOfDice(gameEngine.getDice());
    }

    private void updateImageOfDice(int latestDice) {
        if (latestDice == 1){
            Image sourceimage1 = new Image("file:src/main/resources/dicesImages/dice1.png");
            diceView.setImage(sourceimage1);
        }
        else if (latestDice == 2) {
            //URL url = new URL
            Image sourceimage2 = new Image("file:src/main/resources/dicesImages/dice2.png");
            diceView.setImage(sourceimage2);
        }
        else if (latestDice == 3) {
            Image sourceimage3 = new Image("file:src/main/resources/dicesImages/dice3.png");
            diceView.setImage(sourceimage3);
        }
        else if (latestDice == 4) {
            Image sourceimage4 = new Image("file:src/main/resources/dicesImages/dice4.png");
            diceView.setImage(sourceimage4);
        }
        else if (latestDice == 5) {
            Image sourceimage5 = new Image("file:src/main/resources/dicesImages/dice5.png");
            diceView.setImage(sourceimage5);
        }

        else if (latestDice == 6) {
            Image sourceimage6 = new Image("file:src/main/resources/dicesImages/dice6.png");
            diceView.setImage(sourceimage6);
        }
    }

    private void updatePlayerTurn() {
        
        String p1Name = player1Name.getText();
        String p2Name = player2Name.getText();
        String p3Name = player3Name.getText();
        String p4Name = player4Name.getText();
        
        if (gameEngine.getCurrentPlayer().getHouseNumber() == 1) {
            if (p1Name.charAt(p1Name.length()-1) == 's' || p1Name.charAt(p1Name.length()-1) == 'x' || p1Name.charAt(p1Name.length()-1) == 'z' ) {
                playerTurn.setText(p1Name + "' turn");
                playerTurn.setFill(Color.valueOf("#00FF00"));
            }
            else {
                playerTurn.setText(p1Name + "s turn");
                playerTurn.setFill(Color.valueOf("#00FF00"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 2) {
            if (p2Name.charAt(p2Name.length()-1) == 's' || p2Name.charAt(p2Name.length()-1) == 'x' || p2Name.charAt(p2Name.length()-1) == 'z' ) {
                playerTurn.setText(p2Name + "' turn");
                playerTurn.setFill(Color.valueOf("#FFD700"));
            }
            else {
                playerTurn.setText(p2Name + "s turn");
                playerTurn.setFill(Color.valueOf("#FFD700"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 3) {
            if (p3Name.charAt(p3Name.length()-1) == 's' || p3Name.charAt(p3Name.length()-1) == 'x' || p3Name.charAt(p3Name.length()-1) == 'z' ) {
                playerTurn.setText(p3Name + "' turn");
                playerTurn.setFill(Color.valueOf("#4968bc"));
            }
            else {
                playerTurn.setText(p3Name + "s turn");
                playerTurn.setFill(Color.valueOf("#4968bc"));
            }
        }

        else if (gameEngine.getCurrentPlayer().getHouseNumber() == 4) {
            if (p4Name.charAt(p4Name.length()-1) == 's' || p4Name.charAt(p4Name.length()-1) == 'x' || p4Name.charAt(p4Name.length()-1) == 'z' ) {
                playerTurn.setText(p4Name + "' turn");
                playerTurn.setFill(Color.valueOf("#EE4B2B"));
            }
            else {
                playerTurn.setText(p4Name + "s turn");
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
    public void gameSetup() {

        Settings settings = new Settings();
        
        ArrayList<Player> players = new ArrayList<>();
        
        Player player1;
        Player player2;
        Player player3;
        Player player4;

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

        this.gameEngine = new GameEngine(settings, players);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        updateImageOfDice(1); // setting dice to be 1 as default
    }
}