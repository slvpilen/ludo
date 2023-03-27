package onc.backend;

public interface InterfaceGameEngineListener {
    
    public void currentPlayerChanged();
    public void playerWon(String winnerName);
    public void robotRolledDice();

}
