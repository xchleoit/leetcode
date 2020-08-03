package leetcode.leet_java;/*
 * @author xulei
 * @date 2020-07-08 11:25
 * 概要：
 *     XXXXX
 *
 */

public class leetJava37 {

    public void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][9];
        boolean[][] col = new boolean[9][9];
        boolean[][] box = new boolean[9][9];
        if (board == null || board.length != 9 || board[0].length != 9) return;
        //初始化
        for (int i = 0;i<9;i++){
            for (int j = 0;j<9;j++){
                if (board[i][j]=='.') continue;
                int k = (i/3)*3 + j/3;//将数读区域分为九部分
                int num = board[i][j]-'1';
                row[i][num] =  col[j][num] = box[k][num] =  true;
            }
        }
        resolveSudoku(board,0,row,col,box);
    }

    private boolean resolveSudoku(char[][] board, int d, boolean[][] row, boolean[][] col, boolean[][] box) {
        if (d == 81) return  true;
        int i = d/9,j =  d%9;
        if (board[i][j]!='.') return resolveSudoku(board,d+1,row,col,box);
        for (int num =0;num<9;num++){
            int k = (i/3)*3 + j/3;
            if (row[i][num] ||  col[j][num] || box[k][num]) continue;
            board[i][j] = (char) (num+'1');
            row[i][num] =  col[j][num] = box[k][num] =  true;
            if (resolveSudoku(board,d+1,row,col,box)){
                return true;
            }
            row[i][num] =  col[j][num] = box[k][num] =  false;
        }
        board[i][j]='.';
        return false;
    }
}
