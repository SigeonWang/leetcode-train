package com.wxj.top100;

import java.util.Arrays;

public class No33Search {
    /**
     * 33. 搜索旋转排序数组 (中等)
     *
     * 整数数组 nums 按升序排列，数组中的值互不相同 。
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。
     * 例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。
     * 给你 "旋转后" 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target，则返回它的下标，否则返回 -1。
     *
     * 1 <= nums.length
     * 你可以设计一个时间复杂度为 O(log n) 的解决方案吗？
     *
     * @param nums 数组
     * @param target 整数
     * @return 下标
     */
    public int search(int[] nums, int target) {
        // 既然是有序数组，而且要O(log n)，肯定是要通过二分查找实现，但是具体怎么二分呢？
        // 下面这种还是O(n), 并没有达成目标
        int len = nums.length;
        if (target < nums[0]) {
            int i = len - 1;
            while (i >= 0 && nums[0] > nums[i]) {
                if (nums[i] == target) {
                    return i;
                }
                i --;
            }
        }
        if (target >= nums[0]) {
            int i = 0;
            while (i < len && nums[0] <= nums[i]) {
                if (nums[i] == target) {
                    return i;
                }
                i ++;
            }
        }
        return  -1;
    }

    public int searchPro(int[] nums, int target) {
        // 解法：二分查找
        // 思路：直接二分，两部分数组一定至少有一半是有序的。
        // 如果target在有序部分，可以直接二分查找；否则在无需部分，继续把无需部分二分...以此类推
        // 注意：二分的写法有很多种，所以在判断 target 大小与有序部分的关系的时候可能会出现细节上的差别
        int len = nums.length;
        if (len == 0) {
            return -1;
        }
        if (len == 1) {
            return nums[0] == target ? 0 : -1;
        }
        int l = 0, r = len - 1;
        while (l <= r) {  // 注意这里是<=
            int mid = (l + r) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (nums[0] <= nums[mid]) {  // 一定有序部分
                if (nums[0] <= target && target < nums[mid]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {  // 可能有序部分
                if (nums[mid] < target && target <= nums[len - 1]) {  // 这个不一定满足，不满足就继续二分
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 4, 0};
        No33Search c = new No33Search();
        System.out.println(c.searchPro(arr, 1));

    }
}
