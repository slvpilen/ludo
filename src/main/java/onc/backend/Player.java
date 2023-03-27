package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;

// Denne klassen virker å være ferdig. 

public class Player {

    private String username;
    private Collection<Piece> pieces;
    private int numberOfRollsThisTurn;
    private int houseNumber;
    private GameEngine gameEngine;
    // private GridPane gameGrid;

    // Startplass avhenger av farge

    // husrekkefølge:
    // 2 3
    // 1 4
    public Player(String username, int houseNumber, GridPane gameGrid) {

        if (houseNumber > 4 || houseNumber < 1) {
            throw new IllegalArgumentException("Housenumber must be an integer between 1 and 4.");
        }

        this.username = username;
        this.houseNumber = houseNumber;
        // this.gameEngine = gameEngine;
        // this.gameGrid = gameGrid;
        this.pieces = createPieces(getHomeSquares(), gameGrid);
        addMouseFunctionToPieces();
    }
    // lage egen kosntruktør for å laste inn eksisterende spill (ta inn
    // picesLocation etc)


    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    private void addMouseFunctionToPieces() {
        pieces.forEach(piece -> piece.getCircle().setOnMouseClicked(event -> {
            gameEngine.movePiece(piece);
        }));
    }

    public void setOwnPieceToStart(Pair<Integer, Integer> pieceToRemove) {
        Collection<Pair<Integer, Integer>> piecesLocation = getPiecesPositions();
        if (!piecesLocation.contains(pieceToRemove)) {
            throw new IllegalArgumentException("You tried to remove a piece which the player did not possess.");
        }

        ArrayList<Pair<Integer, Integer>> homeSquares = getHomeSquares();
        // Putter brikken på første ledige plass i spawn-area
        piecesLocation.remove(pieceToRemove);
        for (Pair<Integer, Integer> homeSquare : homeSquares) {
            if (!this.hasOwnPieceOnSquare(homeSquare))
                piecesLocation.add(homeSquare);
        }
    }

    public GameEngine getGameEngine() {
        return gameEngine;
    }

    public Collection<Pair<Integer, Integer>> getPiecesPositions() {
        Collection<Pair<Integer, Integer>> positions = new ArrayList<>();
        for (Piece piece : pieces)
            positions.add(piece.getPosition());
        return positions;
    }

    public Collection<Piece> getPieces() {
        return pieces;
    }

    private Collection<Piece> createPieces(ArrayList<Pair<Integer, Integer>> piecesLocation, GridPane gameGrid) {
        ArrayList<Piece> newPieces = new ArrayList<>();

        for (Pair<Integer, Integer> location : piecesLocation)
            newPieces.add(new Piece(this, location, gameGrid));

        if (newPieces.size() != 4)
            throw new IllegalStateException("Need to be 4 startpieces");

        return newPieces;
    }

    // husrekkefølge:
    // 2 3
    // 1 4
    public ArrayList<Pair<Integer, Integer>> getHomeSquares() {
        ArrayList<Pair<Integer, Integer>> squares = new ArrayList<Pair<Integer, Integer>>();

        if (houseNumber == 1) {
            squares.add(new Pair<Integer, Integer>(2, 18));
            squares.add(new Pair<Integer, Integer>(2, 16));
            squares.add(new Pair<Integer, Integer>(4, 16));
            squares.add(new Pair<Integer, Integer>(4, 18));
        }

        if (houseNumber == 2) {
            squares.add(new Pair<Integer, Integer>(2, 8));
            squares.add(new Pair<Integer, Integer>(2, 6));
            squares.add(new Pair<Integer, Integer>(4, 6));
            squares.add(new Pair<Integer, Integer>(4, 8));
        }

        if (houseNumber == 3) {
            squares.add(new Pair<Integer, Integer>(12, 8));
            squares.add(new Pair<Integer, Integer>(12, 6));
            squares.add(new Pair<Integer, Integer>(14, 6));
            squares.add(new Pair<Integer, Integer>(14, 8));
        }

        if (houseNumber == 4) {
            squares.add(new Pair<Integer, Integer>(12, 18));
            squares.add(new Pair<Integer, Integer>(12, 16));
            squares.add(new Pair<Integer, Integer>(14, 16));
            squares.add(new Pair<Integer, Integer>(14, 18));
        }

        return squares;

    }

    public List<Pair<Integer, Integer>> getEmptyHomeSquares() {
        List<Pair<Integer, Integer>> homeSquares = getHomeSquares();
        List<Pair<Integer, Integer>> filledHomeSquares = pieces.stream()
            .map(Piece::getPosition)
            .filter(homeSquares::contains)
            .collect(Collectors.toList());
        homeSquares.removeAll(filledHomeSquares);
        return homeSquares;
    }
    

    public boolean hasOwnPieceOnSquare(Pair<Integer, Integer> square) {
        if (getPiecesPositions().contains(square)) {
            return true;
        }
        return false;
    }

    // make this methode!!
    public int getAmountOfPiecesOnSquare(Pair<Integer, Integer> square) {
        throw new IllegalArgumentException("This method needs to be made!!");
        // return 0;
    }

    public boolean hasAnyValidMoves() {
        
        if (!gameEngine.getCurrentPlayer().equals(this)) {
            System.out.println("Not this players turn");
            return false;
        }
        
        if (isFinished()) {
            System.out.println("This player has finished the game");
            return false;
        }
        
        int latestDice = gameEngine.getDice();

        if (latestDice == 6 && hasPieceOnHomeSquare()) // Unødvendig, sjekken gjøres allerede i hasLegalMove-metoden!
            return true;

        for (Piece piece : pieces) {
            if (piece.hasLegalMove())
                return true;
        }

        return false;
    }

    private boolean hasPieceOnHomeSquare() {
        Collection<Pair<Integer, Integer>> piecesLocations = getPiecesPositions();
        ArrayList<Pair<Integer, Integer>> homeSquares = getHomeSquares();
        for (Pair<Integer, Integer> location : piecesLocations) {
            if (homeSquares.contains(location))
                return true;
        }
        return false;
    }

    private boolean isFinished() {
        return pieces.stream().allMatch(Piece::isInFinishPaddock);
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

    public boolean hasPieceOnLocation(Pair<Integer, Integer> location) {
        for (Piece piece : pieces) {
            if (piece.getPosition().equals(location))
                return true;
        }
        return false;
    }
}
