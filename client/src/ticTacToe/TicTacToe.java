package ticTacToe;

public class TicTacToe {
    private int[][] board = new int[3][3];      // 0 -> nothing; 1 -> player 1; 2 -> player 2

    public TicTacToe() {}


    /**
     *
     * @return 0 -> no win; 1 -> player 1; 2 -> player 2
     */
    public int checkWin() {
        for (int i = 0; i < 3; i++) {
            int cur = board[i][0];
            boolean win = true;
            for (int j = 0; j < 3; j++) {
                if (board[i][j]!=cur) win = false;
            }
            if (win) return cur;
        }
        for (int i = 0; i < 3; i++) {
            int cur = board[0][i];
            boolean win = true;
            for (int j = 0; j < 3; j++) {
                if (board[j][i]!=cur) win = false;
            }
            if (win) return cur;
        }

        int cur = board[0][0];
        boolean win = true;
        for (int i = 0; i<3; i++) {
            if (board[i][i]!=cur) win = false;
        }
        if (win) return cur;

        cur = board[0][2];
        win = true;
        for (int i = 0; i<3; i++) {
            if (board[i][2-i]!=cur) win = false;
        }
        if (win) return cur;
        return 0;
    }

    public int get(int x, int y) {
        return board[x][y];
    }

    public boolean put(int x, int y, int player) {
        if (player!=1 && player!=2) return false;
        if (get(x,y)!=0) return false;
        board[x][y] = player;
        return true;
    }


}
