import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MCTS {

    public static char player = 'x', computer = 'o';

    public void playRandomMove(char[][] board) {
        ArrayList<int[] > moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                if (board[i][a] == '_') {
                    moves.add(new int[]{i, a});


                }
            }
        }
        int[] move =  moves.get((int) (Math.random() * moves.size()));
        board[move[0]][move[1] ] = computer;

    }

    public void playBestMove(char[][] board, int[] pastMove) {




        Node rootNode = new Node(null,  board, new Action(false, pastMove));
        MCTS(rootNode, 10000);
        int[] bestMove = null;
        double bestValue = -1;

        for (Node child : rootNode.children) {

            double childValue = (double) child.wins / child.visits;
            System.out.println("MOVE: " + Arrays.toString(child.action.move) + " VALUE: " + childValue + " WINS: " + child.wins + " VISITS: " + child.visits);
            if (childValue > bestValue) {
                bestValue = childValue;
                bestMove = child.action.move;
            }
        }

        board[bestMove[0]][bestMove[1]] = TicTacToe.computer;

    }

    public void MCTS(Node rootNode, int iterationCount) {
        for (int i = 0; i < iterationCount; i++) {

            Node selectedNode = selection(rootNode);
            Node expandedNode = expansion(selectedNode);
            int simulationResult = simulation(expandedNode);
            backpropagation(expandedNode, simulationResult);
        }
        int total = getTotalNodes(rootNode);
        System.out.println("Total Nodes: " + total);

    }

    public int getTotalNodes(Node root) {
        int sum = 1;
        for (Node child : root.children) {
            sum += getTotalNodes(child);
        }
        return sum;
    }

    public void backpropagation(Node node, int simulationResult) {
        while (node != null) {
            node.visits++;
            if (simulationResult == 10) {
                node.wins += 1;
            }

            node = node.parent;
        }
    }

    public int simulation(Node node) {
        char[][] board = TicTacToe.cloneBoard(node.board);
        boolean playerTurn = node.action.player;
        while (TicTacToe.isMovesLeft(board)) {
            playerTurn = !playerTurn;
            rolloutPolicy(board, playerTurn);
        }

        int value = TicTacToe.evaluate(board);
        return value;
    }

    public void rolloutPolicy(char[][] board, boolean playerTurn) {
        ArrayList<int[]> moves = TicTacToe.getMovesLeft(board);
        int[] move = moves.get((int) (Math.random() * moves.size()));

        if (playerTurn) {
            board[move[0]][move[1]] = TicTacToe.player;
        } else {
            board[move[0]][move[1]] = TicTacToe.computer;
        }

    }

    public Node expansion(Node node) {
        if (TicTacToe.isMovesLeft(node.board) && !node.isFullyExpanded()) {
            return expandNode(node);
        } else {
            return node;
        }
    }

    public Node expandNode(Node node) {
        ArrayList<Node> unexploredNodes = node.getUnexploredChildren();
        Node unexploredNode = unexploredNodes.get((int) (Math.random() * unexploredNodes.size()));
        Node child = node.addChild(unexploredNode.action.move);
        return child;
    }

    public Node selection(Node node) {
        Node selectedNode = node;
        while (TicTacToe.isMovesLeft(selectedNode.board) && selectedNode.isFullyExpanded()) {
            selectedNode = bestUCT(selectedNode.children);
        }
        return selectedNode;
    }

    public Node bestUCT(ArrayList<Node> children) {
        Node bestNode = null;
        double bestUCTValue = Double.NEGATIVE_INFINITY;
        for (Node child : children) {
            double uctValue = calculateUCT(child);
            if (uctValue > bestUCTValue) {
                bestUCTValue = uctValue;
                bestNode = child;
            }
        }
        return bestNode;
    }

    public double calculateUCT(Node node) {
        if (node.visits == 0) {
            return Double.POSITIVE_INFINITY;
        }
        double exploitation = (double) node.wins / node.visits;
        double exploration = Math.sqrt(Math.log(node.parent.visits) / node.visits);
        return exploitation + 1.4 * exploration;
    }


}
