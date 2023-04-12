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
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void newGameSetup() {
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        ArrayList<String> gameInfoAsList = new ArrayList<>(Arrays.asList("PaaskeCup", "Truls", "Fred", "Megan", "Ivan"));
        gameNameInfo = new GameNameInfo(gameInfoAsList, 4, true);
        gameEngine = new GameEngine(new Settings(), players, player1, 1, 0, false, gameNameInfo);
    }


    @Test
    public void piecesOnHomeSquaresTest() {
        
        Collection<Pair<Integer, Integer>> homeSquares1 = player1.getHomeSquares();
        assertEquals(true, player1.getPieces().stream().allMatch(piece -> homeSquares1.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares2 = player2.getHomeSquares();
        assertEquals(true, player2.getPieces().stream().allMatch(piece -> homeSquares2.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares3 = player3.getHomeSquares();
        assertEquals(true, player3.getPieces().stream().allMatch(piece -> homeSquares3.contains(piece.getPosition())));
        
        Collection<Pair<Integer, Integer>> homeSquares4 = player4.getHomeSquares();
        assertEquals(true, player4.getPieces().stream().allMatch(piece -> homeSquares4.contains(piece.getPosition())));
    }


    @Test
    public void testRollDice1() throws InterruptedException {
    
        gameEngine.rollDice(1);
        assertEquals(false, gameEngine.getCanMakeMove());

        // The test needs to wait for the timer to run out.
        // The gameEngine waits 1 second before it changes the player.
        // This ensures that a player can actually see what he rolled before the next player gets to roll.
        Thread.sleep(1100);
        assertEquals(player2, gameEngine.getCurrentPlayer());
    }


    @Test
    public void testRollDice6() {
        
        //Rolling 6
        gameEngine.rollDice(6);
        assertEquals(true, gameEngine.getCanMakeMove());
        assertEquals(true, player1.hasAnyValidMoves());
        
        //Moving piece
        gameEngine.movePiece(player1.getPieces().get(0));
        assertEquals(player1, gameEngine.getCurrentPlayer());

        //Rolling 6 gives you an extra roll.
        gameEngine.rollDice(3);
        assertEquals(true, gameEngine.getCanMakeMove());
        
        //Player1 rolled 3, and player2 is next in line.
        gameEngine.movePiece(player1.getPieces().get(0));
        assertEquals(player2, gameEngine.getCurrentPlayer());
    }


    @Test
    public void movePiece() {
        
        Piece piece = new Piece(player1, new Pair<>(7, 18), 0);
        piece.addPieceToGrid(gameGrid);
        
        gameEngine.rollDice(4);
        gameEngine.movePiece(piece);

        assertEquals(new Pair<Integer, Integer>(7, 14), piece.getPosition());
        assertEquals(player2, gameEngine.getCurrentPlayer());

    }

    @Test
    public void roll6ThreeTimes() throws InterruptedException {

        //Rolling 6
        gameEngine.rollDice(6);
        assertEquals(true, gameEngine.getCanMakeMove());
        assertEquals(true, player1.hasAnyValidMoves());
        
        //Moving piece
        gameEngine.movePiece(player1.getPieces().get(0));
        assertEquals(player1, gameEngine.getCurrentPlayer());

        //Rolling 6
        gameEngine.rollDice(6);
        assertEquals(true, gameEngine.getCanMakeMove());
        assertEquals(true, player1.hasAnyValidMoves());
        
        //Moving piece
        gameEngine.movePiece(player1.getPieces().get(0));
        assertEquals(player1, gameEngine.getCurrentPlayer());

        //Rolling 6 for the third time
        gameEngine.rollDice(6);
        assertEquals(false, gameEngine.getCanMakeMove());
        
        Thread.sleep(1100);
        assertEquals(false, player1.hasAnyValidMoves());
        assertEquals(player2, gameEngine.getCurrentPlayer());
    }

    @Test
    public void correctCurrentPlayer() throws InterruptedException {

        assertEquals(player1, gameEngine.getCurrentPlayer());

        gameEngine.rollDice(1);
        Thread.sleep(1100);
        assertEquals(player2, gameEngine.getCurrentPlayer());

        gameEngine.rollDice(2);
        Thread.sleep(1100);
        assertEquals(player3, gameEngine.getCurrentPlayer());

        gameEngine.rollDice(3);
        Thread.sleep(1100);
        assertEquals(player4, gameEngine.getCurrentPlayer());

        gameEngine.rollDice(4);
        Thread.sleep(1100);
        assertEquals(player1, gameEngine.getCurrentPlayer());

        gameEngine.rollDice(6);
        assertEquals(player1, gameEngine.getCurrentPlayer());
        
    }

}