package ubc.cosc322;

import java.util.ArrayList;

public class ActionFactory {
    
    public BoardState currenState;

    public ActionFactory(BoardState currenState) {
        this.currenState = currenState;
    }

    public ActionFactory() {
    }

    public ArrayList<BoardAction> generateActions(BoardState state) {
        ArrayList<BoardAction> actions = new ArrayList<BoardAction>();
        int player = state.turn == 1 ? state.WHITE : state.BLACK;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) { // for each position on the board
                // if the position is empty, we can move there and possibly shoot an arrow from there
                // each player has 4 queens on the board
                // each queen can move to any adjacent position and can shoot an arrow to any adjacent position
                // you can only move one queen at a time
                if (state.getBoard()[i][j] == state.EMPTY) { // if the position is empty, we can move there and shoot an arrow
                    // actions.add(new BoardAction(new int[] {i, j}, state.ME));
                    // for each queen, find the queen that's closest to that position, check if the queen can move to that position
                    // if the queen can move to that position, add the action to the list of actions
                    // if the queen can shoot an arrow to that position, add the action to the list of actions
                    // if the queen can't move to that position, check if another queen can move to that position
                    // if no queen can move to that position, don't add the action to the list of actions
                    ArrayList<Queen> queens = player == state.WHITE ? state.WHITEQUEENS : state.BLACKQUEENS;
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
}
