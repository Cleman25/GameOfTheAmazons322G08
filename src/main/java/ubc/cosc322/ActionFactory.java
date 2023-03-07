package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class ActionFactory {

    public List<BoardAction> generateActions(BoardState state) {
        if (state == null) {
            return new ArrayList<>();
        }
        ArrayList<BoardAction> actions = new ArrayList<>();
        int player = state.turn == 1 ? BoardState.WHITE : BoardState.BLACK;
        for (int i = 1; i < GameOfAmazon.BOARD_SIZE; i++) {
            for (int j = 1; j < GameOfAmazon.BOARD_SIZE; j++) { // for each position on the board
                // if the position is empty, we can move there and possibly shoot an arrow from there
                // each player has 4 queens on the board
                // each queen can move to any adjacent position and can shoot an arrow to any adjacent position
                // you can only move one queen at a time
                if (state.getBoard()[i][j] == BoardState.EMPTY) { // if the position is empty, we can move there and shoot an arrow
                    // for each queen, find the queen that's closest to that position, check if the queen can move to that position
                    // if the queen can move to that position, add the action to the list of actions
                    // if the queen can shoot an arrow to that position, add the action to the list of actions
                    // if the queen can't move to that position, check if another queen can move to that position
                    // if no queen can move to that position, don't add the action to the list of actions
                    ArrayList<Queen> queens = player == BoardState.WHITE ? state.WHITEQUEENS : state.BLACKQUEENS;
                    for (Queen queen : queens) {
                        if (queen.canMove( i, j)) {
                            int shootDistance = queen.distanceTo(i, j); // the distance from the queen to the position
                            if (queen.canShootArrow(i, j, shootDistance, state.getBoard())) { // if the queen can shoot an arrow to that position without jumping over another queen
                                int[] arrowPosition = new int[] {i, j}; // the position of the arrow
                                BoardAction action = new BoardAction(queen.getOldPos(), new int[] {i, j}, arrowPosition, state.turn); // the action of moving the queen to the position and shooting an arrow to the position
                                actions.add(action); // add the action to the list of actions
                            } else {
                                actions.add(new BoardAction(new int[] {i, j}, state.turn)); // add the action to the list of actions
                            }
                        }
                    }
                }
            }
        }
        return actions;
    }

    public List<BoardAction> generateActionsV2(BoardState state) {
        if (state == null) {
            return new ArrayList<>();
        }
        List<BoardAction> actions = new ArrayList<>();
        int player = state.turn == 1 ? BoardState.WHITE : BoardState.BLACK;
        ArrayList<Queen> queens = player == BoardState.WHITE ? state.WHITEQUEENS : state.BLACKQUEENS;
    
        for (Queen queen : queens) {
            int[] queenPos = queen.getPos();
    
            // generate moves for the queen
            ArrayList<int[]> moves = state.getEmptyAdjacentPositions(queenPos[0], queenPos[1]);
            for (int[] move : moves) {
                // check if the move is valid
                if (queen.canMove(move[0], move[1])) {
                    // generate shots for the queen
                    ArrayList<int[]> shots = state.getEmptyAdjacentPositions(move[0], move[1]);
                    for (int[] shot : shots) {
                        int distance = queen.distanceTo(shot[0], shot[1]);
                        if (queen.canShootArrow(shot[0], shot[1], distance, state.getBoard())) {
                            actions.add(new BoardAction(queenPos, move, shot, state.turn));
                        }
                    }
                    actions.add(new BoardAction(queenPos, move, state.turn));
                }
            }
        }
    
        return actions;
    }
}
