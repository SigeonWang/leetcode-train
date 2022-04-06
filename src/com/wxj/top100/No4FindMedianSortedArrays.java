package com.wxj.top100;


import java.util.Arrays;

/**
 * 4. 寻找两个正序数组的中位数 [困难]
 *
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的中位数 。
 * 算法的时间复杂度应该为 O(log(m+n)) 。
 *
 * 示例：
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 */
public class No4FindMedianSortedArrays {
    /**
     * 很直观的思路，合并数组找中位数；也可以维护两个指针，直接找到中间的数。
     * 但是时间复杂度不符合要求！
     * 前者时间复杂度是 O(m+n)，空间复杂度是 O(m+n)；后者空间复杂度能做到O(1)，但时间复杂赋不变。
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] mergeArray = mergeSortedArray(nums1, nums2);
        int len = mergeArray.length;
        if (len % 2 == 0) {
            // [0, 1, 3, 6], len=4, len/2=2
            return (double) (mergeArray[len / 2 - 1] + mergeArray[len / 2]) / 2;
        } else {
            // [0, 1, 3, 6, 7], len=5, len/2=2
            return (double) mergeArray[len / 2];
        }
    }

    // 归并
    public static int[] mergeSortedArray(int[] array1, int[] array2) {
        int[] res = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        while (i < array1.length && j < array2.length) {
            res[k] = Math.max(array1[i], array2[j]);
            if (array1[i] < array2[j]) {
                res[k++] = array1[i++];
            } else {
                res[k++] = array2[j++];
            }
        }
        while (i < array1.length) {
            res[k++] = array1[i++];
        }
        while (j < array2.length) {
            res[k++] = array2[j++];
        }
        return res;
    }

    /**
     * 关键1：时间复杂度的要求有 log，通常都需要用到二分查找！
     * 关键2：这道题可以转化成寻找两个有序数组中的第 k 小的数，其中 k 为 (m+n)/2 或 (m+n)/2 + 1。
     * 关键3：
     * 假设两个有序数组分别是 A 和 B。
     * 要找到第 k 个元素，我们可以比较 A[k/2−1] 和 B[k/2−1]，其中 / 表示整数除法（这怎么想得到的？？？）。
     * 由于 A[k/2−1] 和 B[k/2−1] 的前面分别有 A[0..k/2−2] 和 B[0..k/2−2]，即 k/2−1 个元素，对于 A[k/2−1] 和 B[k/2−1] 中的较小值，最多只会有 (k/2−1)+(k/2−1)≤k−2 个元素比它小，那么它就不能是第 k 小的数了。
     *
     * 归纳：
     * 如果 A[k/2-1] < B[k/2-1]，则比 A[k/2-1]A[k/2−1] 小的数最多只有 A 的前 k/2-1 个数和 B 的前 k/2-1 个数，即比 A[k/2-1] 小的数最多只有 k-2 个，因此 A[k/2-1] 不可能是第 k 个数，A[0] 到 A[k/2-1] 也都不可能是第 k 个数，可以全部排除；
     * 如果 A[k/2-1] > B[k/2-1]，则可以排除 B[0] 到 B[k/2-1]；
     * 如果 A[k/2-1] = B[k/2-1]，则可以归入第一种情况处理。
     *
     * 几种需要特殊处理的情况：
     * 如果 A[k/2-1] 或者 B[k/2-1] 越界，那么我们可以选取对应数组中的最后一个元素。在这种情况下，我们必须根据排除数的个数减少 k 的值，而不能直接将 k 减去 k/2；
     * 如果一个数组为空，说明该数组中的所有元素都被排除，我们可以直接返回另一个数组中第 k 小的元素；
     * 如果 k=1，我们只要返回两个数组首元素的最小值即可。
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArraysAnswer(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            return getKthElement(nums1, nums2, midIndex + 1);
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            return (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
        }
    }

    public static int getKthElement(int[] nums1, int[] nums2, int k) {
        /* 主要思路：要找到第 k (k>1) 小的元素，那么就取 pivot1 = nums1[k/2-1] 和 pivot2 = nums2[k/2-1] 进行比较
         * 这里的 "/" 表示整除
         * nums1 中小于等于 pivot1 的元素有 nums1[0 .. k/2-2] 共计 k/2-1 个
         * nums2 中小于等于 pivot2 的元素有 nums2[0 .. k/2-2] 共计 k/2-1 个
         * 取 pivot = min(pivot1, pivot2)，两个数组中小于等于 pivot 的元素共计不会超过 (k/2-1) + (k/2-1) <= k-2 个
         * 这样 pivot 本身最大也只能是第 k-1 小的元素
         * 如果 pivot = pivot1，那么 nums1[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums1 数组
         * 如果 pivot = pivot2，那么 nums2[0 .. k/2-1] 都不可能是第 k 小的元素。把这些元素全部 "删除"，剩下的作为新的 nums2 数组
         * 由于我们 "删除" 了一些元素（这些元素都比第 k 小的元素要小），因此需要修改 k 的值，减去删除的数的个数
         */
        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {0, 5};

        System.out.println(Arrays.toString(mergeSortedArray(arr1, arr2)));
        System.out.println(findMedianSortedArrays(arr1, arr2));

    }
}
