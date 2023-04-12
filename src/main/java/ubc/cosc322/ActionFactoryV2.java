package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class ActionFactoryV2 {
    private static final int MAX_NODES_GENERATED = 500;
    private int[][] board;
    private int player;

    public ActionFactoryV2(int[][] board, int player) {
        this.board = board;
        this.player = player;
    }

    public ArrayList<ArrayList<int[]>> actionsV2() {
		ArrayList<ArrayList<int[]>> actions = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> queenPos=getQueenPositions();
        
        for(int[] queen:queenPos) {
            ArrayList<int[]> action = new ArrayList<>();
            ArrayList<int[]> moves = generateMoves(queen[0], queen[1]);

            for (int[] move : moves) {
                int[][] tempBoard = this.copyBoard();
                ArrayList<int[]> tmove = new ArrayList<>();
                tmove.add(queen);
                tmove.add(move);
                tempBoard = makeMove(tmove, tempBoard);
                ArrayList<int[]> tshot = generateShots(move, tempBoard);

                for (int[] shot : tshot) {
                    action.add(queen);
                    action.add(move);
                    action.add(shot);
                    actions.add(action);
                }
            }
        }
        return actions;
    }

    private ArrayList<int[]> generateShots(int[] move, int[][] tempBoard) {
        ArrayList<int[]> shots = new ArrayList<>();
        int row = move[0];
        int col = move[1];

        // Iterate over all possible shots for the queen
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int r = row + i;
                if (r < 1 || r > 10) {
                    continue;
                }
                int c = col + j;
                if (c < 1 || c > 10) {
                    continue;
                }
                int pos = tempBoard[r][c];
                // since the first row and columns are margins, we want the shot to be between 1-10
                while (r >= 1 && r < 11 && c >= 1 && c < 11 && pos == 0) {
                    shots.add(new int[] {r, c});
                    r += i;
                    c += j;
                }   
            }
        }

        return shots;
    }

    public int[][] makeMove(ArrayList<int[]> move, int[][] tempBoard) {
        int[][] child = this.copyBoard(tempBoard);
        int[] old = move.get(0);
        int[] newP = move.get(1);
        child[old[0]][old[1]] = 0;
        child[newP[0]][newP[1]] = player;
        return child;
    }

    public ArrayList<int[]> generateMoves(int row, int col) {
        ArrayList<int[]> moves = new ArrayList<>();

        // Iterate over all possible moves for the queen
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int r = row + i;
                if (r < 1 || r > 10) {
                    continue;
                }
                int c = col + j;
                if (c < 1 || c > 10) {
                    continue;
                }
                int pos = board[r][c];

                while (r >= 1 && r < 11 && c >= 1 && c < 11 && pos == 0) {
                    moves.add(new int[] {r, c});
                    r += i;
                    c += j;
                }
            }
        }

        return moves;
    }

    public ArrayList<int[]> getQueenPositions() {
        ArrayList<int[]> queens = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                if (board[i][j] == player) {
                    queens.add(new int[] {i, j});
                }
            }
        }
        return queens;
    }

    public int[][] copyBoard() {
        int n = board.length;
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public int[][] copyBoard(int[][] board) {
        int n = board.length;
        int[][] newBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

}