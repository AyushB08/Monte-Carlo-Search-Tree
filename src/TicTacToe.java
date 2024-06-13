import java.util.ArrayList;
import java.util.Arrays;

public class TicTacToe
{
    public static class Move


    {

        int row, col;

        public Move(int row, int col) {
            this.row = row;
            this.col = col;
        }

    };

    public static char player = 'x', computer = 'o';

    // This function returns true if there are moves
// remaining on the board. It returns false if
// there are no moves left to play.
    public static Boolean isMovesLeft(char board[][])

    {

        if (evaluate(board) == 0) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == '_')

                        return true;

        }

        return false;
    }

    // This is the evaluation function as discussed
// in the previous article ( http://goo.gl/sJgv68 )
    public static int evaluate(char b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2])
            {
                if (b[row][0] == player)
                    return +10;
                else if (b[row][0] == computer)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col])
            {
                if (b[0][col] == player)
                    return +10;

                else if (b[0][col] == computer)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == player)
                return +10;
            else if (b[0][0] == computer)
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == player)
                return +10;
            else if (b[0][2] == computer)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board




    public static void playRandomMove(char[][] board) {
        ArrayList<Move> possibleMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                if (board[i][a] == '_') {
                    possibleMoves.add(new Move(i, a));
                }
            }
        }
        int random = (int)(Math.random() * possibleMoves.size());
        Move move = possibleMoves.get(random);
        board[move.row][move.col] = player;
        System.out.println();
        System.out.println("Computer has played Row: " + move.row + " Col: " + move.col);
    }

    public static void printBoard(char[][] board) {
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.println(Arrays.toString(board[i]));

        }
        System.out.println();
    }

    public static char[][] cloneBoard(char[][] board) {
        char[][] clone = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                clone[i][a] = board[i][a];
            }
        }
        return clone;
    }

    public static ArrayList<int[]> getMovesLeft(char[][] board) {
        ArrayList<int[]> moves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int a = 0; a < 3; a++) {
                if (board[i][a] == '_') {
                    moves.add(new int[]{i, a});
                }
            }
        }
        return moves;
    }




}