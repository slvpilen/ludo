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
    private GridPane gameGrid;
    //@FXML
    //private Text diceText;
    @FXML
    private ImageView diceView;


    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @FXML
    public void rollDice(MouseEvent event) throws IOException {
        
        //System.out.println("rulla");
        gameEngine.rollDice();
        //this.diceText.setText(("dice: " + gameEngine.getDice()));
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        updateImageOfDice(1); // setting dice to be 1 as default
        
        // This is for a new game, not for loading game ATM
        // this ceating of players is a temperarly solution





        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("Kåre", 1, gameGrid);
        Player player2 = new Player("Trude", 2, gameGrid);
        Player player3 = new Player("Fred", 3, gameGrid);
        Player player4 = new Player("Børge", 4, gameGrid);
        
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        //
        Settings settings = new Settings();
        this.gameEngine = new GameEngine(settings, players);

        Collection<Piece> pieces = gameEngine.getPieces();
        

    }
}