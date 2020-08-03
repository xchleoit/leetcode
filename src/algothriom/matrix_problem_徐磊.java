package algothriom;/*
 * @author xulei
 * @date 2020-07-20 11:53
 * 概要：
 *
 *输入为N，请 顺时针 螺旋 输出1->NxN到NxN的二维数组，
如：
输入为 2
输出
1,2
4,3
输入为4
输出
1，  2， 3， 4，
12，13，14， 5，
11，16，15， 6，
10， 9， 8， 7，

请手写一个输入为5时的输出

再用手写时的逻辑 代码实现下述方法
int[][] gen(int N)
返回为NxN的矩阵，内部数值符合上述规律
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class matrix_problem_徐磊 {
    public static void main(String[] args){
        //输入矩阵大小
        Scanner scanner = new Scanner(System.in);
        Map<Integer,String> map = new HashMap();
        map.size();
        System.out.println(map.capacity());
//        int[][] result = solve(scanner.nextInt());
//        for (int i =0;i<result.length;i++){
//            for (int j =0;j<result[i].length;j++){
//                System.out.print(result[i][j]+",");
//            }
//            System.out.println();
//        }
    }

    private static int[][] solve(int n) {
        int[][] matrix = new int[n][n];
        String left = "LEFT",right = "RIGHT", down = "DOWN", up = "UP";//判断方向
        int i =0,j=0;
        //初始向右走
        String  direct =  right;
        //值从0开始
        for (int k = 1;k<n*n+1;k++){
            matrix[i][j] = k;
            //朝右走
            if (direct.equals(right)){
                if (j+1<n && matrix[i][j+1] == 0){
                    j++;
                }else{
                    i++;
                    direct =  down;
                    continue;
                }
            }
            //朝下走
            if (direct.equals(down)){
                if (i+1<n && matrix[i+1][j] == 0){
                    i++;
                }else{
                    j--;
                    direct =  left;
                    continue;
                }
            }
            //朝左走
            if (direct.equals(left)){
                if (j-1>=0 && matrix[i][j-1] == 0){
                    j--;
                }else{
                    i--;
                    direct =  up;
                    continue;
                }
            }
            //朝上走
            if (direct.equals(up)){
                if (i-1>=0 && matrix[i-1][j] == 0){
                    i--;
                }else{
                    j++;
                    direct =  right;
                    continue;
                }
            }


        }
        return matrix;

    }
}
