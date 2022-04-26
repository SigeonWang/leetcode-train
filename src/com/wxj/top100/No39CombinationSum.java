package com.wxj.top100;

import java.util.ArrayList;
import java.util.List;

public class No39CombinationSum {
    /**
     * 39. 组合总和 (中等)
     * 给你一个"无重复元素"的整数数组 candidates 和一个目标整数 target，找出 candidates 中可以使数字和为目标数 target 的所有不同组合，
     * 并以列表形式返回。你可以按任意顺序返回这些组合。
     * candidates 中的同一个数字可以"无限制重复被选取"。
     * 如果至少一个数字的被选数量不同，则两种组合是不同的。
     *
     * 对于给定的输入，保证和为 target 的不同组合数少于 150 个。
     *
     * 1 <= candidates.length <= 30
     * 1 <= candidates[i] <= 200
     * candidate 中的每个元素都互不相同
     * 1 <= target <= 500
     *
     * @param candidates 数组
     * @param target 目标整数
     * @return 组合
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        // 第一感觉是动态规划（这是求组合，不是最值问题，不太适合）
        // 官方题解：搜索回溯
        // 注意：对于这类"寻找所有可行解"的题，我们都可以尝试用「回溯」的方法来解决
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> combine = new ArrayList<>();
        trackback(candidates, target, res, combine, 0);
        return res;
    }
    public void trackback(int[] candidates, int target, List<List<Integer>> res, List<Integer> combine, int index) {
        // 结束条件1：找到和为target的组合
        if (target == 0) {
            // 注意这里要添加new ArrList(combine)而不能直接添加combine。因为combine是公用的，而且可能在后面改变包含的元素。
            res.add(new ArrayList<>(combine));
            return;
        }
        // 结束条件2：查找完数组，没有可用组合
        if (index == candidates.length) {
            return;
        }
        // 对于candidates[i]，只有选或不选两种情况：
        // 1 不选
        trackback(candidates, target, res, combine, index + 1);
        // 2 选
        if (target >= candidates[index]) {
            combine.add(candidates[index]);
            // target减小，index不变，因为可以重复选择
            trackback(candidates, target - candidates[index], res, combine, index);
            combine.remove(combine.size() - 1);
        }
    }

    public static void main(String[] args) {
        No39CombinationSum c = new No39CombinationSum();
        int[] arr = {2, 3, 6, 7};
        System.out.println(c.combinationSum(arr, 7));
    }

}
