package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
       
    private GameState game;
    private AI ai;
    public static int count;
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	
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
    	this.gamegui = new BaseGameGUI(this);
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    	gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

    @SuppressWarnings("unchecked")
	@Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
        //This method will be called by the GameClient when it receives a game-related message
        //from the server.

        //For a detailed description of the message types and format,
        //see the method GamePlayer.handleGameMessage() in the game-client-api document.
        switch(messageType) {
        case GameMessage.GAME_STATE_BOARD:
	        this.getGameGUI().setGameState((ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.GAME_STATE));
	        game = new GameState((ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.GAME_STATE));
	        game.printGameState();

	        break;
		case GameMessage.GAME_ACTION_START:
			System.out.println("The game has started!");
			String playerWhite = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
			String playerBlack = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
	/*made 2 white because the board counts row from the bottom when you send the moves. I have not figured out how to reconcile it yet*/
			if (playerWhite.equals(userName)) {
				game.setPlayer(1);
				System.out.println(playerWhite+" is playing as White");
				
			} else {
				game.setPlayer(2);
				System.out.println(playerBlack+ " is playing as Black");
				play();
			}
			break;
       
        case GameMessage.GAME_ACTION_MOVE:
        	System.out.println("Received a move message");
			play(msgDetails);
	    default:
	        	assert(false) :"Unknown message type: "+messageType;
	        	break;
        }
        return true;  	
    }
    
    public void play() {
    	//AI implementation
		//gameClient.sendMoveMessage(queenNew, queenNew, arrow); // send the move to the server
    	ai = new AI(game.getPlayer());
    	ArrayList<int[]> bestMove=ai.getBestMove(game.getBoardState(),2,Integer.MIN_VALUE,Integer.MAX_VALUE,game.getPlayer()==2);
		ArrayList<Integer> queenCurrent = new ArrayList<Integer>();
		ArrayList<Integer> queenNew = new ArrayList<Integer>();
		ArrayList<Integer> arrow = new ArrayList<Integer>();
		queenCurrent.add(bestMove.get(0)[0]);
		queenCurrent.add(bestMove.get(0)[1]);
		queenNew.add(bestMove.get(1)[0]);
		queenNew.add(bestMove.get(1)[1]);
		arrow.add(bestMove.get(2)[0]);
		arrow.add(bestMove.get(2)[1]);
		game.updateBoardState(queenCurrent, queenNew, arrow);
		System.out.println("Made a move");
		System.out.println(queenCurrent+" "+queenNew+" "+arrow);
		game.printGameState();
		gameClient.sendMoveMessage(queenCurrent, queenNew, arrow);
		gamegui.updateGameState(queenCurrent, queenNew, arrow); // update the game state
//		System.out.println("Made a move");
//		System.out.println(queenCurrent+" "+queenNew+" "+arrow);
		
		System.out.println("Moves made "+(++count));
    	
    }
    
    
    public void play(Map<String, Object> msgDetails){
    	//System.out.println("Received a move message");
		ArrayList<Integer> queenCurrent = new ArrayList<Integer>();
		ArrayList<Integer> queenNew = new ArrayList<Integer>();
		ArrayList<Integer> arrow = new ArrayList<Integer>();
		queenCurrent = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
		queenNew = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
		arrow = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
		gamegui.updateGameState(queenCurrent,queenNew,arrow);
		game.updateBoardState(queenCurrent,queenNew,arrow);
		System.out.println(queenCurrent+" "+queenNew+" "+arrow);
		game.printGameState();
		play();
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
		return this.gamegui;
		 
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}
	
 
}//end of class
