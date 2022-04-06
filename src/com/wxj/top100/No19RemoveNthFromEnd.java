package com.wxj.top100;

import com.wxj.util.ListNode;
import com.wxj.util.ListNodeUtil;

import java.util.Deque;
import java.util.LinkedList;

public class No19RemoveNthFromEnd {
    /**
     * 删除链表的第n个结点 (中等)
     *
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 链表中结点的数目为 sz
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     *
     * @param head 链表头
     * @param n 倒数第n个
     * @return 新链表
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 关键：怎么找到倒数第n个结点，然后交换倒数n+1结点的指针指向倒数n-1即可
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;  // 指示当前位置的指针
        ListNode[] nodes = new ListNode[n + 1];
        int cnt = 0;
        while (cur != null) {
            nodes[cnt % (n + 1)] = cur;
            cur = cur.next;
            cnt ++;
        }
        // 倒数第n个结点（正数cnt + 1 - n）在nodes数组里的序号是 (cnt - n) % (n + 1)
        nodes[(cnt - n - 1) % (n + 1)].next = nodes[(cnt - n) % (n + 1)].next;
        return dummy.next;
    }

    /**
     * 重要：
     * 在对链表进行操作时，一种常用的技巧是添加一个哑节点（dummy node），它的 next 指针指向链表的头节点。
     * 这样一来，我们就不需要对头节点进行特殊的判断了！！！
     *
     * 例如，在本题中，如果我们要删除节点 y，我们需要知道节点 y 的前驱节点 x，并将 x 的指针指向 y 的后继节点。
     * 但由于头节点不存在前驱节点，因此我们需要在删除头节点时进行特殊判断。
     * 但如果我们添加了哑节点，那么头节点的前驱节点就是哑节点本身，此时我们就只需要考虑通用的情况即可。
     *
     * // 几种思路
     * 方法1：链表长度。先进行一次遍历，算出链表长度，然后再进行一次遍历，删除正数第size + 1 - n个结点即可
     * 方法2：栈。利用栈先进后出的特点，先遍历入栈，然后弹出n个结点，栈顶的就是第n个结点的前驱结点，直接修改指针即可
     * 方法3：双指针。
     *
     */
    public ListNode removeNthFromEndPro1(ListNode head, int n) {
        // 利用链表长度
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        int len = getListSize(head);
        for (int i = 1; i < len + 1 - n; i ++) {  // 与题目对应，编号从1开始，遍历结束的结点就是需要删除结点的前驱结点
            cur = cur.next;
        }
        cur.next = cur.next.next;
        return dummy.next;
    }
    public int getListSize(ListNode head) {
        int size = 0;
        ListNode cur = head;
        while (cur != null) {
            cur = cur.next;
            size ++;
        }
        return size;
    }

    public ListNode removeNthFromEndPro2(ListNode head, int n) {
        // 利用栈
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        // 注意：Java堆栈Stack类已经过时，官方推荐使用双端队列Deque替代，可作为队列、栈、双端队列使用
        // Deque最常用实现是LinkedList类：Deque deque = new LinkedList()
        // Deque堆栈操作方法：push()、pop()、peek()
        Deque<ListNode> stack = new LinkedList<>();
        while (cur != null) {
            stack.push(cur);  // 入栈
            cur = cur.next;
        }
        for (int i = 0; i < n; i ++) {
            stack.pop();  // 出栈
        }
        ListNode prev = stack.peek();  // 取出栈顶元素，但是不会删除。待删除结点的前驱结点
        prev.next = prev.next.next;
        return dummy.next;
    }

    public ListNode removeNthFromEndPro3(ListNode head, int n) {
        // 利用双指针
        ListNode dummy = new ListNode(0, head);
        ListNode first = head;
        ListNode second = dummy;  // 指向dummy结点是为了遍历结束能指到待删除结点的前驱结点
        for (int i = 0; i < n; i ++) {
            first = first.next;  // first比second领先了n+1个结点
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;  // 此时second即是待删除结点的前驱结点
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode list = new ListNode(1);
        list.next = new ListNode(2);  // 如果链表要增加元素，必须把ListNode赋值到l.next，这样才能确定链接关系。
        list.next.next = new ListNode(3);
        list.next.next.next = new ListNode(4);
        list.next.next.next.next = new ListNode(5);
        ListNodeUtil.print(list);

        No19RemoveNthFromEnd c = new No19RemoveNthFromEnd();
//        ListNode res = c.removeNthFromEnd(list, 1);
        ListNode res = c.removeNthFromEndPro3(list, 1);
        ListNodeUtil.print(res);
    }
}
