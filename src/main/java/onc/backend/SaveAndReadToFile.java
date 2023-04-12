package onc.backend;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;
import javafx.util.Pair;



public class SaveAndReadToFile {
    
    private static final String SAVE_FILE_NAME = "ludoSave.txt";
    private static final String SAVE_FOLDER_PATH = System.getProperty("user.dir") + "/src/main/resources/saveFile";

    public void saveGameState(GameEngine gameEngine, GameNameInfo gameNameInfo) throws IOException {
        
        List<String> lines = new ArrayList<>();

        lines.add(Integer.toString(gameNameInfo.getNumPlayers()));
        lines.add(gameNameInfo.getGameName());
        gameEngine.getPlayers().stream().forEach(player -> lines.add(player.getUsername()));
        

        lines.add(Integer.toString(gameEngine.getCurrentPlayer().getHouseNumber()));
        lines.add(Integer.toString(gameEngine.getDice()));
        lines.add(Integer.toString(gameEngine.getTurnRollCount()));
        lines.add(String.valueOf(gameEngine.getCanMakeMove() ? 1 : 0));

        gameEngine.getPieces().stream().forEach(piece -> lines.add(String.join(",", String.valueOf(piece.getPosition().getKey()), String.valueOf(piece.getPosition().getValue()), Integer.toString(piece.getPathIndex()), Integer.toString(piece.getOwner().getHouseNumber()))));

        Files.write(Paths.get(SAVE_FOLDER_PATH, SAVE_FILE_NAME), lines);
    }

    public GameEngine loadLudoGame() throws IOException {
        
        List<String> lines = Files.readAllLines(Paths.get(SAVE_FOLDER_PATH, SAVE_FILE_NAME));
        Iterator<String> it = lines.iterator();

        int numPlayers = Integer.valueOf(it.next());
        List<String> gameNameInfoAsList = new ArrayList<>();

        IntStream.range(0, 5).forEach(num -> gameNameInfoAsList.add(it.next()));
        GameNameInfo gameNameInfo = new GameNameInfo(gameNameInfoAsList, numPlayers, false);
        
        ArrayList<Player> players = new ArrayList<>();
        
        players.add(new Player(gameNameInfo.getPlayerName1(), 1));
        
        if (numPlayers == 1) {
            players.add(new RobotPlayer(gameNameInfo.getPlayerName2(), 2));
            players.add(new RobotPlayer(gameNameInfo.getPlayerName3(), 3));
            players.add(new RobotPlayer(gameNameInfo.getPlayerName4(), 4));
        }

        else {
            players.add(numPlayers == 2 ? new RobotPlayer(gameNameInfo.getPlayerName2(), 2) : new Player(gameNameInfo.getPlayerName2(), 2));
            players.add(new Player(gameNameInfo.getPlayerName3(), 3));
            players.add(numPlayers == 4 ? new Player(gameNameInfo.getPlayerName4(), 4) : new RobotPlayer(gameNameInfo.getPlayerName4(), 4));
        }

        int currentPlayerHouse = Integer.valueOf(it.next());
        Player currentPlayer = players.get(currentPlayerHouse - 1);
        int latestDice = Integer.valueOf(it.next());
        int turnRollCount = Integer.valueOf(it.next());
        boolean canMakeMove = Boolean.valueOf(Integer.valueOf(it.next()) == 1);
        Settings settings = new Settings();

        while (it.hasNext()) {
            String[] pieceData = it.next().split(",");
            int positionX = Integer.parseInt(pieceData[0]);
            int positionY = Integer.valueOf(pieceData[1]);
            Pair<Integer, Integer> position = new Pair<Integer, Integer>(positionX, positionY);
            int pathIndex = Integer.parseInt(pieceData[2]);
            int houseNumber = Integer.parseInt(pieceData[3]);
            Player owner = players.get(houseNumber - 1);
            
            new Piece(owner, position, pathIndex);
        }

        GameEngine gameEngine = new GameEngine(settings, players, currentPlayer, latestDice, turnRollCount, canMakeMove, gameNameInfo);
        return gameEngine;
    }

}
