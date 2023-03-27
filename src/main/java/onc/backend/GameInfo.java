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

        if (numPlayers == 4) {
            List<String> playerInfoList = gameInfoAsList.subList(1, gameInfoAsList.size());

            if (gameInfoAsList.size() != 5)
                throw new MissingInfoException("Missing info about game!");

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
            return;
        }



        if (numPlayers == 3) {
            
            List<String> playerInfoList = gameInfoAsList.subList(1, gameInfoAsList.size());

            if (gameInfoAsList.size() != 4)
                throw new MissingInfoException("Missing info about game!");

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
            this.playerName4 = "LudoLegend";
            return;
        }


        if (numPlayers == 2) {
            
            List<String> playerInfoList = gameInfoAsList.subList(1, gameInfoAsList.size());

            if (gameInfoAsList.size() != 3)
                throw new MissingInfoException("Missing info about game!");

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
            this.playerName2 = "DiceBot";
            this.playerName3 = gameInfoAsList.get(2);
            this.playerName4 = "LudoLegend";
            return;
        }

        if (numPlayers == 1) {
            
            List<String> playerInfoList = gameInfoAsList.subList(1, gameInfoAsList.size());

            if (gameInfoAsList.size() != 2)
                throw new MissingInfoException("Missing info about game!");

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
            this.playerName2 = "Robby";
            this.playerName3 = "DiceBot";
            this.playerName4 = "LudoLegend";
            return;
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
