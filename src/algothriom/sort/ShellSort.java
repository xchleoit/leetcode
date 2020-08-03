package algothriom.sort;/*
 * @author xulei
 * @date 2020-07-08 10:26
 * 概要：
 *     希尔排序
 *
 */

import javafx.beans.property.IntegerProperty;

import java.util.Arrays;

public class ShellSort {
    public static void main(String[] args) {
        Integer[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
//        Integer[] arr = {3,5,2,4,1};
        shellSort(arr);
        System.out.println("排序后:");
        Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
        System.out.println();
    }
    public static void swap(Integer[] arr,int i,int j){
        int temp  = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void shellSort(Integer[] arr){
        int dk = arr.length/2;
        while (dk>=1){
            for (int i =dk;i<arr.length;i+=dk){
                if (arr[i-dk]>arr[i]){
                    for (int j = i;j> 0 ;j-=dk){
                        if (arr[j-dk]>arr[j]){
                            swap(arr,j-dk,j);
                        }
                    }
                }
            }
            dk/=2;
        }
    }
}
