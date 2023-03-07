
package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	private GameOfAmazon gameOfAmazon;
	
    private String userName = null;
    private String passwd = null;
	public int[][] gameBoard = new int[10][10];
	public int turn = 1;
	public int ME = 0;
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	// HumanPlayer player = new HumanPlayer();
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	 System.out.println("Congratualations!!! "
    	 		+ "I am called because the server indicated that the login is successfully");
    	 System.out.println("The next step is to find a room and join it: "
    	 		+ "the gameClient instance created in my constructor knows how!");
		userName = gameClient.getUserName();
		if (gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
	
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document.
		ArrayList<Integer> boardData = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE);
		switch (messageType) {
			case GameMessage.GAME_STATE_BOARD:
				System.out.println("Received a game state message");
				gamegui.setGameState(boardData);
				gameBoard = convertBoard(boardData);
				gameOfAmazon = new GameOfAmazon(gameBoard);
				GameOfAmazon.gameClient = gameClient;
				GameOfAmazon.gamegui = gamegui;
				break;
			case GameMessage.GAME_ACTION_START:
				System.out.println("Received a game start message");
				String pWhite = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
				String pBlack = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
				if (pWhite.equals(userName)) {
					ME = 1;
				} else {
					ME = 2;
				}
				gameOfAmazon.ME = ME;
				gameOfAmazon.turn = 1;
				if (ME == turn) {
					playTurn(msgDetails);
				}
				break;
			case GameMessage.GAME_ACTION_MOVE:
				System.out.println("Received a move message");
				if (turn == ME) {
					System.out.println("It's my turn");
				} else {
					System.out.println("It's not my turn");
				}
				playTurn(msgDetails);
				break;
			default:
				assert false : "Unknown message type: " + messageType;
				break;
		}
		System.out.println(msgDetails);
    	return true;   	
    }

	public int[][] convertBoard(ArrayList<Integer> boardData) {
		// int boardSize = (int) Math.sqrt(boardData.size());
		// initially we have 121 elements in the arraylist
		// we need to convert it to a 10x10 array by ignoring the first row and first column
		int[][] board = new int[10][10];
		for (int i = 1; i < GameOfAmazon.BOARD_SIZE; i++) {
			for (int j = 1; j < GameOfAmazon.BOARD_SIZE; j++) {
				board[i - 1][j - 1] = boardData.get(i * 11 + j);
				System.out.print(board[i - 1][j - 1] + " ");
			}
			System.out.println();
		}
		return board;
	}

	public void playTurn(Map<String, Object> msgDetails, boolean start) {
		System.out.println("Received a play turn message");
		ArrayList<Integer> queenCurrent = new ArrayList<Integer>();
		ArrayList<Integer> queenNew = new ArrayList<Integer>();
		ArrayList<Integer> arrow = new ArrayList<Integer>();
		if (turn == ME || start) {
			System.out.println("It's my turn");
			// YOUR AI IMPLEMENTATION GOES HERE
			// queenCurrent = YOUR CURRENT QUEEN POSITION [row, col]
			// queenNew = YOUR NEW QUEEN POSITION [row, col]
			// arrow = YOUR ARROW POSITION [row, col]
			queenCurrent.add(rng());
			queenCurrent.add(rng());
			queenNew.add(rng());
			queenNew.add(rng());
			arrow.add(rng());
			arrow.add(rng());
			gameOfAmazon.play();
		} else {
			queenCurrent = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			queenNew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
			arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		}
		// gamegui.updateGameState(msgDetails); // update the game state
		gamegui.updateGameState(queenCurrent, queenNew, arrow); // update the game state
		gameClient.sendMoveMessage(queenNew, queenNew, arrow); // send the move to the server
	}

	public int rng() {
		// generate random number from 1 to 10
		Random rand = new Random();
		return rand.nextInt(10) + 1;
	}
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return  this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
