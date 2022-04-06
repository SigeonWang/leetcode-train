package com.wxj.top100;

import java.util.*;

public class No20IsValid {
    /**
     * 20. 有效的括号 (简单)
     *
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     *
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *
     * 注意：s 仅由括号 '()[]{}' 组成
     *
     * @param s 字符串
     * @return 是否有效
     */
    public boolean isValid(String s) {
        // 如果s长度为奇数，一定不匹配，直接返回，节省效率
        if (s.length() % 2 != 0) {
            return false;
        }
        // 直接用栈解决
        Deque<Character> stack = new LinkedList<>();
        char[] arr = s.toCharArray();
        Map<Character, Character> charMap = new HashMap<>();
        charMap.put(')', '(');
        charMap.put('}', '{');
        charMap.put(']', '[');
        // "(){[]}"
        for (char c: arr) {
            // 左括号入栈
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                continue;
            }
            // 右括号判断
            if (stack.isEmpty() || stack.pop() != charMap.get(c)) {  // 注意这里stack不一定有值，不能直接pop()
               return false;
            }
        }
        return stack.isEmpty();  // 注意结束时如果栈不为空，匹配也是失败的
    }

    public static void main(String[] args) {
        No20IsValid c = new No20IsValid();
        String s = "[";
        String s1 = "]]";
        String s2 = "{}[()]";
        System.out.println(c.isValid(s));
    }
}
