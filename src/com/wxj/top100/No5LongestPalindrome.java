package com.wxj.top100;

import java.util.Arrays;

/**
 * 5. 最长回文字符串 [中等]
 *
 * 给你一个字符串 s，找到 s 中最长的回文子串。
 * 1 <= s.length <= 1000
 *
 * 示例：
 * 输入：s = "babad"
 * 输出："bab"
 * 解释："aba" 同样是符合题意的答案。
 */
public class No5LongestPalindrome {
    public String longestPalindrome(String s) {
        // 1. dp[i][j] 为 s[i, ... , j]是否为回文串
        // 2. dp[i][j] = true, s[i, ... , j] 是回文串
        //             = false, s[i, ... , j] 不是回文串 或 i > j
        // 则 dp[i][j] = dp[i + 1][j - 1] && s[i]==s[j], j >= i + 2
        // 3. dp[i][i] = true, dp[i][i+1] = true, 当 s[i] == s[i + 1]
        //                                = false, 当 s[i] != s[i+1]
        int m = s.length();
        if (m == 1) {
            return s;
        }
        boolean[][] dp = new boolean[m][m];
        int max = 1;
        int begin = 0;
        for (int i = 0; i < m; i ++) {
            // 子串长度为1时
            dp[i][i] = true;
        }
        // 开始递推
        // 先枚举子串长度 l
        for (int l = 2; l <= m; l ++) {
            for (int i = 0; i < m; i ++) {
                int j = i + l - 1;
                // 子串右边界越界，退出循环
                if (j >= m) {
                    break;
                }
                if (s.charAt(i) == s.charAt(j)) {
                    if (l <= 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && l > max) {
                    max = l;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + max);  // 左闭右开
    }

    public static void main(String[] args) {
        //
    }
}
