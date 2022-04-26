package com.wxj.top100;

import java.util.Deque;
import java.util.LinkedList;

public class No42Trap {
    /**
     * 42. 接雨水 (困难)
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     *
     * 1 <= height.length <= 2 * 104
     *
     * @param height 高度集合
     * @return 雨水量
     */
    public int trap(int[] height) {
        // 第一感觉：双指针（类似的，No11求最大面积也是通过双指针解决的）
        // 最关键的点：下标 i 处能接的雨水量等于下标 i 两边的最大高度的最小值减去下标 i 处的高度（在高度差大于i处高度的情况下）。
        // 即 res[i] = min(leftMax, rightMax) - height[i]
        // 方法0：穷举 O(n2) 超时
        int len = height.length;
        int res = 0;
        int[] leftMax = new int[len], rightMax = new int[len];
        for (int i = 0; i < len; i ++) {
            for (int j = 0; j < len; j ++) {
                if (j < i) {
                    leftMax[i] = Math.max(leftMax[i], height[j]);
                }
                if (j > i) {
                    rightMax[i] = Math.max(rightMax[i], height[j]);
                }
            }
            int temp = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (temp > 0) {
                res += temp;
            }
        }
        return res;
    }

    public int trap1(int[] height) {
        // 方法1：动态规划 O(n)，需要分别对左边和右边进行动态规划
        // 1）leftMax[i]表示位置i及其左边的位置中，height 的最大高度；rightMax[i]表示位置i及其右边的位置中，height 的最大高度
        // 2）leftMax[i] = max(leftMax[i-1], height[i])；rightMax[i] = max(rightMax[i+1], height[i])
        // 3）leftMax[0]=height[0]；rightMax[n-1]=height[n-1] n>0
        int len = height.length;
        if (len == 0) {
            return 0;
        }

        int res = 0;
        int[] leftMax = new int[len];
        leftMax[0] = height[0];
        for (int i = 1; i < len; i ++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        int[] rightMax = new int[len];
        rightMax[len - 1] = height[len - 1];
        for (int i = len - 2; i >= 0; i --) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        for (int i = 0; i < len; i ++) {
            res += Math.min(leftMax[i], rightMax[i]) - height[i];
        }

        return res;
    }

    public int trap2(int[] height) {
        // 方法2：单调栈
        // 维护一个单调栈，单调栈存储的是下标，满足从栈底到栈顶的下标对应的数组 height 中的元素递减。
        // 从左到右遍历数组，遍历到下标 i 时，如果栈内至少有两个元素，记栈顶元素为 top，top 的下面一个元素是 left，则一定有 height[left] ≥ height[top]。
        // 如果 height[i] > height[top]，则得到一个可以接雨水的区域，该区域的宽度是 i−left−1，高度是 min(height[left], height[i]) − height[top]，根据宽度和高度即可计算得到该区域能接的雨水量。
        // 为了得到 left，需要将 top 出栈。在对 top 计算能接的雨水量之后，left 变成新的 top，重复上述操作，直到 "栈变为空" 或者 "栈顶下标对应的 height 中的元素大于或等于 height[i]"。
        // 在对下标 i 处计算能接的雨水量之后，将 i 入栈，继续遍历后面的下标，计算能接的雨水量，直到遍历结束。
        int len = height.length;
        int res = 0;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < len; ++i) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int top = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int currWidth = i - left - 1;
                int currHeight = Math.min(height[left], height[i]) - height[top];
                res += currWidth * currHeight;
            }
            stack.push(i);
        }
        return res;
    }

    public int trap3(int[] height) {
        // 方法3：双指针 O(n)
        // 维护两个指针 left 和 right，以及两个变量 leftMax 和 rightMax，初始时 left=0,right=n−1,leftMax=0,rightMax=0。
        // 指针 left 只会向右移动，指针 right 只会向左移动，在移动指针的过程中维护两个变量 leftMax 和 rightMax 的值。
        // 当两个指针没有相遇时，进行如下操作:
        //      使用 height[left] 和 height[right] 的值更新 leftMax 和 rightMax 的值;
        //      如果 height[left] < height[right]，则必有 leftMax < rightMax，下标 left 处能接的雨水量等于 leftMax − height[left]，将下标 left 处能接的雨水量加到能接的雨水总量，然后将 left 加 1（即向右移动一位）；
        //      如果 height[left] ≥ height[right]，则必有 leftMax ≥ rightMax，下标 right 处能接的雨水量等于 rightMax − height[right]，将下标 right 处能接的雨水量加到能接的雨水总量，然后将 right 减 1（即向左移动一位）。
        //当两个指针相遇时，即可得到能接的雨水总量。

        // 双指针的理解：
        // 其实是i, j两个位置一起接水，但省略了两个max!
        // 当i的左边最大值小于j的右边最大值时，i的左边最大值一定小于i的右边最大值，所以此时i位置处可接得的水量就是leftMax-height[left]
        int res = 0;
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        while (left < right) {
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if (height[left] < height[right]) {
                res += leftMax - height[left];
                left ++;
            } else {
                res += rightMax - height[right];
                right --;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        No42Trap c = new No42Trap();
        int[] height = {4,2,0,3,2,5};
        System.out.println(c.trap3(height));
    }
}
