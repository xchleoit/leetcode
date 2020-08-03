package algothriom.sort;/*
 * @author xulei
 * @date 2020-07-08 09:33
 * 概要：
 *     冒泡排序
 *
 */

import java.util.Arrays;

public class BubboSort {
    public static void main(String[] args) {
        Integer[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
//        Integer[] arr = {3,5,2,4};
        bubboSort(arr);
        System.out.println("排序后:");
        Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
        System.out.println();
        Arrays.asList(arr).forEach(System.out::print);
    }
//    public static void bubboSort(Integer[] arr){
//        for (int i =0;i<arr.length-1;i++){
//            for (int j =i+1 ;j<arr.length;j++){
//                if (arr[i]>arr[j]){

//                }
//            }
//            Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
//            System.out.println();
//        }
//    }
    public static void bubboSort(Integer[] arr){
        for (int i =0;i<arr.length-1;i++){
            for (int j =0;j< arr.length-i-1;j++){
                if (arr[j]> arr[j+1]){
                    swap(arr,j,j+1);
                }
            }
            Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
            System.out.println();
        }
    }

    public static void swap(Integer[] arr,int i,int j){
        int temp  = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
