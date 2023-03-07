package ubc.cosc322;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;

public class GameOfAmazon {
    public int ME = -1;
    public int WHITE = 1;
    public int BLACK = 2;
    public int EMPTY = 0;
    public int[][] board;
    public int turn = 1;
    public int MY_ARROW = 3;
    public static int BOARD_SIZE = 11;
    public ArrayList<Queen> BLACKQUEENS = new ArrayList<>(); // each player has 4 queens on the board
    public ArrayList<Queen> WHITEQUEENS = new ArrayList<>(); // each player has 4 queens on the board
    private BoardState currentBoardState;
    // public Map<BoardState

    public static BaseGameGUI gamegui = null;
    public static GameClient gameClient = null;
    // hashmap of all board states to the current depth
    public HashMap<BoardState, Integer> boardStateMap = new HashMap<>();

    public GameOfAmazon(int[][] board) {
        this();
        this.board = board;
        this.currentBoardState = new BoardState(board, ME);
    }

    public GameOfAmazon() {
        this.board = new int[10][10];
    }

    public BoardAction miniMax() { // returns the best move for the current player from the board state possible moves
        List<BoardAction> moves = currentBoardState.possibleActionsV2();
        BoardAction bestMove = null;
        int bestValue = Integer.MIN_VALUE;
        for (BoardAction move : moves) {
            BoardState newBoardState = currentBoardState.copy();
            newBoardState.makeMove(move);
            int value = minValue(newBoardState);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }

    /**
     * MinValue: returns the minimum value of the possible moves from the current board state
     * @param boardState
     * @return
     */
    public int minValue(BoardState boardState) { // returns the minimum value of the possible moves from the current board state
        if (boardState.isTerminal()) {
            return boardState.utility();
        }
        int value = Integer.MAX_VALUE;
        List<BoardAction> moves = boardState.possibleActionsV2(); // list of possible moves
        for (BoardAction move : moves) { // returns the minimum value of the possible moves from the current board state
            BoardState newBoardState = testMove(move);
            value = Math.min(value, maxValue(newBoardState));   
        }
        return value;
    }

    /**
     * MaxValue: returns the maximum value of the possible moves from the current board state
     * @param newBoardState
     * @return
     */
    private int maxValue(BoardState newBoardState) {
        if (newBoardState.isTerminal()) {
            return newBoardState.utility();
        }
        int value = Integer.MIN_VALUE;
        List<BoardAction> moves = newBoardState.possibleActionsV2();
        for (BoardAction move : moves) {
            BoardState boardState = testMove(move);
            value = Math.max(value, minValue(boardState));
        }
        return value;
    }

    public BoardState testMove(BoardAction move) {
        BoardState newBoardState = currentBoardState.copy(); // copy from current board state
        newBoardState.makeMove(move); // copy from current board state
        return newBoardState;
    }

    public int heuristicEvaluation() {
        // TODO Auto-generated method stub
        // Do we need?
        return 0;
    }

    public void makeMove() {
        // TODO Auto-generated method stub
        currentBoardState.printBoard();

    }

    public void play() {
        // TODO Auto-generated method stub
        BoardAction move = currentBoardState.getBestMove();
        ArrayList<Integer> queenCurrent = move.getQueenCurrent();
        ArrayList<Integer> queenNew = move.getQueenNew();
        ArrayList<Integer> arrow = move.getArrow();
        gameClient.sendMoveMessage(queenNew, queenNew, arrow);
        turn = (turn == WHITE) ? BLACK : WHITE;
        saveState();
        currentBoardState = new BoardState(board, turn);
    }

    public void saveState() {
        // TODO Auto-generated method stub
        boardStateMap.put(currentBoardState, boardStateMap.size());
        System.out.println("Board state saved");
    }
}
