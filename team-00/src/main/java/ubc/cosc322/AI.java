package ubc.cosc322;

import java.util.ArrayList;

public class AI {
	
	GameState game;
	int[][] currentBoard;
	static ActionFactory actionFac;
	public AI(GameState game) {
		this.game=game;
		game.setActionFac();
		actionFac=game.getActionFac();
		currentBoard = game.getBoardState();
		
	}
public int[][] getCurrentBoard() {
		return currentBoard;
	}
public ArrayList<int[]> minimax(int[][] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
	
	int bestScore=0;
	if(depth==0) {
		ArrayList<int[]> eval =new ArrayList<int[]>();
		eval.add(new int[]{evaluation(position)});
		return eval;
	}
	ArrayList<int[]> bestMove = new ArrayList<int[]>();
	if(maximizingPlayer) {
		int maxEval = Integer.MIN_VALUE;
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move);
			int eval = minimax(child,depth-1,alpha,beta,false).get(0)[0];
			if(eval>maxEval)
			{
				maxEval=eval;
				bestScore=maxEval;
				bestMove=move;
			}
			alpha = Math.max(alpha,eval);
			if(beta<=alpha)
				break;
			
		}
	}
	else {
		int minEval = Integer.MAX_VALUE;
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move);
			int eval = minimax(child,depth-1,alpha,beta,true).get(0)[0];
			//minEval = Math.min(minEval,eval);
			if(eval<minEval)
			{
				minEval=eval;
				bestScore=minEval;
				bestMove=move;
			}
			beta = Math.min(beta,eval);
			if(beta<=alpha)
				break;
			
		}
		
	}
	//System.out.println("Position Evaluation: "+bestScore);
	return  bestMove;
	
}

	private int evaluation(int[][] position) {
	// TODO Auto-generated method stub
	return 0;
}
//	private ArrayList<int[][]>children(){
//		ArrayList<int[][]> children = new ArrayList<int[][]>();
//		ArrayList<ArrayList<int[]>> actions = actionFac.actions();
//		for(ArrayList<int[]>action:actions) {
//			int[][] child = makeMove(action);
//			children.add(child);
//			
//		}
//			
//		
//		return children;
//		
//	}
	//Makes 
	private int[][] boardCopy(){
		int n=currentBoard.length;
		int[][] copy = new int[n][n];
		for(int i=0; i<n; i++)
			  for(int j=0; j<n; j++)
			    copy[i][j]=currentBoard[i][j];
		return copy;
	}
	private int[][] makeMove(ArrayList<int[]> action){
		
		int[][] child = boardCopy();
		int[] qOld= action.get(0);
		int[] qNew=action.get(1);
		int[] arrow = action.get(2);
		child[qOld[0]][qOld[1]]=0;
		child[qNew[0]][qNew[1]]=game.getPlayer();
		child[arrow[0]][arrow[1]]=3;
			
		return child;
	}
	
	//evaluation function
	public static int evaluate(int player) {
	    int minDistanceSum = 0;
	    for (int[] myQueens : actionFac.getQueenPosition()) {
	        int minDistance = Integer.MAX_VALUE;
	        for (int[] opponentQueens : actionFac.getOpponentQueenPosition()) {
	            int distance = calculateDistance(myQueens, opponentQueens);
	            if (distance < minDistance) {
	                minDistance = distance;
	            }
	        }
	        minDistanceSum += minDistance;
	    }
	    int opponentMinDistanceSum = 0;
	    for (int[] opponentQueens : actionFac.getOpponentQueenPosition()) {
	        int minDistance = Integer.MAX_VALUE;
	        for (int[] myQueens : actionFac.getQueenPosition()) {
	            int distance = calculateDistance(myQueens, opponentQueens);
	            if (distance < minDistance) {
	                minDistance = distance;
	            }
	        }
	        opponentMinDistanceSum += minDistance;
	    }
	    return minDistanceSum - opponentMinDistanceSum;
	}


	private static int calculateDistance(int[] myQueens, int[] opponentQueens) {
	    int dx = Math.abs(myQueens[0] - opponentQueens[0]);
	    int dy = Math.abs(myQueens[1] - opponentQueens[1]);
	    return Math.max(dx, dy);
	}

}

