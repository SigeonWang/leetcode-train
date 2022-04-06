package com.wxj;

import com.wxj.util.ListNode;

import java.lang.reflect.Array;
import java.util.*;

public class Test {
    static int VALUE;  // 静态变量，会自动初始化
    int val;  // 成员变量、类变量，会自动初始化

    // 这里num是形式参数
    public static void add(int num) {
        num += 1;
    }

    public static void concat(String str) {
        str = str.substring(0, 3);
    }

    public static void append(int[] array) {
        array[0] = -1;
    }

    public static void add(ListNode ln) {
        ln.next = new ListNode(3);
    }

    public static void main(String[] args) {
        int a = 3;  // 局部变量，不会自动初始化，需要手动初始化
        add(a);  // 这里a是实际参数
//        System.out.println(a);  // a 没有改变

//        String s = "hello";
//        concat(s);  // 这里a是实际参数
//        s = s.substring(0, 3);
//        System.out.println(s);  // s 没有改变

        int[] l = {0, 1};
        append(l);  // 这里a是实际参数
//        System.out.println(Arrays.toString(l));  // s 改变了

        ListNode l1 = new ListNode(2);
        add(l1);
//        System.out.println(l1);  // l1改变了

        ListNode l2 = new ListNode(2);
        l2 = l2.next;
        l2 = new ListNode(3);
//        System.out.println(l2);  // l2 没有改变，因为没有给链表增加元素，最终只是让l2这个指针指向了ListNode(3)

//        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
//            i = i + 2;
//
//        }

        int[] arr = {1, 2, 5, 3, -1};
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, 2)));


    }

}
