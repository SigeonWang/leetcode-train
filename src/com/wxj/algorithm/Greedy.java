package com.wxj.algorithm;

import java.util.HashMap;

/**
 * 贪心
 *
 * 1. 算法原理:
 * 贪心算法的实现过程就是从问题的一个初始解出发，每一次都作出「当前最优」的选择，直至遇到局部极值点。
 * 贪心所带来的局限性很明显，就是无法保证最后的解是最优的，很容易陷入局部最优的情况。
 * 但是它每一次做选择的速度很快，同时判断条件简单，能够比较快速的给出一种差不多的解决方案。
 *
 * 这是一种算法策略，每次选择得到的都是局部最优解。
 * 选择的策略必须具备无后效性，即某个状态以前的过程不会影响以后的状态，只与当前状态有关。
 *
 * 2. 大致步骤如下:
 * 1）建立数学模型来描述问题；
 * 2）把求解的问题分成若干个子问题
 * 3）对每一个子问题求解，得到子问题的局部最优解
 * 4）把子问题的局部最优解合成原问题的一个解
 *
 * 3. 代码框架:
 * 从问题的某一初始解出发；
 * while （能朝给定总目标前进一步）{
 *    利用可行的决策，求出可行解的一个解元素；
 * }
 * 由所有解元素组合成问题的一个可行解；
 *
 * 4. 存在的问题:
 * 1）不能保证求得的最后解是最佳的；
 * 2）不能用来求最大最小解问题；
 * 3）只能在某些特定条件约束的情况下使用，例如贪心策略必须具备无后效性等。
 *
 * 5. 典型的贪心算法使用领域
 * 马踏棋盘、背包、装箱等
 *
 */
public class Greedy {
    /**
     * 1. 旅行商问题 Traveling Salesman Problem TSP
     *
     * 有若干个城市，任何两个城市之间的距离都是确定的。
     * 现要求一旅行商从某城市出发必须经过每一个城市且只在一个城市逗留一次，最后回到出发的城市，求最短的路径是多少。
     *
     * @param city 城市
     * @param dist 城市间距离
     * @return 最短路径
     */
    public static int tsp(String[] city, int[][] dist, int start) {
        // 针对TSP问题，使用贪心算法的求解的过程为：
        // 从一节点出发遍历所有能到达的下一节点，选择距离最近的节点作为下一节点;
        // 然后把当前节点标记已走过，下一节点作为当前节点;
        // 重复贪心策略，以此类推直至所有节点都标记为已走过
        int n = city.length;
        int[] gone = new int[n];  // 已经走过的城市
        int now = start;  // 当前城市的下标 (初始化为起始点)
        int next = 0;  // 下一个城市
        int sum = 0;  // 当前走过的距离
        int cnt = 1;  // 走到了第几个城市
        while (gone[now] == 0) {
            int[] tempDist = new int[n];
            for (int i = 0; i < n; i ++) {
                if (i != now && gone[i] == 0) {
                    tempDist[i] = dist[now][i];
                }
            }
            next = getMinCity(tempDist, cnt, start);
            sum = sum + dist[now][next];
            gone[now] = 1;  // 已经走过
            System.out.println("从 " + city[now] + " 到 " + city[next]);
            now = next;  // 继续
            cnt ++;
        }
        return sum;
    }
    public static int getMinCity(int[] dist, int cnt, int start) {
        int k = 0;
        int min = Integer.MAX_VALUE;
        if (cnt == dist.length) {
            return start;
        } else {
            for (int i = 0; i < dist.length; i++) {
                if (dist[i] < min && dist[i] > 0) {
                    min = dist[i];
                    k = i;
                }
            }
        }
        return k;
    }

    /**
     * 2-1. 最长回文串 (简单)
     *
     * 给定一个包含大写字母和小写字母的字符串 s，返回通过这些字母构造成的最长的回文串长度。
     * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。
     *
     * 思路：枚举
     *
     * @param s 字符串
     * @return 最长回文串长度
     */
    public static int longestPalindrome(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        int n = s.length();
        int max = 0;
        int cnt = 0;  // 字符总数为单数的字符数
        for (int i = 0; i < n; i ++) {
            Character c = s.charAt(i);
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
                if (map.get(c) % 2 == 0) {
                    max += 2;
                    cnt --;
                } else {
                    cnt ++;
                }
            } else {
                map.put(c, 1);
                cnt ++;
            }
        }
        max = cnt > 0 ? max + 1 : max;
        return max;
    }
    /**
     * 2-2. 最长回文串 (简单)
     *
     * 思路：贪心
     * 这为啥是贪心 ???
     *
     * @param s 字符串
     * @return 最长回文串长度
     */
    public static int longestPalindrome2(String s) {
        // 因为字符可以转化为数字，所以直接用数组存储字符出现的次数，节省空间
        // a-z: 97-122; A-Z: 65-90;
        int[] arr = new int[128];
        for(char c : s.toCharArray()) {
            arr[c]++;
        }
        int count = 0;  // 次数为奇数的个数
        for (int i : arr) {
            count += (i % 2);
        }
        return count == 0 ? s.length() : (s.length() - count + 1);
    }

    public static void main(String[] args) {
//        String[] city = {"A", "B", "C", "D"};
//        int[][] dist = {{0, 2, 4, 5}, {2, 0,4, 4}, {4, 4, 0, 2}, {5, 4, 2, 0}};
//        int sum = tsp(city, dist, 0);
//        System.out.println(sum);

        System.out.println(longestPalindrome("aabc"));
    }
}
