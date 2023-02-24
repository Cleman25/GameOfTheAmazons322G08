package ubc.cosc322;

import java.util.ArrayList;
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
    public ArrayList<Queen> BLACKQUEENS = new ArrayList<>(); // each player has 4 queens on the board
    public ArrayList<Queen> WHITEQUEENS = new ArrayList<>(); // each player has 4 queens on the board
    // public Map<BoardState

    public BaseGameGUI gamegui = null;
    public GameClient gameClient = null;

    public GameOfAmazon(int[][] board, BaseGameGUI gamegui, GameClient gameClient) {
        this(board);
        this.board = board;
        this.gamegui = gamegui;
        this.gameClient = gameClient;
    }

    public GameOfAmazon(int[][] board) {
        this();
        this.board = board;
    }

    public GameOfAmazon() {
        this.board = new int[10][10];
        setQueens();
    }

    public ArrayList<Integer> miniMax() { // returns the best move
        return null;
    }

    public int heuristicEvaluation() { // returns the heuristic value of the current board state
        return 0;
    }

    public void makeMove() {
        // TODO Auto-generated method stub

    }

    public void play() {
        // TODO Auto-generated method stub

    }

    // set initial black and white queen positions
    public void setQueens() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == WHITE) {
                    Queen queen = new Queen(i, j, WHITE);
                    WHITEQUEENS.add(queen);
                } else if (board[i][j] == BLACK) {
                    Queen queen = new Queen(i, j, BLACK);
                    BLACKQUEENS.add(queen);
                }
            }
        }
    }


    
}