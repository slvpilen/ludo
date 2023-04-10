package onc.backend;

import java.util.ArrayList;
import java.util.List;


public class GameNameInfo {
    
    private String gameName;
    private int numPlayers;
    private String playerName1, playerName2, playerName3, playerName4;
    private List<String> gameInfoAsList = new ArrayList<String>();

    /**
     * The constructor checks if the names entered in the createGame-Scene are valid, 
     * and that the number of names correspond to the number of human players.
     * 
     * @param gameInfoAsList The list containing the gameName and the names for the human players.
     * @param numPlayers The number of human players.
     * 
     * @return A list of size 5, which contains the names of the game and the players. The first entry is the name of the game, 
     * and the next entries are the names of player1, player2, player3 and player4.
     */
    public GameNameInfo(List<String> gameInfoAsList, int numPlayers){
        
        playerNameCheck(gameInfoAsList, numPlayers);

        this.numPlayers = numPlayers;
        this.gameInfoAsList.add(gameName);
        this.gameInfoAsList.add(playerName1);
        this.gameInfoAsList.add(playerName2);
        this.gameInfoAsList.add(playerName3);
        this.gameInfoAsList.add(playerName4);
    }   

    /**
     * Utility-method which is run when a player clicks submit in the createGame-scene.
     * The method checks if the player has entered valid names for the players and the game.
     * The method automatically assigns names to the robotPlayers.
     * If numPlayers is 2, then 2 of the players will be robots, and will be given robotNames.
     *
     * @param gameInfoAsList The list containing names for the game and the players.
     * @param numPlayers The number of human players.
     * 
     * @throws IllegalArgumentException If the number of players is not an integer between 1 and 4 (inclusive), or if some of the players have the same names (or the same name as the game).
     * @throws MissingInfoException If the number of names entered is not equal to numPlayers + 1 (the name of the game).
     * @throws IllegalStateException If some of the fields are empty (the empty string).
     * @throws IllegalMonitorStateException If some of the players names have more than 9 characters.
     * @throws GameNameLengthException If the name of the game has more than 25 characters.
     */
    private void playerNameCheck(List<String> gameInfoAsList, int numPlayers) {
        
        if (numPlayers < 1 || numPlayers > 4) {
            throw new IllegalArgumentException("Invalid number of players!");
        }
        
        else if (gameInfoAsList.size() != numPlayers + 1) {
            throw new MissingInfoException("Missing info about game!");
        }
        
        else if (!gameInfoAsList.stream().allMatch(str -> !str.isEmpty())) {
            throw new IllegalStateException("Fill out every field!");
        }
        
        else if (gameInfoAsList.stream().distinct().count() != numPlayers + 1) {
            throw new IllegalArgumentException("The players must have different names");
        }
        
        else if (gameInfoAsList.stream().skip(1).anyMatch(name -> name.length() > 9)) {
            throw new IllegalMonitorStateException("Maximum name length is 9!");
        }
        
        else if (gameInfoAsList.get(0).length() > 25) {
            throw new GameNameLengthException("Maximum game name length is 25");
        }
        
        this.gameName = gameInfoAsList.get(0);
        this.playerName1 = gameInfoAsList.get(1);
        

        if (numPlayers == 2) {
            this.playerName2 = "Robby";
            this.playerName3 = gameInfoAsList.get(2);
            this.playerName4 = "LudoLegend";
            return;
        }

        if (numPlayers > 2) {
            this.playerName2 = gameInfoAsList.get(2);
            this.playerName3 = gameInfoAsList.get(3);
        } 
        
        else {
            playerName2 = "Robby";
            this.playerName3 = "DiceBot";
        }
        
        if (numPlayers > 3) {
            this.playerName4 = gameInfoAsList.get(4);
        } 
        
        else {
            this.playerName4 = "LudoLegend";
        }
    }
    
    /**
     * @return The name of the game.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @return The name of player1
     */
    public String getPlayerName1() {
        return playerName1;
    }

    /**
     * @return The name of player2
     */
    public String getPlayerName2() {
        return playerName2;
    }

    /**
     * @return The name of player3
     */
    public String getPlayerName3() {
        return playerName3;
    }

    /**
     * @return The name of player4
     */
    public String getPlayerName4() {
        return playerName4;
    }

    /**
     * @return The number of human players.
     */
    public int getNumPlayers() {
        return numPlayers;
    }
    
    /**
     * @return A list with 5 elements. The first element is the name of the game, and the next four elements are the names of player1, player2, player3 and player4.
     */
    public List<String> getGameNameInfoAsList() {
        return List.copyOf(gameInfoAsList);
    }

    /**
     * Exception which is thrown when a user inserts a game name which is too long (more than 25 characters).
     */
    public class GameNameLengthException extends IllegalArgumentException {
        public GameNameLengthException(String message) {
            super(message);
        }
    }

    /**
     * Exception which is thrown when one of the name entries are not filled out.
     */
    public class MissingInfoException extends IllegalArgumentException {
        public MissingInfoException(String message) {
            super(message);
        }
    }



    
}
