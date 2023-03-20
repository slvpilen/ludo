package onc.backend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import java.util.ArrayList;

public class Piece {
    
    private int xAxis;
    private int yAxis;
    private ArrayList<Pair<Integer, Integer>> standardPath;
    private Player owner;
    private int houseNumber; // this decide the color of the piece
    private Circle circle;
    

    
    public Piece(Player owner, Pair<Integer, Integer> position){
        this.owner = owner;
        this.xAxis = position.getKey();
        this.yAxis = position.getValue();
        this.houseNumber = owner.getHouseNumber();
        this.circle = new Circle(15, getColor());  // could add setting to this
        circle.setStroke(Color.BLACK);
        getPath(owner);
    }


    public Player getOwner(){
        return owner;
    }

    public void movePlaces(int latestDice){ // make this logic, make it update on board to
        throw new IllegalArgumentException("not added any moethod yet!");
    }

    public int getHouseNumber(){
        return houseNumber;
    }

    public boolean hasLegalMove(int latestDice){
        System.out.println("make this methode!!!");
        return true;
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
    

    private void getPath(Player owner) {
        
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



        
        

        
    }

    public static void main(String[] args) {
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

        System.out.println(standardPath.indexOf(new Pair<Integer, Integer>(14, 13)));
    }

    

}
