package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Board board;
    Node parent; // the parent node
    ArrayList<Node> children; // the children nodes
    List<int[]> moves; // the moves that led to this node
    int visits; // number of times this node has been visited
    int score; // total score of all simulations starting from this node
    int wins; // total number of wins starting from this node

    public Node(Board game) {
        this.board = game;
        this.parent = null;
        this.children = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.visits = 0;
        this.score = 0;
    }

    public Node(Board newBoard, int[] action, int playerColor) {
        this.board = newBoard;
        this.parent = null;
        this.children = new ArrayList<>();
        this.moves = new ArrayList<>();
        this.visits = 0;
        this.score = 0;
    }

    public Node addChild(int[] move) {
        Board childBoard = board.clone();
        childBoard.move(move);
        Node child = new Node(childBoard);
        child.parent = this;
        child.moves = new ArrayList<>(moves);
        child.moves.add(move);
        children.add(child);
        return child;
    }

    public double getWins() {
        return 0;
    }

    public double getVisits() {
        return 0;
    }

    public int getPlayer() {
        return 0;
    }

    public void incrementVisits() {
        visits++;
    }

    public void incrementWins() {
        wins++;
    }

    public Node getParent() {
        return parent;
    }

    public Board getBoard() {
        return board;
    }

    public void addChild(Node childNode) {
        children.add(childNode);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int[] getAction() {
        return moves.get(moves.size() - 1);
    }
}
