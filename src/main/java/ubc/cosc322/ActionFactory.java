package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
	
	public ArrayList<int[]> generateQueenMoves(int row, int col, int[][] board) {
		ArrayList<int[]> queenMoves = new ArrayList<>();
	
		// Check all possible queen moves in 8 directions
		int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int i = row + dx;
			int j = col + dy;
			while (i >0 && i < board.length && j > 0 && j < board[0].length && board[i][j] == 0) {
				// Queen can move to this empty position
				queenMoves.add(new int[]{i, j});
				i += dx;
				j += dy;
			}
		}
	
		return queenMoves;
	}

	public ArrayList<int[]> generateArrowMoves(int row, int col, int[][] board) {
		ArrayList<int[]> arrowMoves = new ArrayList<>();
	
		// Check all possible arrow moves in 8 directions
		int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
		for (int[] direction : directions) {
			int dx = direction[0];
			int dy = direction[1];
			int i = row + dx;
			int j = col + dy;
			while (i >0 && i < board.length && j > 0 && j < board[0].length && board[i][j] == 0) {
				// Arrow can be shot from this empty position
				arrowMoves.add(new int[]{i, j});
				i += dx;
				j += dy;
			}

		}
	
		return arrowMoves;
	}

/**
 * Generates all possible moves for the given player color on the given board.
 * Returns an ArrayList of int arrays, where each int array represents a move.
 * The int array has 4 or 6 elements, depending on whether the move is a queen move or an arrow move.
 * The first two elements represent the queen's current position, and the next two elements represent the queen's new position.
 * If the move is an arrow move, the last two elements represent the arrow's position.
 */
public ArrayList<int[]> generateAllPossibleMoves(int[][] board, int queenColor) {
    ArrayList<int[]> allPossibleMoves = new ArrayList<>();

    List<int[]> queenPositions = findQueens(board, queenColor);

    for (int[] queenPos : queenPositions) {
        List<int[]> queenMoves = generateQueenMoves(queenPos[0], queenPos[1], board);

        for (int[] queenMove : queenMoves) {
            int[][] newBoard = copyBoard(board);
            makeMove(queenPos,queenMove, newBoard);
            List<int[]> arrowMoves = generateArrowMoves(queenMove[0], queenMove[1], newBoard);
            for (int[] arrowMove : arrowMoves) {
                int[] move = { queenPos[0], queenPos[1], queenMove[0], queenMove[1], arrowMove[0], arrowMove[1] };
                allPossibleMoves.add(move);
            }
        }
    }

    return allPossibleMoves;
}
public List<int[]> findQueens(int[][] board, int queenColor) {
    List<int[]> queenPositions = new ArrayList<>();

    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
            if (board[i][j] == queenColor) {
                int[] queenPos = { i, j };
                queenPositions.add(queenPos);
            }
        }
    }

    return queenPositions;
}
/**
 * Creates a deep copy of the given board.
 * Returns a new 2D array with the same dimensions and values as the given board.
 */
public static int[][] copyBoard(int[][] board) {
    int[][] newBoard = new int[board.length][board[0].length];

    for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board[i].length; j++) {
            newBoard[i][j] = board[i][j];
        }
    }

    return newBoard;
}

/**
 * Makes the given move on the given board.
 * Modifies the given board in place.
 */
public static void makeMove(int[] queenPos,int[] move, int[][] board) {
    int queenRow = queenPos[0];
    int queenCol = queenPos[1];

    int newQueenRow = move[0];
    int newQueenCol = move[1];

    // Update queen position
	int queenColor = board[queenRow][queenCol];
    board[queenRow][queenCol] = 0;
    board[newQueenRow][newQueenCol] = (queenColor==1?1:2);

}


	
}
