package onc.backend;

/**
 * The class "Settings" is presently not in use
 */
public class Settings {
    
    int numberPlayers = 4;
    boolean boardContainsFreespaces = false;
    boolean towersAllowed = false; 
    boolean firstWinnerEndGame = true;

    public Settings(){
        this.numberPlayers = 4;
        this.boardContainsFreespaces = false;
        this.towersAllowed = false; 
        this.firstWinnerEndGame = true;

    }

    public Settings(int numPlayers) {
        this.numberPlayers = numPlayers;
        this.boardContainsFreespaces = false;
        this.towersAllowed = false; 
        this.firstWinnerEndGame = true;
    }
    
}
