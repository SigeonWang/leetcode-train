package com.wxj.top100;

/**
 * 10. 正则表达式 (困难)
 *
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素 (* 前面只能是 a-z, . ???)
 * 所谓匹配，是要涵盖整个字符串 s 的，而不是部分字符串。
 *
 * 1 <= s.length <= 20
 * 1 <= p.length <= 30
 * s 只包含从 a-z 的小写字母。
 * p 只包含从 a-z 的小写字母，以及字符 . 和 *
 * 保证每次出现字符 * 时，前面都匹配到有效的字符
 *
 */
public class No10IsMatch {
    // 具体见动态规划
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];  // boolean 类型元素会自动初始化为 false
        dp[0][0] = true;
        for (int i = 0; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (p.charAt(j - 1) != '*') {
                    if (charMatch(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    dp[i][j] = dp[i][j - 2];
                    if (charMatch(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j - 2] || dp[i - 1][j];
                    }
                }

            }
        }
        return dp[m][n];
    }
    public boolean charMatch(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }
}
