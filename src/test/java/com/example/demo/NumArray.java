package com.example.demo;

/**
 * 给定一个整数数组  nums，处理以下类型的多个查询:
 *
 * 计算索引 left 和 right （包含 left 和 right）之间的 nums 元素的 和 ，其中 left <= right
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 使用数组 nums 初始化对象
 * int sumRange(int i, int j) 返回数组 nums 中索引 left 和 right 之间的元素的 总和 ，包含 left 和 right 两点（也就是 nums[left] + nums[left + 1] + ... + nums[right] )
 */
public class NumArray {

    int[] arrays;
    public NumArray(int[] nums) {
        int n = nums.length;
        arrays = new int[n + 1];
        for (int i = 0; i < n; i++) {
            arrays[i + 1] = arrays[i] + nums[i];
        }
//        arrays = nums;
    }

    public int sumRange(int left, int right) {
        return arrays[right + 1] - arrays[left];
//        int sum = 0;
//        for(int i = left;i <= right;i++){
//            sum += arrays[i];
//        }
//        return sum;
    }
}
