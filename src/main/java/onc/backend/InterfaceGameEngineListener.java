package onc.backend;

public interface InterfaceGameEngineListener {
    
    public void currentPlayerChanged();
    public void playerWon(String winnerName);
    public void robotRolledDice();
    public void updateImageOfDice(int latestDice);
    public void playerMadeMove();
    public void noValidMoveText();
    public void diceClickable(boolean arg);
}
