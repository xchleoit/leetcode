package leetcode.leet_java;/*
 * @author xulei
 * @date 2020-06-16 15:28
 * 概要：
 *     XXXXX
 *
 */

import java.util.Arrays;

public class leetJava88 {
    public static void main(String[] args){
        int[] nums1= {1,2,3,0,0,0};
        int m =3;
        int[] nums2 = {2,5,6};
        int n = 3;
        merge(nums1,m,nums2,n);
        System.out.println(Arrays.toString(nums1));
    }
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] nums3=  new int[m+n];
        merge1(nums1,0,m,nums2,0,n,nums3);
        System.out.println(Arrays.toString(nums3));
        System.arraycopy(nums3,0,nums1,0,m+n);
    }
    public static void merge1(int[] nums1,int i,int m,int[] nums2,int j,int n,int[] nums3){
        if (i==m){
            stash(j,nums2,i+j,nums3);
        }else if(j==n){
            stash(i,nums1,i+j,nums3);
        }else{
            if(nums1[i]<nums2[j]){
                nums3[i+j]=nums1[i++];
                merge1(nums1,i,m,nums2,j,n,nums3);
            } else{
                nums3[i+j]=nums2[j++];
                merge1(nums1,i,m,nums2,j,n,nums3);
            }
        }
    }
    public static void stash(int k,int[] nums,int z,int[] nums3){
        for(int i=z;i<nums3.length;i++){
            nums3[i]=nums[k++];
        }
    }
}
