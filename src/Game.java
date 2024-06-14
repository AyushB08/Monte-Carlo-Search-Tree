import java.util.Arrays;
import java.util.Scanner;

public class Game {
    static char player = 'x';
    static char computer = 'o';
    static Scanner sc = new Scanner(System.in);

    public static boolean playerFirst = false;

    public static void main(String[] args) {
        char[][] board = {
                { '_', '_', '_' },
                { '_', '_', '_' },
                { '_', '_', '_' }
        };

        MCTS mcts = new MCTS();

        while (TicTacToe.evaluate(board) == 0 && TicTacToe.isMovesLeft(board)) {

            if (playerFirst) {
                TicTacToe.printBoard(board);


                int[] move = getPlayerMove(board);
                board[move[0]][move[1]] = player;

                TicTacToe.printBoard(board);

                if (TicTacToe.evaluate(board) != 0 || !TicTacToe.isMovesLeft(board)) {
                    break;
                }

                mcts.playBestMove(board, move);

                TicTacToe.printBoard(board);

            } else {
                TicTacToe.printBoard(board);

                mcts.playBestMove(board, null);

                TicTacToe.printBoard(board);

                if (TicTacToe.evaluate(board) != 0 || !TicTacToe.isMovesLeft(board)) {
                    break;
                }

                int[] move = getPlayerMove(board);
                board[move[0]][move[1]] = player;
            }

        }

        int result = TicTacToe.evaluate(board);
        if (result == 10) {
            System.out.println("Computer wins!");
        } else if (result == -10) {
            System.out.println("Player wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    public static int[] getPlayerMove(char[][] board) {
        int[] move = new int[2];
        boolean validMove = false;

        while (!validMove) {
            System.out.println("Enter a number (1-9): ");
            int num = sc.nextInt() - 1;

            int row = num / 3;
            int col = num % 3;

            if (num >= 0 && num < 9 && board[row][col] == '_') {
                move[0] = row;
                move[1] = col;
                validMove = true;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        return move;
    }
}
