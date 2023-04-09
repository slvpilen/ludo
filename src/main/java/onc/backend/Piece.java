package onc.backend;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.*;

public class Piece {
    
    private int xAxis;
    private int yAxis;
    private int pathIndex = -1;
    private ArrayList<Pair<Integer, Integer>> standardPath;
    private Player owner;
    private int houseNumber; // this decide the color of the piece
    private Circle circle;
    private GridPane gameGrid;
    

    
    public Piece(Player owner, Pair<Integer, Integer> position, GridPane gameGrid){
        this.owner = owner;
        this.xAxis = position.getKey();
        this.yAxis = position.getValue();
        this.houseNumber = owner.getHouseNumber();
        this.circle = new Circle(15, getColor());  // could add setting to this
        circle.setStroke(Color.BLACK);
        this.standardPath = getPath();
        this.gameGrid = gameGrid;
        addPieceToGrid(this);
    }

    private void addPieceToGrid(Piece piece){
        GridPane.setColumnIndex(piece.getCircle(), piece.getRow());
        GridPane.setRowIndex(piece.getCircle(), piece.getColumn());
        gameGrid.getChildren().add(piece.getCircle());
    }
    
    /**
     * Moves a piece in the grid.
     * If the piece moves to a place where another piece of the same color is located, then the piece gets an offset, 
     * depending on how many pieces are on that square (ranging from 1 to 3)
     */
    private void movePieceInGrid(){
        
        int numberOfPiecesOnLocation = owner.getGameEngine().getNumberOfPiecesOnLocation(getPosition());
        GridPane.setColumnIndex(getCircle(), getRow());
        GridPane.setRowIndex(getCircle(), getColumn());
        

        // NumberOfPiecesOnLocation includes the piece itself.
        switch (numberOfPiecesOnLocation) {

            case 1:
            getCircle().setTranslateX(0);
            getCircle().setTranslateY(0);

            case 2:
            getCircle().setTranslateX(5);
            getCircle().setTranslateY(0);
            break;

            case 3:
            getCircle().setTranslateX(10);
            getCircle().setTranslateY(0);
            break;

            case 4:
            getCircle().setTranslateX(5);
            getCircle().setTranslateY(-3);
            break;
        }
      
    }
        
    

    public Player getOwner(){
        return owner;
    }

    // this will only be called if there is it hasLegalMove
    public void movePlaces(){ 

        //logic moved down to getLocationAfterPossibleMove(), because it's neccesary to know the possible ending point, before moving
        Pair<Integer,Integer> locationAfterMove = getLocationAfterPossibleMove();        
        
        int numberOfPiecesOnEndLocation = owner.getGameEngine().getNumberOfPiecesOnLocation(locationAfterMove);
        boolean onePieceFromAnotherHouseOnEndLocation = numberOfPiecesOnEndLocation==1 && !owner.hasPieceOnLocation(locationAfterMove);
        
        if (onePieceFromAnotherHouseOnEndLocation)
            owner.getGameEngine().setPieceOnLocationToHouse(locationAfterMove);
        
        xAxis = locationAfterMove.getKey();
        yAxis = locationAfterMove.getValue(); 
        
        movePieceInGrid();
    }

    public int getHouseNumber(){
        return houseNumber;
    }

    /**
     * This method uses the latestDice of the gameEngine to calculate if the given piece has a legal move.
     * @return True if the piece has a legal move.
     */
    public boolean hasLegalMove(){
        
        Collection<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        int latestDice = owner.getGameEngine().getDice();
        boolean isInHomeSquare = homeSquares.contains(getPosition());
        
        if (isInHomeSquare && latestDice!=6)
            return false;

        if (isInFinishPaddock())
            return false;
        
        // This section checks if a piece is blocked by a tower from another house:
        Pair<Integer, Integer> currentLocation = new Pair<>(xAxis, yAxis);
        ArrayList<Pair<Integer, Integer>> currentPath = this.getPath();
        
        // There are som squares near the finish line where an enemy can't have any pieces.
        // Therefore, we only check for towers if the piece is in the standardRoute.

        if (currentPath.indexOf(currentLocation) < 52) {
            
            // The game checks for each of the squares the piece is going to cross, if there are any enemy towers.
            for (int i = 1; i < latestDice + 1; i++) {
                
                // If there is an enemy tower on any of the squares which the piece must cross, the hasLegalMove-method returns false.
                Pair<Integer, Integer> endLocationAfterMove = getLocationAfterPossibleMove(i);
                int numberOfPiecesOnEndLocation = owner.getGameEngine().getNumberOfPiecesOnLocation(endLocationAfterMove);
                if (numberOfPiecesOnEndLocation >= 2 && !owner.hasPieceOnLocation(endLocationAfterMove)) {
                    return false;
                }
            }
        }
        // End of tower section.

        // Checking if the player is going to land on a square with a color different from its own
        Pair<Integer, Integer> endLocationAfterMove = getLocationAfterPossibleMove(latestDice);
        ArrayList<Pair<Integer, Integer>> enemyStartSquares = getEnemyStartSquares(houseNumber);

        if (enemyStartSquares.contains(endLocationAfterMove)) {
            return false;
        }
        // End of square color check. 
        

        return true;
    }

