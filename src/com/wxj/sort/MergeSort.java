package com.wxj.sort;

import com.wxj.algorithm.DivideAndConquer;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        // 归并排序
        DivideAndConquer c = new DivideAndConquer();
        int[] arr = {1, 3, -1, 0, 9};
        c.mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
