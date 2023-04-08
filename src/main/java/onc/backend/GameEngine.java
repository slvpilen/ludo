package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.util.Pair;


public class GameEngine {

    private Player currentPlayer;
    //private Player latestPlayer;
    // private Settings settings;
    private ArrayList<Player> players;
    private int latestDice;
    private int turnRollCount; // counting how many dice roll on a roll
    private boolean canMakeMove; // possible for a player to make a move this will be true, else if its time to roll dice this will be false.
    private List<InterfaceGameEngineListener> listeners = new ArrayList<>();



    public GameEngine(Settings settings, ArrayList<Player> players){
        //this.settings = settings;  // this is not used atm, use it, or delete it. sound on of, could be stored in settings etc
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
        
        // Sjekker om en spiller har vunnet:
        if (piece.getOwner().isFinished()) {
            firePlayerWon(currentPlayer.getUsername());
        }

        firePlayerMadeMove();

        this.canMakeMove = false;
        updateCurrentPlayer(piece);
    }

    private void updateCurrentPlayer(Piece piece){        
        
        //this.latestPlayer = piece.getOwner();
        this.turnRollCount++;

        if (latestDice == 6 && turnRollCount<3) {
            this.currentPlayer = piece.getOwner();
            fireCurrentPlayerChanged();
            fireRobotCheck();
        }
        
        else{
            this.currentPlayer = getNextPlayer();
            fireCurrentPlayerChanged();
            turnRollCount = 0;  // nullstiller tellern
            fireRobotCheck(); 
        }    
    }

    private Player getNextPlayer(){
        
        if(players.size()-1 == players.indexOf(currentPlayer))
            return players.get(0);

        return players.get(players.indexOf(currentPlayer) + 1);

    }

    class TimerNoValidMove extends TimerTask {

        @Override
        public void run() {
            
            if (!(currentPlayer instanceof RobotPlayer)) {
                firePlayerMadeMove();
                currentPlayer = getNextPlayer();
                fireCurrentPlayerChanged();
                fireRobotCheck();
            } else {
                currentPlayer = getNextPlayer();
                fireCurrentPlayerChanged();
                fireRobotCheck();
            }

            fireDiceClickable(true);
        }

    }



    public void rollDice() {
        
        if (canMakeMove)  // not allowed to roll before moved
            return;
        
        Random terning = new Random();
        // this.latestDice = terning.nextInt(6) + 1;
        latestDice = 6;
        fireUpdateImageOfDice(latestDice);
    
        if(currentPlayer.hasAnyValidMoves() && !(turnRollCount == 2 && latestDice == 6)) {
            this.canMakeMove = true;
            return;
        } 
        
        int oldTurnRollCount = turnRollCount;
        fireDiceClickable(false);
        this.canMakeMove = false;
        this.turnRollCount = 0;
        
        if (oldTurnRollCount == 2 && latestDice == 6) {
            fireThreeSixInRowText();
            Timer timer = new Timer();
            timer.schedule(new TimerNoValidMove(), 2000);
        }
        
        else {
            
            fireNoValidMoveText();
            Timer timer = new Timer();
            timer.schedule(new TimerNoValidMove(), 2000);
        }
    }
    

    public int getDice(){
        return latestDice;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean getCanMakeMove() {
        return canMakeMove;
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


    public void addListener(InterfaceGameEngineListener listener) {
        if (listeners.contains(listener)) {
            throw new IllegalArgumentException("This object already listens to GameEngine!");
        }

        listeners.add(listener);
        
    }

    public void removeListener(InterfaceGameEngineListener listener) {
        if (!listeners.contains(listener)) {
            throw new IllegalArgumentException("This object isn't listening to GameEngine, so it does not make sense to remove it from the list of listeners.");
        }

        listeners.remove(listener);
    }

    public void fireCurrentPlayerChanged() {
        listeners.stream().forEach(InterfaceGameEngineListener::currentPlayerChanged);
    }

    public void firePlayerWon(String winnerName) {
        listeners.stream().forEach(listener -> listener.playerWon(winnerName));
    }

    public void fireRobotRolledDice() {
        listeners.stream().forEach(InterfaceGameEngineListener::robotRolledDice);
    }

    public void firePlayerMadeMove() {
        listeners.stream().forEach(InterfaceGameEngineListener::playerMadeMove);
    }

    public void fireUpdateImageOfDice(int latestDice) {
        listeners.stream().forEach(s -> s.updateImageOfDice(latestDice));
    }

    public void fireNoValidMoveText() {
        listeners.stream().forEach(s -> s.updatePlayerText(" can't move"));
    }

    public void fireDiceClickable(boolean arg) {
        listeners.stream().forEach(s -> s.diceClickable(arg));
    }

    public void fireThreeSixInRowText() {
        listeners.stream().forEach(s -> s.updatePlayerText(" got three 6's!"));
    }

    /**
     * Checks if the next player is a robot.
     * If the next player is a robot, then the ability to click the dice is disabled for all human players. 
     */
    public void fireRobotCheck() {
        if (currentPlayer instanceof RobotPlayer) {
            robotProcedure();
        }
        else {
            fireDiceClickable(true);
        }
    }




    /**
     * Denne metoden gjør slik at en bot kaster terningen, og gjør et trekk dersom han har muligheten til det. 
     */
    public void robotProcedure() {
        
        fireDiceClickable(false);

        Timer timer = new Timer();

        // Task 2, flytter en brikke
        class TimerTask2 extends TimerTask {
            @Override
            public void run() {
                firePlayerMadeMove();
                if (currentPlayer instanceof RobotPlayer)
                    if (canMakeMove) {
                        ((RobotPlayer) currentPlayer).makeRobotMove(); 
                    }
            }
        }

        // Task1, kaster terningen
        class TimerTask1 extends TimerTask {

            @Override
            public void run() {
                fireRobotRolledDice();
                TimerTask task2 = new TimerTask2();
                timer.schedule(task2, 2000);
            }
        
        }

        TimerTask task1 = new TimerTask1();
        timer.schedule(task1, 2000);


    }
}




