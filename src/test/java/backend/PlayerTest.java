package backend;

import onc.backend.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;

public class PlayerTest {
    Player player1 = new Player("Truls", 1);
    Player player2 = new Player("Fred", 2);
    Player player3 = new Player("Magan", 3);
    Player player4 = new Player("Ivan", 4);

    @Test
    void testPlayerCreation() {
        assertEquals("Truls", player1.getUsername());
        assertEquals("Fred", player2.getUsername());
        assertEquals("Magan", player3.getUsername());
        assertEquals("Ivan", player4.getUsername());
        assertEquals(1, player1.getHouseNumber());
        assertEquals(2, player2.getHouseNumber());
        assertEquals(3, player3.getHouseNumber());
        assertEquals(4, player4.getHouseNumber());
        assertThrows(IllegalArgumentException.class, () -> new Player("Truls", 0));
        assertThrows(IllegalArgumentException.class, () -> new Player("Truls", 5));
    }

    @Test
    void testHomeSquaresPlayer1(){

        Collection<Pair<Integer, Integer>> homesquare = player1.getHomeSquares();
        assertEquals(homesquare.size(), 4);
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(2, 18)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(2, 16)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(4, 16)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(4, 18)));
    }

    @Test
    void testHomeSquaresPlayer2(){
        Collection<Pair<Integer, Integer>> homesquare = player2.getHomeSquares();
        assertEquals(homesquare.size(), 4);
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(2, 8)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(2, 6)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(4, 6)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(4, 8)));
    }

    @Test
    void testHomeSquaresPlayer3(){
        Collection<Pair<Integer, Integer>> homesquare = player3.getHomeSquares();
        assertEquals(homesquare.size(), 4);
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(12, 8)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(12, 6)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(14, 6)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(14, 8)));
    }

    @Test
    void testHomeSquaresPlayer4(){
        Collection<Pair<Integer, Integer>> homesquare = player4.getHomeSquares();
        assertEquals(homesquare.size(), 4);
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(12, 18)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(12, 16)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(14, 16)));
        assertTrue(homesquare.contains(new Pair<Integer, Integer>(14, 18)));
    }

}