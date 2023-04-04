package ubc.cosc322;

import java.util.ArrayList;


public class GameState {
	private int[][] boardState=new int[11][11];
	private int player;
	
	public GameState() {
		setBoardState();	
	}
	public int[][] setBoardState() {
		boardState=new int[][] {
				{0,0,0,0,0,0,0,0,0,0,0}, //0
				{0,0,0,0,1,0,0,1,0,0,0}, //1
				{0,0,0,0,0,0,0,0,0,0,0}, //2
				{0,0,0,0,0,0,0,0,0,0,0}, //3
				{0,1,0,0,0,0,0,0,0,0,1}, //4
				{0,0,0,0,0,0,0,0,0,0,0}, //5
				{0,0,0,0,0,0,0,0,0,0,0}, //6
				{0,2,0,0,0,0,0,0,0,0,2}, //7
				{0,0,0,0,0,0,0,0,0,0,0}, //8
				{0,0,0,0,0,0,0,0,0,0,0}, //9
				{0,0,0,0,2,0,0,2,0,0,0} //11
				
		};
		return boardState;
		
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

