package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;

public class GameState {
	private ArrayList<Integer> gameState;
	private int[][] boardState=new int[11][11];
	private int player;
	private int turn;
	private ActionFactory actionFac;
	public GameState(ArrayList<Integer> gameState) {
		this.gameState=gameState;
		setBoardState();	
	}
	public int[][] setBoardState() {
		int count=0;
		for(int i=0;i<boardState.length;i++)
			for(int j=0;j<boardState.length;j++) {
				boardState[i][j]=gameState.get(count);
				count++;
			}
		
		return boardState;
		
	}
	public ActionFactory getActionFac() {
		return actionFac;
	}
	public void setActionFac() {
		this.actionFac = new ActionFactory(getBoardState(),getPlayer());
	}
	public int[][] getBoardState(){
		return boardState;
	}
	
	public void setPlayer(int player) {
		this.player=player;
	}
	public int getPlayer() {
		return player;
	}
	public void setTurn(int turn) {
		this.turn=turn;
	}
	public int getTurn() {
		return turn;
	}
	public void updateBoardState(ArrayList<Integer>qOld,ArrayList<Integer>qNew,ArrayList<Integer>arrow) {
		if(	boardState[qOld.get(0)][qOld.get(1)]==1) {
			boardState[qOld.get(0)][qOld.get(1)]=0;
			boardState[qNew.get(0)][qNew.get(1)]=1;
			boardState[arrow.get(0)][arrow.get(1)]=3;}
		else {
			boardState[qOld.get(0)][qOld.get(1)]=0;
			boardState[qNew.get(0)][qNew.get(1)]=2;
			boardState[arrow.get(0)][arrow.get(1)]=3;			
		}
		
	}
	public void printGameState() {
			for(int i=0;i<boardState.length;i++) {
				for(int j=0;j<boardState.length;j++) {
					System.out.print(boardState[i][j]+" ");	
				}
			System.out.println();
		}
	}
}

