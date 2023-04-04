package ubc.cosc322;

import java.util.ArrayList;

public class AmazonsMinimax {

	private int[][] board;
	private int depth;
	private int playerColor;
    private ActionFactory actionFactory=new ActionFactory();

	public AmazonsMinimax(int[][] board, int depth, int playerColor) {
		this.board = board;
		this.depth = depth;
		this.playerColor = playerColor;
	}

	public int[] findBestMove() {
		int[] bestMove = null;
		int bestScore = Integer.MIN_VALUE;

		ArrayList<int[]> possibleMoves = actionFactory.generateAllPossibleMoves(board, playerColor);

		for (int[] move : possibleMoves) {
			int[][] newBoard = ActionFactory.copyBoard(board);
			makeMove(move, newBoard);
			makeMove(move, newBoard);

			int score = minimax(newBoard, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			if (score > bestScore) {
				bestScore = score;
				bestMove = move;
			}
		}

		return bestMove;
	}

	private int minimax(int[][] board, int depth, int alpha, int beta, boolean isMaximizingPlayer) {
		if (depth == 0) {
			return evaluate(board, playerColor);
		}

		if (isMaximizingPlayer) {
			int maxScore = Integer.MIN_VALUE;
			ArrayList<int[]> possibleMoves = actionFactory.generateAllPossibleMoves(board, playerColor);

			for (int[] move : possibleMoves) {
				int[][] newBoard = ActionFactory.copyBoard(board);
				makeMove(move, newBoard);
				makeMove(move, newBoard);

				int score = minimax(newBoard, depth - 1, alpha, beta, false);
				maxScore = Math.max(maxScore, score);
				alpha = Math.max(alpha, score);
				if (beta <= alpha) {
					break;
				}
			}

			return maxScore;
		} else {
			int minScore = Integer.MAX_VALUE;
			ArrayList<int[]> possibleMoves = actionFactory.generateAllPossibleMoves(board, (playerColor==1)?2:1);

			for (int[] move : possibleMoves) {
				int[][] newBoard = ActionFactory.copyBoard(board);
				ActionFactory.makeMove(new int[] { move[0], move[1] }, new int[] { move[2], move[3] }, newBoard);
				ActionFactory.makeMove(new int[] { move[2], move[3] }, new int[] { move[4], move[5] }, newBoard);

				int score = minimax(newBoard, depth - 1, alpha, beta, true);
				minScore = Math.min(minScore, score);
				beta = Math.min(beta, score);
				if (beta <= alpha) {
					break;
				}
			}

			return minScore;
		}
	}
    private static void makeMove(int[] move, int[][] board) {
        // Update queen position
        int queenType = board[move[0]][move[1]];
        board[move[0]][move[1]] = 0;
        board[move[2]][move[3]] =  (queenType== 2) ? 2 : 1;
    
        // Update arrow position
        board[move[4]][move[5]] = 3;
    }

	private int evaluate(int[][] board, int playerColor) {
        int numMoves = actionFactory.generateAllPossibleMoves(board, playerColor).size();
        return(playerColor==2)?numMoves:-numMoves;
    }
}
	
