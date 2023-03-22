
package onc.backend;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameInfo {
    private String gameName;

    // info about players
    private String playerName1;
    private String playerName2;
    private String playerName3;
    private String playerName4;

    public GameInfo(List<String> gameInfoAsList){
        if (gameInfoAsList.size() != 5)
            throw new IllegalArgumentException("Missing info about game!");

        boolean allNonEmpty = gameInfoAsList.stream().allMatch(str -> !str.equals(""));
        if (!allNonEmpty)
            throw new IllegalStateException("Fill out every fields!");

        // all names uniqe 
        Set<String> set = new HashSet<>(gameInfoAsList);
        if (set.size() != gameInfoAsList.size())
            throw new IllegalArgumentException("cant have same names or name as gameName!");

        
            
        this.playerName1 = gameInfoAsList.get(1);
        this.playerName2 = gameInfoAsList.get(2);
        this.playerName3 = gameInfoAsList.get(3);
        this.playerName4 = gameInfoAsList.get(4);

        this.gameName = gameInfoAsList.get(0);
    }


    
}
