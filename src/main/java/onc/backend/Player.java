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
    protected Collection<Piece> pieces = new ArrayList<>();
    protected int houseNumber;
    protected GameEngine gameEngine;


    /**
     * The constructor for the Player class.
     * It sets up the username and houseNumber of the player.
     * It is also responsible for adding all pieces to the player,
     * and displaying the pieces in the game scene.
     * 
     * @param username The name of the player.
     * @param houseNumber Determines which corner of the board the player will reside.
     * @param gameGrid The gridPane which the pieces should be spawned in.
     * 
     * @throws IllegalArgumentException If the housenumber is not an integer between 1 and 4 inclusive.
     * 
     */
    public Player(String username, int houseNumber, GridPane gameGrid) {
        if (houseNumber > 4 || houseNumber < 1) {throw new IllegalArgumentException("Housenumber must be an integer between 1 and 4.");}
        if (username.length() == 0) {throw new IllegalArgumentException("username can't be empty");}

        this.username = username;
        this.houseNumber = houseNumber;
        this.pieces = createPieces(getHomeSquares(), gameGrid);
        addMouseFunctionToPieces();
    }
    /**
     * This constructor is used when loading a saved file.
     * Players are created without Pieces
     */
    public Player(String username, int houseNumber) {
        if (houseNumber > 4 || houseNumber < 1) {throw new IllegalArgumentException("Housenumber must be an integer between 1 and 4.");}
        if (username.length() == 0) {throw new IllegalArgumentException("username can't be empty");}

        this.username = username;
        this.houseNumber = houseNumber;
    }
    
    // lage egen kosntruktør for å laste inn eksisterende spill (ta inn
    // picesLocation etc)

    /**
     * This method is used when loading a saved game, and it adds the piece argument to the list of pieces of the player.
     * When a new piece is created (when a game is being loaded), the pieces are one after the other
     * added to the list of pieces connected to the owner of the piece.
     * @param piece The piece which the player should 
     */
    public void addPieceToPlayer(Piece piece) {
        pieces.add(piece);
        addMouseFunctionToPiece(piece);
    }

    /**
     * Sets the gameEngine which should be connected to the player.
     */
    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    /**
     * @return The gameEngine which is connected to the player.
     */
    public GameEngine getGameEngine() {
        return gameEngine;
    }

    /**
     * @return The houseNumber connected to the player.
     */
    public int getHouseNumber() {
        return houseNumber;
    }

    /**
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return A collection of the pieces-objects which the player owns.
     */
    public Collection<Piece> getPieces() {
        return pieces;
    }

    /**
     * This method is used when loading a game.
     * It takes a piece is argument, and makes the piece clickable, 
     * such that a human player can move the piece, if it has a legal move.
     */
    protected void addMouseFunctionToPiece(Piece piece) {
        piece.getCircle().setOnMouseClicked(event -> gameEngine.movePiece(piece));
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

    

    /**
     * @return A collection of all the Coordinates where the Player has a piece. 
     * The collection will always have size 4, as a player has 4 pieces.
     */
    public Collection<Pair<Integer, Integer>> getPiecesPositions() {
        Collection<Pair<Integer, Integer>> positions = new ArrayList<>();
        pieces.stream().forEach(p -> positions.add(p.getPosition()));
        return positions;
    }


    /**
     * This method is used to create pieces for a player. 
     * Only used in the constructor of the Player-class.
     * It creates pieces for the player, one piece for each of the locations in the piecesLocation-list.
     * 
     * When this method is used in the constructor, a list of all the homeSquares of the player is used.
     * With this setup, a new Piece is created in each of the player's homesquares.
     * 
     * @param piecesLocation piecesLocation An ArrayList of Pair objects representing the x and y 
     * coordinates of each location where a new Piece should be created.
     * @param gameGrid The GridPane where the new Pieces should be placed.
     * @return A Collection of newly created Pieces.
     */
    protected Collection<Piece> createPieces(ArrayList<Pair<Integer, Integer>> piecesLocation, GridPane gameGrid) {
        
        ArrayList<Piece> newPieces = new ArrayList<>();
        piecesLocation.stream().forEach(location -> newPieces.add(new Piece(this, location, gameGrid)));
        
        if (newPieces.size() != 4) {throw new IllegalStateException("The player must have 4 pieces!");}

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
    

    /**
     * Checks if any of the player's pieces has a valid move.
     * @return True if at least one of the player's pieces has a valid move, otherwise false.
     */
    public boolean hasAnyValidMoves() {
        return pieces.stream().anyMatch(p -> p.hasLegalMove());
    }

    /**
     * Checks if the player has all of his pieces in the goal area.
     * @return True if the player has all of his pieces in the goal area, otherwise false.
     */
    public boolean isFinished() {
        return pieces.stream().allMatch(Piece::isInFinishPaddock);
    }

    

    /**
     * Checks if the player has a piece on the location which is provided.
     * @param location The coordinate in the gameGrid, which you want to know if the player has a piece on.
     * @return True if the player has one or more pieces on the specified location, otherwise false.
     */
    public boolean hasPieceOnLocation(Pair<Integer, Integer> location) {
        return getPiecesPositions().contains(location);
    }
}
