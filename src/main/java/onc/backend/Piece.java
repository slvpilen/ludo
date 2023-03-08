package onc.backend;

import javafx.util.Pair;
import java.util.ArrayList;

public class Piece {
    
    private Pair<Integer, Integer> position;
    private ArrayList<Pair<Integer, Integer>> path;
    private Player owner;

    

    
    public Piece(Player owner){
        this.owner = owner;

    }


        

}
