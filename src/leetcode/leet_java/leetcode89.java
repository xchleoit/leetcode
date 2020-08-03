package leetcode.leet_java;/*
 * @author xulei
 * @date 2020-07-08 16:44
 * 概要：
 *     格雷编码
 *
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class leetcode89 {
    public static void main(String[] args) {
        List<Integer> list = new Solution().grayCode(1);
        Arrays.asList(list).forEach(item->{
            System.out.print(item+",");
        });
        
    }

    private static class Solution {
        public List<Integer> grayCode(int n) {
            List<Integer> list = new ArrayList<>();
            list.add(0);
            int shift =1;
            for(int j =1;j<=n;j++){
                for (int i = shift - 1; i >= 0; i--) {
                    list.add(list.get(i) + shift);
                }
                shift <<= 1;
            }
            return  list;
        }

    }
}
