package onc;



import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import onc.backend.Board;
import onc.backend.Piece;
import onc.backend.Player;

public class gameFaceController implements Initializable {

    @FXML
    private GridPane gameGrid;


    /* @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    } */

/*     @FXML
    private void clickedOnPiece(MouseEvent event, Piece p) throws IOException {
        App.setRoot("secondary");
    } */
/* 
    private void addCircle(){
        Circle circle = new Circle(20, Color.BLUE);
        GridPane.setRowIndex(circle, 2);
        GridPane.setColumnIndex(circle, 3);
        gridPane.getChildren().add(circle);
    } */

    private Collection<Piece> testPiece1(){
        ArrayList<Player> players = new ArrayList<>();
        Player player1 = new Player("Kåre", 1);
        Player player2 = new Player("Trude", 2);
        Player player3 = new Player("Fred", 3);
        Player player4 = new Player("Børge", 4);
        
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        Board board = new Board(players);  // needed

        ArrayList<Piece> piecesList = new ArrayList<>();

        for (Player player : players){
            Collection<Piece> pieces =  player.getPieces();
            pieces.forEach(piece -> piecesList.add(piece));
        }

        return piecesList;
  
    }

    private void addPieceToGrid(Piece piece){

        GridPane.setRowIndex(piece.getCircle(), piece.getColumn());
        GridPane.setColumnIndex(piece.getCircle(), piece.getRow());
        gameGrid.getChildren().add(piece.getCircle());
        
    }

    private void movePiece(){
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Collection<Piece> pieces = testPiece1();
        pieces.forEach(piece -> addPieceToGrid(piece));

    }
}