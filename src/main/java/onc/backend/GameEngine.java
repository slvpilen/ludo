package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;
import javafx.util.Pair;

public class GameEngine {

    private Player currentPlayer;
    private Player latestPlayer;
    private Settings settings;
    private ArrayList<Player> players;
    private int latestDice;
    private int turnRollCount; // counting how many dice roll on a roll
    private boolean canMakeMove; // possible for a player to make a move this will be true, else if its time to roll dice this will be false




    public GameEngine(Settings settings, ArrayList<Player> players){
        this.settings = settings;
        this.players = players;
        this.players.sort((p1, p2) -> Integer.compare(p1.getHouseNumber(), p2.getHouseNumber()));
        houseDistributionCheck(this.players);
        this.currentPlayer = players.get(0);
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

    public Collection<Piece> getPieces(){
        Collection<Piece> allPieces = new ArrayList<>();
    
        for (Player player : players){
            Collection<Piece> pieces =  player.getPieces();
            pieces.forEach(piece -> allPieces.add(piece));
        }
    
        return allPieces;
    }
    

    public void movePiece(Piece piece){
        
        if (!this.canMakeMove)
            return;

        if (!piece.hasLegalMove())
            return;

        if (piece.getHouseNumber() != currentPlayer.getHouseNumber())
            return;
        
        piece.movePlaces();
        this.canMakeMove = false;
        updateCurrentPlayer(piece);
    }

    private void updateCurrentPlayer(Piece piece){        
        
        this.latestPlayer = piece.getOwner();
        this.turnRollCount++;

        if (latestDice == 6 && turnRollCount<3)
            this.currentPlayer = piece.getOwner();

        else{
            this.currentPlayer = getNextPlayer();
            turnRollCount = 0;  // nullstiller tellern
        }    
    }

    private Player getNextPlayer(){
        
        if(players.size()-1 == players.indexOf(currentPlayer))
            return players.get(0);

        return players.get(players.indexOf(currentPlayer) + 1);

    }



    public void rollDice() {
        
        if (canMakeMove)  // not allowed to roll before moved
            return;
        
        Random terning = new Random();
        this.latestDice = terning.nextInt(6) + 1;

        if(currentPlayer.hasAnyValidMoves()) { // add this method
            this.canMakeMove = true;
        }
        

        // Will never be used:
        // else if (latestDice==6 && turnRollCount < 3 ){
        //     this.canMakeMove = false;
        //     latestPlayer = currentPlayer;
        //     this.turnRollCount++;
        // }
        
        else{
            this.canMakeMove = false;
            latestPlayer = currentPlayer;
            currentPlayer = getNextPlayer(); 
            this.turnRollCount = 0;
        }

    }

    public int getDice(){
        return latestDice;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getNumberOfPiecesOnLocation(Pair<Integer, Integer> location){
        int counter = 0;
        for (Player player : players){
            Collection<Piece> pieces = player.getPieces();
            for (Piece piece : pieces){
                if (piece.getPosition().equals(location))
                    counter++;
            }
        }
        return counter;
    }

    public void setPieceOnLocationToHouse(Pair<Integer, Integer> endLocation){
        players.forEach(player -> player.getPieces().stream().
        filter(piece -> piece.getPosition().equals(endLocation)).collect(Collectors.toList()).
        forEach(piece -> piece.setToHouse()));
    }
}


