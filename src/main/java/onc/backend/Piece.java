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
        return owner;
    }

    public void movePlaces(){ // make this logic, make it update on board to
        //throw new IllegalArgumentException("not added any moethod yet!");
        ArrayList<Pair<Integer, Integer>> homeSquares = owner.getHomeSquares();
        if (homeSquares.contains(getPosition())){
            this.xAxis = standardPath.get(0).getKey();
            this.yAxis = standardPath.get(0).getValue();
        }
        else {
            int indexOfPath = standardPath.indexOf(getPosition());
            int newIndex = indexOfPath + owner.getGameEngine().getDice();
            this.xAxis = standardPath.get(newIndex).getKey();
            this.yAxis = standardPath.get(newIndex).getValue();
        }
        
        //addPieceToGrid(this);
        movePieceInGrid(this);
    }

    public int getHouseNumber(){
        return houseNumber;
    }

    public boolean hasLegalMove(){
        System.out.println("has lega move is not made: make this methode!!!");
        return true;
    }

    public Pair<Integer, Integer> getLocationAfterPossibelMove(){
        if (!hasLegalMove())
            throw new IllegalStateException("This has no legal move");
        throw new IllegalArgumentException("Make a return statment that return the positon after its moved");
        //////////////too do!
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
        throw new IllegalStateException("piece dont contain in valide house");
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

    public static void main(String[] args) {
        ArrayList<Pair<Integer, Integer>> standardPath = new ArrayList<Pair<Integer, Integer>>();

        
    }

    

}
