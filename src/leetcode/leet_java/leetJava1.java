package leetcode.leet_java;

import java.util.HashMap;
import java.util.Map;

public class leetJava1 {
    //给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
//
// 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
//
//
//
// 示例:
//
// 给定 nums = [2, 7, 11, 15], target = 9
//
//因为 nums[0] + nums[1] = 2 + 7 = 9
//所以返回 [0, 1]
//
// Related Topics 数组 哈希表
    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        int[] result =twoSum(nums,target);
        for (int key:result){
            System.out.println(key);
        }
    }
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>();
        int[] result = new int[2];
        for (int key=0;key<nums.length;key++){
            if (nums[key]<target){
                if (map.containsKey(target - nums[key])) {
                    result[0] = map.get(target-nums[key]);
                    result[1] = key;return result;
                }
                map.put(nums[key],key);
            }
        }
        return result;
    }
}
