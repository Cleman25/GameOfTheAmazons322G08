package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
	//rep of game board, java.util.ArrayList<java.lang.Integer> gameS
	
	private int [][] board;

	public ActionFactory(int [][] board) {
		this.board = board;
	}

	public List<int[]> getLegalMoves(int player) {
		List<int[]> legalMoves = new ArrayList<int[]>(); // list of legal moves
		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				if (board[i][j] == player) {
					List<int[]> queenMoves = getQueenMoves(i, j);
					List<int[]> queenShots = getQueenShots(i, j);
					legalMoves.addAll(queenMoves);
					legalMoves.addAll(queenShots);
				}
			}
		}
		return legalMoves;
	}

	public List<int[]> getQueenMoves(int row, int col) {
		List<int[]> queenMoves = new ArrayList<int[]>();
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {
				if (di == 0 && dj == 0)
					continue;
				int i = row + di;
				int j = col + dj;
				while (i >= 1 && i <= 10 && j >= 1 && j <= 10) {
					if (board[i][j] == 0) {
						int[] move = { row, col, i, j, -1, -1};
						queenMoves.add(move);
					} else {
						break;
					}
					i += di;
					j += dj;
				}
			}
		}
		return queenMoves;
	}

	public List<int[]> getQueenShots(int row, int col) {
		List<int[]> queenShots = new ArrayList<int[]>();
		for (int di = -1; di <= 1; di++) {
			for (int dj = -1; dj <= 1; dj++) {
				if (di == 0 && dj == 0)
					continue;
				int i = row + di;
				int j = col + dj;
				while (i >= 1 && i <= 10 && j >= 1 && j <= 10 && board[i][j] == 0) {
					i += di;
					j += dj;
				}
				if (i >= 1 && i <= 10 && j >= 1 && j <= 10 && board[i][j] != 0) {
					int ai = i;
					int aj = j;
					while (ai >= 1 && ai <= 10 && aj >= 1 && aj <= 10) {
						if (board[ai][aj] == 0) {
							int[] shot = { row, col, i, j, ai, aj};
							queenShots.add(shot);
						} else {
							break;
						}
						ai += di;
						aj += dj;
					}
				}
			}
		}
		return queenShots;
	}

	public int[][] applyMove(int[] move) {
		int[][] newBoard = new int[board.length][board.length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		newBoard[move[0]][move[1]] = 0;
		newBoard[move[2]][move[3]] = board[move[0]][move[1]];
		if (move[4] != -1) {
			newBoard[move[4]][move[5]] = 1;
		}
		return newBoard;
	}

	public boolean isLegalMove(int[] move) {
		List<int[]> legalMoves = getLegalMoves(board[move[0]][move[1]]);
		for (int[] legalMove : legalMoves) {
			if (legalMove[0] == move[0] && legalMove[1] == move[1] && legalMove[2] == move[2] && legalMove[3] == move[3]
					&& legalMove[4] == move[4] && legalMove[5] == move[5]) {
				return true;
			}
		}
		return false;
	}
}