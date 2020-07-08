package algothriom.sort;/*
 * @author xulei
 * @date 2020-06-22 11:43
 * 概要：
 *     给n个员工进行排序
 *
 */

import java.util.Arrays;

public class CountAge {
    public static void main(String[] args){
        Integer[] ages = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, 23, 22};
        sortAge(ages,ages.length);
        Arrays.asList(ages).forEach((item)->{
            System.out.print(item+",");
        });
    }

    private static void sortAge(Integer[] ages, int length) {
        if (ages == null || length<=0){
            System.out.println("something wrong");
        }
        int oldestAge = 99;
        Integer[] timmeAges = new Integer[oldestAge+1];
        for (int i =0;i<=oldestAge;i++){
            timmeAges[i] = 0;
        }
        for (int i =0;i <length;i++){
            ++timmeAges[ages[i]];
        }
        int index =0;
        for (int i =0;i<= oldestAge;i++){
            for (int j =0 ;j<timmeAges[i];j++){
                ages[index++]=i;
            }
        }
    }
}
