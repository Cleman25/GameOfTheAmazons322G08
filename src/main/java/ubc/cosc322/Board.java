package ubc.cosc322;

import java.util.ArrayList;

public class Board implements Cloneable {
    int[][] board;
    ArrayList<int[]> queens;
    int playerColor;
    ActionFactory aFac;

    public Board(int[][] board, int playerColor) {
        this.board = board;
        this.playerColor = playerColor;
        aFac = new ActionFactory(board);
    }

    public boolean isPositionInsideBoard(int[] newPosition) {
        boolean inside = false;
        if (newPosition[0] >= 0 && newPosition[0] < board.length && newPosition[1] >= 0 && newPosition[1] < board.length) {
            inside = true;
        }
        return inside;
    }

    public boolean isEmpty(int[] newPosition) {
        boolean empty = false;
        if (board[newPosition[0]][newPosition[1]] == 0) {
            empty = true;
        }
        return empty;
    }

    public ArrayList<int[]> getQueens(int playerColor) {
        ArrayList<int[]> queens = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == playerColor) {
                    int[] queen = { i, j };
                    queens.add(queen);
                }
            }
        }
        return queens;
    }

    public void move(int[] move) {
        board[move[0]][move[1]] = 0;
        board[move[2]][move[3]] = playerColor;
        if (move[4] != -1) {
            board[move[4]][move[5]] = 1;
        }
    }

    public void makeMove(int[] move) {
        board[move[0]][move[1]] = 0;
        board[move[2]][move[3]] = playerColor;
        if (move[4] != -1) {
            board[move[4]][move[5]] = 1;
        }
    }

    public void undoMove(int[] move) {
        board[move[0]][move[1]] = playerColor;
        board[move[2]][move[3]] = 0;
        if (move[4] != -1) {
            board[move[4]][move[5]] = 0;
        }
    }

    // clone the board
    public Board clone() {
        int[][] newBoard = new int[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return new Board(newBoard, playerColor);
    }

    public boolean isTerminal() {
        boolean terminal = false;
        if (aFac.getLegalMoves(playerColor).size() == 0) {
            terminal = true;
        }
        return terminal;
    }

    public int getWinner() {
        int winner = 0;
        if (aFac.getLegalMoves(playerColor).size() == 0) {
            winner = playerColor == 1 ? 2 : 1;
        }
        return winner;
    }

    public ArrayList<int[]> getPossibleMoves() {
        return aFac.generateActions(this, playerColor);
    }


}
