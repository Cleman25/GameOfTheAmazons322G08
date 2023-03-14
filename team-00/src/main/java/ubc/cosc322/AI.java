package ubc.cosc322;

import java.util.ArrayList;

public class AI {
	static int player;
	static ActionFactory actionFac;
	
	public AI(int player) {
		AI.player = player;
		
	}

public ArrayList<ArrayList<int[]>> minimax(int[][] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
	
	if(depth==0) {
		ArrayList<ArrayList<int[]>> result=new ArrayList<ArrayList<int[]>>();
		int eval=evaluate(position);
		ArrayList<int[]> evaluation = new ArrayList<int[]>();
		evaluation.add(new int[]{eval});
		result.add(evaluation);
		return result;
	}
	if(maximizingPlayer) {
		int maxEval = Integer.MIN_VALUE;
		actionFac = new ActionFactory(position,player);
		ArrayList<int[]> bestMove=null;
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move,position);
			int eval = minimax(child,depth-1,alpha,beta,false).get(0).get(0)[0];
			maxEval = Math.max(maxEval, eval);
			if(maxEval==eval)
			{
				bestMove=move;
			}
			alpha = Math.max(alpha,eval);
			if(beta<=alpha)
				break;
			
		}
		ArrayList<ArrayList<int[]>> result=new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> evaluation = new ArrayList<int[]>();
		evaluation.add(new int[]{maxEval});
		result.add(evaluation);
		result.add(bestMove);
		return result ;
	}
	else {
		int minEval = Integer.MAX_VALUE;
		actionFac = new ActionFactory(position,player);
		ArrayList<int[]> bestMove = null;
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move,position);
			int eval = minimax(child,depth-1,alpha,beta,true).get(0).get(0)[0];
			minEval = Math.min(eval,minEval);
			if(eval==minEval)
			{
				bestMove=move;
			}
			beta = Math.min(beta,eval);
			if(beta<=alpha)
				break;
			
		}
		ArrayList<ArrayList<int[]>> result=new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> evaluation = new ArrayList<int[]>();
		evaluation.add(new int[]{minEval});
		result.add(evaluation);
		result.add(bestMove);
		return result;
	}
	
}
 

	private int[][] boardCopy(int[][] currentBoard){
		int n=currentBoard.length;
		int[][] copy = new int[n][n];
		for(int i=0; i<n; i++)
			  for(int j=0; j<n; j++)
			    copy[i][j]=currentBoard[i][j];
		return copy;
	}
	private int[][] makeMove(ArrayList<int[]> action,int[][] currentPosition){
		
		int[][] child = boardCopy(currentPosition);
		int[] qOld= action.get(0);
		int[] qNew=action.get(1);
		int[] arrow = action.get(2);
		child[qOld[0]][qOld[1]]=0;
		child[qNew[0]][qNew[1]]=player;
		child[arrow[0]][arrow[1]]=3;
			
		return child;
	}
	
	//evaluation function
	public static int evaluate(int[][] position) {
		return 0;
	}


	public class Node{
		private int eval;
		private int[][] position;
		
		public Node(int eval,int[][] position) {
			
			this.eval=eval;
			this.position=position;
			
			
		}

	}
}

