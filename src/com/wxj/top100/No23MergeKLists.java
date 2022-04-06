package com.wxj.top100;

import com.wxj.util.ListNode;
import com.wxj.util.ListNodeUtil;

import java.util.Arrays;

public class No23MergeKLists {
    /**
     * 23. 合并k个生序链表 (困难)
     *
     * 给你一个链表数组，每个链表都已经按升序排列。
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     *
     * 思路：分治
     *
     * @param lists 链表集合
     * @return 合并后的链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        int n = lists.length;
        if (n == 0) {
            return null;
        }
        if (n == 1) {
            return lists[0];
        }
        int mid = n / 2;
        // Arrays.copyOfRange() 左闭右开
        ListNode l1 = mergeKLists(Arrays.copyOfRange(lists, 0, mid));
        ListNode l2 = mergeKLists(Arrays.copyOfRange(lists, mid, n));
        return merge(l1, l2);
    }

    public ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode dummy = new ListNode(0, null);
        ListNode cur = dummy, cur1 = l1, cur2 = l2;
        while (cur1 != null && cur2 != null) {
            if (cur1.val < cur2.val) {
                cur.next = new ListNode(cur1.val, null);
                cur1 = cur1.next;
            } else {
                cur.next = new ListNode(cur2.val, null);
                cur2 = cur2.next;
            }
            cur = cur.next;
        }
        while (cur1 != null) {
            cur.next = new ListNode(cur1.val, null);
            cur1 = cur1.next;
            cur = cur.next;
        }
        while (cur2 != null) {
            cur.next = new ListNode(cur2.val, null);
            cur2 = cur2.next;
            cur = cur.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(1);
        l1.next.next = new ListNode(3);
        l1.next.next.next = new ListNode(4);
        ListNodeUtil.print(l1);

        ListNode l2 = new ListNode(0);
        l2.next = new ListNode(2);
        l2.next.next = new ListNode(3);
        ListNodeUtil.print(l2);

        ListNode l3 = new ListNode(-1);
        l3.next = new ListNode(2);
        l3.next.next = new ListNode(7);
        ListNodeUtil.print(l3);

        // ListNode[] arr = {l1, l2, l3};
        ListNode[] arr = {l1};

        No23MergeKLists c = new No23MergeKLists();
        ListNodeUtil.print(c.mergeKLists(arr));
    }
}
