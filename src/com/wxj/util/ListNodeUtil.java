package com.wxj.util;

import java.util.ArrayList;
import java.util.List;

public class ListNodeUtil {
    public static int size(ListNode head) {
        ListNode cur = head;
        int size = 0;
        while (cur != null) {
            cur = cur.next;
            size ++;
        }
        return size;
    }

    public static ListNode insert(ListNode head, int index, ListNode node) {
        int size = size(head);
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        for (int i = 0; i < index; i ++) {  // 注意：这里是找待删除结点的前驱结点，所以i最大遍历到index-1
            cur = cur.next;
        }
        node.next = cur.next;  // 注意：这里顺序不能颠倒
        cur.next = node;
        return dummy.next;
    }

    public static ListNode insert(ListNode head, ListNode node) {
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while (cur.next != null) {  // 注意：这里是找最后一个结点
            cur = cur.next;
        }
        cur.next = node;
        return dummy.next;
    }

    public static ListNode delete(ListNode head, int index) {
        int size = size(head);
        if (index < 0 || index + 1 > size) {
            throw new IndexOutOfBoundsException();
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        for (int i = 0; i < index; i ++) {  // 注意：这里是找待删除结点的前驱结点，所以i最大遍历到index-1
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return dummy.next;
    }

    public static void print(ListNode head) {
        List<Integer> res = new ArrayList<>();
        ListNode cur = head;  // 临时指针，指示当前结点
        while (cur != null) {
            res.add(cur.val);
            cur = cur.next;
        }
        System.out.println(res);
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);  // 如果链表要增加元素，必须把ListNode赋值到l.next，这样才能确定链接关系。
        l1.next.next = new ListNode(3);

        print(l1);
//        System.out.println(size(l1));
        print(insert(l1, 0, new ListNode(1)));
//        print(delete(l1, 2));
    }
}
