package onc.backend;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;
import javafx.scene.layout.GridPane;

public class RobotPlayer extends Player {

    public RobotPlayer(String username, int houseNumber, GridPane gameGrid) {
        super(username, houseNumber, gameGrid);
    }

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
