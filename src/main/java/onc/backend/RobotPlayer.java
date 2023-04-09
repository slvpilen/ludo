package onc.backend;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
import javafx.scene.layout.GridPane;

public class RobotPlayer extends Player {

    /**
     * This constructor creates a RobotPlayer with the exact same cosntructor as the constructor for the Player-class.
     * @param username The name of the bot.
     * @param houseNumber The houseNumber connected to the bot. An integer between 1 and 4 inclusive.
     * @param gameGrid The gameGrid
     */
    public RobotPlayer(String username, int houseNumber, GridPane gameGrid) {
        super(username, houseNumber, gameGrid);
    }

    /**
     *This method checks if the robot has any valid moves.  
     *If the bot has one or more valid moves, it takes a look at all of the pieces which have
     *a legal move, and picks one of them at random.
     *The selected piece is then moved in the gameGrid.
    */ 
    public void makeRobotMove() {

        if (!hasAnyValidMoves()) {return;}

        List<Piece> availablePieces = pieces.stream().filter(piece -> piece.hasLegalMove()).collect(Collectors.toList());
        
        Random rng = new Random();
        int randomIndex = rng.nextInt(availablePieces.size());
        Piece selectedPiece = availablePieces.get(randomIndex); 

        gameEngine.movePiece(selectedPiece);
    }

    /**
     * This method ensures that a human player cannot roll the dice, when it is the robots turn. 
     */
    @Override
    protected void addMouseFunctionToPieces() {
        pieces.forEach(piece -> piece.getCircle().setOnMouseClicked(null));
    }

}
