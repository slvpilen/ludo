package backend;

import onc.backend.GameEngine;
import onc.backend.Piece;
import onc.backend.Player;
import onc.backend.Settings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;




public class PieceTest {

    Player player1 = new Player("testPlayer1", 1);
    Player player2 = new Player("testPlayer2", 2);
    Player player3 = new Player("testPlayer3", 3);
    Player player4 = new Player("testPlayer4", 4);




    @Test
    void testPieceCreation() {
        Piece piece = new Piece(player1, new Pair<>(2, 18), new GridPane());
        assertNotNull(piece);
        assertThrows(IllegalArgumentException.class, () -> new Piece(player1, new Pair<>(-1, -2), new GridPane()));
    }

    @Test
    void testMovePiece() {
        //Piece piece = new Piece(player1, new Pair<>(1, 2), new GridPane());

        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        GameEngine gameEngine = new GameEngine(new Settings(), players);
        player1.setGameEngine(gameEngine);
        gameEngine.rollDice();
        System.out.println(gameEngine.getDice());

        //System.out.println(gameEngine.getCurrentPlayer());

        //assertEquals(gameEngine.getCurrentPlayer().equals(player1), piece.hasLegalMove());
        Collection<Pair<Integer, Integer>> homeSquares = player1.getHomeSquares();
        assertEquals(true, player1.getPieces().stream().allMatch(piece -> homeSquares.contains(piece.getPosition())));
        // wont work, because player1.getPieces return null. I think it's because the gamegrid has no fields in this test
        boolean hasLegalMove = player1.getPieces().stream().findFirst().orElse(null).hasLegalMove();
        assertEquals(gameEngine.getDice() == 6, hasLegalMove);

        
    }
}