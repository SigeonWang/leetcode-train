package com.wxj.algorithm;

/**
 * [动态规划]
 *
 * 一、算法原理：
 * 我们知道分治思想最重要的一点是分解出的子问题是相互独立且结构特征相同的。
 * 这一点并不是所有问题都能满足，许多问题的划分的子问题往往都是相互重叠且互相影响的，那么就很难使用分治算法进行有效而又干净的子问题划分。
 *
 * 于是乎，动态规划来了。动态规划同样需要将问题划分为多个子问题，但是子问题之间往往不是互相独立的。
 * 当前子问题的解可看作是前多个阶段问题的完整总结。
 * 因此这就需要在在子问题求解的过程中进行多阶段的决策，同时当前阶段之前的决策都能够构成一种最优的子结构。这就是所谓的最优化原理。
 *
 * 两大原则：
 * 1、最优子结构
 * 就是DP 状态最优值由更小规模的 DP 状态最优值推出，此处 DP 状态即为子问题。
 * 2、无后效性
 * 就是无论 DP 状态是如何得到的，都不会影响后续 DP 状态的取值
 *
 * 动态规划是在目前看来非常不接近人类思维方式一种算法，主要原因是在于人脑在演算的过程中很难对每一次决策的结果进行记忆。
 * 动态规划在实际的操作中，往往需要额外的空间对每个阶段的状态数据进行保存，以便下次决策的使用。
 *
 * 动态规划的求解思路：动归的开始需要将问题按照一定顺序划分为各个阶段，然后确定每个阶段的状态，如图中节点的F0等。
 * 然后重点是根据决策的方法来确定状态转移方程。也就是需要根据当前阶段的状态确定下一阶段的状态。
 * 在这个过程中，下一状态的确定往往需要参考之前的状态。因此需要在每一次状态转移的过程中将当前的状态变量进行记录，方便之后的查找。
 *
 * 动态规划主要就是用来解决多阶段决策的问题，但是实际问题中往往很难有统一的处理方法，必须结合问题的特点来进行算法的设计，这也是这种算法很难真正掌握的原因。
 * 能否使用动态规划取决于该问题拆解的这些”小问题“能不能被重复调用。
 *
 * 二、算法解析：
 * 参考：https://zhuanlan.zhihu.com/p/91582909
 * 动态规划，无非就是利用历史记录，来避免我们的重复计算。
 * 而这些历史记录，我们得需要一些变量来保存，一般是用一维数组或者二维数组来保存。
 *
 * 下面我们先来讲下做动态规划题很重要的三个步骤：
 * 1、定义数组元素的含义
 * 2、找出数组元素之间的关系式
 * 3、找出初始值
 *
 * 备注：
 * 有两种解决动态规划的方式：自下而上的迭代（空间换时间） 和 自上而下的递归
 * 可以理解为动态规划是一种优化的递归，最关键的是找到递推关系和边界条件
 *
 * 注意：
 * 大部分动态规划题目都是采用二维数组解决；
 * 有些题的 dp[i][j] 元素之间的关系比较难找一点，但是不管多难找，大部分情况下，dp[i][j] 和 dp[i-1][j]、dp[i][j-1]、dp[i-1][j-1] 肯定存在某种关系。
 * 90% 的字符串问题都可以用动态规划解决，并且90%是采用二维数组。尤其是对于有两个字符串的，基本都是二维数组来存放。
 *
 */
