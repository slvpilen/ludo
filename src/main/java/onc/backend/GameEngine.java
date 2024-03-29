package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.util.Pair;
import java.util.concurrent.CountDownLatch;


public class GameEngine implements InterfacePopupListener {

    private Player currentPlayer;
    private Settings settings;
    private ArrayList<Player> players;
    private int latestDice;
    private int turnRollCount; 
    private boolean canMakeMove;
    private boolean popupDisplayed;
    private CountDownLatch popupLatch;
    private GameNameInfo gameNameInfo;
    private List<InterfaceGameEngineListener> listeners = new ArrayList<>();


    /**
     * This method is used in the SaveAndReadToFile-class when a saved game is loaded.
     * The SaveAndReadToFile-class will return a gameEngine using this constructor.
     * By using this constructor, all the relevant information about the game will be stored in the gameEngine.
     * @param settings The settings which the ludoGame is running on.
     * @param players The players who are playing.
     * @param currentPlayer The player which is currently at turn.
     * @param latestDice The value of the latest diceroll.
     * @param turnRollCount The number of dice-rolls the current player has performed during his turn.
     * @param canMakeMove True if the current player can make a move, otherwise false.
     * @param gameNameInfo An object of the gameNameInfo-class, containing information about the names of the players.
     */
    public GameEngine(Settings settings, ArrayList<Player> players, Player currentPlayer, int latestDice, int turnRollCount, boolean canMakeMove, GameNameInfo gameNameInfo) {
        this.settings = settings;
        this.players = players;
        this.players.sort((p1, p2) -> Integer.compare(p1.getHouseNumber(), p2.getHouseNumber()));
        

        this.currentPlayer = currentPlayer;
        this.latestDice = latestDice;
        this.turnRollCount = turnRollCount;
        this.canMakeMove = canMakeMove;
        this.gameNameInfo = gameNameInfo;
        players.forEach(player -> player.setGameEngine(this));

    }

    /**
     * This method is used to get a collection of all the different pieces which are present on the ludoBoard.
     * @return A collection of all the pieces on the ludoBoard.
     */
    public Collection<Piece> getPieces(){

        return players.stream().map(Player::getPieces).flatMap(Collection::stream).collect(Collectors.toList());
    }
    
    /**
     * This method only does something if the selected piece has a valid move.
     * If you run this method on a piece which does not have a valid move, nothing will happen.
     * Assuming that the selected piece has a legal move, the method makes the piece move in the ludoBoard, and sets the canMakeMove-variable to false.
     * The method then runs the updateCurrentPlayer-method, which finds out which player is the next in line.
     * @param piece The piece which you want to move
     */
    public void movePiece(Piece piece){
        
        if (!canMakeMove)
            return;

        if (!piece.hasLegalMove())
            return;

        if (piece.getHouseNumber() != currentPlayer.getHouseNumber())
            return;
        
        piece.movePlaces();

        if (piece.getOwner().isFinished()) {
            firePlayerWon(currentPlayer.getUsername());
        }

        firePlayerMadeMove();

        this.canMakeMove = false;
        updateCurrentPlayer(piece);
    }

    /**
     * This utility-method determines who the next player which must make a move is.
     * The method is only run after a player has moved a piece.
     * @param piece The piece which just moved.
     */
    private void updateCurrentPlayer(Piece piece){        
        
        if (latestDice == 6 && turnRollCount < 3) {
            this.currentPlayer = piece.getOwner();
            fireCurrentPlayerChanged();
            fireRobotCheck();
        }
        
        else{
            this.currentPlayer = getNextPlayer();
            fireCurrentPlayerChanged();
            turnRollCount = 0;  
            fireRobotCheck(); 
        }    
    }

    /**
     * This utility-method is used inside of the updateCurrentPlayer-method.
     * If the player who just made a move is not the player who is next in line,
     * then getNextPlayer is used to find out who the next player is.
     * @return The next player in correct order.
     */
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

    /**
     * This method runs when the dice is rolled.
     * If the game is in a state where the player is not allowed to roll the dice (for example if a player already has rolled the die that turn),
     * then this method does nothing.
     * If the game is in a state where it is legal to roll the dice, then it creates a random number between 1 and 6, adds 1 to turnRollCount, and checks if the current player has a legal move.
     * If the player does not have a legal move, then some text is displayed, and the game switches to the next player.
     * If the player has a legal move, then the game will not proceed until that player has clicked on a piece which can make a move.
     */
    public void rollDice() {
        
        Random rng = new Random();
        int diceRoll = rng.nextInt(6) + 1;
        rollDice(diceRoll);
    }

