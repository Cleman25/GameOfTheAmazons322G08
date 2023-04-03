package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class AI {
	// int[][] board;
	Board board;
	int player = -1;
	long timeLimit = 3000;
	int simulations;

	public AI(Board board, int player) {
		this.board = board;
		this.player = player;
		board.setPlayer(player);
		board.printGameState();
		System.out.println("Player: " + player);
	}

	public AI(int[][] board, int player, int simulations) {
		this(new Board(board), player);
		this.simulations = simulations;
	}

	public int[] findBestMove() {
		Node rootNote = new Node(board);
		long endTime = System.currentTimeMillis() + timeLimit;
		System.out.println("Time limit: " + timeLimit);
		while(System.currentTimeMillis() < endTime) {
			System.out.println("Time left: " + (endTime - System.currentTimeMillis()));
			Node node = rootNote;
			Board simulationBoard = board.copyBoard(board);
			
			System.out.println("Move: " + node.getMove());
			while(!node.isLeaf() && simulationBoard.getMoves(node.getMoves()).isEmpty()) {
				node = node.selectChild();
				System.out.println("Move: " + node.getMove());
				simulationBoard.move(node.getMove());
			}

			if(!node.isLeaf()) {
				int[] move = simulationBoard.getMoves(node.getMoves()).get(0);
				node = node.addChild(move);
				simulationBoard.move(move);
			}

			int score = simulate(simulationBoard);

			while(node != null) {
				node.update(score);
				node = node.getParent();
			}
		}
		System.out.println("Time left: " + (endTime - System.currentTimeMillis()));
		System.out.println("Best child: " + rootNote.getBestChild().getMove());

		Node bestChild = rootNote.getBestChild();
		return bestChild.getMove();
	}

	public int simulate(Board simulationBoard) {
		while (!simulationBoard.isOver()) {
			ActionFactory aFac = new ActionFactory(simulationBoard.board);
			List<int[]> moves = aFac.getLegalMoves(player);

			if (moves.isEmpty()) {
				board = board.copyBoard(simulationBoard.board);
				continue;
			}

			int[] action = moves.get((int) (Math.random() * moves.size()));

			simulationBoard.move(action);
		}

		return simulationBoard.getScore(player);
	}
}

class Board {
	int[][] board;
	ActionFactory aFac;
	int player;

	public Board(int[][] board) {
		this.board = board;
		aFac = new ActionFactory(board);
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getWinner() {
		// player who still has moves
		int winner = 0;
		if (aFac.getLegalMoves(1).size() == 0) {
			winner = 2;
		} else if (aFac.getLegalMoves(2).size() == 0) {
			winner = 1;
		} else {
			winner = 0;
		}
		return winner;
	}

	public void move(int[] move) {
		board = aFac.applyMove(move);
	}

	public Board copyBoard(Board board) {
		int[][] newBoard = new int[11][11];
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				newBoard[i][j] = board.board[i][j];
			}
		}
		return new Board(newBoard);
	}

	public Board copyBoard(int[][] board) {
		int[][] newBoard = new int[11][11];
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return new Board(newBoard);
	}

	public List<int[]> getMoves(List<int[]> moves) {
		List<int[]> legalMoves = new ArrayList<int[]>();
		for (int[] move : moves) {
			if (aFac.isLegalMove(move)) {
				legalMoves.add(move);
			}
		}
		return legalMoves;
	}

	public List<int[]> getMoves(int player) {
		return aFac.getLegalMoves(player);
	}

	public boolean isOver() {
		return getWinner() != 0;
	}

	public List<int[]> getQueens(int player) {
		List<int[]> queens = new ArrayList<int[]>();
		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				if (board[i][j] == player) {
					queens.add(new int[] {i, j});
				}
			}
		}
		return queens;
	}

	public int getScore(int player) {
		int pMoves = getMoves(player).size();
		int oMoves = getMoves((player == 1) ? 2 : 1).size();
		int pThreats = 0;
		int oThreats = 0;

		for (int i = 1; i < 11; i++) {
			for (int j = 1; j < 11; j++) {
				int square = board[i][j];
				if (square == player) {
					if (getThreats(i, j, player)) {
						pThreats++;
					}
				} else if (square == ((player == 1) ? 2 : 1)) {
					if (getThreats(i, j, ((player == 1) ? 2 : 1))) {
						oThreats++;
					}
				}
			}
		}

		return pMoves - oMoves + pThreats - oThreats;
	}

	public boolean getThreats(int i, int j, int player) {
		int opp = (player == 1) ? 2 : 1;
		for (int k = -1; k < 2; k++) {
			for (int l = -1; l < 2; l++) {
				if (k == 0 && l == 0) {
					continue;
				}

				int r = i + k;
				int c = j + l;
				while (r > 0 && r < 11 && c > 0 && c < 11) {
					int pos = board[r][c];
					if (pos == opp) {
						return true;
					} else if (pos != 0) {
						break;
					}
					r += k;
					c += l;
				}
			}
		}
		return false;
	}

	public void printGameState() {
		for(int i=0;i<board.length;i++) {
			for(int j=0;j<board.length;j++) {
				System.out.print(board[i][j]+" ");	
			}
		System.out.println();
	}
}
}

class Node {
	Board board;
	Node parent;
	List<Node> children;
	List<int[]> moves;
	int visits;
	int score;

	public Node(Board board) {
		this.board = board;
		this.parent = null;
		this.children = new ArrayList<Node>();
		this.moves = new ArrayList<int[]>();
		this.visits = 0;
		this.score = 0;
	}

	public Node addChild(int[] move) {
		Board childBoard = board.copyBoard(board);
		childBoard.move(move);
		Node child = new Node(childBoard);
		child.parent = this;
		child.moves = moves;
		child.moves.add(move);
		children.add(child);
		return child;
	}

	public Node selectChild() {
		Node bestChild = null;
		double bestUCB = Double.NEGATIVE_INFINITY;
		for (Node child : children) {
			double ucb = child.getUCB(visits);
			if (ucb > bestUCB) {
				bestChild = child;
				bestUCB = ucb;
			}
		}
		return bestChild;
	}

	public double getUCB(int parentVisits) {
		// return (double) score / visits + Math.sqrt(2 * Math.log(parentVisits) / visits);
		if (visits == 0) {
			return Double.POSITIVE_INFINITY;
		}
		return (double) score / visits + 2 * Math.sqrt(Math.log(parentVisits) / visits);
	}

	public Node getBestChild() {
		Node bestChild = null;
		double bestScore = Double.NEGATIVE_INFINITY;
		for (Node child : children) {
			double score = child.score / child.visits;
			if (score > bestScore) {
				bestChild = child;
				bestScore = score;
			}
		}
		return bestChild;
	}

	public void update(int score) {
		visits++;
		this.score += score;
	}

	public Node getParent() {
		return parent;
	}

	public List<int[]> getMoves() {
		return moves;
	}

	public int[] getMove() {
		if (moves.size() == 0) {
			return null;
		}
		return moves.get(moves.size() - 1);
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}


}