public class DynamicProgramming {
    /**
     * 1. 爬楼梯问题 (简单)
     *
     * 描述：
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 1 <= n <= 45
     *
     * @param n 楼梯阶数
     * @return 爬法
     */
    public static int climbStairs(int n) {
        // 1. 定义：dp[n]表示n阶台阶总共的爬法
        // 2. 状态转移方程：dp[n] = dp[n-1] +  dp[n-2] (n>1)
        // 3. 初始值：dp[1] = 1, dp[2] = 2
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];  // 保存历史状态的数组
        dp[1] = 1;  // 初始值
        dp[2] = 2;
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    /**
     * 2. 不同路径 (中等)
     *
     * 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
     * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
     * 问总共有多少条不同的路径？
     * 1 <= m, n <= 100
     *
     * @param m 宽
     * @param n 长
     * @return 路径数
     */
    public static int uniquePaths(int m, int n) {
        // 1. 定义：dp[i][j] 表示从[0][0]到[i][j]的不同路径数, 0 <= i, j <= 99
        // 2. 状态转移方程：dp[i][j] = dp[i-1][j] + dp[i][j-1]
        // 3. 初始值：dp[0][j] = 1, dp[i][0] = 1
        // 备注：O(n * m) 的空间复杂度可以优化成 O(min(n, m)) 的空间复杂度
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i ++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i ++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }
    // 优化：
    // dp[i][j] 只跟 dp[i - 1][j]、dp[i][j - 1] 有关，用一个一位数组来暂存
    public static int uniquePathsPlus(int m, int n) {
        int[] dp = new int[n];
        for (int i = 0; i < n ; i ++) {
            dp[i] = 1;  // 第0行
        }
        for (int i = 1; i < m; i ++) {  // 第i行
            dp[0] = 1;  // 初始化第一个元素
            for (int j = 1; j < n; j ++) {
                dp[j] = dp[j - 1] + dp[j];  // 覆盖其他元素
            }
        }
        return dp[n-1];
    }

