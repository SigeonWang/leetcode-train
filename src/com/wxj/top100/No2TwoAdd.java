package com.wxj.top100;

import com.wxj.util.ListNode;


/**
 * 2. 两数相加 [中等]
 * 给你两个非空的链表，表示两个非负的整数。它们每位数字都是按照逆序的方式存储的，并且每个节点只能存储一位数字。
 * 请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例 ：
 * 输入：l1 = [2,4,3], l2 = [5,6,4]
 * 输出：[7,0,8]
 * 解释：342 + 465 = 807
 */
public class No2TwoAdd {
    /**
     * 自解
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode twoAdd(ListNode l1, ListNode l2) {
        ListNode l3 = new ListNode();
        // 踩坑1：保留一个头指针，用于返回
        ListNode header = l3;
        int jinNum = 0;
        while (l1 != null && l2 != null) {
            int num = l1.val + l2.val + jinNum;
            l3.val = num % 10;
            jinNum = num / 10;
            l1 = l1.next;
            l2 = l2.next;
            // 踩坑2：需要增加判断，在l1或l2节点没有遍历完的时候才增加l3的节点
            if (l1 != null || l2 != null) {
                l3.next = new ListNode();
                l3 = l3.next;
            }
        }
        while (l2 != null) {
            int num = l2.val + jinNum;
            l3.val = num % 10;
            jinNum = num / 10;
            l2 = l2.next;
            if (l2 != null) {
                l3.next = new ListNode();
                l3 = l3.next;
            }
        }
        while (l1 != null) {
            int num = l1.val + jinNum;
            l3.val = num % 10;
            jinNum = num / 10;
            l1 = l1.next;
            if (l1 != null) {
                l3.next = new ListNode();
                l3 = l3.next;
            }
        }
        // 踩坑3：注意最后还可能有进位！
        if (jinNum > 0) {
            l3.next = new ListNode(jinNum);
        }
        return header;
    }

    /**
     * 官方参考答案
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode twoAddAnswer1(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;  // 亮点1：一起赋值，简化代码
        int carry = 0;
        while (l1 != null || l2 != null) {
            int n1 = l1 != null ? l1.val : 0;  // 亮点2：简单的if else赋值逻辑，直接用? :三目运算符代替
            int n2 = l2 != null ? l2.val : 0;
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);  // 亮点3：没有直接初始化，避免l1和l2都为null返回错误的链表
            } else {
                tail.next = new ListNode(sum % 10);  // 关键点：都是要new节点建立新链表的
                tail = tail.next;
            }
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }

    /**
     * 其他解法：利用递归
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode twoAddAnswer2(ListNode l1, ListNode l2) {
        return add(l1, l2, 0);
    }

    /**
     返回两个链表相加的头部
     */
    public static ListNode add(ListNode l1, ListNode l2, int bit) {
        if (l1 == null && l2 == null && bit == 0) {
            return null;
        }
        int val = bit;
        if (l1 != null) {
            val += l1.val;
            l1 = l1.next;
        }
        if (l2 != null) {
            val += l2.val;
            l2 = l2.next;
        }
        ListNode node = new ListNode(val % 10);
        node.next = add(l1, l2, val / 10);
        return node;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        l1.next = new ListNode(9);
        l1.next.next = new ListNode(9);
        l1.next.next.next = new ListNode(9);

        ListNode l2 = new ListNode(9);
        l2.next = new ListNode(9);
        l2.next.next = new ListNode(9);
//        l2.next.next.next = new ListNode(9);

        System.out.println(l1.toString());
        System.out.println(l2.toString());

        System.out.println(twoAdd(l1, l2).toString());

    }
}
