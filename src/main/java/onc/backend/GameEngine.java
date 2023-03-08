package onc.backend;

import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.Random;
import javafx.util.Pair;

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
        this.board = new Board(players);
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


    public int rollDice() {
        Random terning = new Random();
        int terningkast = terning.nextInt(6) + 1;
        return terningkast;
    }

    // Må senere implementere sjekker for om det står et tårn i veien. 
    
    public boolean legalMove(Player player, Pair<Integer, Integer> piece, int diceroll) {
        
        if (!player.getPiecesLocation().contains(piece)) {
            throw new IllegalArgumentException("Du kan ikke flytte en brikke du ikke eier.");
        }

        if (player.homeSquares().contains(piece)) {
            
            if (diceroll == 6) {
                return true;
            }

            return false;
        }
    }

    public boolean rollDice()













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
