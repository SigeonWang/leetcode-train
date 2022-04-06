package com.wxj.top100;

import java.util.*;

/**
 *  15. 三数之和 (中等)
 *
 *  给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？
 *  请你找出所有和为 0 且不重复的三元组。
 *  0 <= nums.length <= 3000
 *
 * 注意：答案中不可以包含重复的三元组。
 *
 */
public class No15ThreeSum {
    // 提交报错：超时
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        if (n >= 3) {
            for (int i = 0; i < n; i ++) {
                // 转化为两数之和 - nums[i]
                Map<Integer, Integer> m = new HashMap<>();
                for (int j = 0; j < n; j ++) {
                    if (j == i) {
                        continue;
                    }
                    // 错误代码：
                    if (m.containsKey(- nums[i] - nums[j])) {
                        List<Integer> l = Arrays.asList(nums[i], - nums[i] - nums[j], nums[j]);
                        // System.out.println(l);
                        if (!containsList(res, l)) {
                            res.add(l);  // // 错误点：目标数字不会添加到m，导致后续比较出问题
                        }
                    } else {
                        m.put(nums[j], j);  // 错误点：重复数字会覆盖；
                    }
                }
            }
        }

        return res;
    }
    public boolean containsList(List<List<Integer>> res, List<Integer> l1) {
//        for(List<Integer> l: res) {
//            if (l.containsAll(l1)) {
//                return true;
//            }
//        }
        for(List<Integer> l2: res) {
            if (l1.containsAll(l2) && l2.containsAll(l1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 排序 + 双指针
     *
     * 通过i遍历数组，维护两个指针left=i+1，right=len-1
     * 如果三个数相加小于0，右移left；如果大于0，左移right
     *
     * 注意：如果找到等于0的，还要继续移动，可能有不同解；还要避免重复的结果。
     *
     * @param nums 数组
     * @return 三个和为0的数
     */
    public List<List<Integer>> threeSumPro(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        int len = nums.length;
        Arrays.sort(nums);
        for (int i = 0; i < len; i ++) {
            // 大于0的不可能有0解，直接退出遍历
            if (nums[i] > 0) {
                break;
            }
            // 去重-1
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1;
            int right = len - 1;
            while (left < right) {  // 注意双指针的条件
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重-2
                    while (left < right && nums[left] == nums[left + 1]) {
                        left ++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right --;
                    }
                    right --;  // 这时候双指针同时移动
                    left ++;
                } else if (sum > 0) {
                    right --;
                } else {
                    left ++;
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] nums = {-5, 0, -2, 3, -2, 1, 1, 3, 0, -5, 3, 3, 0, -1};
        No15ThreeSum c = new No15ThreeSum();
        System.out.println(c.threeSumPro(nums));
    }
}
