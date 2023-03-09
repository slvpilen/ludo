package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.IntStream;
import java.util.Random;
import javafx.util.Pair;

public class GameEngine {

    private Player currentPlayer;
    private Player latestPlayer;
    private Board board;
    private Settings setttings;
    private ArrayList<Player> players;
    private int latestDice;
    private int turnRollCount; // counting how many dice roll on a roll
    private boolean canMakeMove;




    public GameEngine(Settings settings, ArrayList<Player> players){
        this.setttings = settings;
        this.players = players;
        this.players.sort((p1, p2) -> Integer.compare(p1.getHouseNumber(), p2.getHouseNumber()));
        houseDistributionCheck(this.players);
        this.currentPlayer = players.get(0);
        this.board = new Board(players);
        this.canMakeMove = false; // game starts to roll dice
        this.currentPlayer = players.get(0);
        players.forEach(player -> player.setGameEngine(this));
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

    public Collection<Piece> getPices(){
        Collection<Piece> allPieces = new ArrayList<>();

        for (Player player : players){
            Collection<Piece> pieces =  player.getPieces();
            pieces.forEach(piece -> allPieces.add(piece));
        }

        return allPieces;

    }

    public void movePiece(Piece piece){
        System.out.println("clicked on a piece");
        if (!this.canMakeMove)
            return;
        if (!piece.hasLegalMove())
            return;
        if (piece.getHouseNumber() != currentPlayer.getHouseNumber())
            return;

        this.canMakeMove = false; 
        
        piece.movePlaces(latestDice);
        updateCurrentPlayer(piece);
    }

    private void updateCurrentPlayer(Piece piece){
        this.latestPlayer = piece.getOwner();
        this.turnRollCount++;
        if (latestDice == 6 && turnRollCount<3)
            this.currentPlayer = piece.getOwner();
        else{
            this.currentPlayer = getNextPlayer();  //add this methode!
            turnRollCount = 0;  // nullstiller tellern
        }
    }

/*     private Player nextPlayer(){
        //if make any logic that return whos next, by loop proincip
        if (players.hasNext..... etc)
    } */


    public void rollDice() {
        //System.out.println("pressed rolled dice");
        if (canMakeMove)  // not allowd to roll befor moved
            return;
        Random terning = new Random();
        this.latestDice = terning.nextInt(6) + 1;

        if(currentPlayer.hasAnyValidMoves(latestDice)) // add this methode
            this.canMakeMove = true;
        else{
            this.canMakeMove = false;
            latestPlayer = currentPlayer;
            currentPlayer = getNextPlayer(); // addd this methode
        }

        //return terningkast;
    }

    public int getDice(){
        return latestDice;
    }

    // Må senere implementere sjekker for om det står et tårn i veien. 
/*     
    public ArrayList<> legalMove(Player player, Piece piece) {
        
        if (!player.getPiecesPositions().contains(piece)) {
            throw new IllegalArgumentException("Du kan ikke flytte en brikke du ikke eier.");
        }

        if (player.getHomeSquares().contains(piece)) {
            
             if (diceroll == 6) {
                return true;
            }

            return false;
        }
    } */

    //public boolean rollDice()









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
