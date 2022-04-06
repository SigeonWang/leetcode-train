package com.wxj.top100;

import scala.math.Ordering;

import java.util.*;

public class No17LetterCombinations {
    /**
     * 17. 电话号码的字母组合 (中等)
     *
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     *
     * 备注：其实就是数字全排序的升级版！
     *
     * @param digits 电话号码数字
     * @return 字母组合
     */
    public List<String> letterCombinations(String digits) {
        // 层次 + 枚举：回溯
        List<String> res = new ArrayList<>();
        if (digits.length() == 0) {
            return res;
        }
        Map<Character, String> charMap = new HashMap<>();
        charMap.put('1', "");
        charMap.put('2', "abc");
        charMap.put('3', "def");
        charMap.put('4', "ghi");
        charMap.put('5', "jkl");
        charMap.put('6', "mno");
        charMap.put('7', "pqrs");
        charMap.put('8', "tuv");
        charMap.put('9', "wxyz");
        StringBuilder track = new StringBuilder();
        backtrack(digits, res, track, charMap, 0);  // 通过序号控制遍历决策树的层级，也是退出回溯的条件
        return res;
    }
    public void backtrack(String digits, List<String> res, StringBuilder track, Map<Character, String> charMap, int index) {
        if (index == digits.length()) {  // 退出条件
            res.add(track.toString());
            return;
        }
        String chars = charMap.get(digits.charAt(index));
        for (int i = 0; i < chars.length(); i ++) {  // 选择列表
            track.append(chars.charAt(i));
            backtrack(digits, res, track, charMap, index + 1);
            track.deleteCharAt(index);
        }
    }

    public static void main(String[] args) {
        No17LetterCombinations c = new No17LetterCombinations();
        System.out.println(c.letterCombinations(""));

    }
}
