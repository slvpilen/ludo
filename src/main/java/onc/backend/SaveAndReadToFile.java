package onc.backend;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import onc.GameFaceController;



public class SaveAndReadToFile {
    
    private static final String SAVE_FILE_NAME = "ludoSave.txt";
    private static final String SAVE_FOLDER_PATH = System.getProperty("user.dir") + "/src/main/resources/saveFile";

    public void saveGameState(GameEngine gameEngine, GameNameInfo gameNameInfo) throws IOException {
        
        List<String> lines = new ArrayList<>();

        lines.add(Integer.toString(gameNameInfo.getNumPlayers()));
        lines.add(gameNameInfo.getGameName());
        gameEngine.getPlayers().stream().forEach(player -> lines.add(player.getUsername()));
        

        lines.add(Integer.toString(gameEngine.getCurrentPlayer().getHouseNumber()));
        lines.add(Integer.toString(gameEngine.getTurnRollCount()));
        lines.add(String.valueOf(gameEngine.getCanMakeMove() ? 1 : 0));

        gameEngine.getPieces().stream().forEach(piece -> lines.add(String.join(",", String.valueOf(piece.getPosition().getKey()), String.valueOf(piece.getPosition().getValue()), Integer.toString(piece.getPathIndex()), Integer.toString(piece.getOwner().getHouseNumber()))));

        Files.write(Paths.get(SAVE_FOLDER_PATH, SAVE_FILE_NAME), lines);
    }

    public GameEngine loadGameState() throws IOException {
        
        List<String> lines = Files.readAllLines(Paths.get(SAVE_FILE_NAME));
        Iterator<String> it = lines.iterator();

        int numPlayers = Integer.valueOf(it.next());
        List<String> gameNameInfoAsList = new ArrayList<>();

        IntStream.range(0, 5).forEach(num -> gameNameInfoAsList.add(it.next()));
        GameNameInfo gameNameInfo = new GameNameInfo(gameNameInfoAsList, numPlayers);
        
        int currentPlayerHouse = Integer.valueOf(it.next());
        int latestDice = Integer.valueOf(it.next());
        boolean canMakeMove = Boolean.valueOf(it.next());
        Settings settings = new Settings();

        while (it.hasNext()) {
            String[] pieceData = it.next().split(",");
            int positionX = Integer.parseInt(pieceData[0]);
            int positionY = Integer.valueOf(pieceData[1]);
            int index = Integer.parseInt(pieceData[2]);
            int owner = Integer.parseInt(pieceData[3]);
            
            game.addPiece(new Piece(position, index, owner));
        }
        
       

        GameEngine gameEngine = new GameEngine(settings, players, );
        return game;
    }

}
