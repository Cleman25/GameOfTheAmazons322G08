package ubc.cosc322;

import java.util.ArrayList;

public class BoardAction {
    private int[] move = new int[6];
    private int[] currPos = new int[2];
    private int[] newPos = new int[2];
    private int[] arrowPos = new int[2];
    private int[] oldMove = new int[6];
    private ArrayList<int[]> WHITEQUEENS = new ArrayList<int[]>(); // each player has 4 queens on the board
    private ArrayList<int[]> BLACKQUEENS = new ArrayList<int[]>(); // each player has 4 queens on the board
    private ArrayList<Integer> queenCurrent = new ArrayList<Integer>();
    private ArrayList<Integer> queenNew = new ArrayList<Integer>();
    private ArrayList<Integer> arrow = new ArrayList<Integer>();
    private int player;
    public boolean canShootArrow = false;

    public BoardAction() {
    }

    public BoardAction(int[] move, int player) {
        this.move = move;
        this.player = player;
        this.currPos[0] = move[0];
        this.currPos[1] = move[1];
        this.newPos[0] = move[2];
        this.newPos[1] = move[3];
        this.arrowPos[0] = move[4];
        this.arrowPos[1] = move[5];
        this.queenCurrent.add(move[0]);
        this.queenCurrent.add(move[1]);
        this.queenNew.add(move[2]);
        this.queenNew.add(move[3]);
        this.arrow.add(move[4]);
        this.arrow.add(move[5]);
    }

    public BoardAction(int[] currPos, int[] newPos, int[] arrowPos, int player) {
        this.currPos = currPos;
        this.newPos = newPos;
        this.arrowPos = arrowPos;
        this.player = player;
        this.queenCurrent.add(currPos[0]);
        this.queenCurrent.add(currPos[1]);
        this.queenNew.add(newPos[0]);
        this.queenNew.add(newPos[1]);
        this.arrow.add(arrowPos[0]);
        this.arrow.add(arrowPos[1]);
        this.canShootArrow = true;
    }

    public int[] getMove() {
        return move;
    }

    public void setMove(int[] move) {
        this.move = move;
    }

    public int[] getcurrPos() {
        return currPos;
    }

    public void setcurrPos(int[] currPos) {
        this.currPos = currPos;
    }

    public int[] getNewPos() {
        return newPos;
    }

    public void setNewPos(int[] newPos) {
        this.newPos = newPos;
    }

    public int[] getArrowPos() {
        return arrowPos;
    }

    public void setArrowPos(int[] arrowPos) {
        this.arrowPos = arrowPos;
        this.canShootArrow = true;
    }

    public int[] getOldMove() {
        return oldMove;
    }

    public void setOldMove(int[] oldMove) {
        this.oldMove = oldMove;
    }

    public ArrayList<Integer> getQueenCurrent() {
        return queenCurrent;
    }

    public void setQueenCurrent(ArrayList<Integer> queenCurrent) {
        this.queenCurrent = queenCurrent;
    }

    public ArrayList<Integer> getQueenNew() {
        return queenNew;
    }

    public void setQueenNew(ArrayList<Integer> queenNew) {
        this.queenNew = queenNew;
    }

    public ArrayList<Integer> getArrow() {
        return arrow;
    }

    public void setArrow(ArrayList<Integer> arrow) {
        this.arrow = arrow;
        this.canShootArrow = true;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void printMove() {
        System.out.println("Queen current position: " + queenCurrent.get(0) + " " + queenCurrent.get(1));
        System.out.println("Queen new position: " + queenNew.get(0) + " " + queenNew.get(1));
        System.out.println("Arrow position: " + arrow.get(0) + " " + arrow.get(1));
    }
}
