package onc;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
//import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import onc.backend.Board;
import onc.backend.GameEngine;
import onc.backend.Piece;
import onc.backend.Player;
import onc.backend.Settings;

public class GameFaceController implements Initializable {

    private GameEngine gameEngine;
    @FXML
    private GridPane gameGrid;
    @FXML
    private Text diceText;




    @FXML
    public void rollDice(ActionEvent event) throws IOException {
        
        //System.out.println("rulla");
        gameEngine.rollDice();
        this.diceText.setText(("dice: " + gameEngine.getDice()));
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
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
        Board board = new Board(players);  // needed?
        Settings settings = new Settings();
        this.gameEngine = new GameEngine(settings, players);

        Collection<Piece> pieces = gameEngine.getPices();
        //pieces.forEach(piece -> addPieceToGrid(piece));
        

    }
}