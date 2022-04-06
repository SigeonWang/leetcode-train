package com.wxj.top100;

import com.wxj.util.ListNode;
import com.wxj.util.ListNodeUtil;

public class No21MergeTwoLists {
    /**
     * 21. 合并两个有序链表 (简单)
     *
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     *
     * 两个链表的节点数目范围是 [0, 50]
     *
     * @param list1  链表1
     * @param list2 链表2
     * @return 合并之后的链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 类似二分查找的合并有序数组的过程
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        ListNode dummy = new ListNode(0, null);
        ListNode cur = dummy, cur1 = list1, cur2 = list2;
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

        No21MergeTwoLists c = new No21MergeTwoLists();
        ListNodeUtil.print(c.mergeTwoLists(l1, l2));
    }
}
