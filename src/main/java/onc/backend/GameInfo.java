package onc.backend;

import java.util.ArrayList;
import java.util.List;


public class GameInfo {
    
    private String gameName;

    // info about players
    private String playerName1;
    private String playerName2;
    private String playerName3;
    private String playerName4;

    private List<String> gameInfoAsList = new ArrayList<String>();


    public GameInfo(List<String> gameInfoAsList, int numPlayers){
        
        playerNameCheck(gameInfoAsList, numPlayers);

        this.gameName = gameInfoAsList.get(0);

        this.gameInfoAsList.add(gameName);
        this.gameInfoAsList.add(playerName1);
        this.gameInfoAsList.add(playerName2);
        this.gameInfoAsList.add(playerName3);
        this.gameInfoAsList.add(playerName4);
        
    }   




    public void playerNameCheck(List<String> gameInfoAsList, int numPlayers) {
        if (numPlayers < 1 || numPlayers > 4) {
            throw new IllegalArgumentException("Invalid number of players!");
        }
        
        if (gameInfoAsList.size() != numPlayers + 1) {
            throw new MissingInfoException("Missing info about game!");
        }
        
        if (!gameInfoAsList.stream().allMatch(str -> !str.isEmpty())) {
            throw new IllegalStateException("Fill out every field!");
        }
        
        if (gameInfoAsList.stream().distinct().count() != numPlayers + 1) {
            throw new IllegalArgumentException("The players must have different names");
        }
        
        if (gameInfoAsList.stream().skip(1).anyMatch(name -> name.length() > 9)) {
            throw new IllegalMonitorStateException("Maximum name length is 9!");
        }
        
        if (gameInfoAsList.get(0).length() > 25) {
            throw new GameNameLengthException("Maximum game name length is 25");
        }
        
        this.playerName1 = gameInfoAsList.get(1);
        
        if (numPlayers > 1) {
            this.playerName2 = gameInfoAsList.get(2);
        } else {
            this.playerName2 = "Robby";
        }
        
        if (numPlayers > 2) {
            this.playerName3 = gameInfoAsList.get(3);
        } else {
            this.playerName3 = "DiceBot";
        }
        
        if (numPlayers > 3) {
            this.playerName4 = gameInfoAsList.get(4);
        } else {
            this.playerName4 = "LudoLegend";
        }
    }
    
    
    
    public String getGameName() {
        return gameName;
    }

    public String getPlayerName1() {
        return playerName1;
    }

    public String getPlayerName2() {
        return playerName2;
    }

    public String getPlayerName3() {
        return playerName3;
    }

    public String getPlayerName4() {
        return playerName4;
    }

    public List<String> getGameInfoAsList() {
        return List.copyOf(gameInfoAsList);
    }

    public class GameNameLengthException extends IllegalArgumentException {
        public GameNameLengthException(String message) {
            super(message);
        }
    }

    public class MissingInfoException extends IllegalArgumentException {
        public MissingInfoException(String message) {
            super(message);
        }
    }



    
}
