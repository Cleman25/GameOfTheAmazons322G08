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
		    
	    return queenMoves;
	}

	public ArrayList<int[]> getArrowMoves(int[] queenPos,int[][]tempBoard) {
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
	        while (i > 0 && i < 11 && j > 0 && j < 11 && tempBoard[i][j] == 0) {
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
		//System.out.print(queenPos+":");
		for(int[] pos:queenPos) {
			ArrayList<int[]> action = new ArrayList<int[]>();
			ArrayList<int[]> queenMoves = getQueenMoves(pos);
			//System.out.print(queenMoves+":");
			for(int[] qMove:queenMoves) {
				int[][] boardTemp = boardCopy(board);
				ArrayList<int[]> move = new ArrayList<int[]>();
				move.add(pos);
				move.add(qMove);
				boardTemp=makeMove(move,boardTemp);
				ArrayList<int[]> arrowMoves = getArrowMoves(qMove,boardTemp);
				//System.out.println(queenPos.size()+": "+queenMoves.size()+": "+arrowMoves.size());
				//System.out.println(arrowMoves);
				for(int[] aMove:arrowMoves) {
					int[] arrowMove = aMove;
					action.add(pos);
					action.add(qMove);
					action.add(arrowMove);
					actions.add(action);
					//System.out.println("["+pos[0]+","+pos[1]+"]"+","+"["+qMove[0]+","+qMove[1]+"]"+","+"["+aMove[0]+","+aMove[1]+"]");
				}
			
			}
			
		}
		// System.out.println(actions.size());
		return actions;
	}
	
	public String toString(ArrayList<int[]> action) {
		
		int[] qOld= action.get(0);
		int[] qNew=action.get(1);
		int[] arrow = action.get(2);
		
		String s ="";
		s=s+"["+qOld[0]+","+qOld[1]+"]"+","+"["+qNew[0]+","+qNew[1]+"]"+","+"["+arrow[0]+","+arrow[1]+"]";
		return s;
			
	}

	private int[][] boardCopy(int[][] currentBoard){
		int n=currentBoard.length;
		int[][] copy = new int[n][n];
		for(int i=0; i<n; i++)
			  for(int j=0; j<n; j++)
			    copy[i][j]=currentBoard[i][j];
		return copy;
	}
	private int[][] makeMove(ArrayList<int[]> move,int[][] currentPosition){
		
		int[][] child = boardCopy(currentPosition);
		int[] qOld= move.get(0);
		int[] qNew=move.get(1);
		child[qOld[0]][qOld[1]]=0;
		child[qNew[0]][qNew[1]]=player;
			
		return child;
	}
	
}
