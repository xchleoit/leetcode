package leetcode.leet_java;/*
 * @author xulei
 * @date 2020-07-08 14:45
 * 概要：
 *     扰乱字符串
 *
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class leetCode87 {
   public static void main(String[] args){
       String string =  "great";
       System.out.println(string.substring(0,string.length()/2));
       System.out.println(string.substring(string.length()/2,string.length()-1));
       System.out.println(new Solution().isScramble("great","rgeat"));
   }
}
 class  Solution {
    public boolean isScramble(String s1,String s2){
        //明显不想等的情况
        if (s1 == null || s2 == null || s1.length() != s2.length()) return  false;
        if (s1.equals(s2)) return  true;
        Map<Character , Integer> map = new HashMap<>();
        int n = s1.length();
        //检查两个字符串里的字符是否完全一样，只有一样的时候，才可能是扰动字符串
        for ( int i = 0;i<n;i++){
            map.put(s1.charAt(i),map.getOrDefault(s1.charAt(i),0)+1);
            map.put(s2.charAt(i),map.getOrDefault(s2.charAt(i),0)-1);
        }
        for (Character key : map.keySet()){
            if (map.get(key)!= 0){return false;}
        }
        boolean[][][] dp = new boolean[n][n][n+1];
//        初始化
        for (int i =0;i<n;i++){
            for (int j =0;j<n;j++){
                dp[i][j][1] = s1.charAt(i) == s2.charAt(j);
            }
        }
        for(int l = 2;l<=n;l++){
            for (int i =0;i<=n-l;i++){
                for (int j =0;j<=n-l;j++){
                    for (int k = 1;k<l;k++){
                        if (dp[i][j][k] && dp[i+k][j+k][l-k]){
                            dp[i][j][l] =  true;
                            break;
                        }
                        if (dp[i][j+l-k][k] && dp[i+k][j][l-k]){
                            dp[i][j][l] =  true;
                            break;
                        }
                    }
                }
            }
        }
         return dp[0][0][n];

    }

     public boolean isScramble1(String s1, String s2) {
         //明显不想等的情况
         if (s1 == null || s2 == null || s1.length() != s2.length()) return  false;
         if (s1.equals(s2)) return  true;
         Map<Character , Integer> map = new HashMap<>();
         int n = s1.length();
         //检查两个字符串里的字符是否完全一样，只有一样的时候，才可能是扰动字符串
         for ( int i = 0;i<n;i++){
             map.put(s1.charAt(i),map.getOrDefault(s1.charAt(i),0)+1);
             map.put(s2.charAt(i),map.getOrDefault(s2.charAt(i),0)-1);
         }
         for (Character key : map.keySet()){
             if (map.get(key)!= 0){return false;}
         }

         for (int i=1;i<n;i++){
             boolean flag = isScramble1(s1.substring(0,i),s2.substring(0,i)) && isScramble1(s1.substring(i),s2.substring(i));
             flag = flag || isScramble1(s1.substring(0,i),s2.substring(n-i)) && isScramble1(s1.substring(i) ,s2.substring(0,n-i));
             if (flag == true){
                 return  true;
             }
         }
         return  false;
     }

 }
