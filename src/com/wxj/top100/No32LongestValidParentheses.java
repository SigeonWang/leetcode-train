package com.wxj.top100;

import java.util.Deque;
import java.util.LinkedList;

public class No32LongestValidParentheses {
    /**
     * 32. 最长有效括号 (困难)
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     *
     * 0 <= s.length <= 3 * 104
     *
     * @param s 字符串
     * @return 有效括号的最大长度
     */
    public int longestValidParentheses(String s) {
        // 第一感觉：动态规划，想了想没有思路。
        // 注意：有效括号一定是"("开头")"结尾
        // 换种思路：简单暴力点，直接双循环穷举 + 验证是否有效（想过用双指针，但是发现不好判断如何移动指针）。但是超时了。。。O(n3)
        char[] arr = s.toCharArray();
        int len = arr.length;
        int max = 0;
        for (int i = 0; i < len; i ++) {
            if (arr[i] != '(') {
                continue;
            }
            for (int j = i + 1; j < len; j ++) {
                if (arr[j] != ')') {
                    continue;
                }
                if (isValid(s.substring(i, j + 1))) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }
        return max;
    }
    public boolean isValid(String s) {
        int balance = 0;
        for (char c: s.toCharArray()) {
            if (c == '(') {
                balance ++;
            }
            if (c == ')') {
                // 至少出现过一个 "(" 才能出现 ")"
                if (balance < 1) {
                    return false;
                }
                balance --;
            }
        }
        return balance == 0;
    }

    public int longestValidParenthesesPro(String s) {
        // 解答一：动态规划 (有点难想)
        // 1. 设dp[i]是以s[i]为结尾的有效括号的最大长度
        // 2. 如果s[i]==')':
        //          如果s[i-1]s[i] == '()'，dp[i] = dp[i-2] + 2
        //          如果s[i-1]s[i] == '))' 且 s[i- dp[i-1] - 1] == '('，则dp[i] = dp[i − dp[i−1] − 2] + dp[i-1] + 2（太难想了这一步==）
        //    其他：dp[i] = 0
        // 3. dp[0] = 0, dp[1] = 2 if s[0]s[1] == '('')' else 0
        int len = s.length();
        int max = 0;
        int[] dp = new int[len];  // 默认都是0
        for (int i = 1; i < len; i ++) {
            if (s.charAt(i - 1) == '(' && s.charAt(i) == ')') {
                dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
            }
            // 注意这里的判断比较复杂！
            if (s.charAt(i - 1) == ')' && s.charAt(i) == ')' && i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                dp[i] = ((i - dp[i - 1] - 2) >= 0 ? dp[i - dp[i - 1] - 2] : 0) + dp[i - 1] + 2;
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int longestValidParenthesesPro2(String s) {
        // 解答二：栈 (推荐)
        // 通过栈，我们可以在遍历给定字符串的过程中去判断到目前为止扫描的子串的有效性，同时能得到最长有效括号的长度。
        //
        // 具体思路：
        // 始终保持栈底元素为当前已经遍历过的元素中「最后一个没有被匹配的右括号的下标」（这样的做法主要是考虑了边界条件的处理），栈里其他元素维护左括号的下标。
        // 对于遇到的每个 '(' ，我们将它的下标放入栈中；
        // 对于遇到的每个 ')' ，先弹出栈顶元素表示匹配了当前右括号：
        //      如果栈为空，说明当前的右括号为没有被匹配的右括号，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的右括号的下标」
        //      如果栈不为空，当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
        //
        // 注意：
        // 如果一开始栈为空，第一个字符为左括号的时候我们会将其放入栈中，这样就不满足提及的「最后一个没有被匹配的右括号的下标」。
        // 为了保持统一，我们在一开始的时候往栈中放入一个值为 -1 的元素。
        char[] arr = s.toCharArray();
        int max = 0;
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        for (int i = 0; i < arr.length; i ++) {
            if (arr[i] == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (!stack.isEmpty()) {
                    max = Math.max(max, i - stack.peek());  // 注意这里peek，不能直接pop出去
                } else {
                    stack.push(i);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        No32LongestValidParentheses c = new No32LongestValidParentheses();
        String s = "())";
        System.out.println("输入：" + s);
        System.out.println("最长有效括号：" + c.longestValidParenthesesPro2(s));
    }
}
