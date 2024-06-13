import javax.swing.*;
import java.util.ArrayList;

public class Node {

    public Node parent;
    public char[][] board;
    public ArrayList<Node> children = new ArrayList<>();

    public ArrayList<int[]> initializedChildren = new ArrayList<>();
    public int visits = 0;
    public int wins = 0;





    Action action;

    public Node(Node parent, char[][] board, Action action) {
        this.parent = parent;
        this.board = TicTacToe.cloneBoard(board);
        this.action = action;

        initializedChildren = TicTacToe.getMovesLeft(board);



    }



    public Node addChild(int[] move) {

        char[][] clone = TicTacToe.cloneBoard(board);

        boolean playerTurn = action.player;
        Action childAction = new Action(!playerTurn, move);
        if (playerTurn) {

            board[move[0]][move[1]] = TicTacToe.computer;
        } else {

            board[move[0]][move[1]] = TicTacToe.player;
        }

        Node child = new Node(this, clone, childAction);

        children.add(child);
        return child;

    }

    public boolean isFullyExpanded() {
        return initializedChildren.size() == children.size();


    }

    public ArrayList<Node> getUnexploredChildren() {
        ArrayList<Node> unexploredNodes = new ArrayList<>();
        for (int i = 0; i < initializedChildren.size(); i++) {
            int[] move = initializedChildren.get(i);
            boolean isExplored = false;
            for (int a = 0; a < children.size(); a++) {
                int[] exploredMove = children.get(a).action.move;
                if (move[0] == exploredMove[0] && move[1] == exploredMove[1]) {
                    isExplored = true;
                    break;
                }
            }
            if (!isExplored) {
                char[][] clone = TicTacToe.cloneBoard(board);
                if (action.player) {
                    clone[move[0]][move[1]] = TicTacToe.computer;
                } else {
                    clone[move[0]][move[1]] = TicTacToe.player;
                }

                unexploredNodes.add(new Node(this, TicTacToe.cloneBoard(board), new Action(!action.player, move)));
            }
        }
        return unexploredNodes;
    }





}
