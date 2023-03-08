package onc.backend;

public class Settings {
    // settings is default
    int numberPlayers = 4;
    boolean boardContainsFreespaces = false;
    boolean towersAllowed = true; 
    boolean firstWinnerEndGame = true;

    public Settings(){
        this.numberPlayers = 4;
        this.boardContainsFreespaces = false;
        this.towersAllowed = true; 
        this.firstWinnerEndGame = true;

    }
    
}
