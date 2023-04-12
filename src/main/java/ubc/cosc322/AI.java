package ubc.cosc322;

import java.util.ArrayList;

public class AI {
	static ActionFactory actionFac;
	static ActionFactoryV2 actionFacV2;
	public int player=0;
	
	public ArrayList<int[]> getBestMove(int[][] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
		ArrayList<ArrayList<int[]>> result= minimaxV(position,depth,alpha,beta,maximizingPlayer);
		System.out.println(result.get(0).get(0)[0]);
		return result.get(1);
	}

	public AI(int player) {
		this.player=player;
	}
	public ArrayList<ArrayList<int[]>> minimaxV(int[][] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
		
		if(depth==0) {
			ArrayList<ArrayList<int[]>> result=new ArrayList<ArrayList<int[]>>();
			int eval=evaluateK(position);
			ArrayList<int[]> evaluation = new ArrayList<int[]>();
			evaluation.add(new int[]{eval});
			result.add(evaluation);
			return result;
		}
		if(maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			actionFacV2 = new ActionFactoryV2(position,player);
			ArrayList<int[]> bestMove=null;
			for(ArrayList<int[]> move:actionFacV2.actionsV2()) {
				int child[][]=makeMove(move,position);
				int eval = minimaxV(child,depth-1,alpha,beta,false).get(0).get(0)[0];
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
			actionFacV2 = new ActionFactoryV2(position,player);
			ArrayList<int[]> bestMove = null;
			for(ArrayList<int[]> move:actionFacV2.actionsV2()) {
				int child[][]=makeMove(move,position);
				int eval = minimaxV(child,depth-1,alpha,beta,true).get(0).get(0)[0];
				minEval = Math.min(eval,minEval);
				if(eval==minEval) {
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
	public static int evaluateK(int[][] position) {
		actionFacV2 = new ActionFactoryV2(position,1);
		
		ArrayList<int[]> whiteQueenPosition = whiteQueenPosition(position);
		ArrayList<int[]> blackQueenPosition = BlackQueenPosition(position);
	
		//get all possible moves white
		int wpm=0;
		for(int[] queenPos: whiteQueenPosition) {
			ArrayList<int[]> queenMoves = actionFacV2.generateMoves(queenPos[0],queenPos[1]);
			wpm+=queenMoves.size();
		}
		//get all possbile black moves
		int bpm=0;
		for(int[] queenPos: blackQueenPosition) {
			ArrayList<int[]> queenMoves = actionFacV2.generateMoves(queenPos[0],queenPos[1]);
			bpm+=queenMoves.size();
		}
		//Possible moves score
		int pScore=bpm-wpm;
		return pScore;
	}
	public static ArrayList<int[]> whiteQueenPosition(int[][]b){
		ArrayList<int[]> queenPos=new ArrayList<int[]>();
		for(int i=0;i<b.length;i++)
			for(int j=0;j<b.length;j++)
				if(b[i][j]==1)
					queenPos.add(new int[]{i,j} );
					
		return queenPos;
	}
	public static ArrayList<int[]>BlackQueenPosition(int[][]b){
		ArrayList<int[]> queenPos=new ArrayList<int[]>();
		for(int i=0;i<b.length;i++)
			for(int j=0;j<b.length;j++)
				if(b[i][j]==2)
					queenPos.add(new int[]{i,j} );
					
		return queenPos;
	}
}

