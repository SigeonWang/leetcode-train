package com.wxj.top100;

import java.util.Arrays;

public class No31NextPermutation {
    /**
     * 31. 下一个排列 (中等)
     * 给你一个整数数组 nums ，找出 nums 的下一个排列。必须原地修改，只允许使用额外常数空间。
     *
     * 整数数组的一个 "排列" 就是将其所有成员以序列或线性顺序排列。
     * 例如，arr = [1,2,3] ，以下这些都可以视作 arr 的排列：[1,2,3]、[1,3,2]、[3,1,2]、[2,3,1] 。
     *
     * 整数数组的 "下一个排列" 是指其整数的下一个字典序更大的排列。
     * 更正式地，如果数组的所有排列根据其字典顺序从小到大排列在一个容器中，那么数组的 "下一个排列" 就是在这个有序容器中排在它后面的那个排列。
     * 如果不存在下一个更大的排列，那么这个数组必须重排为字典序最小的排列（即，其元素按升序排列）。
     * 例如，arr = [1,2,3] 的下一个排列是 [1,3,2] 。
     * 类似地，arr = [2,3,1] 的下一个排列是 [3,1,2] 。
     * 而 arr = [3,2,1] 的下一个排列是 [1,2,3] ，因为 [3,2,1] 不存在一个字典序更大的排列。
     *
     * 1 <= nums.length <= 100
     *
     * @param nums 数组
     */
    public void nextPermutation(int[] nums) {
        // 思路：两遍扫描
        // 1）我们需要将一个左边的「较小数」与一个右边的「较大数」交换，以能够让当前排列变大，从而得到下一个排列。
        // 2）同时我们要让这个「较小数」尽量靠右，而「较大数」尽可能小。当交换完成后，「较大数」右边的数需要按照升序重新排列。
        // 这样可以在保证新排列大于原来排列的情况下，使变大的幅度尽可能小。
        //
        // 具体算法：
        // 1）首先"从后向前"查找第一个顺序对 (i,i+1)，满足 a[i] < a[i+1]。这样「较小数」即为 a[i]。此时 [i+1,n) 必然是下降序列。
        // 2）如果找到了顺序对，那么在区间 [i+1,n) 中"从后向前查"找第一个元素 j 满足 a[i] < a[j]。这样「较大数」即为 a[j]。
        // 3）交换 a[i] 与 a[j]，此时可以证明区间 [i+1,n) 必为降序。我们可以直接使用双指针反转区间 [i+1,n) 使其变为升序，而无需对该区间进行排序。
        int len = nums.length;
        int minIndex = -1;
        for (int i = len - 1; i > 0; i --) {
            // 1. 找到尽量靠右的"较小数"
            if(nums[i - 1] < nums[i]) {
                minIndex = i - 1;
                break;  // 注意：这里找到第一个后就要退出循环！！！
            }
        }
        if (minIndex != -1) {
            for (int i = len - 1; i > minIndex; i--) {
                // 2. 找到尽量小的"较大数"（因为[minIndex+1, n)肯定是降序的，所以从后向前找到第一个就可以），并交换
                if(nums[i] > nums[minIndex]) {
                    swap(nums, minIndex, i);
                    break;  // 注意：这里找到第一个后就要退出循环！！！
                }
            }
        }
        // 3. 最后让[minIndex+1, n)生序（如果是最大序列，就会跳过2，直接交换成最小序列）
        for (int i = minIndex + 1, j = len - 1; i < j; i ++, j --) {
            swap(nums, i, j);  // 注意这里包括"较大数"，都要一起排序
        }
    }
    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 2};
        System.out.println("数组：" + Arrays.toString(arr));
        No31NextPermutation c = new No31NextPermutation();
        c.nextPermutation(arr);
        System.out.println("下一个排序：" + Arrays.toString(arr));
    }
}

