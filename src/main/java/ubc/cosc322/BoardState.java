package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class BoardState {
    private int[][] board = new int[10][10];
    private BoardAction bestMove = new BoardAction();
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

    public BoardState(int[][] board, int playedBy) {
        this.setBoard(board);
        this.playedBy = playedBy;
        findQueens();
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
        findQueens();
    }

    public BoardAction getBestMove() {
        return bestMove;
    }

    public void setBestMove(BoardAction move) {
        this.bestMove = move;
    }

    public int getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(int playedBy) {
        this.playedBy = playedBy;
    }

    public BoardState copy() {
        int[][] newBoard = new int[10][10];
        for (int i = 1; i < GameOfAmazon.BOARD_SIZE; i++) {
            System.arraycopy(board[i], 1, newBoard[i], 1, GameOfAmazon.BOARD_SIZE - 1);
        }
        BoardState newBoardState = new BoardState(newBoard, playedBy);
        return newBoardState;
    }

    public void findQueens() {
        for (int i = 1; i < GameOfAmazon.BOARD_SIZE; i++) {
            for (int j = 1; j < GameOfAmazon.BOARD_SIZE; j++) {
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
        for (int i = 1; i < GameOfAmazon.BOARD_SIZE; i++) {
            for (int j = 1; j < GameOfAmazon.BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public List<BoardAction> possibleActions() {
        return new ActionFactory().generateActions(this);
    }

    public List<BoardAction> possibleActionsV2() {
        return new ActionFactory().generateActionsV2(this);
    }

    public ArrayList<int[]> getEmptyAdjacentPositions(int row, int col) {
        ArrayList<int[]> positions = new ArrayList<>();
    
        // Check all adjacent positions
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // Skip current position
                if (i == row && j == col) {
                    continue;
                }
                // Check if position is on board
                if (i >= 1 && i < GameOfAmazon.BOARD_SIZE && j >= 1 && j < GameOfAmazon.BOARD_SIZE) {
                    // Check if position is empty
                    if (board[i][j] == EMPTY) {
                        positions.add(new int[] {i, j});
                    }
                }
            }
        }
    
        return positions;
    }

    public boolean isTerminal() {
        if (playedBy == WHITE) {
            return WHITEQUEENS.isEmpty();
        } else {
            return BLACKQUEENS.isEmpty();
        }
    }

    /**
     * Returns the utility of the board state
     * @return
     */
    public int utility() {
        if (isTerminal()) {
            if (playedBy == WHITE) {
                return Integer.MIN_VALUE;
            } else {
                return Integer.MAX_VALUE;
            }
        } else {
            return 0;
        }
    }
}
