package com.wxj.algorithm;

/**
 * [递推和递归]
 *
 * 算法原理：
 * 递推思想的核心就是从已知条件出发，每一次推导的结果可以作为下一次推导的的开始，逐步推算出问题的解。
 * 递归算法的实现，是在函数或者子过程的内部，直接或间接的调用自己算法。所以在实现的过程中，最重要的是确定递归过程终止的条件，也就是迭代过程跳出的条件判断。
 *
 * 递推与递归区别：
 * 递推每一次推导的结果可以作为下一次推导的的开始，这似乎跟迭代、递归的思想有点类似，不过递推的范畴要更广一些。
 * 相较于递推而言，递归算法的范畴更小，要求子问题跟父问题的结构相同。递归算法实际上是把问题转化成规模更小的同类子问题，先解决子问题，再通过相同的求解过程逐步解决更高层次的问题，最终获得最终的解。
 *
 * 递归的优势：
 * 代码简洁且易读
 *
 * 递归的劣势（需要压栈和出栈）：
 * 1.容易产生"栈溢出"错误（stack overflow）
 * 因为需要同时保存成千上百个调用记录，所以递归非常耗费内存。无法预测运行期间内存的使用情况
 * 2. 效率方面，可能存在冗余计算
 * 速度慢
 *
 * 注意：普通递归的这些劣势可以通过尾递归的实现方式来避免。
 * 尾递归调用，精髓就是通过参数传递结果，达到不压栈的目的。但是不是所有编译器都支持尾递归的优化，Scala是有的，Java暂时没有。
 *
 */
public class Recursive {
    public static int hanoiCNT = 0;

    /**
     * 1. 兔子问题
     *
     * 描述：
     * 从3个月起每个月都生一对兔子，小兔子长到第3个月后每个月又生一对兔子，假如兔子都不死，问每个月的兔子总数为多少？
     *
     * 通用问题：有一对小兔子，小兔子过Z个月长大，一对大兔子X个月生Y对小兔子，求n个月后的兔子总对数。
     * f(n)= f(n-1)+ f(n-x-z) * (y/x)
     *
     * @param month 月
     * @return 兔子数
     */
    public static int rabbits(int month) {
        if (month < 1) {
            return 0;
        } else if (month == 1 || month == 2) {
            return 1;
        } else {
            // 从第三个月开始，每个月的兔子数是前两个月的和
            return rabbits(month - 1) + rabbits(month - 2);
        }
    }

    /**
     * 2. 汉诺塔问题
     * 汉诺塔(Tower of Hanoi)源于印度传说中，大梵天创造世界时造了三根金钢石柱子，其中一根柱子自底向上叠着64片黄金圆盘。
     * 大梵天命令婆罗门把圆盘从下面开始按大小顺序重新摆放在另一根柱子上。
     * 并且规定，在小圆盘上不能放大圆盘，在三根柱子之间一次只能移动一个圆盘。
     *
     * 这里我们先把上方的63个盘子看成整体，这下就等于只有两个盘子，自然很容易了，我们只要完成两个盘子的转移就行了。
     * 现在我们先不管第64个盘子，假设a柱只有63个盘子，与之前一样的解决方式，前62个盘子先完成移动目标。
     * 就这样一步步向前找到可以直接移动的盘子，62,61,60，......，2,1
     * 最终，最上方的盘子是可以直接移动到c柱的，那就好办了，我们的2号盘也能完成向c柱的转移，这时c柱上时已经转移成功的2个盘，于是3号盘也可以了，一直到第64号盘。
     *
     * 最终需要移动的次数为 2^n - 1
     *
     * @param num 层数
     * @param a 源柱子
     * @param b 中转柱子
     * @param c 目标柱子
     */
    public static void hanoiTower(int num, String a, String b, String c) {
        // 递归终止条件
        if (num == 1) {
            System.out.printf("将盘子 [%d] 从 %s 柱移动到 %s 柱. %n", num, a, c);
        } else {
            hanoiTower(num - 1, a, c, b);
            System.out.printf("将盘子 [%d] 从 %s 柱移动到 %s 柱. %n", num, a, c);
            hanoiTower(num - 1, b, a, c);
        }
        hanoiCNT++;

    }

    /**
     * 3. 爬楼梯问题
     *
     * 注意：也可以通过动态规划求解，即简单的一维DP问题
     *
     * @param n 楼梯阶数
     * @return 爬法数
     */
    // 普通递归实现方式
    public static int climbStairs(int n) {
        if (n <= 2) {
            return n;
        } else {
            return climbStairs(n - 1) + climbStairs(n - 2);
        }
    }

    /**
     * 尾递归
     *
     * @param n 阶数
     * @param cnt1 台阶数为1时的值，现为1
     * @param cnt2 台阶数为2时的值，现为2
     * @return 爬法数
     */
    public static int climbStairs2(int n, int cnt1, int cnt2) {
        // 假设res初始化为0
        if (n == 1) {
            // climbStairs2(1, 1, 2) = 1
            return cnt1;
        } else if (n == 2) {
            // climbStairs2(2, 1, 2) = 2
            return cnt2;
        } else {
            // 当n == 5, climbStairs2(5, 1, 2):
            // return climbStairs2(4, 2, 3)
            // return climbStairs2(3, 3, 5)
            // return climbStairs2(2, 5, 8)
            // return 8
            return climbStairs2(n - 1, cnt2, cnt1 + cnt2);  // ??? 怎么找到这个关系的
        }
    }
    // 递推实现
    // ??? 这和迭代什么区别
    public static int climbStairs3(int n) {
        if (n <= 2) {
            return n;
        }
        // 利用递推公式和循环计算
        int first = 1;  // 指针1台阶的爬法数
        int second = 2;  // 指针2台阶的爬法数
        int res = 0;
        for (int i = 3; i <= n; i++) {
            res = first + second;
            first = second;
            second = res;
        }
        return res;

    }

    public static int tailSum(int n, int sum) {
        if (n == 0) {
            return sum;
        }
        return tailSum(n - 1, n + sum);
    }

    public static void main(String[] args) {
        // System.out.println(rabbits(12));

        // hanoiTower(3, "a", "b", "c");
        // System.out.println("总共需要移动 " + hanoiCNT + " 次");

        System.out.println(climbStairs(10));
        System.out.println(climbStairs2(10, 1, 2));
        System.out.println(climbStairs3(10));

        // 会报错：Exception in thread "main" java.lang.StackOverflowError
        // 因为java是没有尾递归优化的，所以递归太深会导致栈溢出：
        //      -Xss设置的参数是针对每一个栈的，而非JVM所有线程栈内存总大小。
        //      每个方法的调用将创建一个栈帧。
        //      SUM(每个栈帧大小)>栈大小发生栈溢出。
        // 注意：同样的方法，在有尾递归优化的编译器中执行，就不会报错，如Scala
        // System.out.println(tailSum(1000000, 0));
    }
}
