package com.wxj.top100;


import java.util.*;

/**
 * 3. 无重复字符的最长子串 [中等]
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例:
 * 输入: s = "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 */
public class No3LengthOfLongestSubstring {
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> l = new HashSet<>();
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (l.contains(s.charAt(j))) {
                    max = Math.max(max, l.size());  // 最大值刷新2：有重复
                    l.clear();
                    break;
                }
                l.add(s.charAt(j));
                max = Math.max(max, l.size());  // 最大值刷新1：无重复
            }
        }
        return max;
    }

    public static int lengthOfLongestSubstringPro(String s) {
        Map<Character, Integer> m = new HashMap<>();
        int max = 0;
        int p = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = p; j < s.length(); j++) {
                p = j;
                if (m.containsKey(s.charAt(j)) && m.get(s.charAt(j)) >= i) {
                    max = Math.max(max, j - i);  // 最大值刷新2：有重复
                    i = m.get(s.charAt(j));
                    break;
                }
                m.put(s.charAt(j), j);
                max = Math.max(max, j - i + 1);  // 最大值刷新1：无重复
            }
        }
        return max;
    }

    public static int lengthOfLongestSubstringAnswer(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    public static void main(String[] args) {
        String s1 = "pwwkew";
        System.out.println(lengthOfLongestSubstringPro(s1));
    }

}
