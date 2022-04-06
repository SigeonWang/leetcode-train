package com.wxj.algorithm;

import java.util.Arrays;

/**
 * 分治
 *
 * 算法原理：
 * 分治思想最重要的一点是分解出的子问题是相互独立且结构特征相同的
 * 分治思想即通过层层粒度上的划分，将原问题划分为最小的子问题，然后再向上依次得到更高粒度的解。
 * 从上而下，再从下而上。先分解，再求解，再合并。
 *
 * 怎么分？遵循计算机的最擅长的重复运算，划分出来的子问题需要相互独立并且与原问题结构特征相同，这样能够保证解决子问题后，主问题也就能够顺势而解。
 * 怎么治？这就涉及到最基本子问题的求解，我们约定最小的子问题是能够轻易得到解决的，这样的子问题划分才具有意义，所以在治的环节就是需要对最基本子问题的简易求解。
 *
 */
public class DivideAndConquer {
    /**
     * 1. 归并排序
     *
     * @param arr 待排序数组
     * @param left 最小下标
     * @param right 最大下标
     */
    public static void mergeSort(int[] arr, int left, int right) {
        // 注意这个边界条件。
        // 最小的子问题情况是left = right - 1，这时候开始自下而上将子问题的解逐层递增融入主问题的求解中
        if (left < right) {
            int mid = ( left + right) / 2;
            // 1. 分
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            // 2. 治
            merge(arr, left, mid, right);
        }
    }
    // 合并已经排好序的两个数组段
    public static void merge(int[] arr, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                tmp[k++] = arr[i++];
            } else {
                tmp[k++] = arr[j++];
            }
        }
        // 把左边剩余的数移入数组
        while(i <= mid){
            tmp[k++] = arr[i++];
        }
        // 把右边边剩余的数移入数组
        while(j <= left){
            tmp[k++] = arr[j++];
        }
        // 把合并后的数组元素覆盖原数组
        if (tmp.length >= 0) {
            // 数组拷贝方法：src, srcPos, dest, destPos, length
            System.arraycopy(tmp, 0, arr, left, tmp.length);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, -1, 0, 9};
        mergeSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }
}