    /**
     * 3. 最小路径和 (中等)
     *
     * 给定一个包含非负整数的 m x n 网格 grid ，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * 说明：每次只能向下或者向右移动一步。
     * 1 <= m, n <= 200
     *
     * @param grid 网格数组
     * @return 最小路径和
     */
    public static int minPathSum(int[][] grid) {
        // 1. dp[i][j] 表示从[0][0]到[i][j]的最小路径和, 0 <= i, j <= 199
        // 2. dp[i][j] = min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j]
        // 3. dp[0][j] = dp[0][j - 1] + grid[0][j], dp[i][0] = dp[i - 1][0] + grid[i][0])
        // 备注：O(n*m) 的空间复杂度可以优化成 O(min(n, m)) 的空间复杂度
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < m; i ++) {
            dp[i][0] += dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i ++) {
            dp[0][i] += dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < m; i ++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 4. 编辑距离 (困难)
     *
     * 给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数。
     * 你可以对一个单词进行如下三种操作：
     * 1、插入一个字符
     * 2、删除一个字符
     * 3、替换一个字符
     *
     * 0 <= word1.length, word2.length <= 500
     * word1 和 word2 由小写英文字母组成
     *
     * @param word1 单词1
     * @param word2 单词2
     * @return 最小操作数
     */
    public static int minDistance(String word1, String word2) {
        // 1. 定义 dp[i][j] 为当字符串 word1 的长度为 i，字符串 word2 的长度为 j 时，将 word1 转化为 word2 所使用的最少操作0《次数；
        //    注意：
        //    1) 范围 0 <= i <= word1.length() , 0 <= j <= word2.length;
        //    2) 问题转化成求 dp[word1.length()][word2.length]
        // 2. 当word1[i] == word2[j], 则dp[i][j] = dp[i - 1][j - 1]
        //    当word1[i] != word2[j], 有三种情况, 分别对应三种可能的操作：
        //       1) 替换word1[i]后与word2[j]相等, 则dp[i][j] == dp[i - 1][j - 1] + 1;
        //       2) 删除word1[i]后与word2[j]相等, 则dp[i][j] == dp[i - 1][j] + 1
        //       3) 插入word1[i]尾部后与word2[j]相等, 则dp[i][j] == dp[i][j - 1] + 1
        //       为使操作最少，则dp[i][j] = min(dp[i - 1][j - 1], dp[i - 1][j], dp[i][j - 1]) + 1
        // 3. 因为状态转换方程要求 i, j >= 1, 所以 i = 0 或 j = 0 就是初始值的情况
        //    dp[0][0 ... j] = j  // 只插入
        //    dp[0 ... i][0] = i  // 只删除
        int m = word1.length();
        int n = word2.length();
        int[][] dp = new int[m + 1][n + 1];  // 这里容易错！
        for (int i = 0; i <= n; i ++) {
            dp[0][i] = i;
        }
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {  // 这里容易错！
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i - 1][j]), dp[i][j - 1]) + 1;
                }
            }
        }
        return dp[m][n];
    }
    public static int minDistancePlus(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        int[] dp = new int[n + 1];
        for (int i = 0; i <= n; i ++) {
            dp[i] = i;
        }
        for (int i = 1; i <= m; i ++) {
            int temp = dp[0];  // 有于交换
            dp[0] = i;
            for (int j = 1; j <= n; j ++) {
                int pre = temp;  // 用于暂存dp[i - 1][j - 1]
                temp = dp[j];    // 用于pre的交换
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {  // 这里容易错！
                    dp[j] = pre;
                } else {
                    dp[j] = Math.min(Math.min(pre, dp[j]), dp[j - 1]) + 1;
                }
            }
        }
        return dp[n];
    }

    /**
     * 5. 打家劫舍 (中等)
     *
     * 你是一个专业的小偷，计划偷窃沿街的房屋。
     * 每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统.
     * 如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你不触动警报装置的情况下一夜之内能够偷窃到的最高金额。
     *
     * 1 <= nums.length <= 100
     *
     * @param nums 每户金额
     * @return 总金额
     */
    public static int rob(int[] nums) {
        // 1. dp[i] 表示偷窃到序号为i的房屋时的最高金额
        // 2. 对于sum[i], 只有偷和不偷两种选择：
        //    1) 如果偷, dp[i] = dp[i - 2] + nums[i]
        //    2) 如果不偷, dp[i] = dp[i - 1]
        //   即 dp[i] = max(dp[i - 1], dp[i - 2] + nums[i])
        // 3. dp[0] = nums[0], dp[1] = max(nums[0], nums[1])
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i ++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[n-1];
    }

    /**
     * 6. 最大子数组和 (简单)
     *
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组是数组中的一个连续部分。
     *
     * 1 <= nums.length <= 105
     *
     * 备注：用枚举思想，两层for循环是比较直接的思路，也是这个题被标记为简单的原因。但是动态规划并不是能一眼看出来。
     *
     * @param nums 数组
     * @return 最大连续子数组和
     */
    public static int maxSubArray(int[] nums) {
        // 1. 定义 dp[i] 是以nums[i]为结尾的子序的最大和   // 难点，用上"连续"这个条件, 注意此时返回值不是简单的dp[i]了
        // 2. 因为必须以nums[i]结尾, 则dp[i] = max(dp[i - 1] + nums[i], nums[i])
        // 3. dp[0] = nums[0]
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < n; i ++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    /**
     * 7. 零钱兑换 (中等)
     *
     * 给你一个整数数组 coins 表示不同面额的硬币，以及一个整数 amount 表示总金额。
     * 计算并返回可以凑成总金额所需的最少的硬币个数，如果没有任何一种硬币组合能组成总金额，返回 -1 。
     * 可以认为每种硬币的数量是无限的。
     *
     * 1 <= coins.length <= 12
     * 1 <= coins[i] <= 231 - 1
     * 0 <= amount <= 104
     *
     * @param coins 硬币
     * @param amount 总金额
     * @return 最少硬币个数
     */
    public int coinChange(int[] coins, int amount) {
        // 1. 定义 dp[i] 表示凑成金额为 i 的最少硬币数, 0 <= dp[i] <= amount
        // 2. 对于每个coins[j], 只有选或者不选：
        //    如果 i >= coins[j]:
        //        如果选：dp[i] = dp[i - coins[j]] + 1
        //        如果不选：dp[i] = dp[i]
        //        即 dp[i] = min(dp[i], dp[i - coins[j]] + 1)
        //    如果 i < coins[j]：dp[i] = dp[i]，没变化
        //    则：dp[i] = min(dp[i], dp[i - coins[j]] + 1)
        // 3. dp[0] = 0
        int[] dp = new int[amount + 1];
        for(int i = 0; i <= amount; i++) {
            dp[i] = amount + 1;  // 初始值，避免min判断出错
        }
        dp[0] = 0;
        for (int i = 1; i <= amount; i ++) {
            for (int coin : coins) {
                if (i >= coin) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    /**
     * 8. 正则表达式 (困难)
     *
     * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
     * '.' 匹配任意单个字符
     * '*' 匹配零个或多个前面的那一个元素 (* 前面只能是 a-z, . ???)
     * 所谓匹配，是要涵盖整个字符串 s 的，而不是部分字符串。
     *
     * 1 <= s.length <= 20
     * 1 <= p.length <= 30
     * s 只包含从 a-z 的小写字母。
     * p 只包含从 a-z 的小写字母，以及字符 . 和 *
     * 保证每次出现字符 * 时，前面都匹配到有效的字符
     *
     * @param s 字符串
     * @param p 匹配符
     * @return 是否匹配上
     */
    public boolean isMatch(String s, String p) {
        // 关键：二维数组；p[j] 三种情况：. * 普通字符；匹配成功后进行状态转移
        // 1. dp[i][j] 表示当 s 长为 i 且 p 长为 j 时的子字符串是否匹配
        // 2. 当 p[j] == 普通字符 或 .
        //       dp[i][j] = dp[i - 1][j - 1], 当 p[j] 匹配 s[i] （转化为子问题）
        //       dp[i][j] = false, 当 p[j] 不匹配 s[i]
        //    当 p[j] == *（必须把 p[j - 1]* 当做一个整体考虑）
        //       dp[i][j] = dp[i][j - 2], 当 p[j - 1] != s[i], 即 p[j - 1]* 匹配 0 次 s[i]；
        //       dp[i][j] = dp[i][j - 2], 当 p[j - 1] == s[i] 且 p[j - 1]* 不再匹配（j 指向p[j - 1]是 冗余状态，跳过）；
        //       dp[i][j] = dp[i - 1][j], 当 p[j - 1] == s[i] 且 p[j - 1]* 继续匹配；
        //    注意：当p[j] == . 时能匹配任意字符
        // 3. dp[0][0] = true;  // 虽然题目条件中两个字符串都不为空，但不影响动态规划的边界条件是都为空串
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];  // boolean 类型元素会自动初始化为 false
        dp[0][0] = true;
        for (int i = 0; i <= m; i ++) {
            for (int j = 1; j <= n; j ++) {
                if (p.charAt(j - 1) != '*') {
                    if (charMatch(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                } else {
                    dp[i][j] = dp[i][j - 2];
                    if (charMatch(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                    }
                }
            }
        }
        return dp[m][n];
    }
    // 比较s的第i个字符和p的第j个字符是否匹配 (只需要考虑普通字符和., 因为*需要依赖前面的字符)
    // 注意：对应字符下标分别是i-1, j-1
    public static boolean charMatch(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        }
        return s.charAt(i - 1) == p.charAt(j - 1);
    }

    /**
     * 9.1 01背包问题
     * 假设一共有 N 件物品，第i（i>=1）件物品的重量为w[i]，价值为v[i]。
     * 在总重量不超过背包承载上限 W 的情况下，能够装入背包的最大价值是多少？
     *
     *
     * @param weight 商品重量
     * @param value 商品价值
     * @param w 背包承重上限
     * @return 最大价值
     */
    public static int knapsack01(int[] weight, int[] value, int w) {
        // 注意：weight.length == value.length
        // 1. dp[i][j] 表示将前 i 种物品装入限重为 j 背包的最大价值
        // 2. dp[i][j] = dp[i - 1][j], 当不装入第 i 种物品
        //             = dp[i - 1][j - weight[i]] + value[i], 当装入第 i 种物品 (j >= weight[i])
        // 3. dp[0][0...j] = 0, dp[0...i][0] = 0
        int m = weight.length;
        int[][] dp = new int[m + 1][w + 1];
        // 这里不初始化结果也是一样的，因为数组的缺省值就是0
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= w; i ++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= w; j ++) {
                if (j >= weight[i - 1]) {  // j >= 第 i 种物品重量，能装下（不装 / 装）
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i - 1]] + value[i - 1]);
                }
                else {  // 装不下
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m][w];
    }

    /**
     * 9.2 完全背包
     *
     * 与01背包不同就是每种物品数量为：无限
     *
     * @param weight 商品重量
     * @param value 商品价值
     * @param w 背包承重上限
     * @return 最大价值
     */
    public static int knapsackUnbounded(int[] weight, int[] value, int w) {
        // 变量、目标和01背包没有区别，所以可以定义与01背包问题几乎完全相同的状态dp
        // 1. dp[i][j] 表示将前 i 种物品装入限重为 j 背包的最大价值
        // 2. dp[i][j] = dp[i - 1][j], 当不装入第 i 种物品
        //             // 跟01背包主要的区别就是放入一个i物品后，还可以继续放入i物品
        //             = dp[i][j - weight[i]] + value[i], 当装入第 i 种物品 (j >= weight[i])
        // 3. dp[0][0...j] = 0, dp[0...i][0] = 0
        int m = weight.length;
        int[][] dp = new int[m + 1][w + 1];
        // 这里不初始化结果也是一样的，因为数组的缺省值就是0
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= w; i ++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= w; j ++) {
                if (j >= weight[i - 1]) {  // j >= 第 i 种物品重量，能装下
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - weight[i - 1]] + value[i - 1]);  // 区别！！！
                } else {  // 装不下
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[m][w];
    }

    /**
     * 9.3 多重背包
     *
     * 与01背包不同就是每种物品数量为: n[i]
     *
     * @param weight 商品重量
     * @param value 商品价值
     * @param n 商品数量
     * @param w 背包承重上限
     * @return 最大价值
     */
    public static int knapsackBounded(int[] weight, int[] value, int[] n, int w) {
        // 变量、目标和01背包有一点区别，还是可以定义与01背包问题几乎完全相同的状态dp
        // 1. dp[i][j] 表示将前 i 种物品装入限重为 j 背包的最大价值
        // 2. dp[i][j] = dp[i - 1][j], 当不装入第 i 种物品
        //             // 跟01背包主要的区别就是放入一个i物品后，还可以继续放入i物品，但是有限
        //             = dp[i][j - weight[i]] + value[i], 当装入第 i 种物品 (j >= weight[i] && k <= n[k])
        //             =
        // 3. dp[0][0...j] = 0, dp[0...i][0] = 0
        int m = weight.length;
        int[][] dp = new int[m + 1][w + 1];
        // 这里不初始化结果也是一样的，因为数组的缺省值就是0
        for (int i = 0; i <= m; i ++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i <= w; i ++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i <= m; i ++) {
            for (int j = 1; j <= w; j ++) {
                int maxN = Math.min(j / weight[i - 1], n[i - 1]);
                for (int k = 0; k <= maxN; k ++) {
                    if (j >= k * weight[i - 1]) {  // j >= 第 i 种物品重量，能装下
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - k * weight[i - 1]] + k * value[i - 1]);  // 区别！！！
                    } else {  // 装不下
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }
        return dp[m][w];
    }

    public static void main(String[] args) {
//        System.out.println(climbStairs(1));

//        System.out.println(uniquePaths(3, 7));
//        System.out.println(uniquePathsPlus(3, 7));

//        int[][] grid1 = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
//        int[][] grid2 = {{1, 2, 3}};
//        System.out.println(minPathSum(grid2));

        int[] weight = {2, 3, 5, 5};
        int[] value = {2, 4, 3, 7};
        System.out.println(knapsack01(weight, value, 10));
    }
}
