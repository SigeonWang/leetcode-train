package com.wxj.top100;

/**
 * 11. 盛水最多的容器 (中等)
 *
 * 给定一个长度为 n 的整数数组 height。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i])。
 * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 * 返回容器可以储存的最大水量。
 * 说明：你不能倾斜容器。
 *
 * n == height.length
 * 2 <= n <= 10^5
 * 0 <= height[i] <= 10^4
 *
 */
public class No11MaxArea {
    // 很容易想到穷举的思路，且 s = min(height[i], height[j]) * (j - i), j > i
    // 但是时间复杂度太差，会报错：超出时间限制 ...
    public int maxArea(int[] height) {
        int n = height.length;
        int max = 0;
        for (int i = 0; i < n - 1; i ++) {
            for (int j = i + 1; j < n; j ++) {
                max = Math.max(max, Math.min(height[i], height[j]) * (j - i));
            }
        }
        return max;
    }
    // 我的第一感觉也是维护两个指针处理，但是具体处理没想清楚...
    // 题解：双指针（对撞指针）
    // 关键：每次移动高度较小的指针，因为可以简单证明移动较大高度的指针后，新面积一定不大于原来的面积
    public int maxAreaPro(int[] height) {
        int max = 0;
        int left = 0;
        int right = height.length - 1;
        while (left != right) {
            max = Math.max(max, Math.min(height[left], height[right]) * (right - left));
            if (height[left] <= height[right]) {
                left ++;
            } else {
                right --;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        No11MaxArea c = new No11MaxArea();
        System.out.println(c.maxAreaPro(height));
    }
}
