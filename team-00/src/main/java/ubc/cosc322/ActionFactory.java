package ubc.cosc322;

import java.util.ArrayList;

public class ActionFactory {
	//rep of game board, java.util.ArrayList<java.lang.Integer> gameS
	
	private int [][] board;
	private int player;
	
	public ActionFactory(int[][] board,int player){
		this.board=board;
		this.player=player;
	}
	
	public ArrayList<int[]> getQueenMoves(int[] queenPos) {
	    /*
	     * Gets all possible moves for the given piece on the board.
	     * 
	     * Args: - board: a 2D array representing the current state of the Queen of Amazons board
	     * - piecePos: an array containing the row and column indices of the piece on the board
	     * 
	     * Returns: a list of arrays containing the row and column indices of all possible moves
	     * for the given piece on the board
	     */
		
	    ArrayList<int[]> queenMoves = new ArrayList<int[]>();
		    int row = queenPos[0];
		    int col = queenPos[1];
	
		    // Check all possible directions
		    int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };
		    for (int[] direction : directions) {
		        int i = row + direction[0];
		        int j = col + direction[1];
		        while (i > 0 && i < 11 && j > 0 && j < 11 && board[i][j] == 0) {
		            queenMoves.add(new int[] {i, j});
		            i += direction[0];
		            j += direction[1];
		        }
		    }

	    // Remove moves that are blocked by other pieces
//	    ArrayList<int[]> validMoves = new ArrayList<int[]>();
//	    for (int[] move : moves) {
//	        if (board[move[0]][move[1]] == 0) {
//	            validMoves.add(move);
//	        }
//	    }
//
	    return queenMoves;
	}

	public ArrayList<int[]> getArrowMoves(int[] queenPos) {
	    /*
	     * Gets all possible arrow moves for the given arrow position on the board.
	     * 
	     * Args: - board: a 2D array representing the current state of the Queen of Amazons board
	     * - arrowPos: an array containing the row and column indices of the arrow on the board
	     * 
	     * Returns: a list of arrays containing the row and column indices of all possible arrow
	     * moves for the given arrow position on the board
	     */

	    ArrayList<int[]> arrowMoves = new ArrayList<int[]>();
	    int row = queenPos[0];
	    int col = queenPos[1];

	    // Check all possible directions
	    int[][] directions = { {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1} };
	    for (int[] direction : directions) {
	        int i = row + direction[0];
	        int j = col + direction[1];
	        while (i >= 0 && i < 10 && j >= 0 && j < 10 && board[i][j] == 0) {
	            arrowMoves.add(new int[] {i, j});
	            i += direction[0];
	            j += direction[1];
	        }
	    }

	    return arrowMoves;
	}

	public ArrayList<int[]> getQueenPosition(){
		ArrayList<int[]> queenPos=new ArrayList<int[]>();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board.length;j++)
				if(board[i][j]==player)
					queenPos.add(new int[]{i,j} );
					
		return queenPos;
	}
	public ArrayList<int[]> getOpponentQueenPosition(){
		ArrayList<int[]> queenPos=new ArrayList<int[]>();
		for(int i=0;i<board.length;i++)
			for(int j=0;j<board.length;j++)
				if(board[i][j]==(player==1? 2:1))
					queenPos.add(new int[]{i,j} );
					
		return queenPos;
	}
	public ArrayList<ArrayList<int[]>> actions(){
		ArrayList<ArrayList<int[]>> actions = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> queenPos=getQueenPosition();
		for(int[] pos:queenPos) {
			ArrayList<int[]> action = new ArrayList<int[]>();
			int[] queenCurrent = pos;
			action.add(queenCurrent);
			ArrayList<int[]> queenMoves = getQueenMoves(pos);
			
			for(int[] qMove:queenMoves) {
				int[] queenNew=qMove;
				action.add(queenNew);
				ArrayList<int[]> arrowMoves = getArrowMoves(queenNew);
				
				for(int[] aMove:arrowMoves) {
					int[] arrowMove = aMove;
					action.add(arrowMove);
					actions.add(action);
				}
			
			}
			
		}
		
		return actions;
	}
	
	
}
