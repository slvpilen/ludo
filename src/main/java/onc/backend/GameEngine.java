package onc.backend;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class GameEngine {

    private Player currentPlayer;
    private Board board;
    private Settings setttings;
    private ArrayList<Player> players;

    public GameEngine(Settings settings, ArrayList<Player> players){
        this.setttings = settings;
        this.players = players;
        this.players.sort((p1, p2) -> Integer.compare(p1.getHouseNumber(), p2.getHouseNumber()));
        houseDistributionCheck(this.players);
        this.currentPlayer = players.get(0);
        this.board = new Board();
    }
    // egen konstruktør for innlastet spill, må ta inn brett etc
    

    // checks that it's one player for each house (1-4)
    private void houseDistributionCheck(ArrayList<Player> players){
        boolean HouseDistributionOK =  IntStream.rangeClosed(1, 4).
        allMatch(houseNumber -> players.stream().
        anyMatch(player -> player.getHouseNumber() == houseNumber));

        if(!HouseDistributionOK)
            throw new IllegalStateException("Need one player for each house 1-4");
    }












    public static void main(String[] args) {
        Player player1 = new Player("kåre", 1);
        Player player2 = new Player("kåre", 2);
        Player player3 = new Player("kåre", 3);
        Player player4 = new Player("kåre", 5);

        ArrayList<Player> playersList = new ArrayList<>();
        playersList.add(player1);
        playersList.add(player2);
        playersList.add(player3);
        playersList.add(player4);

        GameEngine gameEngine = new GameEngine(new Settings(), playersList);

    }
}
