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

    /**
     * This class sets up a timed task.
     * The task is to do all the stuff that needs to be done after a player
     * has thrown the die, and has no valid moves.
     * This class is used in the method rollDice to ensure that the transition from throwing dice 
     * to going to the next player is not instant.
     */
    class TimerNoValidMove extends TimerTask {

        @Override
        public void run() {
            
            if (!(currentPlayer instanceof RobotPlayer)) {
                firePlayerMadeMove();
                currentPlayer = getNextPlayer();
                fireCurrentPlayerChanged();
                fireDiceClickable(true);
                fireRobotCheck();
            } else {
                currentPlayer = getNextPlayer();
                fireCurrentPlayerChanged();
                fireDiceClickable(true);
                fireRobotCheck();
            }            
        }
    }


    public void rollDice() {
        
        if (canMakeMove)  // not allowed to roll before moved
            return;
        
        Random terning = new Random();
        latestDice = terning.nextInt(6) + 1;
        fireUpdateImageOfDice(latestDice);
    
        if(currentPlayer.hasAnyValidMoves() && !(turnRollCount == 2 && latestDice == 6)) {
            firePlayerMustMoveText();
            this.canMakeMove = true;
            return;
        } 
        
        int oldTurnRollCount = turnRollCount;
        fireDiceClickable(false);
        this.canMakeMove = false;
        this.turnRollCount = 0;

        if (oldTurnRollCount == 2 && latestDice == 6) {
            fireThreeSixInRowText();
        }
        
        else {
            fireNoValidMoveText();
        }
        
        Timer timer = new Timer();
        timer.schedule(new TimerNoValidMove(), 1000);
    
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

    /**
     * This method is used once, to make GameFaceController listen to the changes which happen
     * in the gameEngine-class.
     * Because gameFaceController listens to gameEngine, we can inform the controller 
     * about updates in the game to the controller, and make it react appropriately.
     * 
     * @param listener The thing that should listen to GameEngine (usually gameFaceController)
     */
    public void addListener(InterfaceGameEngineListener listener) {
        if (listeners.contains(listener)) {
            throw new IllegalArgumentException("This object already listens to GameEngine!");
        }

        listeners.add(listener);
        
    }


    /**
     * This method tells the gameFaceController that there has been a change in which player's turn it is.
     */
    public void fireCurrentPlayerChanged() {
        listeners.stream().forEach(InterfaceGameEngineListener::currentPlayerChanged);
    }

    /**
     * Sends a signal to the gameFaceController that a player has won the game.
     * The playerWon-method is called in the controller-class, and a pop-up will appear on the gameScreen.
     */
    public void firePlayerWon(String winnerName) {
        listeners.stream().forEach(listener -> listener.playerWon(winnerName));
    }

    /**
     * This method is used by the robot to roll the die without actually clicking it.
     * A signal is sent to gameFaceController, and the die gets rolled.
     */
    public void fireRobotRolledDice() {
        listeners.stream().forEach(InterfaceGameEngineListener::robotRolledDice);
    }

    /**
     * This method makes the image of the die go black. 
     * This indicates that the player who's turn it is, must roll the die to continue.
     */
    public void firePlayerMadeMove() {
        listeners.stream().forEach(InterfaceGameEngineListener::playerMadeMove);
    }

    /**
     * Updates the image of the die in the gameFaceScene, such that the latest diceRoll is displayed.
     */
    public void fireUpdateImageOfDice(int latestDice) {
        listeners.stream().forEach(s -> s.updateImageOfDice(latestDice));
    }

    /**
     * Text is shown in the gameFaceScene, which tells the player that he must make a move.
     */
    public void firePlayerMustMoveText() {
        listeners.stream().forEach(s -> s.updatePlayerText(" must move"));
    }

    /**
     * Text is shown in the gameFaceScene, which tells the player that he can't make a move.
     */
    public void fireNoValidMoveText() {
        listeners.stream().forEach(s -> s.updatePlayerText(" can't move"));
    }
    
    /**
     * Text is shown in the gameFaceScene, which tells the player that he got three 6's in a row.
     */
    public void fireThreeSixInRowText() {
        listeners.stream().forEach(s -> s.updatePlayerText(" got three 6's!"));
    }

    /**
     * Sends a signal to the gameFaceController, and tells that a human player should be able to click the dice or not, depending on the argument.
     * @param arg If true, a player should be able to click the dice. If false, then a player will not be able to click the dice.
     */
    public void fireDiceClickable(boolean arg) {
        listeners.stream().forEach(s -> s.diceClickable(arg));
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
     * This method makes the bot throw the die, and make a move if it has any legal moves.
     * There are timers in this method, such that robot doesn't make moves instantly.
     * Currently, the robot uses 1 second to think before throwing the dice, and 
     * also uses 1 second to think about which piece to move. 
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
                timer.schedule(task2, 1000);
            }
        
        }

        TimerTask task1 = new TimerTask1();
        timer.schedule(task1, 1000);


    }
}




