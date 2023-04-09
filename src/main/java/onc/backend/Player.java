package onc.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Player {

    // husrekkefølge:
    // 2 3
    // 1 4

    protected String username;
    protected Collection<Piece> pieces;
    protected int numberOfRollsThisTurn;
    protected int houseNumber;
    protected GameEngine gameEngine;

    public Player(String username, int houseNumber, GridPane gameGrid) {

        if (houseNumber > 4 || houseNumber < 1) {
            throw new IllegalArgumentException("Housenumber must be an integer between 1 and 4.");
        }

        this.username = username;
        this.houseNumber = houseNumber;
        this.pieces = createPieces(getHomeSquares(), gameGrid);
        addMouseFunctionToPieces();
    }
    
    // lage egen kosntruktør for å laste inn eksisterende spill (ta inn
    // picesLocation etc)

    /**
     * Sets the gameEngine which should be connected to the player.
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * This method adds a click-function to all of the pieces of a human player.
     * If a human player clicks on one of his pieces, then that piece will move,
     * provided that it is the player's turn and that the piece has a legal move.
     */
    protected void addMouseFunctionToPieces() {
        pieces.forEach(piece -> piece.getCircle().setOnMouseClicked(event -> {
            gameEngine.movePiece(piece);
        }));
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

    protected Collection<Piece> createPieces(ArrayList<Pair<Integer, Integer>> piecesLocation, GridPane gameGrid) {
        ArrayList<Piece> newPieces = new ArrayList<>();

        for (Pair<Integer, Integer> location : piecesLocation)
            newPieces.add(new Piece(this, location, gameGrid));

        if (newPieces.size() != 4)
            throw new IllegalStateException("The player must have 4 pieces!");

        return newPieces;
    }

    
    
    /**
     * This method gives you a list of the homeSquares which the player has.
     * The homeSquares are calculated based on the houseNumber of the player.
     * @return A list of Pair(Integer, Integer), which contains the homesquare-positions for the player in the gameGrid.
     */
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

    /**
     * This methods returns a list with all of the empty homeSquares which the player has.
     * @return A list of Pair(Integer, Integer), which tell you the HomeSquare-positions which are unoccupied.
     */
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


    public boolean hasAnyValidMoves() {
        
        if (!gameEngine.getCurrentPlayer().equals(this)) {
            System.out.println("Not this player's turn");
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

    protected boolean hasPieceOnHomeSquare() {
        Collection<Pair<Integer, Integer>> piecesLocations = getPiecesPositions();
        ArrayList<Pair<Integer, Integer>> homeSquares = getHomeSquares();
        for (Pair<Integer, Integer> location : piecesLocations) {
            if (homeSquares.contains(location))
                return true;
        }
        return false;
    }

    public boolean isFinished() {
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
