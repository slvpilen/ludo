package onc.backend;

/**
 * This interface is used to make it possible for the gameEngine to send information to the gameFaceController, 
 * such that changes in the gameEngine converts to graphical changes in the gameFaceScene.
 */
public interface InterfaceGameEngineListener {
    public void currentPlayerChanged();
    public void playerWon(String winnerName);
    public void robotRolledDice();
    public void updateImageOfDice(int latestDice);
    public void updatePlayerText(String text);
    public void playerMadeMove();
    public void diceClickable(boolean arg);
}
