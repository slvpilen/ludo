package backend;

import onc.backend.GameEngine;
import onc.backend.GameNameInfo;
import onc.backend.Piece;
import onc.backend.Player;
import onc.backend.Settings;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.print.Collation;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Pair;

public class GameEngineTest {
    
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

    @BeforeEach
    public void newGameSetup() {
        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        ArrayList<String> gameInfoAsList = new ArrayList<>(Arrays.asList("PaaskeCup", "Truls", "Fred", "Megan", "Ivan"));
        gameNameInfo = new GameNameInfo(gameInfoAsList, 4, true);
        gameEngine = new GameEngine(new Settings(), players, player1, 1, 0, false, gameNameInfo);
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
    public void testMovePiece() {
        
        Piece piece = new Piece(player1, new Pair<>(7, 18), 0);
        piece.addPieceToGrid(gameGrid);
        
        gameEngine.rollDice(4);
        gameEngine.movePiece(piece);

        assertEquals(new Pair<Integer, Integer>(7, 14), piece.getPosition());
        assertEquals(player2, gameEngine.getCurrentPlayer());

    }

    @Test
    public void testRoll6ThreeTimes() throws InterruptedException {

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
    public void testCorrectCurrentPlayer() throws InterruptedException {

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
    @Test
    public void testMovingAPieceNotCurrentPlayer(){
        // rolling dice
        gameEngine.rollDice(6);
        // trying to move wrong piece, expect no error
        assertDoesNotThrow(() -> {
            gameEngine.movePiece(player4.getPieces().get(0));
        });

        Collection<Pair<Integer,Integer>> homeSquaresPlayer4 = player4.getHomeSquares();
        // testing that piece is not moved, because its player1 currentplayer and not player4
        assertTrue(homeSquaresPlayer4.contains(player4.getPieces().get(0).getPosition()));
        // testing current player still player 1
        assertEquals(player1, gameEngine.getCurrentPlayer(),"wrong current player");

    }


}
