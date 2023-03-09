package onc.backend;

import java.util.ArrayList;
import javafx.util.Pair;

public class Board {

    private ArrayList<Player> players;


    public Board(ArrayList<Player> players){
        this.players = players; 
        this.players.forEach(player -> player.setBoard(this));
    }


    public boolean spotIsEmpty(Pair<Integer, Integer> square){
        return players.stream().allMatch(player -> !player.hasOwnPieceOnSquare(square));
    }

    public Pair<Player, Integer> playerOnSpot(Pair<Integer, Integer> square){
        if (spotIsEmpty(square))
            return null;

            for (Player player : players) {
                if (player.hasOwnPieceOnSquare(square)) {
                    return new Pair<Player, Integer>(player, player.getAmountOfPiecesOnSquare(square));
                }
            }
        

        return new Pair<Player, Integer>(players.get(0), 9); //player, number of pices
        
    }

    // husrekkefølge:
    //  2   3   
    //  1   4
    public ArrayList<Pair<Integer, Integer>> getHomeSquares(Player player) {
        int houseNumber =player.getHouseNumber();
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



}








