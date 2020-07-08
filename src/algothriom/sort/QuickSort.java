package algothriom.sort;/*
 * @author xulei
 * @date 2020-06-22 10:14
 * 概要：
 *     快速排序
 *
 */

import java.util.Arrays;

public class QuickSort {
    public static void main(String[] args) {
        Integer[] arr = {49, 38, 65, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后:");
        Arrays.asList(arr).forEach((item)->{System.out.print(item+"，");});
        System.out.println();
        Arrays.asList(arr).forEach(System.out::print);
    }

    private static void quickSort(Integer[] arr, int i, int i1) {
        if (i<i1){
            int index = getIndex(arr,i,i1);
            System.out.println(index);
            quickSort(arr,i,index-1);
            quickSort(arr,index+1,i1);
        }

    }

    private static int getIndex(Integer[] arr, int low, int high) {
        int temp = arr[low];
        while (low<high){
            while (low<high && temp<=arr[high]){high--;}
            arr[low] = arr[high];
            while (low<high && temp>=arr[low]){low++;}
            arr[high] = arr[low];
        }
        arr[low] = temp;
        return low;
    }


}

