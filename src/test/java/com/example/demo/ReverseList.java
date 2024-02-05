package com.example.demo;

import java.util.Arrays;

public class ReverseList {
    static class ListNode{
        int val;
        ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode iterate(ListNode head){
        ListNode prev = null,curr,next;
        curr = head;
        while(curr != null){
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public static ListNode recursion(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = recursion(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        Integer i1 = 1;
        Integer i2 = 1;
        System.out.println(i1==i2);
        ListNode node5 = new ListNode(5,null);
        ListNode node4 = new ListNode(4,node5);
        ListNode node3 = new ListNode(3,node4);
        ListNode node2 = new ListNode(2,node3);
        ListNode node1 = new ListNode(1,node2);
        ListNode node = iterate(node1);
        ListNode node_1 = recursion(node);
        System.out.println(node_1);
        int[] nums = new int[]{1, 2, 3, 3, 5, 1};
        System.out.println(pivotIndex(nums));
    }

    public static void getMaxMin(int[] nums) {
        // 最小的和第二小的
        int min1 = 0, min2 = 0;
        // 最大的、第二大的和第三大的
        int max1 = 0, max2 = 0, max3 = 0;

        for (int x : nums) {
            if (x < min1) {
                min2 = min1; min1 = x;
            } else if (x < min2) {
                min2 = x;
            }

            if (x > max1) {
                max3 = max2;
                max2 = max1;
                max1 = x;
            } else if (x > max2) {
                max3 = max2;
                max2 = x;
            } else if (x > max3) {
                max3 = x;
            }
        }
    }


    public static int pivotIndex(int[] nums) {
        int sum1 = Arrays.stream(nums).sum();
        int sum2 = 0;
        for(int i = 0; i<nums.length; i++){
            sum2 += nums[i];
            if(sum1 == sum2){
                return i;
            }
            sum1 = sum1 - nums[i];
        }
        return -1;
    }

}