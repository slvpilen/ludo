package onc.skrot;

import java.util.*;
import java.util.stream.*;
import onc.backend.*;

public class Skrot {
    
    // checks that it's one player for each house (1-4)
    private void houseDistributionCheck(ArrayList<Player> players){
        boolean HouseDistributionOK =  IntStream.rangeClosed(1, 4).
        allMatch(houseNumber -> players.stream().
        anyMatch(player -> player.getHouseNumber() == houseNumber));

        if(!HouseDistributionOK)
            throw new IllegalStateException("Need one player for each house 1-4");
    }

}
