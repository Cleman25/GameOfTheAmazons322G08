package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI {
	private Node rootNode;
    private int playerColor;
    private int simulationCount;
    private final double C = Math.sqrt(2);
    private long timeLimit;
	ActionFactory aFac;
    
    public AI(int playerColor, int simulationCount, long timeLimit) {
        this.playerColor = playerColor;
        this.simulationCount = simulationCount;
        this.timeLimit = timeLimit;
    }
    
    public int[] findBestMove(Board board) {
		this.aFac = new ActionFactory(board.board);
        rootNode = new Node(board, null, playerColor);
        long startTime = System.currentTimeMillis();
        int simulationCounter = 0;
        while (simulationCounter < simulationCount && System.currentTimeMillis() - startTime < timeLimit) {
            Node node = selection(rootNode);
            if (node.getVisits() == 0) {
                expand(node);
            }
            Node expandedNode = selection(node);
            int result = simulate(expandedNode);
            backpropagate(expandedNode, result);
            simulationCounter++;
        }
        Node bestChild = getBestChild(rootNode);
        return bestChild.getAction();
    }
    
    private Node selection(Node node) {
        while (!node.getBoard().isTerminal()) {
            if (node.getVisits() == 0) {
                return node;
            }
            if (node.getBoard().getPossibleMoves().size() > node.getVisits()) {
                return expand(node);
            }
            node = getBestChild(node);
        }
        return node;
    }
    
    private Node expand(Node node) {
        List<int[]> possibleActions = aFac.getLegalMoves(playerColor);
        Random random = new Random();
        int[] action = possibleActions.get(random.nextInt(possibleActions.size()));
        Board newBoard = node.getBoard().clone();
        newBoard.makeMove(action);
        Node childNode = new Node(newBoard, action, playerColor);
        node.addChild(childNode);
        return childNode;
    }
    
    private int simulate(Node node) {
        Board board = node.getBoard().clone();
        int currentPlayer = node.getPlayer();
        while (!board.isTerminal()) {
            List<int[]> possibleMoves = aFac.getLegalMoves(currentPlayer);
            int[] randomMove = possibleMoves.get(new Random().nextInt(possibleMoves.size()));
            board.makeMove(randomMove);
            currentPlayer = 1 - currentPlayer;
        }
        if (board.getWinner() == playerColor) {
            return 1;
		} else {
            return 0;
        }
    }
    
    private void backpropagate(Node node, int result) {
        while (node != null) {
            node.incrementVisits();
            if (node.getPlayer() == playerColor) {
                node.incrementWins();
            }
            node = node.getParent();
        }
    }
    
    private Node getBestChild(Node node) {
        ArrayList<Node> children = node.getChildren();
        Node bestChild = children.get(0);
        double bestScore = getUCB1Score(bestChild, node);
        for (int i = 1; i < children.size(); i++) {
            Node child = children.get(i);
            double score = getUCB1Score(child, node);
            if (score > bestScore) {
                bestChild = child;
                bestScore = score;
            }
        }
        return bestChild;
    }
    
    private double getUCB1Score(Node node, Node parent) {
        double exploitation = (double) node.getWins() / node.getVisits();
        double exploration = C * Math.sqrt(Math.log(parent.getVisits()) / node.getVisits());
        return exploitation + exploration;
    }
}