package onc.backend;

import java.util.ArrayList;
import java.util.Collection;

import javafx.util.Pair;


// Denne klassen virker å være ferdig. 



public class Player {

    private String username;
    private Collection<Piece> pieces; 
    private int numberOfRollsThisTurn;
    private int houseNumber;
    private Board board;
    private int turnRollCount; // counting how many dice roll on a roll
    
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
    }
    // lage egen kosntruktør for å laste inn eksisterende spill (ta inn picesLocation etc)


    public void setBoard(Board board){
        this.board = board;
        this.pieces = createPieces(board.getHomeSquares(this));
    }

    public void setOwnPieceToStart(Pair<Integer, Integer> pieceToRemove) {
        Collection<Pair<Integer, Integer>> piecesLocation = getPiecesPositions();        
        if (!piecesLocation.contains(pieceToRemove)) {
            throw new IllegalArgumentException("You tried to remove a piece which the player did not possess.");
            }
        
        ArrayList<Pair<Integer, Integer>> homeSquares = board.getHomeSquares(this);
        // Putter brikken på første ledige plass i spawn-area
        piecesLocation.remove(pieceToRemove);
        for (Pair<Integer, Integer> homeSquare : homeSquares) {
            if (!this.hasOwnPieceOnSquare(homeSquare)) 
                piecesLocation.add(homeSquare);
        }  
    }

    public Collection<Pair<Integer, Integer>> getPiecesPositions(){
        Collection<Pair<Integer, Integer>> positions = new ArrayList<>();
        for (Piece piece : pieces)
            positions.add(piece.getPosition());
        return positions;
    }

    public Collection<Piece> getPieces(){
        return pieces;
    }

    private Collection<Piece> createPieces(ArrayList<Pair<Integer, Integer>> piecesLocation){
        ArrayList<Piece> newPieces = new ArrayList<>();
        for (Pair<Integer, Integer> location : piecesLocation)
            newPieces.add(new Piece(this, location));
        
        if (newPieces.size() != 4)
            throw new IllegalStateException("Need to be 4 startpieces");
        return newPieces;
    }



    public boolean hasOwnPieceOnSquare(Pair<Integer, Integer> square) {
        if (getPiecesPositions().contains(square)) {
            return true;
        }
        return false;
    } 

    // make this methode!!
    public int getAmountOfPiecesOnSquare(Pair<Integer, Integer> square){
        throw new IllegalArgumentException("This methode needs to be made!!");
        //return 0;
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
    
}




























