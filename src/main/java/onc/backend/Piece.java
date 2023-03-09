package onc.backend;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import java.util.ArrayList;

public class Piece {
    
    private int xAxis;
    private int yAxis;
    private ArrayList<Pair<Integer, Integer>> path;
    private Player owner;
    private int houseNumber; // this decide the color of the piece
    private Circle circle;
    

    
    public Piece(Player owner, Pair<Integer, Integer> position){
        this.owner = owner;
        this.xAxis = position.getKey();
        this.yAxis = position.getValue();
        this.houseNumber = owner.getHouseNumber();
        this.circle = new Circle(15, getColor());
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
        

}
