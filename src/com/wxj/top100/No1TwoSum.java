package com.wxj.top100;

import java.util.Arrays;
import java.util.HashMap;


/**
 * 1. 两数之和 [简单]
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值 target 的那两个整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案，但是数组中同一个元素在答案里不能重复出现。
 * 你可以按任意顺序返回答案。
 *
 * 示例 ：
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
 */
public class No1TwoSum {
    public static int[] twoSum(int[] nums, int target) {
        // 静态初始化：int[] res = new int[]{0, 0, 0};
        // 简化的静态初始化：int[] res = {0, 0, 0};
        // 动态初始化：int[] res = new int3];
        for (int i = 0; i < nums.length; i++) {
            // 这里j >= 0 也行，但是i左边肯定是没有匹配上的，可以跳过
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[j] == target - nums[i]) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static int[] twoSumPro(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // key 设置成需要寻找的值，下标作为value，这样找到确定的key后，可以通过get()找到value，反之不可以。
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }


    public static void main(String[] args) {
        int[] nums = {3, 2, 4};
        System.out.println(Arrays.toString(twoSum(nums, 6)));
        System.out.println(Arrays.toString(twoSumPro(nums, 6)));
    }
}
