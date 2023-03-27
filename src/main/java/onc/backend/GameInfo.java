
package onc.backend;


import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Set;

import javax.crypto.IllegalBlockSizeException;



public class GameInfo {
    
    private String gameName;

    // info about players
    private String playerName1;
    private String playerName2;
    private String playerName3;
    private String playerName4;

    public GameInfo(List<String> gameInfoAsList){
        
        List<String> playerInfoList = gameInfoAsList.subList(1, gameInfoAsList.size());

        if (gameInfoAsList.size() != 5)
            throw new IllegalArgumentException("Missing info about game!");

        boolean allNonEmpty = gameInfoAsList.stream().allMatch(str -> !str.equals(""));
        if (!allNonEmpty)
            throw new IllegalStateException("Fill out every field!");

        // all names unique 
        if (gameInfoAsList.stream().anyMatch(name -> gameInfoAsList.indexOf(name) != gameInfoAsList.lastIndexOf(name))) {
            throw new IllegalArgumentException("The players must have different names");
        } 

        if (playerInfoList.stream().anyMatch(name -> name.length() > 9)) {
            throw new IllegalMonitorStateException("Maximum name length is 9!");
        }
        
        if (gameInfoAsList.get(0).length() > 25) {
            throw new GameNameLengthException("Maximum game name length is 25");
        }

        this.playerName1 = gameInfoAsList.get(1);
        this.playerName2 = gameInfoAsList.get(2);
        this.playerName3 = gameInfoAsList.get(3);
        this.playerName4 = gameInfoAsList.get(4);

        this.gameName = gameInfoAsList.get(0);
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

    public class GameNameLengthException extends IllegalArgumentException {
        public GameNameLengthException(String message) {
            super(message);
        }
    }

    
}
