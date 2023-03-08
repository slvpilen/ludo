package onc.backend;

import java.util.ArrayList;
import java.util.Collection;

import javafx.util.Pair;


// Denne klassen virker å være ferdig. 



public class Player {

    private String username;
    private Collection<Pair<Integer, Integer>> piecesLocation; // = new ArrayList<Pair<Integer, Integer>>();
    private int numberOfRollsThisTurn;
    private int houseNumber;
    private Board board;
    
    // Startplass avhenger av farge

    // husrekkefølge:
    //  2   3   
    //  1   4
    public Player(String username, int houseNumber){
        
        if (houseNumber > 4  || houseNumber < 1) {
            throw new IllegalArgumentException("Housenumber must be an integer between 1 and 4.");
        }
        
        this.username = username;
        this.houseNumber = houseNumber;
        piecesLocation = homeSquares(houseNumber);
    }
    // lage egen kosntruktør for å laste inn eksisterende spill (ta inn picesLocation etc)

    public void setPieceToStart(Pair<Integer, Integer> removePiece) {
        
        ArrayList<Pair<Integer, Integer>> homeSquares = homeSquares(this.houseNumber);

        // Exception dersom du prøver å fjerne en brikke du ikke har.
        if (!piecesLocation.contains(removePiece)) {
            throw new IllegalArgumentException("You tried to remove a piece which the player did not possess.");
            }
        
        // Putter brikken på første ledige plass i spawn-area
        piecesLocation.remove(removePiece);
        for (Pair<Integer, Integer> homeSquare : homeSquares) {
            if (this.hasNoOwnPieceOnSquare(homeSquare)) {
                piecesLocation.add(homeSquare);
                return;
            }
        }  
    }

    public ArrayList<Pair<Integer, Integer>> homeSquares() {
        
        ArrayList<Pair<Integer, Integer>> pieces = new ArrayList<Pair<Integer, Integer>>();

        if (houseNumber == 1) {
            pieces.add(new Pair<Integer, Integer>(1, 9));
            pieces.add(new Pair<Integer, Integer>(1, 11));
            pieces.add(new Pair<Integer, Integer>(3, 9));
            pieces.add(new Pair<Integer, Integer>(3, 11));
        }

        if (houseNumber == 2) {
            pieces.add(new Pair<Integer, Integer>(1, 1));
            pieces.add(new Pair<Integer, Integer>(1, 3));
            pieces.add(new Pair<Integer, Integer>(3, 1));
            pieces.add(new Pair<Integer, Integer>(3, 3));
        }

        if (houseNumber == 3) {
            pieces.add(new Pair<Integer, Integer>(9, 1));
            pieces.add(new Pair<Integer, Integer>(9, 3));
            pieces.add(new Pair<Integer, Integer>(11, 1));
            pieces.add(new Pair<Integer, Integer>(11, 3));
        }

        if (houseNumber == 4) {
            pieces.add(new Pair<Integer, Integer>(9, 9));
            pieces.add(new Pair<Integer, Integer>(9, 11));
            pieces.add(new Pair<Integer, Integer>(11, 9));
            pieces.add(new Pair<Integer, Integer>(11, 11));
        }

        return pieces;
        
    } 

    public boolean hasNoOwnPieceOnSquare(Pair<Integer, Integer> square) {
        if (piecesLocation.contains(square)) {
            return false;
        }
        return true;
    } 

    // make this methode!!
    public int getAmountOfPiecesOnSquare(Pair<Integer, Integer> square){
        return 0;
    }
    
    public int getHouseNumber() {
        return houseNumber;
    }

    public void addRollThisTurn() {
        numberOfRollsThisTurn++;
    }

    public int getNumberOfRollsThisTurn() {
        return numberOfRollsThisTurn;
    }

    public String getUsername() {
        return username;
    }
    
    public Collection<Pair<Integer, Integer>> getPiecesLocation() {
        return new ArrayList<Pair<Integer, Integer>>(piecesLocation);
    }
    
}




























