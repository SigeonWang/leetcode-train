package com.wxj.search;

import com.wxj.algorithm.DivideAndConquer;

import java.util.Arrays;

public class BinarySearch {
    public int binarySearch(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;  // 空数组或者非空数组且没有查到都会返回-1
    }

    public static void main(String[] args) {
        BinarySearch c = new BinarySearch();
        int[] arr = {};
        System.out.println(c.binarySearch(arr, 9));
    }
}