    /**
     * This method gives the endLocation of a piece after a move.
     * The endLocation is calculated by using the latest dice-roll.
     * The method takes into account the complications which might occur
     * at the end, if your dice-roll isn't exactly right to get your piece in goal.
     * This method moves the piece.
     * 
     * @return the end location of the piece after the move
     */
    private Pair<Integer, Integer> getLocationAfterPossibleMove(){
        
        ArrayList<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        int latestDice= owner.getGameEngine().getDice();
        
        boolean isInHomeSquares = homeSquares.contains(getPosition());
        boolean isPieceBeyondField = pathIndex + latestDice > standardPath.size()-1;

        if (isInHomeSquares){
            pathIndex = 0;
            return standardPath.get(pathIndex);
        }


        else if(isPieceBeyondField){
            pathIndex = 2 * (standardPath.size() - 1) - pathIndex - latestDice;
            return standardPath.get(pathIndex); 
        }

        else {
            pathIndex += latestDice;
            return standardPath.get(pathIndex); 
        }
    }



    /**
     * This method gives the endLocation of a piece after a move.
     * The endLocation is calculated by using an integer argument which can be between 1 and 6 (inclusive).
     * The method takes into account the complications which might occur
     * at the end, if your dice-roll isn't exactly right to get your piece in goal.
     * This method does not move the piece. 
     * 
     * @param numSpaces The number of spaces to move the piece. Must be an integer between 1 and 6 (inclusive)
     * @return the end location of the piece after the move
     */
    private Pair<Integer, Integer> getLocationAfterPossibleMove(int numSpaces){
        
        ArrayList<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        int latestDice = numSpaces;

        int copyPathIndex = pathIndex;

        boolean isInHomeSquares = homeSquares.contains(getPosition());
        boolean isPieceBeyondField = pathIndex + latestDice > standardPath.size()-1;

        if (isInHomeSquares){
            copyPathIndex = 0;
            return standardPath.get(copyPathIndex);
        }


        else if(isPieceBeyondField){
            copyPathIndex = 2 * (standardPath.size() - 1) - pathIndex - latestDice;
            return standardPath.get(copyPathIndex); 
        }

        else {
            copyPathIndex += latestDice;
            return standardPath.get(copyPathIndex); 
        }
    }

    /**
     * This method returns a list of all the squares which the piece may not enter.
     * @param houseNumber The house which the piece belongs to
     * @return A list of 3 squares which the piece cannot enter.
     */
    public ArrayList<Pair<Integer, Integer>> getEnemyStartSquares(int houseNumber) {
        
        List<Integer> xAxis = Arrays.asList(7, 2, 9, 14);
        List<Integer> yAxis = Arrays.asList(18, 11, 6, 13);

        
        ArrayList<Pair<Integer, Integer>> enemyStartSquares = addXandYlistAsPair(new ArrayList<>(), xAxis, yAxis);
        enemyStartSquares.remove(houseNumber - 1);
        return enemyStartSquares;

    }

    public void setToHouse(){
        
        Collection<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        if (homeSquares.contains(getPosition()))
            throw new IllegalStateException("Setting a piece that already in homeSquares into homeSquares");

        List<Pair<Integer, Integer>> emptyHomeSquares = owner.getEmptyHomeSquares();
        if (emptyHomeSquares.size() == 0)
            throw new IllegalStateException("No empty squares");

        this.xAxis = emptyHomeSquares.get(0).getKey();
        this.yAxis = emptyHomeSquares.get(0).getValue(); 
        pathIndex = -1;

        movePieceInGrid();
    }