    /**
     * This method is only used in the PieceTest-class, and it is used to roll the dice without any randomness.
     * It allows you to roll the dice, with the argument diceRoll as the roll.
     * @param diceRoll The diceroll you want.
     */
    public void rollDice(int diceRoll) {

        if (canMakeMove) 
            return;
        
        turnRollCount++;
        latestDice = diceRoll;
        fireUpdateImageOfDice();
    
        if(currentPlayer.hasAnyValidMoves() && !(turnRollCount == 3 && latestDice == 6)) {
            firePlayerMustMoveText();
            this.canMakeMove = true;
            return;
        } 
        
        int oldTurnRollCount = turnRollCount;
        fireDiceClickable(false);
        this.canMakeMove = false;
        this.turnRollCount = 0;

        if (oldTurnRollCount == 3 && latestDice == 6) {
            fireThreeSixInRowText();
        }
        
        else {
            fireNoValidMoveText();
        }
        
        Timer timer = new Timer();
        timer.schedule(new TimerNoValidMove(), 1000);

    }

    /**
     * @return The latest dice roll.
     */
    public int getDice(){
        return latestDice;
    }

    /**
     * @return The player who's turn it is.
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * @return True if the current player can make a move, otherwise false.
     */
    public boolean getCanMakeMove() {
        return canMakeMove;
    }

    /**
     * @return A list of size four, containing all the Player-objects.
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * @return The gameNameInfo-class connected to the gameEngine.
     */
    public GameNameInfo getGameNameInfo() {
        return gameNameInfo;
    }

    /**
     * @return The total number of die-rolls the current player has performed during his turn.
     */
    public int getTurnRollCount() {
        return turnRollCount;
    }
    
    /**
     * This method counts the number of pieces on a specific square in the ludo board.
     * @param location The location where you want to check the number of pieces. 
     */
    public int getNumberOfPiecesOnLocation(Pair<Integer, Integer> location){
        
        return (int) players.stream().map(Player::getPieces).flatMap(Collection::stream).filter(piece -> piece.getPosition().equals(location) && piece.getGameGrid() != null).count();
    }

    /**
     * This method is used in the movePlaces-method in the Piece class.
     * If the piece which moves lands on a endLocation where there is one enemy piece,
     * then this method is performed.
     * This method looks through all of the pieces in the gameEngine, and sets the enemy piece on the
     * endlocation to one of the houseSquares.
     * @param endLocation The location which the moving piece is moving to. 
     */
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
     * This method makes the image of the die go to correct color. 
     * This indicates that the player who's turn it is, must roll the die to continue.
     */
    public void firePlayerMadeMove() {
        listeners.stream().forEach(InterfaceGameEngineListener::playerMadeMove);
    }

    /**
     * Updates the image of the die in the gameFaceScene, such that the latest diceRoll is displayed.
     */
    public void fireUpdateImageOfDice() {
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
     * If the next player is a robot, then the ability to click the die is disabled for all human players. 
     * Additionally, the robotProcedure is run. The robotProcedure makes the robot roll the dice and
     * make random legal moves, until it is the next player's turn.
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
     * This method sets the popupDisplayed variable to be true.
     * The method is run when a popup is displayed in the gameFaceScene.
     */
    @Override
    public void popupDisplayed() {
        popupDisplayed = true;
    }

    /**
     * This method sets the popupDisplayed variable to be false, and it counts down
     * the popupLatch, such that the robotPlayers may continue their operations.
     */
    @Override
    public void popupClosed() {
        popupLatch.countDown();
        popupDisplayed = false;
    }


    /**
     * This method makes the bot roll the die, and make a move if it has any legal moves.
     * There are timers in this method, such that robot doesn't make moves instantly.
     * Currently, the robot uses 1 second to think before throwing the dice, and 
     * also uses 1 second to think about which piece to move.
     * If a popup is displayed in the gameEngine while robotProcedure is running, the robotProcedure will pause until the popup is closed.
     */
    public void robotProcedure() {

        fireDiceClickable(false);
        popupLatch = new CountDownLatch(1);
        Timer timer = new Timer();

        // Task 2, flytter en brikke
        class TimerTask2 extends TimerTask {
            @Override
            public void run() {
                
                if (popupDisplayed) {
                    try {
                        popupLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

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
                
                if (popupDisplayed) {
                    try {
                        popupLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                fireRobotRolledDice();
                TimerTask task2 = new TimerTask2();
                timer.schedule(task2, 1000);
            }
        
        }

        TimerTask task1 = new TimerTask1();
        timer.schedule(task1, 1000);
    }
        
}




