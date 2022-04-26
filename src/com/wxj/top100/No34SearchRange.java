package com.wxj.top100;


import java.util.Arrays;

public class No34SearchRange {
    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置 (中等)
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。
     * 找出给定目标值在数组中的开始位置和结束位置。
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     *
     * 示例：
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     *
     * 进阶：
     * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
     *
     * 0 <= nums.length <= 105
     *
     * @param nums 数组
     * @param target 目标值
     * @return 位置
     */
    public int[] searchRange(int[] nums, int target) {
        // O(n) 的话就遍历一遍，用两个变量保存目标值的最小下标和最大下标，但是没有利用有序的条件。
        // 但是要求 O(log n) 且 有序，肯定是二分了，但是细节怎么做不太容易一下想到！！！
        // 思路：
        // 最小下标: 如果mid >= target，令right = mid - 1；否则left = left + 1。
        // 最大下标: 如果mid >= target，令right = mid - 1；否则left = left + 1。
        // int left = binarySearch(nums, target, true);
        // int right = binarySearch(nums, target, false) - 1;
        int left = binarySearch2(nums, target, false) + 1;
        int right = binarySearch2(nums, target, true);
        if (left <= right && left > -1 && nums[left] == target && nums[right] == target) {
            return new int[]{left, right};
        }
        return new int[]{-1, -1};
    }
    public int binarySearch(int[] nums, int target, boolean lower) {
        int left = 0, right = nums.length - 1, index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 因为存在重复元素且需要最大或最小位置，这里不能需要判断相等，而只是判断范围。
            // 最大和最小下标的区别仅仅是查找最小下标时，如果mid=target还要继续查找左边的元素，看是否还有相等的。
            if (nums[mid] > target || lower && nums[mid] >= target) {
                right = mid - 1;
                index = mid;
            } else {
                left = left + 1;
            }
        }
        return index;
    }
    public int binarySearch2(int[] nums, int target, boolean higher) {
        int left = 0, right = nums.length - 1, index = -1;
        while (left <= right) {
            int mid = (left + right) / 2;
            // 因为存在重复元素且需要最大或最小位置，这里不能需要判断相等。
            // 最大和最小下标的区别仅仅是查找最小下标时，如果mid=target还要继续查找左边的元素，看是否还有相等的。
            if (nums[mid] < target || higher && nums[mid] <= target) {
                left = left + 1;
                index = mid;
            } else {
                right = mid - 1;
            }
        }
        return index;
    }

    public static void main(String[] args) {
        No34SearchRange c = new No34SearchRange();
        int[] arr = {-1, 0, 3, 3, 3, 4, 7};
        System.out.println(Arrays.toString(c.searchRange(arr, 3)));
    }
}
