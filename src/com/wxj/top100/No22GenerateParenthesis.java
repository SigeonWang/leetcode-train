package com.wxj.top100;

import java.util.*;

public class No22GenerateParenthesis {
    /**
     * 22. 括号生成 (中等)
     *
     * 数字 n 代表生成括号()的对数，请你设计一个函数，用于能够生成所有可能的并且有效的括号组合。
     *
     * 输入：n = 3
     * 输出：["((()))","(()())","(())()","()(())","()()()"]
     *
     * @param n 括号对数
     * @return 有效括号组合的集合
     */
    public List<String> generateParenthesis(int n) {
        // 第一感觉：回溯（然后具体呢...）
        // 思路：
        // 只在序列仍然保持有效时才添加 '(' or ')'。
        // 我们可以通过跟踪到目前为止放置的左括号和右括号的数目来做到这一点，
        // 如果左括号数量不大于 n，我们可以放一个左括号。如果右括号数量小于左括号的数量，我们可以放一个右括号。
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        trackback(n, res, sb, 0, 0);
        return res;
    }
    public void trackback(int n, List<String> res, StringBuilder sb, int open, int close) {
        if (sb.length() == n * 2) {
            res.add(sb.toString());
            return;
        }
        // 回溯也可以分两部分！
        if (open < n) {  // 先插入(
            sb.append('(');
            trackback(n, res, sb, open + 1, close);
            sb.deleteCharAt(sb.length() - 1);
        }
        if (close < n) {  // 后插入)
            sb.append(')');
            trackback(n, res, sb, open, close + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    // 暴力生成所以序列 + 检测是否有效
    // 因为只有一种括号，可以不通过栈，而是简单通过一个变量 balance 表示左括号的数量减去右括号的数量来判断是否有效
    public List<String> generateParenthesisPro1(int n) {
        List<String> res = new ArrayList<>();
        char[] cur = new char[2 * n - 1];
        generateAll(res, 0, cur);
        return res;
    }
    public void generateAll(List<String> res, int pos, char[] cur) {
        if (pos == cur.length) {
            if (isValid(cur)) {
                res.add(new String(cur));
            }
        } else {
            cur[pos] = '(';
            generateAll(res, pos + 1, cur);
            cur[pos] = ')';
            generateAll(res, pos + 1, cur);
        }
    }
    public boolean isValid(char[] cur) {
        int balance = 0;
        for (char c: cur) {
            if (c == '(') {
                balance ++;
            } else {
                balance --;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }

    // 利用"递归 + HashSet去重"
    // 比较暴力，效率不高
    public List<String> generateParenthesisPro2(int n) {
        if (n == 1) {
            return Arrays.asList("()");
        }
        Set<String> set = new HashSet<>();
        for (String s: generateParenthesisPro2(n-1)) {  // 遍历每个有效符号组合
            for (int i = 0; i < 2 * n - 2; i ++) {  // 在每个位置插入"()", 通过set去重
                set.add(s.substring(0, i) + "()" + s.substring(i, s.length()));
            }
        }
        return new ArrayList<>(set);
    }

    public static void main(String[] args) {

    }
}
