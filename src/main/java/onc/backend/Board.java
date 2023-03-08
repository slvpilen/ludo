package onc.backend;

import java.util.ArrayList;
import java.util.Collection;

import javafx.util.Pair;

public class Board {

    private ArrayList<Player> players;



    public Board(ArrayList<Player> players){
        this.players = players; 
    }


    public boolean spotIsEmpty(Pair<Integer, Integer> square){
        return players.stream().allMatch(player -> player.hasNoOwnPieceOnSquare(square));
    }

    public Pair<Player, Integer> playerOnSpot(Pair<Integer, Integer> square){
        if (spotIsEmpty(square))
            return null;

            for (Player player : players) {
                if (!player.hasNoOwnPieceOnSquare(square)) {
                    return new Pair<Player, Integer>(player, player.getAmountOfPiecesOnSquare(square));
                }
            }
        

        return new Pair<Player, Integer>(players.get(0), 9); //player, number of pices
        
    }


    public ArrayList<Pair<Integer, Integer>> getHomeSquares(Player player) {
        int houseNumber =player.getHouseNumber();
        ArrayList<Pair<Integer, Integer>> pieces = new ArrayList<Pair<Integer, Integer>>();

        if (houseNumber == 1) {
            pieces.add(new Pair<Integer, Integer>(1, 9));
            pieces.add(new Pair<Integer, Integer>(1, 11));
            pieces.add(new Pair<Integer, Integer>(3, 9));
            pieces.add(new Pair<Integer, Integer>(3, 11));
        }

        if (houseNumber == 2) {
            pieces.add(new Pair<Integer, Integer>(1, 1));
            pieces.add(new Pair<Integer, Integer>(1, 3));
            pieces.add(new Pair<Integer, Integer>(3, 1));
            pieces.add(new Pair<Integer, Integer>(3, 3));
        }

        if (houseNumber == 3) {
            pieces.add(new Pair<Integer, Integer>(9, 1));
            pieces.add(new Pair<Integer, Integer>(9, 3));
            pieces.add(new Pair<Integer, Integer>(11, 1));
            pieces.add(new Pair<Integer, Integer>(11, 3));
        }

        if (houseNumber == 4) {
            pieces.add(new Pair<Integer, Integer>(9, 9));
            pieces.add(new Pair<Integer, Integer>(9, 11));
            pieces.add(new Pair<Integer, Integer>(11, 9));
            pieces.add(new Pair<Integer, Integer>(11, 11));
        }

        return pieces;
        
    } 



}








