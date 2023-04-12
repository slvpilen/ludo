package backend;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Pair;
import onc.backend.GameEngine;
import onc.backend.Player;
import onc.backend.GameNameInfo;
import onc.backend.Piece;
import onc.backend.SaveAndReadToFile;
import onc.backend.Settings;



public class SaveAndReadToFileTest {
    GridPane gameGrid;
    Player player1;
    Player player2;
    Player player3;
    Player player4;
    GameEngine gameEngine;
    GameNameInfo gameNameInfo;
    SaveAndReadToFile saveAndReadToFile;



    @BeforeEach
    public void setup() {
        
        GridPane gameGrid = new GridPane();

        // Add 50 columns
        for (int i = 0; i < 50; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100 / 50.0);
            gameGrid.getColumnConstraints().add(col);
        }

        // Add 50 rows
        for (int i = 0; i < 50; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100 / 50.0);
            gameGrid.getRowConstraints().add(row);
        }

        player1 = new Player("Truls", 1, gameGrid);
        player2 = new Player("Fred", 2, gameGrid);
        player3 = new Player("Megan", 3, gameGrid);
        player4 = new Player("Ivan", 4, gameGrid);

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));
        ArrayList<String> gameInfoAsList = new ArrayList<>(Arrays.asList("PaaskeCup", "Truls", "Fred", "Megan", "Ivan"));
        gameNameInfo = new GameNameInfo(gameInfoAsList, 4, true);
        
        gameEngine = new GameEngine(new Settings(), players, player1, 6, 0, true, gameNameInfo);

        saveAndReadToFile = new SaveAndReadToFile();
    }


    @Test
    public void testSaveAndLoad() throws IOException {
        
        saveAndReadToFile.saveLudoGame(gameEngine, gameNameInfo);
        GameEngine loadedGameEngine = saveAndReadToFile.loadLudoGame();
        
        assertEquals("Truls", gameEngine.getPlayers().get(0).getUsername());
        assertEquals("Fred", gameEngine.getPlayers().get(1).getUsername());
        assertEquals("Megan", gameEngine.getPlayers().get(2).getUsername());
        assertEquals("Ivan", gameEngine.getPlayers().get(3).getUsername());

        assertEquals("Truls", loadedGameEngine.getPlayers().get(0).getUsername());
        assertEquals("Fred", loadedGameEngine.getPlayers().get(1).getUsername());
        assertEquals("Megan", loadedGameEngine.getPlayers().get(2).getUsername());
        assertEquals("Ivan", loadedGameEngine.getPlayers().get(3).getUsername());

        assertEquals(player1, gameEngine.getCurrentPlayer());
        assertEquals(6, gameEngine.getDice());
        assertEquals(true, gameEngine.getCanMakeMove());
        assertEquals(gameNameInfo, gameEngine.getGameNameInfo());
    }


    @Test public void moveAndSave() throws IOException, InterruptedException {

        Piece piece = player1.getPieces().get(0);

        gameEngine.movePiece(piece);
        gameEngine.rollDice(2);
        gameEngine.movePiece(piece);
        Thread.sleep(1100);

        Pair<Integer, Integer> expectedPostion = piece.getPath().get(2);
        assertEquals(expectedPostion, piece.getPosition());

        saveAndReadToFile.saveLudoGame(gameEngine, gameNameInfo);
        GameEngine loadedGameEngine = saveAndReadToFile.loadLudoGame();

        assertEquals("Truls", gameEngine.getPlayers().get(0).getUsername());
        assertEquals("Fred", gameEngine.getPlayers().get(1).getUsername());
        assertEquals("Megan", gameEngine.getPlayers().get(2).getUsername());
        assertEquals("Ivan", gameEngine.getPlayers().get(3).getUsername());

        assertEquals("Truls", loadedGameEngine.getPlayers().get(0).getUsername());
        assertEquals("Fred", loadedGameEngine.getPlayers().get(1).getUsername());
        assertEquals("Megan", loadedGameEngine.getPlayers().get(2).getUsername());
        assertEquals("Ivan", loadedGameEngine.getPlayers().get(3).getUsername());

        assertEquals(player2, gameEngine.getCurrentPlayer());
        assertEquals(2, gameEngine.getDice());
        assertEquals(false, gameEngine.getCanMakeMove());
        assertEquals(gameNameInfo, gameEngine.getGameNameInfo());
    }

    
}