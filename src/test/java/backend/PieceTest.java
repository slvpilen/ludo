package backend;

import onc.backend.GameEngine;
import onc.backend.GameNameInfo;
import onc.backend.Piece;
import onc.backend.Player;
import onc.backend.Settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Pair;




public class PieceTest {

    private static GridPane gameGrid;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;
    GameEngine gameEngine;
    GameNameInfo gameNameInfo;

    @BeforeAll
    public static void setup() {
        
        GridPane newGameGrid = new GridPane();

        // Add 50 columns
        for (int i = 0; i < 50; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / 50.0);
            newGameGrid.getColumnConstraints().add(col);
        }

        // Add 50 rows
        for (int i = 0; i < 50; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / 50.0);
            newGameGrid.getRowConstraints().add(row);
        }

        gameGrid = newGameGrid;
        player1 = new Player("Truls", 1, gameGrid);
        player2 = new Player("Fred", 2, gameGrid);
        player3 = new Player("Megan", 3, gameGrid);
        player4 = new Player("Ivan", 4, gameGrid);
    }



    @Test
    public void testPieceCreation() {  
        assertNotNull(new Piece(player1, new Pair<>(2, 18), gameGrid));
        assertThrows(IllegalArgumentException.class, () -> new Piece(player1, new Pair<>(-1, -2), gameGrid));
    }


    @Test
    public void piecesOnHomeSquaresTest() {
        
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        ArrayList<String> gameInfoAsList = new ArrayList<>(Arrays.asList("PaaskeCup", "Truls", "Fred", "Megan", "Ivan"));
        gameNameInfo = new GameNameInfo(gameInfoAsList, 4, true);
        gameEngine = new GameEngine(new Settings(), players, player1, 1, 0, false, gameNameInfo);

        Collection<Pair<Integer, Integer>> homeSquares1 = player1.getHomeSquares();
        assertEquals(true, player1.getPieces().stream().allMatch(piece -> homeSquares1.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares2 = player2.getHomeSquares();
        assertEquals(true, player2.getPieces().stream().allMatch(piece -> homeSquares2.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares3 = player3.getHomeSquares();
        assertEquals(true, player3.getPieces().stream().allMatch(piece -> homeSquares3.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares4 = player4.getHomeSquares();
        assertEquals(true, player4.getPieces().stream().allMatch(piece -> homeSquares4.contains(piece.getPosition())));
    }


    
}