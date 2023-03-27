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
    
    private void movePieceInGrid(Piece piece){
        GridPane.setColumnIndex(piece.getCircle(), piece.getRow());
        GridPane.setRowIndex(piece.getCircle(), piece.getColumn());
    }

    public Player getOwner(){
        return this.owner;
    }

    // this will only be called if there is it hasLegalMove
    public void movePlaces(){ 

        //logic moved down to getLocationAfterPossibleMove(), because it's neccesary to know the possible ending point, before moving
        Pair<Integer,Integer> locationAfterMove = getLocationAfterPossibleMove();        
        
        int numberOfPiecesOnEndLocation = owner.getGameEngine().getNumberOfPiecesOnLocation(locationAfterMove);
        boolean onePieceFromAnotherHouseOnEndLocation = numberOfPiecesOnEndLocation==1 && !owner.hasPieceOnLocation(locationAfterMove);
        
        if (onePieceFromAnotherHouseOnEndLocation)
            owner.getGameEngine().setPieceOnLocationToHouse(locationAfterMove);
        
        this.xAxis = locationAfterMove.getKey();
        this.yAxis = locationAfterMove.getValue(); 
        
        movePieceInGrid(this);
    }

    public int getHouseNumber(){
        return houseNumber;
    }

    public boolean hasLegalMove(){
        //System.out.println("has legal move is made, but missing some important things!!!");
        
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

        return true;
    }

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
            
            
            // Du hadde glemt en parentes mot slutten av uttrykket, derfor ble kalkulert index gal.
            // Det korrekte uttrykket har blitt implementert.
            // Slik det var tildigere, tok brikken alltid to steg for lite dersom den var nærme målområdet.

            // Galt uttrykk:
            // int newIndex = (standardPath.size()-1) - (indexOfPath + latestDice - standardPath.size()-1 );      
            
            // Riktig uttrykk:
            // int newIndex = (standardPath.size()-1) - (indexOfPath + latestDice - (standardPath.size()-1) );      

            // Lett feil å fikse da, nå er grunnspillet nesten ferdig!

            pathIndex = 2 * (standardPath.size() - 1) - pathIndex - latestDice;
            return standardPath.get(pathIndex); 
        }

        else {
            pathIndex += latestDice;
            return standardPath.get(pathIndex); 
        }
    }


    // must probably copy code over to this one

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
            
            
            // Du hadde glemt en parentes mot slutten av uttrykket, derfor ble kalkulert index gal.
            // Det korrekte uttrykket har blitt implementert.
            // Slik det var tildigere, tok brikken alltid to steg for lite dersom den var nærme målområdet.

            // Galt uttrykk:
            // int newIndex = (standardPath.size()-1) - (indexOfPath + latestDice - standardPath.size()-1 );      
            
            // Riktig uttrykk:
            // int newIndex = (standardPath.size()-1) - (indexOfPath + latestDice - (standardPath.size()-1) );      

            // Lett feil å fikse da, nå er grunnspillet nesten ferdig!

            copyPathIndex = 2 * (standardPath.size() - 1) - pathIndex - latestDice;
            return standardPath.get(copyPathIndex); 
        }

        else {
            copyPathIndex += latestDice;
            return standardPath.get(copyPathIndex); 
        }
    }



    public void setToHouse(){
        
        Collection<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        if (homeSquares.contains(getPosition()))
            throw new IllegalStateException("setting a piece that already in homeSquares into homeSquares");

        List<Pair<Integer, Integer>> emptyHomeSquares = owner.getEmptyHomeSquares();
        if (emptyHomeSquares.size() == 0)
            throw new IllegalStateException("No empty squares");

        this.xAxis = emptyHomeSquares.get(0).getKey();
        this.yAxis = emptyHomeSquares.get(0).getValue(); 
        pathIndex = -1;

        movePieceInGrid(this);
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
        
        houseNumber = owner.getHouseNumber();

        ArrayList<Pair<Integer, Integer>> standardPath = new ArrayList<Pair<Integer, Integer>>();

        //GreenSpot
        standardPath.add(new Pair<Integer, Integer>(7, 18));
        standardPath.add(new Pair<Integer, Integer>(7, 17));
        standardPath.add(new Pair<Integer, Integer>(7, 16));
        standardPath.add(new Pair<Integer, Integer>(7, 15));
        standardPath.add(new Pair<Integer, Integer>(7, 14));
        
        standardPath.add(new Pair<Integer, Integer>(6, 13));
        standardPath.add(new Pair<Integer, Integer>(5, 13));
        standardPath.add(new Pair<Integer, Integer>(4, 13));
        standardPath.add(new Pair<Integer, Integer>(3, 13));
        standardPath.add(new Pair<Integer, Integer>(2, 13));
        standardPath.add(new Pair<Integer, Integer>(1, 13));
        
        standardPath.add(new Pair<Integer, Integer>(1, 12));
        standardPath.add(new Pair<Integer, Integer>(1, 11));
        
        // YellowSpot
        standardPath.add(new Pair<Integer, Integer>(2, 11));
        standardPath.add(new Pair<Integer, Integer>(3, 11));
        standardPath.add(new Pair<Integer, Integer>(4, 11));
        standardPath.add(new Pair<Integer, Integer>(5, 11));
        standardPath.add(new Pair<Integer, Integer>(6, 11));
        
        standardPath.add(new Pair<Integer, Integer>(7, 10));
        standardPath.add(new Pair<Integer, Integer>(7, 9));
        standardPath.add(new Pair<Integer, Integer>(7, 8));
        standardPath.add(new Pair<Integer, Integer>(7, 7));
        standardPath.add(new Pair<Integer, Integer>(7, 6));
        standardPath.add(new Pair<Integer, Integer>(7, 5));
        
        standardPath.add(new Pair<Integer, Integer>(8, 5));
        standardPath.add(new Pair<Integer, Integer>(9, 5));
        
        //BlueSpot
        standardPath.add(new Pair<Integer, Integer>(9, 6));
        standardPath.add(new Pair<Integer, Integer>(9, 7));
        standardPath.add(new Pair<Integer, Integer>(9, 8));
        standardPath.add(new Pair<Integer, Integer>(9, 9));
        standardPath.add(new Pair<Integer, Integer>(9, 10));
        
        standardPath.add(new Pair<Integer, Integer>(10, 11));
        standardPath.add(new Pair<Integer, Integer>(11, 11));
        standardPath.add(new Pair<Integer, Integer>(12, 11));
        standardPath.add(new Pair<Integer, Integer>(13, 11));
        standardPath.add(new Pair<Integer, Integer>(14, 11));
        standardPath.add(new Pair<Integer, Integer>(15, 11));
        
        standardPath.add(new Pair<Integer, Integer>(15, 12));
        standardPath.add(new Pair<Integer, Integer>(15, 13));
        
        //RedSpot
        standardPath.add(new Pair<Integer, Integer>(14, 13));
        standardPath.add(new Pair<Integer, Integer>(13, 13));
        standardPath.add(new Pair<Integer, Integer>(12, 13));
        standardPath.add(new Pair<Integer, Integer>(11, 13));
        standardPath.add(new Pair<Integer, Integer>(10, 13));
        
        standardPath.add(new Pair<Integer, Integer>(9, 14));
        standardPath.add(new Pair<Integer, Integer>(9, 15));
        standardPath.add(new Pair<Integer, Integer>(9, 16));
        standardPath.add(new Pair<Integer, Integer>(9, 17));
        standardPath.add(new Pair<Integer, Integer>(9, 18));
        standardPath.add(new Pair<Integer, Integer>(9, 19));
        
        standardPath.add(new Pair<Integer, Integer>(8, 19));
        standardPath.add(new Pair<Integer, Integer>(7, 19));
        
        if (this.houseNumber == 1) {
            standardPath.add(new Pair<Integer, Integer>(7, 18));
            standardPath.add(new Pair<Integer, Integer>(8, 18));
            standardPath.add(new Pair<Integer, Integer>(8, 17));
            standardPath.add(new Pair<Integer, Integer>(8, 16));
            standardPath.add(new Pair<Integer, Integer>(8, 15));
            standardPath.add(new Pair<Integer, Integer>(8, 14));
            standardPath.add(new Pair<Integer, Integer>(8, 13));
            return standardPath;
        }

        if (this.houseNumber == 2) {
            
            ArrayList<Pair<Integer, Integer>> path2 = new ArrayList<>(standardPath.subList(13, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 13));
            path2.addAll(extra);
            
            path2.add(new Pair<Integer, Integer>(2, 11));
            path2.add(new Pair<Integer, Integer>(2, 12));
            path2.add(new Pair<Integer, Integer>(3, 12));
            path2.add(new Pair<Integer, Integer>(4, 12));
            path2.add(new Pair<Integer, Integer>(5, 12));
            path2.add(new Pair<Integer, Integer>(6, 12));
            path2.add(new Pair<Integer, Integer>(7, 12));
            
            return path2;
        }

        
        if (this.houseNumber == 3) {
            
            ArrayList<Pair<Integer, Integer>> path3 = new ArrayList<>(standardPath.subList(26, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 26));
            path3.addAll(extra);
            
            path3.add(new Pair<Integer, Integer>(9, 6));
            path3.add(new Pair<Integer, Integer>(8, 6));
            path3.add(new Pair<Integer, Integer>(8, 7));
            path3.add(new Pair<Integer, Integer>(8, 8));
            path3.add(new Pair<Integer, Integer>(8, 9));
            path3.add(new Pair<Integer, Integer>(8, 10));
            path3.add(new Pair<Integer, Integer>(8, 11));
            
            return path3;
        }

        if (this.houseNumber == 4) {
            
            ArrayList<Pair<Integer, Integer>> path4 = new ArrayList<>(standardPath.subList(39, 52));
            ArrayList<Pair<Integer, Integer>> extra = new ArrayList<>(standardPath.subList(0, 39));
            path4.addAll(extra);
            
            path4.add(new Pair<Integer, Integer>(14, 13));
            path4.add(new Pair<Integer, Integer>(14, 12));
            path4.add(new Pair<Integer, Integer>(13, 12));
            path4.add(new Pair<Integer, Integer>(12, 12));
            path4.add(new Pair<Integer, Integer>(11, 12));
            path4.add(new Pair<Integer, Integer>(10, 12));
            path4.add(new Pair<Integer, Integer>(9, 12));
            
            return path4;
        }

        throw new IllegalMonitorStateException("Din monitor har eksplodert! (noe gikk galt!)");

        
    }

    

}
