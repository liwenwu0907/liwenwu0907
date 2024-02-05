package com.example.demo;

import com.example.demo.entity.ListNode;
import com.example.demo.entity.TreeNode;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

public class Solution5 {

    /**
     * 给你单链表的头结点 head ，请你找出并返回链表的中间结点。
     * 如果有两个中间结点，则返回第二个中间结点。
     * 输入：head = [1,2,3,4,5]
     * 输出：[3,4,5]
     * 解释：链表只有一个中间结点，值为 3 。
     */
    public ListNode middleNode(ListNode head) {
        //单指针
//        List<Integer> list = new ArrayList<>();
//        ListNode need = head;
//        while (head != null){
//            list.add(head.val);
//            head = head.next;
//        }
//        int size = list.size();
//        int num = size / 2;;
//        for(int i = 0; i < size; i++){
//            if(num == i){
//                return need;
//            }
//            need = need.next;
//        }
//        return need;
        //数组
//        ListNode[] A = new ListNode[100];
//        int t = 0;
//        while (head != null) {
//            A[t++] = head;
//            head = head.next;
//        }
//        return A[t / 2];
        //快慢指针
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;

    }

    /**
     * 句子 是一串由空格分隔的单词。每个 单词 仅由小写字母组成。
     * 如果某个单词在其中一个句子中恰好出现一次，在另一个句子中却 没有出现 ，那么这个单词就是不常见的。
     * 给你两个 句子 s1 和 s2 ，返回所有 不常用单词 的列表。返回列表中单词可以按 任意顺序组织。
     * 输入：s1 = "this apple is sweet", s2 = "this apple is sour"
     * 输出：["sweet","sour"]
     */
    public static String[] uncommonFromSentences(String s1, String s2) {
//        String[] s1Array = s1.split(" ");
//        String[] s2Array = s2.split(" ");
//        Map<String,Integer> map1 = new HashMap<>();
//        Map<String,Integer> map2 = new HashMap<>();
//        for(String str : s1Array){
//            map1.put(str,map1.getOrDefault(str,0) + 1);
//        }
//        for(String str : s2Array){
//            map2.put(str,map2.getOrDefault(str,0) + 1);
//        }
//        Set<String> set = new HashSet<>();
//        for(Map.Entry<String,Integer> entry : map1.entrySet()){
//            if(entry.getValue() == 1 && !Arrays.asList(s2Array).contains(entry.getKey())){
//                set.add(entry.getKey());
//            }
//        }
//        for(Map.Entry<String,Integer> entry : map2.entrySet()){
//            if(entry.getValue() == 1 && !Arrays.asList(s1Array).contains(entry.getKey())){
//                set.add(entry.getKey());
//            }
//        }
//        return set.toArray(new String[0]);
        Map<String, Integer> freq = new HashMap<String, Integer>();
        insert(s1, freq);
        insert(s2, freq);

        List<String> ans = new ArrayList<String>();
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 1) {
                ans.add(entry.getKey());
            }
        }
        return ans.toArray(new String[0]);
    }

    public static void insert(String s, Map<String, Integer> freq) {
        String[] arr = s.split(" ");
        for (String word : arr) {
            freq.put(word, freq.getOrDefault(word, 0) + 1);
        }
    }

    /**
     * 爱丽丝和鲍勃拥有不同总数量的糖果。给你两个数组 aliceSizes 和 bobSizes ，aliceSizes[i] 是爱丽丝拥有的第 i 盒糖果中的糖果数量，bobSizes[j] 是鲍勃拥有的第 j 盒糖果中的糖果数量。
     * 两人想要互相交换一盒糖果，这样在交换之后，他们就可以拥有相同总数量的糖果。一个人拥有的糖果总数量是他们每盒糖果数量的总和。
     * 返回一个整数数组 answer，其中 answer[0] 是爱丽丝必须交换的糖果盒中的糖果的数目，answer[1] 是鲍勃必须交换的糖果盒中的糖果的数目。如果存在多个答案，你可以返回其中 任何一个 。题目测试用例保证存在与输入对应的答案。
     * 输入：aliceSizes = [1,1], bobSizes = [2,2]
     * 输出：[1,2]
     */
    public static int[] fairCandySwap(int[] aliceSizes, int[] bobSizes) {
//        Arrays.sort(aliceSizes);
//        Arrays.sort(bobSizes);
//        int aliceSum = Arrays.stream(aliceSizes).sum();
//        int bobSum = Arrays.stream(bobSizes).sum();
//        if(aliceSum > bobSum){
//            //从最大的开始交换
//            for(int i = aliceSizes.length - 1; i >= 0; i--){
//                for(int j = 0; j < bobSizes.length; j++){
//                    if(aliceSum - aliceSizes[i] + bobSizes[j] == bobSum + aliceSizes[i] - bobSizes[j]){
//                        int[] result = new int[2];
//                        result[0] = aliceSizes[i];
//                        result[1] = bobSizes[j];
//                        return result;
//                    }
//                }
//            }
//        }else {
//            for(int i = bobSizes.length - 1; i >= 0; i--){
//                for(int j = 0; j < aliceSizes.length; j++){
//                    if(bobSum - bobSizes[i] + aliceSizes[j] == aliceSum + bobSizes[i] - aliceSizes[j]){
//                        int[] result = new int[2];
//                        result[0] = aliceSizes[j];
//                        result[1] = bobSizes[i];
//                        return result;
//                    }
//                }
//            }
//        }
//        return null;
        int sumA = Arrays.stream(aliceSizes).sum();
        int sumB = Arrays.stream(bobSizes).sum();
        int delta = (sumA - sumB) / 2;
        Set<Integer> rec = new HashSet<Integer>();
        for (int num : aliceSizes) {
            rec.add(num);
        }
        int[] ans = new int[2];
        for (int y : bobSizes) {
            int x = y + delta;
            if (rec.contains(x)) {
                ans[0] = x;
                ans[1] = y;
                break;
            }
        }
        return ans;
    }

    /**
     * 如果数组是单调递增或单调递减的，那么它是 单调 的。
     * 如果对于所有 i <= j，nums[i] <= nums[j]，那么数组 nums 是单调递增的。 如果对于所有 i <= j，nums[i]> = nums[j]，那么数组 nums 是单调递减的。
     * 当给定的数组 nums 是单调数组时返回 true，否则返回 false。
     * 输入：nums = [1,2,2,3]
     * 输出：true
     */
    public static boolean isMonotonic(int[] nums) {
        boolean incremental = false;
        boolean decrement = false;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
            } else if (nums[i] < nums[i + 1]) {
                if (decrement) {
                    return false;
                }
                incremental = true;
            } else {
                if (incremental) {
                    return false;
                }
                decrement = true;
            }
        }
        return true;
    }

    /**
     * 给你一棵二叉搜索树的 root ，请你 按中序遍历 将其重新排列为一棵递增顺序搜索树，使树中最左边的节点成为树的根节点，并且每个节点没有左子节点，只有一个右子节点。
     */
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = Solution2.inorderTraversal(root);
        TreeNode dummyNode = new TreeNode(-1);
        TreeNode currNode = dummyNode;
        for (int value : list) {
            currNode.right = new TreeNode(value);
            currNode = currNode.right;
        }
        return dummyNode.right;
    }

    /**
     * 给你一个整数数组 nums，将 nums 中的的所有偶数元素移动到数组的前面，后跟所有奇数元素。
     * 返回满足此条件的 任一数组 作为答案。
     * 输入：nums = [3,1,2,4]
     * 输出：[2,4,3,1]
     * 解释：[4,2,3,1]、[2,4,1,3] 和 [4,2,1,3] 也会被视作正确答案。
     */
    public int[] sortArrayByParity(int[] nums) {
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                int tmp = nums[i];
                nums[i] = nums[index];
                nums[index] = tmp;
                index++;
            }
        }
        return nums;
    }

    /**
     * 给你一个整数数组 nums，和一个整数 k 。
     * 在一个操作中，您可以选择 0 <= i < nums.length 的任何索引 i 。将 nums[i] 改为 nums[i] + x ，其中 x 是一个范围为 [-k, k] 的整数。对于每个索引 i ，最多 只能 应用 一次 此操作。
     * nums 的 分数 是 nums 中最大和最小元素的差值。
     * 在对  nums 中的每个索引最多应用一次上述操作后，返回 nums 的最低 分数 。
     * 输入：nums = [1], k = 0
     * 输出：0
     * 解释：分数是 max(nums) - min(nums) = 1 - 1 = 0。
     */
    public int smallestRangeI(int[] nums, int k) {
//        if(nums.length == 1){
//            return 0;
//        }
//        Arrays.sort(nums);
//        int len = nums.length;
//        if(nums[len - 1] - k >= nums[0] + k){
//            return nums[len - 1] - k - (nums[0] + k);
//        }else {
//            return 0;
//        }
        int minNum = Arrays.stream(nums).min().getAsInt();
        int maxNum = Arrays.stream(nums).max().getAsInt();
        return maxNum - minNum <= 2 * k ? 0 : maxNum - minNum - 2 * k;
    }

    /**
     * 给定一副牌，每张牌上都写着一个整数。
     * 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
     * 每组都有 X 张牌。
     * 组内所有的牌上都写着相同的整数。
     * 仅当你可选的 X >= 2 时返回 true。
     * 输入：deck = [1,2,3,4,4,3,2,1]
     * 输出：true
     * 解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]
     */
    public boolean hasGroupsSizeX(int[] deck) {
        if (deck.length == 1) {
            return false;
        }
        int N = deck.length;
        int[] count = new int[10000];
        for (int c : deck) {
            count[c]++;
        }

        List<Integer> values = new ArrayList<Integer>();
        for (int i = 0; i < 10000; ++i) {
            if (count[i] > 0) {
                values.add(count[i]);
            }
        }

        for (int X = 2; X <= N; ++X) {
            if (N % X == 0) {
                boolean flag = true;
                for (int v : values) {
                    if (v % X != 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 给你一个字符串 s ，根据下述规则反转字符串：
     * 所有非英文字母保留在原有位置。
     * 所有英文字母（小写或大写）位置反转。
     * 返回反转后的 s 。
     * 输入：s = "ab-cd"
     * 输出："dc-ba"
     */
    public static String reverseOnlyLetters(String s) {
        int n = s.length();
        int left = 0, right = n - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            if (Character.isLetter(s.charAt(left))) {
                if (Character.isLetter(s.charAt(right))) {
                    char tmp = chars[left];
                    chars[left] = s.charAt(right);
                    chars[right] = tmp;
                    left++;
                    right--;
                }
            } else {
                left++;
            }
            if (left < right) {
                if (Character.isLetter(s.charAt(right))) {
                    if (Character.isLetter(s.charAt(left))) {
                        char tmp = chars[left];
                        chars[left] = s.charAt(right);
                        chars[right] = tmp;
                        left++;
                        right--;
                    }
                } else {
                    right--;
                }
            }

        }
        return new String(chars);
    }

    /**
     * 给定一个非负整数数组 nums，  nums 中一半整数是 奇数 ，一半整数是 偶数 。
     * 对数组进行排序，以便当 nums[i] 为奇数时，i 也是 奇数 ；当 nums[i] 为偶数时， i 也是 偶数 。
     * 你可以返回 任何满足上述条件的数组作为答案 。
     * 输入：nums = [4,2,5,7]
     * 输出：[4,5,2,7]
     * 解释：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也会被接受。
     */
    public int[] sortArrayByParityII(int[] nums) {
//        int n = nums.length;
//        for(int i = 0; i < n; i++){
//            if(i % 2 == 0 && nums[i] % 2 != 0){
//                //找下一个处于奇数位的偶数
//                int index = i;
//                while (index < n){
//                    if(index % 2 != 0 && nums[index] % 2 == 0){
//                        //交换
//                        int tmp = nums[i];
//                        nums[i] = nums[index];
//                        nums[index] = tmp;
//                        break;
//                    }
//                    index++;
//                }
//            }else if(i % 2 != 0 && nums[i] % 2 == 0){
//                //找下一个处于偶数位的奇数
//                int index = i;
//                while (index < n){
//                    if(index % 2 == 0 && nums[index] % 2 != 0){
//                        //交换
//                        int tmp = nums[i];
//                        nums[i] = nums[index];
//                        nums[index] = tmp;
//                        break;
//                    }
//                    index++;
//                }
//            }
//        }
//        return nums;
        int n = nums.length;
        int[] ans = new int[n];

        int i = 0;
        for (int x : nums) {
            if (x % 2 == 0) {
                ans[i] = x;
                i += 2;
            }
        }
        i = 1;
        for (int x : nums) {
            if (x % 2 == 1) {
                ans[i] = x;
                i += 2;
            }
        }
        return ans;
    }

    /**
     * 你的朋友正在使用键盘输入他的名字 name。偶尔，在键入字符 c 时，按键可能会被长按，而字符可能被输入 1 次或多次。
     * 你将会检查键盘输入的字符 typed。如果它对应的可能是你的朋友的名字（其中一些字符可能被长按），那么就返回 True。
     * 输入：name = "alex", typed = "aaleex"
     * 输出：true
     * 解释：'alex' 中的 'a' 和 'e' 被长按。
     * 输入：name = "saeed", typed = "ssaaedd"
     * 输出：false
     */
    public static boolean isLongPressedName(String name, String typed) {
//        char pre = name.charAt(0);
//        int index = 0;
//        for(int i = 0; i < name.length(); i++){
//            char ch = name.charAt(i);
//            if(index >= typed.length()){
//                return false;
//            }
//            if(ch == typed.charAt(index)){
//                pre = ch;
//                index++;
//            }else if(typed.charAt(index) == pre){
//                while (index < typed.length() && typed.charAt(index) == pre){
//                    index++;
//                }
//                if(index < typed.length() && ch == typed.charAt(index)){
//                    pre = ch;
//                    index++;
//                }else {
//                    return false;
//                }
//            }else {
//                return false;
//            }
//        }
//        if(index < typed.length()){
//            for(int i = index; i < typed.length(); i++){
//                if(typed.charAt(i) != pre){
//                    return false;
//                }
//            }
//        }
//        return true;

        int i = 0, j = 0;
        while (j < typed.length()) {
            if (i < name.length() && name.charAt(i) == typed.charAt(j)) {
                i++;
                j++;
            } else if (j > 0 && typed.charAt(j) == typed.charAt(j - 1)) {
                j++;
            } else {
                return false;
            }
        }
        return i == name.length();
    }

    /**
     * 每个 有效电子邮件地址 都由一个 本地名 和一个 域名 组成，以 '@' 符号分隔。除小写字母之外，电子邮件地址还可以含有一个或多个 '.' 或 '+' 。
     * 例如，在 alice@leetcode.com中， alice 是 本地名 ，而 leetcode.com 是 域名 。
     * 如果在电子邮件地址的 本地名 部分中的某些字符之间添加句点（'.'），则发往那里的邮件将会转发到本地名中没有点的同一地址。请注意，此规则 不适用于域名 。
     * 例如，"alice.z@leetcode.com” 和 “alicez@leetcode.com” 会转发到同一电子邮件地址。
     * 如果在 本地名 中添加加号（'+'），则会忽略第一个加号后面的所有内容。这允许过滤某些电子邮件。同样，此规则 不适用于域名 。
     * 例如 m.y+name@email.com 将转发到 my@email.com。
     * 可以同时使用这两个规则。
     * 给你一个字符串数组 emails，我们会向每个 emails[i] 发送一封电子邮件。返回实际收到邮件的不同地址数目。
     * 输入：emails = ["test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"]
     * 输出：2
     * 解释：实际收到邮件的是 "testemail@leetcode.com" 和 "testemail@lee.tcode.com"。
     */
    public static int numUniqueEmails(String[] emails) {
        Set<String> set = new HashSet<>();
        for (String email : emails) {
            String[] strings = email.split("@");
            String first = "";
            if (strings[0].contains("+")) {
                first = strings[0].substring(0, strings[0].indexOf("+")).replace(".", "");
            } else {
                first = strings[0].replace(".", "");
            }
            set.add(first + "@" + strings[1]);
        }
        return set.size();
    }

    /**
     * 给定二叉搜索树的根结点 root，返回值位于范围 [low, high] 之间的所有结点的值的和。
     * 输入：root = [10,5,15,3,7,null,18], low = 7, high = 15
     * 输出：32
     */
    public int rangeSumBST(TreeNode root, int low, int high) {
//        List<Integer> list = Solution2.inorderTraversal(root);
//        return list.stream().filter(integer -> integer >= low && integer <= high).mapToInt(Integer::intValue).sum();
        if (root == null) {
            return 0;
        }
        if (root.val > high) {
            return rangeSumBST(root.left, low, high);
        }
        if (root.val < low) {
            return rangeSumBST(root.right, low, high);
        }
        return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low, high);
    }

    /**
     * 给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
     * 让我们回顾一下，如果 arr 满足下述条件，那么它是一个山脉数组：
     * arr.length >= 3
     * 在 0 < i < arr.length - 1 条件下，存在 i 使得：
     * arr[0] < arr[1] < ... arr[i-1] < arr[i]
     * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
     * 输入：arr = [3,5,5]
     * 输出：false
     */
    public static boolean validMountainArray(int[] arr) {
//        if(arr.length < 3){
//            return false;
//        }
//        boolean decr = false;
//        boolean incr = false;
//        //只能有一个顶峰,且要有升有降
//        for(int i = 0; i < arr.length - 1; i++){
//            if(arr[i] < arr[i + 1]){
//                if(decr){
//                    return false;
//                }
//                incr = true;
//            }else if(arr[i] > arr[i + 1]){
//                decr = true;
//            }else {
//                return false;
//            }
//        }
//        return decr && incr;
        int N = arr.length;
        int i = 0;
        // 递增扫描
        while (i + 1 < N && arr[i] < arr[i + 1]) {
            i++;
        }
        // 最高点不能是数组的第一个位置或最后一个位置
        if (i == 0 || i == N - 1) {
            return false;
        }
        // 递减扫描
        while (i + 1 < N && arr[i] > arr[i + 1]) {
            i++;
        }
        return i == N - 1;
    }

    /**
     * 由范围 [0,n] 内所有整数组成的 n + 1 个整数的排列序列可以表示为长度为 n 的字符串 s ，其中:
     * 如果 perm[i] < perm[i + 1] ，那么 s[i] == 'I'
     * 如果 perm[i] > perm[i + 1] ，那么 s[i] == 'D'
     * 给定一个字符串 s ，重构排列 perm 并返回它。如果有多个有效排列perm，则返回其中 任何一个 。
     * 输入：s = "IDID"
     * 输出：[0,4,1,3,2]
     */
    public static int[] diStringMatch(String s) {
//        int count = s.length();
//        int a = 0;
//        int[] ans = new int[count + 1];
//        List<Integer> list = new ArrayList<>();
//        for(int i = 0; i <= count; i++){
//            list.add(i);
//        }
//        for(int i = 0; i < s.length(); i++){
//            if(s.charAt(i) == 'I'){
//                ans[i] = a;
//                list.remove((Object)a);
//                a++;
//            }else {
//                ans[i] = count;
//                list.remove((Object)count);
//                count--;
//            }
//        }
//        ans[s.length()] = list.get(0);
//        return ans;
        int n = s.length(), lo = 0, hi = n;
        int[] perm = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            perm[i] = s.charAt(i) == 'I' ? lo++ : hi--;
        }
        perm[n] = lo; // 最后剩下一个数，此时 lo == hi
        return perm;
    }

    /**
     * 给你由 n 个小写字母字符串组成的数组 strs，其中每个字符串长度相等。
     * 这些字符串可以每个一行，排成一个网格。例如，strs = ["abc", "bce", "cae"] 可以排列为：
     * abc
     * bce
     * cae
     * 你需要找出并删除 不是按字典序非严格递增排列的 列。在上面的例子（下标从 0 开始）中，列 0（'a', 'b', 'c'）和列 2（'c', 'e', 'e'）都是按字典序非严格递增排列的，而列 1（'b', 'c', 'a'）不是，所以要删除列 1 。
     * 返回你需要删除的列数。
     */
    public int minDeletionSize(String[] strs) {
//        int count = 0;
//        //行
//        int n = strs.length;
//        //列
//        int m = strs[0].length();
//        for(int i = 0; i < m; i++){
//            List<Character> list = new ArrayList<>();
//            for(int j = 0; j < n; j++){
//                char ch = strs[j].charAt(i);
//                list.add(ch);
//            }
//            for(int k = 0; k < list.size() - 1; k++){
//                if (list.get(k) > list.get(k + 1)){
//                    count++;
//                    break;
//                }
//            }
//        }
//        return count;
        int row = strs.length;
        int col = strs[0].length();
        int ans = 0;
        for (int j = 0; j < col; ++j) {
            for (int i = 1; i < row; ++i) {
                if (strs[i - 1].charAt(j) > strs[i].charAt(j)) {
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     * 输入：words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
     * 输出：true
     * 解释：在该语言的字母表中，'h' 位于 'l' 之前，所以单词序列是按字典序排列的。
     */
    public boolean isAlienSorted(String[] words, String order) {
        int[] index = new int[26];
        for (int i = 0; i < order.length(); ++i) {
            index[order.charAt(i) - 'a'] = i;
        }
        for (int i = 1; i < words.length; i++) {
            boolean valid = false;
            for (int j = 0; j < words[i - 1].length() && j < words[i].length(); j++) {
                int prev = index[words[i - 1].charAt(j) - 'a'];
                int curr = index[words[i].charAt(j) - 'a'];
                if (prev < curr) {
                    valid = true;
                    break;
                } else if (prev > curr) {
                    return false;
                }
            }
            if (!valid) {
                /* 比较两个字符串的长度 */
                if (words[i - 1].length() > words[i].length()) {
                    return false;
                }
            }
        }
        return true;
    }

    Set<Integer> set = new HashSet<>();

    /**
     * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
     * 只有给定的树是单值二叉树时，才返回 true；否则返回 false。
     */
    public boolean isUnivalTree(TreeNode root) {
        if (root != null) {
            set.add(root.val);
            if (set.size() > 1) {
                return false;
            }
            return isUnivalTree(root.left) && isUnivalTree(root.right);
        }
        return true;
    }

    /**
     * 给定由一些正数（代表长度）组成的数组 nums ，返回 由其中三个长度组成的、面积不为零的三角形的最大周长 。
     * 如果不能形成任何面积不为零的三角形，返回 0。
     * 输入：nums = [2,1,2]
     * 输出：5
     * 解释：你可以用三个边长组成一个三角形:1 2 2。
     */
    public int largestPerimeter(int[] nums) {
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            if (nums[i] < nums[i - 1] + nums[i - 2]) {
                return nums[i - 1] + nums[i - 2] + nums[i];
            }
        }
        return 0;
    }

    /**
     * 整数的 数组形式  num 是按照从左到右的顺序表示其数字的数组。
     * 例如，对于 num = 1321 ，数组形式是 [1,3,2,1] 。
     * 给定 num ，整数的 数组形式 ，和整数 k ，返回 整数 num + k 的 数组形式 。
     * 输入：num = [1,2,0,0], k = 34
     * 输出：[1,2,3,4]
     * 解释：1200 + 34 = 1234
     */
    public static List<Integer> addToArrayForm(int[] num, int k) {
//        long sum = 0;
//        for(int i = num.length - 1; i >= 0; i--){
//            sum += num[i] * Math.pow(10,(num.length - 1 - i));
//        }
//        sum += k;
//        char[] chars = String.valueOf(sum).toCharArray();
//        List<Integer> list = new ArrayList<>();
//        for(char ch : chars){
//            list.add(Integer.parseInt(String.valueOf(ch)));
//        }
//        return list;
        /*
        上面的例子 123+912，我们把它表示成 [1,2,3+912]。然后，我们计算 3+912=915。5 留在当前这一位，将 910/10=91 以进位的形式加入下一位。
        然后，我们再重复这个过程，计算 [1,2+91,5]。我们得到 93，3 留在当前位，将 90/10=9 以进位的形式加入下一位。继而又得到 [1+9,3,5]，重复这个过程之后，最终得到结果 [1,0,3,5]。
         */
        List<Integer> res = new ArrayList<Integer>();
        int n = num.length;
        for (int i = n - 1; i >= 0 || k > 0; --i, k /= 10) {
            if (i >= 0) {
                k += num[i];
            }
            res.add(k % 10);
        }
        Collections.reverse(res);
        return res;
    }

    /**
     * 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
     * 如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。
     * 我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。
     * 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。
     */
    // x 的信息
    int x;
    TreeNode xParent;
    int xDepth;
    boolean xFound = false;

    // y 的信息
    int y;
    TreeNode yParent;
    int yDepth;
    boolean yFound = false;

    public boolean isCousins(TreeNode root, int x, int y) {
        this.x = x;
        this.y = y;
        dfs(root, 0, null);
        return xDepth == yDepth && xParent != yParent;
    }

    public void dfs(TreeNode node, int depth, TreeNode parent) {
        if (node == null) {
            return;
        }

        if (node.val == x) {
            xParent = parent;
            xDepth = depth;
            xFound = true;
        } else if (node.val == y) {
            yParent = parent;
            yDepth = depth;
            yFound = true;
        }

        // 如果两个节点都找到了，就可以提前退出遍历
        // 即使不提前退出，对最坏情况下的时间复杂度也不会有影响
        if (xFound && yFound) {
            return;
        }

        dfs(node.left, depth + 1, node);

        if (xFound && yFound) {
            return;
        }

        dfs(node.right, depth + 1, node);
    }

    /**
     * 小镇里有 n 个人，按从 1 到 n 的顺序编号。传言称，这些人中有一个暗地里是小镇法官。
     * 如果小镇法官真的存在，那么：
     * 小镇法官不会信任任何人。
     * 每个人（除了小镇法官）都信任这位小镇法官。
     * 只有一个人同时满足属性 1 和属性 2 。
     * 给你一个数组 trust ，其中 trust[i] = [ai, bi] 表示编号为 ai 的人信任编号为 bi 的人。
     * 如果小镇法官存在并且可以确定他的身份，请返回该法官的编号；否则，返回 -1 。
     * 输入：n = 2, trust = [[1,2]]
     * 输出：2
     */
    public int findJudge(int n, int[][] trust) {
//        List<Integer> list = new ArrayList<>();
//        for(int i = 1; i <= n; i++){
//            list.add(i);
//        }
//        for(int[] nums : trust){
//            list.remove((Object)nums[0]);
//        }
//        if(list.size() != 1){
//            return -1;
//        }
//        int judge = list.get(0);
//        int count = 0;
//        for(int[] nums : trust){
//            if(judge == nums[1]){
//                count++;
//            }
//        }
//        return count == n - 1 ? judge : -1;
        int[] inDegrees = new int[n + 1];
        int[] outDegrees = new int[n + 1];
        for (int[] edge : trust) {
            int x = edge[0], y = edge[1];
            ++inDegrees[y];
            ++outDegrees[x];
        }
        for (int i = 1; i <= n; ++i) {
            if (inDegrees[i] == n - 1 && outDegrees[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 在一个 8 x 8 的棋盘上，有一个白色的车（Rook），用字符 'R' 表示。棋盘上还可能存在空方块，白色的象（Bishop）以及黑色的卒（pawn），分别用字符 '.'，'B' 和 'p' 表示。不难看出，大写字符表示的是白棋，小写字符表示的是黑棋。
     * 车按国际象棋中的规则移动。东，西，南，北四个基本方向任选其一，然后一直向选定的方向移动，直到满足下列四个条件之一：
     * 棋手选择主动停下来。
     * 棋子因到达棋盘的边缘而停下。
     * 棋子移动到某一方格来捕获位于该方格上敌方（黑色）的卒，停在该方格内。
     * 车不能进入/越过已经放有其他友方棋子（白色的象）的方格，停在友方棋子前。
     * 你现在可以控制车移动一次，请你统计有多少敌方的卒处于你的捕获范围内（即，可以被一步捕获的棋子数）。
     */
    public int numRookCaptures(char[][] board) {
        //我们可以建立方向数组表示在这个方向上移动一步的增量，比如向北移动一步的时候，白色车的 x 轴坐标减 1，而 y 轴坐标不会变化，
        // 所以我们可以用 (-1, 0) 表示白色车向北移动一步的增量，其它三个方向同理
        int cnt = 0, st = 0, ed = 0;
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (board[i][j] == 'R') {
                    st = i;
                    ed = j;
                    break;
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            for (int step = 0; ; ++step) {
                int tx = st + step * dx[i];
                int ty = ed + step * dy[i];
                if (tx < 0 || tx >= 8 || ty < 0 || ty >= 8 || board[tx][ty] == 'B') {
                    break;
                }
                if (board[tx][ty] == 'p') {
                    cnt++;
                    break;
                }
            }
        }
        return cnt;
    }

    /**
     * 给你一个字符串数组 words ，请你找出所有在 words 的每个字符串中都出现的共用字符（ 包括重复字符），并以数组形式返回。你可以按 任意顺序 返回答案。
     * 输入：words = ["bella","label","roller"]
     * 输出：["e","l","l"]
     */
    public static List<String> commonChars(String[] words) {
//        List<String> list = new ArrayList<>();
//        String str = words[0];
//        for(int i = 0; i < str.length(); i++){
//            char ch = str.charAt(i);
//            int count = 0;
//            for(int j = 1; j < words.length; j++){
//                if(!words[j].contains(String.valueOf(ch))){
//                    break;
//                }else {
//                    count++;
//                }
//            }
//            if(count == words.length - 1){
//                list.add(String.valueOf(ch));
//                for(int j = 1; j < words.length; j++){
//                    words[j] = words[j].replaceFirst(String.valueOf(ch),"");
//                }
//            }
//        }
//        return list;
        int[] minfreq = new int[26];
        Arrays.fill(minfreq, Integer.MAX_VALUE);
        for (String word : words) {
            int[] freq = new int[26];
            int length = word.length();
            for (int i = 0; i < length; ++i) {
                char ch = word.charAt(i);
                ++freq[ch - 'a'];
            }
            for (int i = 0; i < 26; ++i) {
                minfreq[i] = Math.min(minfreq[i], freq[i]);
            }
        }

        List<String> ans = new ArrayList<String>();
        for (int i = 0; i < 26; ++i) {
            for (int j = 0; j < minfreq[i]; ++j) {
                ans.add(String.valueOf((char) (i + 'a')));
            }
        }
        return ans;
    }

    /**
     * 给你一个整数数组 nums 和一个整数 k ，按以下方法修改该数组：
     * 选择某个下标 i 并将 nums[i] 替换为 -nums[i] 。
     * 重复这个过程恰好 k 次。可以多次选择同一个下标 i 。
     * 以这种方式修改数组后，返回数组 可能的最大和 。
     * 输入：nums = [4,2,3], k = 1
     * 输出：5
     * 解释：选择下标 1 ，nums 变为 [4,-2,3] 。
     */
    public int largestSumAfterKNegations(int[] nums, int k) {
        /*
        如果数组中存在 0，那么我们可以对它进行多次修改，直到把剩余的修改次数用完；
        如果数组中不存在 0 并且剩余的修改次数是偶数，由于对同一个数修改两次等价于不进行修改，因此我们也可以在不减小数组的和的前提下，把修改次数用完；
        如果数组中不存在 0 并且剩余的修改次数是奇数，那么我们必然需要使用单独的一次修改将一个正数变为负数（剩余的修改次数为偶数，就不会减小数组的和）。
        为了使得数组的和尽可能大，我们就选择那个最小的正数。
         */
//        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
//        for (int num : nums) {
//            freq.put(num, freq.getOrDefault(num, 0) + 1);
//        }
//        int ans = Arrays.stream(nums).sum();
//        for (int i = -100; i < 0; ++i) {
//            if (freq.containsKey(i)) {
//                int ops = Math.min(k, freq.get(i));
//                ans += (-i) * ops * 2;
//                freq.put(i, freq.get(i) - ops);
//                freq.put(-i, freq.getOrDefault(-i, 0) + ops);
//                k -= ops;
//                if (k == 0) {
//                    break;
//                }
//            }
//        }
//        if (k % 2 == 1 && !freq.containsKey(0)) {
//            for (int i = 1; i <= 100; ++i) {
//                if (freq.containsKey(i)) {
//                    ans -= i * 2;
//                    break;
//                }
//            }
//        }
//        return ans;

        // 排序，把可能有的负数排到前面
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            // 贪心：如果是负数，而k还有盈余，就把负数反过来
            if (nums[i] < 0 && k > 0) {
                nums[i] = -1 * nums[i];
                k--;
            }
            sum += nums[i];
        }
        Arrays.sort(nums);
        // 如果k没剩，那说明能转的负数都转正了，已经是最大和，返回sum
        // 如果k有剩，说明负数已经全部转正，所以如果k还剩偶数个就自己抵消掉，不用删减，如果k还剩奇数个就减掉2倍最小正数。
        return sum - (k % 2 == 0 ? 0 : 2 * nums[0]);
    }

    /**
     * 给你一个整数数组 arr，只有可以将其划分为三个和相等的 非空 部分时才返回 true，否则返回 false。
     * 形式上，如果可以找出索引 i + 1 < j 且满足 (arr[0] + arr[1] + ... + arr[i] == arr[i + 1] + arr[i + 2] + ... + arr[j - 1]
     * == arr[j] + arr[j + 1] + ... + arr[arr.length - 1]) 就可以将数组三等分。
     * 输入：arr = [0,2,1,-6,6,-7,9,1,2,0,1]
     * 输出：true
     * 解释：0 + 2 + 1 = -6 + 6 - 7 + 9 + 1 = 2 + 0 + 1
     */
    public static boolean canThreePartsEqualSum(int[] arr) {
//        int sum = Arrays.stream(arr).sum();
//        if(sum % 3 != 0){
//            return false;
//        }
//        int aver = sum / 3;
//        int i = 0;
//        int j = arr.length - 1;
//        int cnt = 0;
//        boolean flag = true;
//        while ((i < j && cnt != aver) || flag){
//            cnt += arr[i];
//            i++;
//            flag = false;
//        }
//        if(cnt == aver){
//            cnt = 0;
//        }else {
//            return false;
//        }
//        while ((i < j && cnt != aver) || !flag){
//            cnt += arr[j];
//            j--;
//            flag = true;
//        }
//        if(cnt == aver){
//            cnt = 0;
//        }else {
//            return false;
//        }
//        if(i > j || i == 0 || j == arr.length - 1){
//            return false;
//        }
//        for (int m = i; m <= j; m++){
//            cnt += arr[m];
//        }
//        return cnt == aver;
        int s = 0;
        for (int num : arr) {
            s += num;
        }
        if (s % 3 != 0) {
            return false;
        }
        int target = s / 3;
        int n = arr.length, i = 0, cur = 0;
        while (i < n) {
            cur += arr[i];
            if (cur == target) {
                break;
            }
            ++i;
        }
        if (cur != target) {
            return false;
        }
        int j = i + 1;
        while (j + 1 < n) {  // 需要满足最后一个数组非空
            cur += arr[j];
            if (cur == target * 2) {
                return true;
            }
            ++j;
        }
        return false;

    }

    /**
     * 有效括号字符串为空 ""、"(" + A + ")" 或 A + B ，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。
     * 例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。
     * 如果有效字符串 s 非空，且不存在将其拆分为 s = A + B 的方法，我们称其为原语（primitive），其中 A 和 B 都是非空有效括号字符串。
     * 给出一个非空有效字符串 s，考虑将其进行原语化分解，使得：s = P_1 + P_2 + ... + P_k，其中 P_i 是有效括号字符串原语。
     * 对 s 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回 s 。
     * 输入：s = "(()())(())"
     * 输出："()()()"
     * 解释：
     * 输入字符串为 "(()())(())"，原语化分解得到 "(()())" + "(())"，
     * 删除每个部分中的最外层括号后得到 "()()" + "()" = "()()()"。
     */
    public String removeOuterParentheses(String s) {
//        int begin = 0;
//        int end = 0;
//        int count = 0;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == '(') {
//                count++;
//            } else {
//                count--;
//            }
//            if (count == 0) {
//                end = i;
//                sb.append(s.substring(begin + 1, end));
//                begin = end + 1;
//            }
//        }
//        return sb.toString();
        int level = 0;
        StringBuffer res = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ')') {
                level--;
            }
            if (level > 0) {
                res.append(c);
            }
            if (c == '(') {
                level++;
            }
        }
        return res.toString();
    }

    /**
     * 给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
     * 例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
     * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
     * 返回这些数字之和。题目数据保证答案是一个 32 位 整数。
     * 输入：root = [1,0,1,0,1,0,1]
     * 输出：22
     * 解释：(100) + (101) + (110) + (111) = 4 + 5 + 6 + 7 = 22
     */
    public int sumRootToLeaf(TreeNode root) {
        return dfs2(root, 0);
    }

    public int dfs2(TreeNode root, int val) {
        if (root == null) {
            return 0;
        }
        val = (val << 1) | root.val;
        if (root.left == null && root.right == null) {
            return val;
        }
        return dfs2(root.left, val) + dfs2(root.right, val);
    }

    /**
     * 爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。
     * 最初，黑板上有一个数字 n 。在每个玩家的回合，玩家需要执行以下操作：
     * (1)选出任一 x，满足 0 < x < n 且 n % x == 0 。
     * (2)用 n - x 替换黑板上的数字 n 。
     * 如果玩家无法执行这些操作，就会输掉游戏。
     * 只有在爱丽丝在游戏中取得胜利时才返回 true 。假设两个玩家都以最佳状态参与游戏。
     * 输入：n = 3
     * 输出：false
     * 解释：爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。
     */
    public boolean divisorGame(int n) {
        //n 为奇数的时候 Alice（先手）必败，n 为偶数的时候 Alice 必胜。
        return n % 2 == 0;
    }

    /**
     * 给定一个数组 points ，其中 points[i] = [xi, yi] 表示 X-Y 平面上的一个点，如果这些点构成一个 回旋镖 则返回 true 。
     * 回旋镖 定义为一组三个点，这些点 各不相同 且 不在一条直线上 。
     * 输入：points = [[1,1],[2,2],[3,3]]
     * 输出：false
     */
    public boolean isBoomerang(int[][] points) {
//        int x = points[1][0] - points[0][0];
//        int y = points[1][1] - points[0][1];
//        if(x == 0 && y == 0){
//            return false;
//        }
//        int nx = points[2][0] - points[1][0];
//        int ny = points[2][1] - points[1][1];
//        if(x == 0 || y == 0){
//            if((x == 0 && nx == 0) || (y == 0 && ny == 0)){
//                return false;
//            }else {
//                return true;
//            }
//        }else {
//            if(nx/x == ny/y && nx % x == ny % y){
//                return false;
//            }else {
//                return true;
//            }
//        }
        //这两个向量的叉乘结果不为零
        int[] v1 = {points[1][0] - points[0][0], points[1][1] - points[0][1]};
        int[] v2 = {points[2][0] - points[0][0], points[2][1] - points[0][1]};
        return v1[0] * v2[1] - v1[1] * v2[0] != 0;
    }

    /**
     * 有一堆石头，每块石头的重量都是正整数。
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     * 输入：[2,7,4,1,8,1]
     * 输出：1
     * 解释：
     * 先选出 7 和 8，得到 1，所以数组转换为 [2,4,1,1,1]，
     * 再选出 2 和 4，得到 2，所以数组转换为 [2,1,1,1]，
     * 接着是 2 和 1，得到 1，所以数组转换为 [1,1,1]，
     * 最后选出 1 和 1，得到 0，最终数组转换为 [1]，这就是最后剩下那块石头的重量。
     */
    public static int lastStoneWeight(int[] stones) {
//        if(stones.length == 1){
//            return stones[0];
//        }else if(stones.length == 0){
//            return 0;
//        }
//        Arrays.sort(stones);
//        int len = stones.length;
//        int sub = stones[len - 1] - stones[len - 2];
//        if(sub == 0){
//            return lastStoneWeight(Arrays.copyOfRange(stones,0,len - 2));
//        }else {
//            int[] newStones = new int[len - 1];
//            for(int i = 0; i < len - 2; i++){
//                newStones[i] = stones[i];
//            }
//            newStones[len - 2] = sub;
//            return lastStoneWeight(newStones);
//        }
        //优先级队列,大根队列
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        for (int stone : stones) {
            pq.offer(stone);
        }

        while (pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            if (a > b) {
                pq.offer(a - b);
            }
        }
        return pq.isEmpty() ? 0 : pq.poll();
    }

    /**
     * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
     * 在 S 上反复执行重复项删除操作，直到无法继续删除。
     * 在完成所有重复项删除操作后返回最终的字符串。答案保证唯一。
     * 输入："abbaca"
     * 输出："ca"
     * 解释：
     * 例如，在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
     */
    public static String removeDuplicates(String s) {
//        if(s.length() == 1){
//            return s;
//        }
//        int pre = 0;
//        int cur = 1;
//        for(int i = 1; i < s.length(); i++){
//            if(s.charAt(pre) == s.charAt(cur)){
//                String str = String.valueOf(s.charAt(pre)) + String.valueOf(s.charAt(pre));
//                s = s.replace(str,"");
//                return removeDuplicates(s);
//            }else {
//                pre = cur;
//                cur++;
//            }
//        }
//        return s;


//        StringBuffer stack = new StringBuffer();
//        int top = -1;
//        for (int i = 0; i < s.length(); ++i) {
//            char ch = s.charAt(i);
//            if (top >= 0 && stack.charAt(top) == ch) {
//                stack.deleteCharAt(top);
//                --top;
//            } else {
//                stack.append(ch);
//                ++top;
//            }
//        }
//        return stack.toString();

        char[] charArray = s.toCharArray();
        int top = -1;
        for (int i = 0; i < s.length(); i++) {
            if (top == -1 || charArray[top] != charArray[i]) {
                charArray[++top] = charArray[i];
            } else {
                top--;
            }
        }
        return String.valueOf(charArray, 0, top + 1);
    }

    /**
     * 学校打算为全体学生拍一张年度纪念照。根据要求，学生需要按照 非递减 的高度顺序排成一行。
     * 排序后的高度情况用整数数组 expected 表示，其中 expected[i] 是预计排在这一行中第 i 位的学生的高度（下标从 0 开始）。
     * 给你一个整数数组 heights ，表示 当前学生站位 的高度情况。heights[i] 是这一行中第 i 位学生的高度（下标从 0 开始）。
     * 返回满足 heights[i] != expected[i] 的 下标数量 。
     * 输入：heights = [1,1,4,2,1,3]
     * 输出：3
     * 解释：
     * 高度：[1,1,4,2,1,3]
     * 预期：[1,1,1,2,3,4]
     * 下标 2 、4 、5 处的学生高度不匹配。
     */
    public int heightChecker(int[] heights) {
        int[] newHeight = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            newHeight[i] = heights[i];
        }
        System.arraycopy(heights, 0, newHeight, 0, heights.length);

        Arrays.sort(heights);
        int count = 0;
        for (int i = 0; i < heights.length; i++) {
            if (heights[i] != newHeight[i]) {
                count++;
            }
        }
        return count;
    }

    /**
     * 给定四个整数 rows ,   cols ,  rCenter 和 cCenter 。有一个 rows x cols 的矩阵，你在单元格上的坐标是 (rCenter, cCenter) 。
     * 返回矩阵中的所有单元格的坐标，并按与 (rCenter, cCenter) 的 距离 从最小到最大的顺序排。你可以按 任何 满足此条件的顺序返回答案。
     * 单元格(r1, c1) 和 (r2, c2) 之间的距离为|r1 - r2| + |c1 - c2|。
     * 输入：rows = 2, cols = 2, rCenter = 0, cCenter = 1
     * 输出：[[0,1],[0,0],[1,1],[1,0]]
     * 解释：从 (r0, c0) 到其他单元格的距离为：[0,1,1,2]
     * [[0,1],[1,1],[0,0],[1,0]] 也会被视作正确答案。
     */
    public static int[][] allCellsDistOrder(int rows, int cols, int rCenter, int cCenter) {
        int[][] ret = new int[rows * cols][];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ret[i * cols + j] = new int[]{i, j};
            }
        }
        Arrays.sort(ret, Comparator.comparingInt(a -> (Math.abs(a[0] - rCenter) + Math.abs(a[1] - cCenter))));
        return ret;
    }

    /**
     * 对于字符串 s 和 t，只有在 s = t + ... + t（t 自身连接 1 次或多次）时，我们才认定 “t 能除尽 s”。
     * 给定两个字符串 str1 和 str2 。返回 最长字符串 x，要求满足 x 能除尽 str1 且 x 能除尽 str2 。
     * 输入：str1 = "ABABAB", str2 = "ABAB"
     * 输出："AB"
     */
    public static String gcdOfStrings(String str1, String str2) {
//        int n = str1.length();
//        int m = str2.length();
//        int index = 0;
//        String reg = "";
//        while (index < n && index < m){
//            if(str1.charAt(index) == str2.charAt(index)){
//                String str = str1.substring(0,index + 1);
//                String[] array = str1.split(str);
//                String[] array2 = str2.split(str);
//                if(array.length == 0 && array2.length == 0){
//                    reg = str;
//                }
//                index++;
//            }else {
//                return "";
//            }
//        }
//        return reg;
        //如果 str1 和 str2 拼接后等于 str2和 str1 拼接起来的字符串（注意拼接顺序不同），那么一定存在符合条件的字符串 X
        if (!str1.concat(str2).equals(str2.concat(str1))) {
            return "";
        }
        return str1.substring(0, gcd(str1.length(), str2.length()));
    }

    public static int gcd(int a, int b) {
        int remainder = a % b;
        while (remainder != 0) {
            a = b;
            b = remainder;
            remainder = a % b;
        }
        return b;
    }

    /**
     * 给出第一个词 first 和第二个词 second，考虑在某些文本 text 中可能以 "first second third" 形式出现的情况，其中 second 紧随 first 出现，third 紧随 second 出现。
     * 对于每种这样的情况，将第三个词 "third" 添加到答案中，并返回答案。
     * 输入：text = "alice is a good girl she is a good student", first = "a", second = "good"
     * 输出：["girl","student"]
     */
    public static String[] findOcurrences(String text, String first, String second) {
//        int fromIndex = 0;
//        List<String> list = new ArrayList<>();
//        while (text.indexOf(first + " " + second,fromIndex) >= 0){
//            int index = text.indexOf(first + " " + second,fromIndex);
//            int begin = index + first.length() + second.length() + 2;
//            int end = begin;
//            fromIndex = end;
//            while (text.charAt(end) != ' ' && end < text.length() - 1){
//                end++;
//            }
//            if(end == text.length() - 1){
//                list.add(text.substring(begin,end + 1));
//            }else {
//                list.add(text.substring(begin,end));
//            }
//
//        }
//        return list.toArray(new String[0]);
        String[] words = text.split(" ");
        List<String> list = new ArrayList<String>();
        for (int i = 2; i < words.length; i++) {
            if (words[i - 2].equals(first) && words[i - 1].equals(second)) {
                list.add(words[i]);
            }
        }
        int size = list.size();
        String[] ret = new String[size];
        for (int i = 0; i < size; i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    /**
     * 给你一个长度固定的整数数组 arr ，请你将该数组中出现的每个零都复写一遍，并将其余的元素向右平移。
     * 注意：请不要在超过该数组长度的位置写入元素。请对输入的数组 就地 进行上述修改，不要从函数返回任何东西。
     * 输入：arr = [1,0,2,3,0,4,5,0]
     * 输出：[1,0,0,2,3,0,0,4]
     */
    public static void duplicateZeros(int[] arr) {
        //我们用 top 来标记栈顶位置，用 i 来标记现在需要放置的元素位置，那么我们找到原数组中对应放置在最后位置的元素位置，
        // 然后在数组最后从该位置元素往前来进行模拟放置即可。
//        int n = arr.length;
//        int top = 0;
//        int i = -1;
//        while (top < n) {
//            i++;
//            if (arr[i] != 0) {
//                top++;
//            } else {
//                top += 2;
//            }
//        }
//        int j = n - 1;
//        if (top == n + 1) {
//            arr[j] = 0;
//            j--;
//            i--;
//        }
//        while (j >= 0) {
//            arr[j] = arr[i];
//            j--;
//            if (arr[i] == 0) {
//                arr[j] = arr[i];
//                j--;
//            }
//            i--;
//        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; ++i)
            sb.append(arr[i]);
        String s = sb.toString();
        s = s.replace("0", "00");
        for (int i = 0; i < arr.length; ++i)
            arr[i] = s.charAt(i) - '0';

    }

    /**
     * 我们买了一些糖果 candies，打算把它们分给排好队的 n = num_people 个小朋友。
     * 给第一个小朋友 1 颗糖果，第二个小朋友 2 颗，依此类推，直到给最后一个小朋友 n 颗糖果。
     * 然后，我们再回到队伍的起点，给第一个小朋友 n + 1 颗糖果，第二个小朋友 n + 2 颗，依此类推，直到给最后一个小朋友 2 * n 颗糖果。
     * 重复上述过程（每次都比上一次多给出一颗糖果，当到达队伍终点后再次从队伍起点开始），直到我们分完所有的糖果。注意，就算我们手中的剩下糖果数不够（不比前一次发出的糖果多），这些糖果也会全部发给当前的小朋友。
     * 返回一个长度为 num_people、元素之和为 candies 的数组，以表示糖果的最终分发情况（即 ans[i] 表示第 i 个小朋友分到的糖果数）。
     * 输入：candies = 7, num_people = 4
     * 输出：[1,2,3,1]
     * 解释：
     * 第一次，ans[0] += 1，数组变为 [1,0,0,0]。
     * 第二次，ans[1] += 2，数组变为 [1,2,0,0]。
     * 第三次，ans[2] += 3，数组变为 [1,2,3,0]。
     * 第四次，ans[3] += 1（因为此时只剩下 1 颗糖果），最终数组变为 [1,2,3,1]。
     */
    public int[] distributeCandies(int candies, int num_people) {
        int[] array = new int[num_people];
        int count = 1;
        while (candies > 0) {
            for (int i = 0; i < num_people; i++) {
                if (candies >= count) {
                    array[i] = array[i] + count;
                    candies -= count;
                } else {
                    array[i] = array[i] + candies;
                    candies = 0;
                }
                count++;
            }
        }
        return array;
    }

    /**
     * 给你两个数组，arr1 和 arr2，arr2 中的元素各不相同，arr2 中的每个元素都出现在 arr1 中。
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int upper = 0;
        for (int x : arr1) {
            upper = Math.max(upper, x);
        }
        int[] frequency = new int[upper + 1];
        for (int x : arr1) {
            ++frequency[x];
        }
        int[] ans = new int[arr1.length];
        int index = 0;
        for (int x : arr2) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
            frequency[x] = 0;
        }
        for (int x = 0; x <= upper; ++x) {
            for (int i = 0; i < frequency[x]; ++i) {
                ans[index++] = x;
            }
        }
        return ans;

//        Map<Integer, Integer> map = new HashMap<>();
//        List<Integer> list = new ArrayList<>();
//        for (int num : arr1)
//            list.add(num);
//        for (int i = 0; i < arr2.length; i++)
//            map.put(arr2[i], i);
//        Collections.sort(list, (x, y) -> {
//            if (map.containsKey(x) || map.containsKey(y))
//                return map.getOrDefault(x, 1001) - map.getOrDefault(y, 1001);
//            return x - y;
//        });
//        for (int i = 0; i < arr1.length; i++)
//            arr1[i] = list.get(i);
//        return arr1;
    }

    public static void main(String[] args) throws ParseException {
        Random peopleRandom = new Random();
        for(int i = 0; i < 10 ; i++){
            double male = peopleRandom.nextInt(30) + 1;
//            System.out.println(male);
            double minValue = 0.0; // 最小值（包含）
            double maxValue = 0.2; // 最大值（不包含）

            double randomNum = peopleRandom.nextDouble() * (maxValue - minValue) + minValue; // 生成[minValue, maxValue)之间的随机小数
            DecimalFormat format = new DecimalFormat("#0.0");
            String str = format.format(randomNum);
            System.out.println(peopleRandom.nextInt(3));
//            System.out.println("随机小数为：" + str);
        }
//        duplicateZeros(new int[]{1, 0, 2, 3, 0, 4, 5, 0});

//        System.out.println(findOcurrences("we will we will rock you","we","will"));
//        System.out.println(gcdOfStrings("ABAB","ABABAB"));
//        System.out.println(allCellsDistOrder(2,2,0,1));
//        System.out.println(removeDuplicates("abbaca"));
//        System.out.println(lastStoneWeight(new int[]{2,7,4,1,8,1}));
//        System.out.println(canThreePartsEqualSum(new int[]{-87,94,14,-13,50,99,-56,86,-99,-84,-43,43,22,-81,-82,8,-69,92,-81,3,-84,50,73,-15,-74,30,75,62,-15,-28,29,78,-30,54,58,-84,87,-55,-81,62,9,-31,-88,-44,74,16,-75,32,40,-28,5,-44,-91,18,68,89,59,-16,46,2,-98,16,71,-14,-7,-39,-78,-48,78,-39,0,70,-28,45,-25,-82,19,99,57,-26,25,25,86,48,90,-61,-56,-81,89,-61,-68,88,82,-92,-19,-61,-14,35,92,-95,47,-20,-32,47,-89,50,-1,-57,68,30,25,0,-17,34,56,58,-20,72,-4,-36,-36,-63,46,76,36,42,-94,-89,-4,-9,12,-64,11,-52,46,-39,-66,-36,0}));
//        System.out.println(commonChars(new String[]{"bella","label","roller"}));
//        System.out.println(addToArrayForm(new int[]{9,9,9,9,9,9,9,9,9,9},1));
//        System.out.println(diStringMatch("IDID"));
//        System.out.println(validMountainArray(new int[]{0,1,2,3,4,5,6,7,8,9}));
//        System.out.println(numUniqueEmails(new String[]{"test.email+alex@leetcode.com","test.email.leet+alex@code.com"}));
//        System.out.println(isLongPressedName("kikcxmvzi","kiikcxxmmvvzz"));
//        System.out.println(reverseOnlyLetters("a-bC-dEf-ghIj"));
//        System.out.println(isMonotonic(new int[]{1,2,2,3}));
//        System.out.println(fairCandySwap(new int[]{32,38,8,1,9},new int[]{68,92}));
//        System.out.println(uncommonFromSentences("s z z z s","s z ejt"));
    }

}
