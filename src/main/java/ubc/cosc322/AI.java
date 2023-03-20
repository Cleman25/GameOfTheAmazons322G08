package ubc.cosc322;

import java.util.ArrayList;

public class AI {
	static int player;
	static ActionFactory actionFac;
	private ArrayList<int[]> bestMove=null;
	
	public ArrayList<int[]> getBestMove() {
	    return bestMove;
	}
	
	public AI(int player) {
		AI.player = player;
		
	}

public int minimax(int[][] position, int depth, int alpha, int beta, boolean maximizingPlayer) {
	
	if(depth==0) {
		return evaluate(position);
	}
	if(maximizingPlayer) {
		int maxEval = Integer.MIN_VALUE;
		bestMove=null;
		actionFac = new ActionFactory(position,player);
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move,position);
			int eval = minimax(child,depth-1,alpha,beta,false);
			if(eval>maxEval)
			{
				maxEval=eval;
				bestMove=move;
			}
			
			alpha = Math.max(alpha,eval);
			if(beta<=alpha)
				break;
		}
		return maxEval;
	}
	else {
		int minEval = Integer.MAX_VALUE;
		actionFac = new ActionFactory(position,player);
		bestMove=null;
		for(ArrayList<int[]> move:actionFac.actions()) {
			int child[][]=makeMove(move,position);
			int eval = minimax(child,depth-1,alpha,beta,true);
		
			if(eval<minEval) {
				minEval=eval;
				this.bestMove=move;
			}
			beta = Math.min(beta,eval);
			if(beta<=alpha)
				break;
			
		}
		return minEval;
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
	public static int evaluateN(int[][] position) {
		actionFac = new ActionFactory(position,player);
		// int territory[][]= new int[11][11];
		
		ArrayList<int[]> whiteQueenPosition = queenPosition(position, 1);
		ArrayList<int[]> blackQueenPosition = queenPosition(position, 2);
	
		//get all possible moves white
		int wpm=0;
		for(int[] queenPos: whiteQueenPosition) {
			ArrayList<int[]> queenMoves = actionFac.getQueenMoves(queenPos);
			wpm+=queenMoves.size();
		}
		//get all possbile black moves
		int bpm=0;
		for(int[] queenPos: blackQueenPosition) {
			ArrayList<int[]> queenMoves = actionFac.getQueenMoves(queenPos);
			bpm+=queenMoves.size();
		}
		
		//Possible moves score
		int pScore=wpm-bpm;
		
		//Calculate territory score
		return pScore;
	}

	public static ArrayList<int[]> queenPosition(int[][] q, int player){
		ArrayList<int[]> queenPos=new ArrayList<int[]>();
		for(int i=0;i<q.length;i++)
			for(int j=0;j<q.length;j++)
				if(q[i][j]==player)
					queenPos.add(new int[]{i,j} );
					
		return queenPos;
	}

	/**
	 * This method evaluates the current board state and returns a score for it. The score is based on the number of arrows
	 * @param currentBoard
	 * @return
	 */
		public static int evaluate(int[][]currentBoard) {
			//default all distances to 100 so they are too big if there is no queen found 
			int xAxisDistancetoBQ = 100; // x axis distance to black queen
			int yAxisDistancetoBQ = 100; // y axis distance to black queen
			int seDistancetoBQ = 100; // south east distance to black queen
			int nwDistancetoBQ = 100; // north west distance to black queen
			int neDistancetoBQ = 100; // north east distance to black queen
			int swDistancetoBQ = 100; // south west distance to black queen
				
			int xAxisDistancetoWQ = 100;
			int yAxisDistancetoWQ = 100;
			int seDistancetoWQ = 100;
			int nwDistancetoWQ = 100;
			int neDistancetoWQ = 100;
			int swDistancetoWQ = 100;

			int xAxisDistancetoA = 100;
			int yAxisDistancetoA = 100;
				
			int blackTerritory = 0; //overall territory belonging to black 
			int whiteTerritory = 0; //overall territory belonging to white
			int neutTerritory = 0; //space no-one owns
			int winningby = 0; //how much more territory are they winning by
			int arrowNum = 0; //count how much arrows have been put down
				
			int[][] territoryBoard = new int[11][11]; //set up new version of board to show territory

			//hard code board state array that is being evaluated
				
			//need to check each coordinate on the board for how close they are to either player's queen. 
			//using x and y as variables to make coordinates easier to grasp
			for (int x=0; x<currentBoard.length; x++) {
					
				for (int y=0; y<currentBoard.length; y++) {
					//check to see whose queen is closest to this coordinate along x and y axis
					//first two for loops find the coordinates of the space being checked
					int control = 0; //neither white and black queens are in control as default
					
					if (currentBoard[x][y] == 2 || currentBoard[x][y] == 1 || currentBoard[x][y] == 3) {
						//if the space is a queen or arrow, don't evaluate its territory (its not a spcae you can move to, so it doesnt count)
						territoryBoard[x][y] = currentBoard[x][y];
						//it is just equal to whatever it is on the regular board.
						if (currentBoard[x][y] == 3) {
							arrowNum += 1;//add 1 to arrow counter
						}
					}
					else {
						for (int i=0; i<currentBoard.length; i++) { //FIND distance to Q in X-AXIS
							//check to see along x axis
							if (currentBoard[i][y] == 2) {  //if its a BLACK queen
								//count how close it is
								int newXDistancetoBQ = Math.abs(i-x); //positive form of BQ's x minus the x of the position checked

								if (newXDistancetoBQ < xAxisDistancetoBQ) { //only keep track of queens a shorter distance from the space than previously found
									if (i < x) {
										xAxisDistancetoBQ = newXDistancetoBQ; //if the newest distance is shorter, use that as new closest queen
									}
									else if (i > x) {
										xAxisDistancetoBQ = newXDistancetoBQ; //if the newest distance is shorter, use that as new closest queen
										i = currentBoard.length; //if queen is to the right of the space, stop looking.
									}
								}
							}
							else if (currentBoard[i][y] == 1) {   //if its a WHITE queen
								//count how close it is
								int newXDistancetoWQ = Math.abs(i-x); //positive form of WQ's x minus the x of the position checked

								if (newXDistancetoWQ < xAxisDistancetoWQ) { //only keep track of queens a shorter distance from the space than previously found
									if (i < x) {
										xAxisDistancetoWQ = newXDistancetoWQ; //if the newest distance is shorter, use that as new closest queen
									}
									else if (i > x) {
										xAxisDistancetoWQ = newXDistancetoWQ; //if the newest distance is shorter, use that as new closest queen
										i = currentBoard.length; //if queen is to the right of the space, stop looking.
									}
								}
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[i][y] == 3) {   //if its an ARROW
								//count how close it is
								int newXDistancetoA = Math.abs(i-x); //positive form of Arrow's x minus the x of the position checked

								if (newXDistancetoA < xAxisDistancetoA) { //only keep track of arrows a shorter distance from the space than previously found
									if (i < x) {//if arrow before space, keep looking.
										if (newXDistancetoA < xAxisDistancetoBQ) { //if arrow is closer than a queen we found, reset the queen distance
											xAxisDistancetoBQ = 100; //reset BQ distance to 0, since that queen found previously can't move thru arrows
										}
										else if (newXDistancetoA < xAxisDistancetoWQ) { //if arrow is closer than a queen we found, reset the queen distance
											xAxisDistancetoWQ = 100; //reset BQ distance to 0, since that queen found previously can't move thru arrows
										}
										xAxisDistancetoA = newXDistancetoA; //if the newest distance is shorter, use that as new closest queen
									}
									else if (i > x) {
										xAxisDistancetoA = newXDistancetoA; //if the newest distance is shorter, use that as new closest queen
										i = currentBoard.length; //if arrow is to the right of the space, stop looking.
									}
								}
							}
						}
							
						for (int j=0; j<currentBoard.length; j++) { //FIND distance to Q in Y-AXIS
							//check to see along y axis
							if (currentBoard[x][j] == 2) {  //if its a BLACK queen
								//count how close it is
								int newYDistancetoBQ = Math.abs(j-y); //positive form of BQ's y minus the y of the position checked

								if (newYDistancetoBQ < yAxisDistancetoBQ) { //only keep track of queens a shorter distance from the space than previously found
									if (j < y) {//if queen is above the space, keep looking
										yAxisDistancetoBQ = newYDistancetoBQ; //if the newest distance is shorter, use that as new closest queen
									}
									else if (j > y) {
										yAxisDistancetoBQ = newYDistancetoBQ; //if the newest distance is shorter, use that as new closest queen
										j = currentBoard.length; //if queen is under the space, stop looking.
									}
								} //if the queen found is further away, do nothing
							}
							else if (currentBoard[x][j] == 1) {   //if its a WHITE queen
								//count how close it is
								int newYDistancetoWQ = Math.abs(j-y); //positive form of WQ's y minus the y of the position checked

								if (newYDistancetoWQ < yAxisDistancetoWQ) { //only keep track of queens a shorter distance from the space than previously found
									if (j < y) {//if queen is above the space, keep looking
										yAxisDistancetoWQ = newYDistancetoWQ; //if the newest distance is shorter, use that as new closest queen
									}
									else if (j > y) {
										yAxisDistancetoWQ = newYDistancetoWQ; //if the newest distance is shorter, use that as new closest queen
										j = currentBoard.length; //if queen is under the space, stop looking.
									}
								} //if the queen found is further away, do nothing
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[x][j] == 3) {   //if its an ARROW
								int newYDistancetoA = Math.abs(j-y); //positive form of Arrow's x minus the x of the position checked

								if (newYDistancetoA < yAxisDistancetoA) { //only keep track of arrows a shorter distance from the space than previously found
									if (j < y) {//if arrow before space, keep looking.
										if (newYDistancetoA < yAxisDistancetoBQ) { //if arrow is closer than a queen we found, reset the queen distance
											yAxisDistancetoBQ = 100; //reset BQ distance to 0, since that queen found previously can't move thru arrows
										}
										else if (newYDistancetoA < yAxisDistancetoWQ) { //if arrow is closer than a queen we found, reset the queen distance
											yAxisDistancetoWQ = 100; //reset BQ distance to 0, since that queen found previously can't move thru arrows
										}
										yAxisDistancetoA = newYDistancetoA;//update closest arrow position
									}
									else if (j > y) {
										yAxisDistancetoA = newYDistancetoA; //if the newest distance is shorter, use that as new closest queen
										j = currentBoard.length; //if arrow is below the space, stop looking.
									}
								} //if arrow found further away, ignore it
							}
						}
							
						for (int increase=1; (x+increase)<currentBoard.length && (y+increase)<currentBoard.length; increase++) { 
							//FIND Q DIAGONALLY   SOUTH EAST
							//
							//--> x+inc && y+inc must be valid coords (less than the board length in this case)
							int newx = x+increase;
							int newy = y+increase;
							if (currentBoard[newx][newy] == 2) {  //if its a BLACK queen
								//count how close it is
								seDistancetoBQ = increase; //The increase == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								increase=currentBoard.length;
							}
							else if (currentBoard[newx][newy] == 1) {   //if its a WHITE queen
								//count how close it is
								seDistancetoWQ = increase; //The increase == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								increase=currentBoard.length;
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[newx][newy] == 3) {   //if its an ARROW
								//stop checking rest of x once arrow is found
								increase=currentBoard.length;
							}
						}
							
						for (int decrease=1; (x-decrease)>0 && (y-decrease)>0; decrease++) { 
							//FIND Q DIAGONALLY    NORTH WEST
							//
							//--> x-dec && y-dec must be valid coords (positive in this case)
							int newx = x-decrease;
							int newy = y-decrease;
							if (currentBoard[newx][newy] == 2) {  //if its a BLACK queen
								//count how close it is
								nwDistancetoBQ = decrease; //The decrease == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								decrease=currentBoard.length;
							}
							else if (currentBoard[newx][newy] == 1) {   //if its a WHITE queen
								//count how close it is
								nwDistancetoWQ = decrease; //The decrease == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								decrease=currentBoard.length;
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[newx][newy] == 3) {   //if its an ARROW
								//stop checking rest of x once arrow is found
								decrease=currentBoard.length;
							}
						}
							
						for (int incX=1; (x+incX)<currentBoard.length && (y-incX)>0; incX++) { 
							//FIND Q DIAGONALLY    NORTH EAST
							//
							//--> x+incX && y-incX must be valid coords (x < board length, and pos y in this case)
							int newx = x+incX;
							int newy = y-incX;
							if (currentBoard[newx][newy] == 2) {  //if its a BLACK queen
								//count how close it is
								neDistancetoBQ = incX; //The incX == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								incX=currentBoard.length;
							}
							else if (currentBoard[newx][newy] == 1) {   //if its a WHITE queen
								//count how close it is
								neDistancetoWQ = incX; //The incX == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								incX=currentBoard.length;
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[newx][newy] == 3) {   //if its an ARROW
								//stop checking rest of x once arrow is found
								incX=currentBoard.length;
							}
						}
							
						for (int incY=1; (y+incY)<currentBoard.length && (x-incY)>0; incY++) { 
							//FIND Q DIAGONALLY    SOUTH WEST
							//
							//--> y+incY && x-incY must be valid coords (y < board length, and pos x in this case)
							int newx = x-incY;
							int newy = y+incY;
							if (currentBoard[newx][newy] == 2) {  //if its a BLACK queen
								//count how close it is
								swDistancetoBQ = incY; //The incY == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								incY=currentBoard.length;
							}
							else if (currentBoard[newx][newy] == 1) {   //if its a WHITE queen
								//count how close it is
								swDistancetoWQ = incY; //The incY == how many moves the queen is from the space
								//stop checking rest of the diagonal spaces once a queen is found
								incY=currentBoard.length;
							}
							//if it hits an arrow before a queen, end there -- no queen can move through it to claim the spot on this axis
							else if (currentBoard[newx][newy] == 3) {   //if its an ARROW
								//stop checking rest of x once arrow is found
								incY=currentBoard.length;
							}
						}
							
						//compare all distances to find who is control of this space (the closest queen)
						int[] closestBQ = calcClosestQueen(x,y, xAxisDistancetoBQ, yAxisDistancetoBQ, seDistancetoBQ, nwDistancetoBQ, neDistancetoBQ, swDistancetoBQ);
						int[] closestWQ = calcClosestQueen(x,y, xAxisDistancetoWQ, yAxisDistancetoWQ, seDistancetoWQ, nwDistancetoWQ, neDistancetoWQ, swDistancetoWQ);
						
						// System.out.print("For space: " +x+","+y+ ", Closest BQ is: " +closestBQ[0] + " and WQ is: " +closestWQ[0] + "   \n");
						int closestOverall = Math.min(closestBQ[0], closestWQ[0]); //get shortest value overall
							
						if (closestBQ[0] == closestWQ[0]) { //if the space is an equal distance from either team
							control = 0;   //this space is not controlled by either player.
							//leave playerTerritory alone (neither gain or lose)
							neutTerritory += 1; //add a space to neutral territory
								
							territoryBoard[x][y] = control; //update new board to reflect who owns it
						}	
						else if (closestOverall == closestBQ[0]) {
							control = 8; //this space is controlled by Black (8 represents even territory)
							blackTerritory += 1; //add a space to overall territory

							//System.out.print(closestBQ[0] +"B: "+ closestBQ[1] +", "+ closestBQ[2] + "   \n"); //prints the closest queen for testing
								
							territoryBoard[x][y] = control; //update new board to reflect who owns it
						}
						else if (closestOverall == closestWQ[0]) {
							control = 7; //this space is controlled by White (7 represents odd territory)
							whiteTerritory += 1; //add a space to overall territory
							
							//System.out.print(closestWQ[0] +"W: "+ closestWQ[1] +", "+ closestWQ[2] + "    \n"); //prints the closest queen for testing

							territoryBoard[x][y] = control; //update new board to reflect who owns it
						}
					}
					xAxisDistancetoBQ = 100;
					yAxisDistancetoBQ = 100;
					seDistancetoBQ = 100;
					nwDistancetoBQ = 100;
					neDistancetoBQ = 100;
					swDistancetoBQ = 100;
						
					xAxisDistancetoWQ = 100;
					yAxisDistancetoWQ = 100;
					seDistancetoWQ = 100;
					nwDistancetoWQ = 100;
					neDistancetoWQ = 100;
					swDistancetoWQ = 100;

					xAxisDistancetoA = 100;
					yAxisDistancetoA = 100;  //reset distances after EVERY SPACE 
						
				}//ends y for loop
					
			}//ends x for loop
				
		//	        int mostTerr = Math.max(blackTerritory, whiteTerritory); //get team with most territory
		//	        int leastTerr = Math.min(blackTerritory, whiteTerritory); //get team with least territory
		//	            
		//	        winningby = mostTerr - leastTerr; //who is winning?
		//
		//	        String winningTeam = "No-one";//default no one is winning
		//	        if (mostTerr == blackTerritory){ 
		//	            winningTeam = "Black"; //if black is winning
		//	        }
		//	        else if (mostTerr == whiteTerritory){ 
		//	            winningTeam = "White"; //if white is winning
		//	        }
			return whiteTerritory-blackTerritory;
			//print final territory board for testing
		//	        printTerritoryState(territoryBoard);
		//	        System.out.print("\n");//print to new line after printing whole territory board
		//	        System.out.print(winningTeam);
		//	        System.out.println(" is winning currently. They are winning with " + winningby + " more spaces. "+mostTerr+" to "+leastTerr+ ". There are " + arrowNum + " arrows, and " + neutTerritory + " neutral spaces. ");



		}//end main

		private static ArrayList<int[]> optimizedEvaluate(int[][] currentBoard) {
			int control = 0; //default to no control
			int[] yAxisToBQ = new int[]{100,11,11};
			int[] xAxisToBQ = new int[]{100,11,11};
			// positive diag is SE, negative is NW, positive diag is NE, negative is SW
			int[] diagToBQ = new int[]{100, 100, -100, -100};

			int[] yAxisToWQ = new int[]{100,11,11};
			int[] xAxisToWQ = new int[]{100,11,11};
			int[] diagToWQ = new int[]{100,100,100,100};

			int[] yAxisToA = new int[]{100,11,11};
			int[] xAxisToA = new int[]{100,11,11};
			
			int blackTerritory = 0;
			int whiteTerritory = 0;
			int neutTerritory = 0;
			int winningby = 0;
			int arrowNum = 0;
			int[][] newTerritoryBoard = new int[11][11];
			return null;
		}

	    private static int[] calcClosestQueen(int x,int y,int xAxisDistance, int yAxisDistance, int seDistance, int nwDistance, int neDistance, int swDistance) {
	        int closestQ = 110;
	        int xCoord = 11;
	        int yCoord = 11;
	        //compare every distance to each other (start with xaxis)
	        if (xAxisDistance <= yAxisDistance && xAxisDistance <= seDistance && xAxisDistance <= nwDistance && xAxisDistance <= neDistance && xAxisDistance <= swDistance) {
	            closestQ = xAxisDistance; //if xAxis is the closest Q, set it to be that and note its coords
	            xCoord = x + xAxisDistance;
	            yCoord = y;
	        }
	        else if (yAxisDistance <= xAxisDistance && yAxisDistance <= seDistance && yAxisDistance <= nwDistance && yAxisDistance <= neDistance && yAxisDistance <= swDistance) {
	            closestQ = yAxisDistance;
	            xCoord = x;
	            yCoord = y + yAxisDistance;
	        }
	        else if (seDistance <= xAxisDistance && seDistance <= yAxisDistance && seDistance <= nwDistance && seDistance <= neDistance && seDistance <= swDistance) {
	            closestQ = seDistance;
	            xCoord = x + seDistance;
	            yCoord = y + seDistance;
	        }
	        else if (nwDistance <= xAxisDistance && nwDistance <= yAxisDistance && nwDistance <= seDistance && nwDistance <= neDistance && nwDistance <= swDistance) {
	            closestQ = nwDistance;
	            xCoord = x - nwDistance;
	            yCoord = y - nwDistance;
	        }
	        else if (neDistance <= xAxisDistance && neDistance <= yAxisDistance && neDistance <= seDistance && neDistance <= nwDistance && neDistance <= swDistance) {
	            closestQ = neDistance;
	            xCoord = x + neDistance;
	            yCoord = y - neDistance;
	        }
	        else if (swDistance <= xAxisDistance && swDistance <= yAxisDistance && swDistance <= seDistance && swDistance <= nwDistance && swDistance <= neDistance) {
	            closestQ = swDistance;
	            xCoord = x - swDistance;
	            yCoord = y + swDistance;
	        } 
	        int[] closestQueen = {closestQ, xCoord, yCoord}; //for testing. explains where the space is getting its assigment from
	        //the x and y seem to be swapped for some reason tho?
	        return closestQueen;
	    }
	        
//	    public static void printTerritoryState(int[][] territoryBoard) {
//	        System.out.print("\n");
//	        for(int i=0; i<territoryBoard.length; i++) {
//	            for(int j=0; j<territoryBoard.length; j++) {
//	                System.out.print(territoryBoard[i][j]+" ");	
//	            }
//	            System.out.println();
//	        }
//	 }

}

