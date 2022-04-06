package com.wxj.algorithm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * 回溯
 *
 * 算法原理：
 * 回溯算法也可称作试探算法，简单来说，回溯的过程就是在做出下一步选择之前，先对每一种可能进行试探；
 * 只有当可能性存在时才会向前迈进，倘若所有选择都不可能，那么则向后退回原来的位置，重新选择。
 * 回溯算法的过程就是不断进行这样的试探、判断、退回并重新试探，直至找到一条完整的前进路径。
 * 只不过在这个过程中，可以通过「剪枝」等限制条件降低试探搜索的空间，从而避免重复无效的试探。
 *
 * 这样看起来，回溯算法很像是一种进行中的枚举算法，在行进的过程中对所有可能性进行枚举并判断。
 *
 * 回溯思想在许多大规模的问题的求解上都能得到有效的运用。
 * 回溯能够将复杂问题进行分步调整，从而在中间的过程中可对所有可能运用枚举思想进行遍历。
 * 这样往往能够清地看到问题解决的层次，从而可以帮助更好地理解问题的最终解结构。
 *
 * 常用的应用场景就在对树结构、图结构以及棋盘落子的遍历上。
 * 穷举整棵决策树是无法避免也是回溯算法的一个特点，不像动态规划存在重叠子问题可以优化，回溯算法就是纯暴力穷举，复杂度一般都很高。
 *
 * 三个关键点：
 * 1、路径：也就是已经做出的选择。
 * 2、选择列表：也就是你当前可以做的选择。
 * 3、结束条件：也就是到达决策树底层，无法再做选择的条件。
 *
 * 回溯算法的代码框架：
 * result = []
 * def backtrack(路径, 选择列表):
 *     if 满足结束条件:
 *         result.add(路径)
 *         return
 *
 *     for 选择 in 选择列表:
 *         // 做选择
 *         选择列表.remove(选择)
 *         路径.add(选择)
 *         backtrack(路径, 选择列表)  // 递归，关键！
 *         // 撤销选择
 *         路径.remove(选择)
 *         选择列表.add(选择)
 *
 */
public class Backtrack {
    /**
     * 1. 全排列 (中等)
     *
     * 给定一个不含重复数字的数组 nums，返回其所有可能的全排列，可以按任意顺序返回答案。
     *
     * 1 <= nums.length <= 6
     * -10 <= nums[i] <= 10
     *
     * @param nums 待排序数组
     * @return 全排列
     */
    public List<List<Integer>> permute(int[] nums) {
        // 主函数
        List<List<Integer>> res1 = new LinkedList<>();  // 结果
        List<Integer> track = new ArrayList<>();  // 路径
        backtrack(nums, res1, track);
        return res1;
    }
    public void backtrack(int[] nums, List<List<Integer>> res1, List<Integer> track) {
        // 回溯函数
        // 路径：记录在 track 中，记录当前已经做出的选择
        // 选择列表：nums 中不存在于 track 的那些元素
        // 结束条件：nums 中的元素全都出现在 track 中
        if (track.size() == nums.length) {  // 其中一条路径完成，结束回溯
            res1.add(new ArrayList<>(track));  // 添加此路径
            return;
        }
        for (int num : nums) {
            // 避免路径中出现重复元素
            if (track.contains(num)) {
                continue;
            }
            // 做选择
            track.add(num);
            backtrack(nums, res1, track);
            // 取消选择
            track.remove(track.size() - 1);  // 回溯上一步
        }
    }

    /**
     * 2. 八皇后 (51. 困难)
     *
     * 描述：
     * n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。
     * 给你一个整数 n，返回所有不同的 n 皇后问题 的解决方案。
     * 每一种解法包含一个不同的 n皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。
     *
     * @param n 皇后数
     * @return 摆法
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new LinkedList<>();
        List<String> board = new ArrayList<>();
        StringBuilder tmp = new StringBuilder();
        for (int i = 0; i < n; i ++) {  // 初始化一行
            tmp.append(".");
        }
        for (int i = 0; i < n; i ++) {
            board.add(tmp.toString());  // 初始化棋盘
        }
        backtrack2(n, res, board, 0);
        return res;
    }
    // row 表示行数
    public void backtrack2(int n, List<List<String>> res, List<String> board, int row) {
        if (row == n) {
            res.add(new ArrayList<>(board));
            return;
        }
        for (int col = 0; col < n; col ++) {  // 循环列数
            if (!isValid(board, row, col)) {
                continue;
            }
            StringBuilder tmp = new StringBuilder(board.get(row));
            tmp.replace(col, col + 1, "Q");
            board.set(row, tmp.toString());
            backtrack2(n, res, board, row + 1);  // 下一行
            tmp.replace(col, col + 1, ".");
            board.set(row, tmp.toString());

        }
    }
    public boolean isValid(List<String> board, int row, int col) {
        // 因为从上往下放棋子，所以只需要判断竖、左上斜和右上斜
        int n = board.get(row).length();
        // 1. 竖
        for (int i = 0; i < row; i ++) {
            if (board.get(i).charAt(col) == 'Q') {
                return false;
            }
        }
        // 2. 左上斜
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i --, j--) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        for (int i = row - 1, j = col + 1; i >= 0 && j < n; i --, j++) {
            if (board.get(i).charAt(j) == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Backtrack bk = new Backtrack();

        int[] nums = {1,2,3};
        System.out.println(bk.permute(nums));

        System.out.println(bk.solveNQueens(4));
    }
}
