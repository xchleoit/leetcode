package algothriom.sort;/*
 * @author xulei
 * @date 2020-07-08 10:00
 * 概要：
 *     选择排序
 *
 */

import java.util.Arrays;

public class ChoiceSort {
    public static void main(String[] args) {
        Integer[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
        choiceSort(arr);
        System.out.println("排序后:");
        Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
        System.out.println();
    }
    public static void swap(Integer[] arr,int i,int j){
        int temp  = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void choiceSort(Integer[] arr){
        for (int i=0;i<arr.length-1;i++){
            //寻找第i+1小第值
            int key =i;
            for (int j = i+1;j<arr.length;j++){
                if (arr[key]>arr[j]){
                    key = j;
                }
            }
            //找到了放在第i位上
            swap(arr,key,i);
        }
    }
}
