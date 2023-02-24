package ubc.cosc322;

import java.util.ArrayList;

public class BoardState {
    private int[][] board = new int[10][10];
    private BoardAction move = new BoardAction();
    private int playedBy;
    public int ME = -1;
    public static int WHITE = 1;
    public static int BLACK = 2;
    public static int EMPTY = 0;
    public int turn = 1;
    public int MY_ARROW = 3;
    public ArrayList<Queen> BLACKQUEENS = new ArrayList<>(); // each player has 4 queens on the board
    public ArrayList<Queen> WHITEQUEENS = new ArrayList<>(); // each player has 4 queens on the board

    public BoardState() {
    }

    public BoardState(int[][] board, BoardAction move, int playedBy) {
        this.board = board;
        this.move = move;
        this.playedBy = playedBy;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public BoardAction getMove() {
        return move;
    }

    public void setMove(BoardAction move) {
        this.move = move;
    }

    public int getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(int playedBy) {
        this.playedBy = playedBy;
    }

    public BoardState copy() {
        int[][] newBoard = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        BoardAction newMove = new BoardAction(move.getMove(), playedBy);
        return new BoardState(newBoard, newMove, playedBy);
    }

    public int[] getQueenPosition() {
        int[] queenPosition = new int[2];
        if (playedBy == WHITE) {
            queenPosition[0] = move.getMove()[0];
            queenPosition[1] = move.getMove()[1];
        } else {
            queenPosition[0] = move.getMove()[2];
            queenPosition[1] = move.getMove()[3];
        }
        return queenPosition;
    }

    public void findQueens() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == WHITE) {
                    Queen queen = new Queen(i, j, WHITE);
                    WHITEQUEENS.add(queen);
                } else if (board[i][j] == BLACK) {
                    Queen queen = new Queen(i, j, BLACK);
                    BLACKQUEENS.add(queen);
                }
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public ArrayList<BoardAction> possibleActions() {
        return new ActionFactory().generateActions(this);
    }

    public ArrayList<int[]> getEmptyAdjacentPositions(int row, int col) {
        ArrayList<int[]> positions = new ArrayList<int[]>();
    
        // Check all adjacent positions
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip current position
                if (i == row && j == col) {
                    continue;
                }
                // Check if position is on board
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                    // Check if position is empty
                    if (board[i][j] == EMPTY) {
                        positions.add(new int[] {i, j});
                    }
                }
            }
        }
    
        return positions;
    }
}