    private Color getColor(){
        if (houseNumber == 1)
            return Color.GREEN;

        if (houseNumber == 2)
            return Color.YELLOW;
        
        if (houseNumber == 3)
            return Color.BLUE;

        if (houseNumber == 4)
            return Color.RED;
        throw new IllegalStateException("piece doesn't have a valid house.");
    }

    public Pair<Integer, Integer> getPosition(){
        return new Pair<Integer, Integer>(xAxis, yAxis);
    }

    public int getRow(){
        return xAxis;
    }

    public int getColumn(){
        return yAxis;
    }

    public Circle getCircle(){
        return this.circle;
    }


    public boolean isInFinishPaddock() {
        Pair<Integer, Integer> endLocation = standardPath.get(standardPath.size()-1);
        return endLocation.getKey()==xAxis && endLocation.getValue()==yAxis;
    }

    public ArrayList<Pair<Integer, Integer>> getPath() {
        List<Integer> xAxis = Arrays.asList(7,7,7,7,7,6,5,4,3,2,1,1,1,2,3,4,5,6,7,7,7,7,7,7,
        8,9,9,9,9,9,9,10,11,12,13,14,15,15,15,14,13,12,11,10,9,9,9,9,9,9,8,7);

        List<Integer> yAxis = Arrays.asList(18,17,16,15,14,13,13,13,13,13,13,12,11,11,11,11,11,11,
        10,9,8,7,6,5,5,5,6,7,8,9,10,11,11,11,11,11,11,12,13,13,13,13,13,13,14,15,16,17,18,19,19,19);

        ArrayList<Pair<Integer, Integer>> standardPath =  addXandYlistAsPair(new ArrayList<>(), xAxis, yAxis);

        if (this.houseNumber == 1) {
            List<Integer> xAxisHouse1 = Arrays.asList(7,8,8,8,8,8,8);
            List<Integer> yAxisHouse1 = Arrays.asList(18,18,17,16,15,14,13);

            return addXandYlistAsPair(standardPath, xAxisHouse1, yAxisHouse1);
        }

        if (this.houseNumber == 2) {
            
            ArrayList<Pair<Integer, Integer>> path2 = new ArrayList<>(standardPath.subList(13, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 13));
            path2.addAll(extra);

            List<Integer> xAxisHouse2 = Arrays.asList(2,2,3,4,5,6,7);
            List<Integer> yAxisHouse2 = Arrays.asList(11,12,12,12,12,12,12);
            
            return addXandYlistAsPair(path2, xAxisHouse2, yAxisHouse2);
        }
        
        if (this.houseNumber == 3) {
            
            ArrayList<Pair<Integer, Integer>> path3 = new ArrayList<>(standardPath.subList(26, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 26));
            path3.addAll(extra);

            List<Integer> xAxisHouse3 = Arrays.asList(9,8,8,8,8,8,8);
            List<Integer> yAxisHouse3 = Arrays.asList(6,6,7,8,9,10,11);
            
            return addXandYlistAsPair(path3, xAxisHouse3, yAxisHouse3);
        }

        if (this.houseNumber == 4) {
            
            ArrayList<Pair<Integer, Integer>> path4 = new ArrayList<>(standardPath.subList(39, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 39));
            path4.addAll(extra);

            List<Integer> xAxisHouse4 = Arrays.asList(14,14,13,12,11,10,9);
            List<Integer> yAxisHouse4 = Arrays.asList(13,12,12,12,12,12,12);
            
            return addXandYlistAsPair(path4, xAxisHouse4, yAxisHouse4);
        }

        throw new IllegalMonitorStateException("Din monitor har eksplodert! (noe gikk galt!)");

    } 



    private ArrayList<Pair<Integer, Integer>> addXandYlistAsPair(ArrayList<Pair<Integer, Integer>> path, List<Integer> xAxis, List<Integer> yAxis){
        if (xAxis.size() != yAxis.size())
            throw new IllegalArgumentException("Need same length of x- and y axis");
        for (int i =0 ; i <xAxis.size(); i++){
            Pair<Integer, Integer> pair = new Pair<Integer, Integer>(xAxis.get(i), yAxis.get(i));
            path.add(pair);
        }
        return path;

    }

    

}
