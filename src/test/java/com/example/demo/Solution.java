package com.example.demo;

import com.example.demo.entity.ListNode;
import com.example.demo.entity.Node;
import com.example.demo.entity.TreeNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class Solution {

    /**
     * 输入：grid = [[1,2,4],[3,3,1]]
     * 输出：8
     * 解释：上图展示在每一步中需要移除的值。
     * - 在第一步操作中，从第一行删除 4 ，从第二行删除 3（注意，有两个单元格中的值为 3 ，我们可以删除任一）。在答案上加 4 。
     * - 在第二步操作中，从第一行删除 2 ，从第二行删除 3 。在答案上加 3 。
     * - 在第三步操作中，从第一行删除 1 ，从第二行删除 1 。在答案上加 1 。
     * 最终，答案 = 4 + 3 + 1 = 8 。
     *
     * @param grid
     * @return
     */
    public int deleteGreatestValue(int[][] grid) {
//        int loop = grid[0].length;
//        int count = 0;
//        for(int m = 0;m<loop;m++){
//            int max = 0;
//            for (int i = 0; i < grid.length; i++) {
//                int need = 0;
//                int index = 0;
//                for(int j = 0; j < grid[i].length; j++){
//                    if(grid[i][j] >= need){
//                        need = grid[i][j];
//                        index = j;
//                    }
//                }
//                grid[i][index] = 0;
//                if(need >= max){
//                    max = need;
//                }
//            }
//            count = count + max;
//        }
//        return count;

        System.out.println("------------------------------------------->");
        int ans = 0;
        for (int[] g : grid) {
            Arrays.sort(g);
        }
        for (int i = 0; i < grid[0].length; ++i) {
            int max = grid[0][i];
            for (int j = 1; j < grid.length; ++j) {
                max = Math.max(max, grid[j][i]);
            }
            ans += max;
        }

        return ans;
    }

    /**
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * <p>
     * 你可以按任意顺序返回答案。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
//        int[] result = new int[2];
//        for(int i=0;i<nums.length;i++){
//            for(int j=i+1;j<nums.length;j++){
//                if(nums[i] + nums[j] == target){
//                    result[0] = i;
//                    result[1] = j;
//                    break;
//                }
//            }
//        }
//        return result;

        Map<Integer, Integer> hashtable = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; ++i) {
            if (hashtable.containsKey(target - nums[i])) {
                return new int[]{hashtable.get(target - nums[i]), i};
            }
            hashtable.put(nums[i], i);
        }
        return new int[0];
    }

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 输入: s = "pwwkew"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * @param s
     */
    public int lengthOfLongestSubstring(String s) {
//        String str2 = "";
//        if (StringUtils.isNotBlank(s)) {
//            for (int start = 0; start < s.length(); start++) {
//                for (int end = start + 1; end < s.length(); end++) {
//                    String str = s.substring(start, end + 1);
//                    if (containRepeat(str)) {
//                        //有重复字符
//                        if (StringUtils.isBlank(str2)) {
//                            str2 = s.substring(start, end);
//                        }
//                        if (s.substring(start, end).length() > str2.length()) {
//                            str2 = s.substring(start, end);
//                        }
//
//                        break;
//                    }
//                }
//            }
//        }
//        return str2.length();
        char arr[] = s.toCharArray();
        int pos[] = new int[128];
        int i = 0, j = 0, ans = 0;
        for (i = 0; i < arr.length; i++) {

            j = Math.max(j, pos[arr[i]]);
            pos[arr[i]] = i + 1;
            ans = Math.max(ans, i - j + 1);
        }

        return ans;
    }

    public boolean containRepeat(String s) {
        char[] chars = s.toCharArray();
        Set<Object> charSet = new HashSet<>();
        for (char c : chars) {
            charSet.add(c);
        }
        return s.length() != charSet.size();
    }

    /**
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请
     * <p>
     * 你返回所有和为 0 且不重复的三元组。
     * <p>
     * 注意：答案中不可以包含重复的三元组。
     * 输入：nums = [-1,0,1,2,-1,-4]
     * 输出：[[-1,-1,2],[-1,0,1]]
     *
     * @param nums
     */
    public List<List<Integer>> threeSum(int[] nums) {
//        Set<List<Integer>> set = new HashSet<>();
//        if(nums.length >= 3){
//            Arrays.sort(nums);
//            for(int i=0;i<nums.length-2;i++){
//                for(int j=i+1;j<nums.length-1;j++){
//                    for(int k=j+1;k<nums.length;k++){
//                        if (nums[i] < 0 && nums[k] > 0 || nums[i] == nums[k]) {
//                            if (nums[i] + nums[j] + nums[k] == 0) {
//                                List list = new ArrayList();
//                                list.add(nums[i]);
//                                list.add(nums[j]);
//                                list.add(nums[k]);
//                                set.add(list);
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        }
//        return new ArrayList<>(set);
        //定一找二
        //结果集
        ArrayList<List<Integer>> res = new ArrayList<>();
        //排序
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            //若定的最小元素大于0，那找三数之和一定大于0
            if (nums[i] > 0) {
                break;
            }
            //去重：若当前元素与前一个元素相等，得到的结果前一次已经得到了
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int l = i + 1;
            int r = nums.length - 1;
            while (l < r) {
                int num = nums[i] + nums[l] + nums[r];
                if (num == 0) {
                    //去重
                    while (l < r && nums[r] == nums[r - 1]) {
                        r--;
                    }
                    //去重
                    while (l < r && nums[l] == nums[l + 1]) {
                        l++;
                    }
                    //添加到结果集
                    res.add(Arrays.asList(nums[i], nums[l], nums[r]));
                    r--;
                    l++;
                } else if (num > 0) {
                    //因为有序，若大于0，则r--
                    r--;
                } else {
                    //小于0，则l++
                    l++;
                }
            }
        }
        return res;
    }

    /**
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * 如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。
     * 输入：s = "babad"
     * 输出："bab"
     * 解释："aba" 同样是符合题意的答案。
     *
     * @param s
     */
    public String longestPalindrome(String s) {
//        String result = "";
//        int max = 0;
//        if(s.length() > 0){
//            if(s.length() == 1){
//                result = s;
//            }
//            char[] chars = s.toCharArray();
//            for(int i=0;i<chars.length -1;i++){
//                boolean flag = false;
//                char start = s.charAt(i);
//                int rIndex = s.lastIndexOf(start);
//                String str = s.substring(i,rIndex + 1);
//                while (str.length() >max){
//                    if(flag){
//                        if(str.length() > rIndex + 1){
//                            str = str.substring(0,rIndex + 1);
//                        }
//                        rIndex = str.lastIndexOf(start);
//                        str = str.substring(0,rIndex + 1);
//
//                    }
//                    String reverseStr = reverseByStack(str);
//                    if(str.equals(reverseStr)){
//                        if(str.length() > max){
//                            result = str;
//                            max = str.length();
//                        }
//                        break;
//                    }else {
//                        rIndex--;
//                        flag = true;
//                    }
//                }
//                if(rIndex <= 0 && result.length() <= 0){
//                    result = String.valueOf(start);
//                }
//
//            }
//        }
//        return result;
        /*

         */
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * @description 字符串反转
     * @author liwenwu
     * @time 2023/7/31
     */
    public String reverseByStack(String str) {
        if (str == null || str.length() == 1) {
            return str;
        }
        Stack<Character> stack = new Stack<Character>();
        //字符串转换成字符数组
        char[] ch = str.toCharArray();
        for (char c : ch) {
            //每个字符，推进栈
            stack.push(c);
        }
        for (int i = 0; i < ch.length; i++) {
            //移除这个堆栈的顶部对象
            ch[i] = stack.pop();
        }
        return new String(ch);
    }

    /**
     * 给你一个链表的头节点 head ，判断链表中是否有环。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。注意：pos 不作为参数进行传递 。仅仅是为了标识链表的实际情况。
     * 如果链表中存在环 ，则返回 true 。 否则，返回 false 。
     * 假想「乌龟」和「兔子」在链表上移动，「兔子」跑得快，「乌龟」跑得慢。当「乌龟」和「兔子」从链表上的同一个节点开始移动时，如果该链表中没有环，那么「兔子」将一直处于「乌龟」的前方；如果该链表中有环，那么「兔子」会先于「乌龟」进入环，并且一直在环内移动。等到「乌龟」进入环时，由于「兔子」的速度快，它一定会在某个时刻与乌龟相遇，即套了「乌龟」若干圈。
     * <p>
     * 我们可以根据上述思路来解决本题。具体地，我们定义两个指针，一快一慢。慢指针每次只移动一步，而快指针每次移动两步。初始时，慢指针在位置 head，而快指针在位置 head.next。这样一来，如果在移动的过程中，快指针反过来追上慢指针，就说明该链表为环形链表。否则快指针将到达链表尾部，该链表不为环形链表。
     */
    public boolean hasCycle(ListNode head) {
//        Set<ListNode> set = new HashSet<>();
//        while (null != head){
//            if(!set.add(head)){
//                return true;
//            }
//            head = head.next;
//        }
//        return false;
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head.next;
        ListNode slow = head;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = head.next;
            fast = head.next.next.next;
        }
        return true;
    }

    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * 输入：nums = [3,2,2,3], val = 3
     * 输出：2, nums = [2,2]
     * 解释：函数应该返回新的长度 2, 并且 nums 中的前两个元素均为 2。你不需要考虑数组中超出新长度后面的元素。例如，函数返回的新长度为 2 ，而 nums = [2,2,3,3] 或 nums = [2,2,0,0]，也会被视作正确答案。
     * 如果左指针 left 指向的元素等于 val，此时将右指针 right 指向的元素复制到左指针 left的位置，然后右指针 right左移一位。如果赋值过来的元素恰好也等于 val，可以继续把右指针 right指向的元素的值赋值过来（左指针 left指向的等于 val 的元素的位置继续被覆盖），直到左指针指向的元素的值不等于 val为止。
     * <p>
     * 当左指针 left 和右指针 right重合的时候，左右指针遍历完数组中所有的元素。
     */
    public int removeElement(int[] nums, int val) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right - 1];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }

    /**
     * 给你一个长度为 n 的整数数组 nums 和 一个目标值 target。请你从 nums 中选出三个整数，使它们的和与 target 最接近。
     * 返回这三个数的和。
     * 假定每组输入只存在恰好一个解。
     * 输入：nums = [-1,2,1,-4], target = 1
     * 输出：2
     * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
     */
    public int threeSumClosest(int[] nums, int target) {
        int result = 0;
        if (nums.length >= 3) {
            int min = 10000;
            nums = Arrays.stream(nums).sorted().toArray();
            for (int i = 0; i < nums.length - 2; i++) {
                int l = i + 1;
                int r = nums.length - 1;
                if (nums[i] + nums[l] + nums[r] == target) {
                    result = target;
                    break;
                }
                while (nums[i] + nums[l] + nums[r] <= target && l < r - 1) {
                    if (nums[i] + nums[l] + nums[r] == target) {
                        result = target;
                        return result;
                    }
                    l++;
                }
                while (nums[i] + nums[l] + nums[r] >= target && l < r - 1) {
                    if (nums[i] + nums[l] + nums[r] == target) {
                        result = target;
                        return result;
                    }
                    r--;
                }
                int sum = nums[i] + nums[l] + nums[r];
//                min = Math.abs(sum - target);
                if (Math.abs(sum - target) < min) {
                    min = Math.abs(sum - target);
                    result = sum;
                    if (min == 0) {
                        break;
                    }
                }

            }
        }
        return result;
    }

    public int threeSumClosest2(int[] nums, int target) {
        Arrays.sort(nums);
        int n = nums.length;
        int best = 10000000;

        // 枚举 a
        for (int i = 0; i < n; ++i) {
            // 保证和上一次枚举的元素不相等
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 使用双指针枚举 b 和 c
            int j = i + 1, k = n - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                // 如果和为 target 直接返回答案
                if (sum == target) {
                    return target;
                }
                // 根据差值的绝对值来更新答案
                if (Math.abs(sum - target) < Math.abs(best - target)) {
                    best = sum;
                }
                if (sum > target) {
                    // 如果和大于 target，移动 c 对应的指针
                    int k0 = k - 1;
                    // 移动到下一个不相等的元素
                    while (j < k0 && nums[k0] == nums[k]) {
                        --k0;
                    }
                    k = k0;
                } else {
                    // 如果和小于 target，移动 b 对应的指针
                    int j0 = j + 1;
                    // 移动到下一个不相等的元素
                    while (j0 < k && nums[j0] == nums[j]) {
                        ++j0;
                    }
                    j = j0;
                }
            }
        }
        return best;
    }

    /**
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 请必须使用时间复杂度为 O(log n) 的算法。
     * 输入: nums = [1,3,5,6], target = 5
     * 输出: 2
     * 输入: nums = [1,3,5,6], target = 2
     * 输出: 1
     */
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            if (nums[left] == target) {
                return left;
            }
            if (nums[right] == target) {
                return right;
            }
            if (nums[left] > target) {
                return left;
            }
            if (nums[right] < target) {
                return right + 1;
            }
            if (nums[left] < target) {
                left++;
            }
            if (nums[right] > target) {
                right--;
            }
        }
        if (nums[left] > target) {
            return left;
        } else {
            return left - 1;
        }
    }

    /**
     * 给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     * 不允许修改 链表。
     * 输入：head = [3,2,0,-4], pos = 1
     * 输出：返回索引为 1 的链表节点
     * 解释：链表中有一个环，其尾部连接到第二个节点。
     */
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (null != head) {
            if (!set.add(head)) {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
     * 输入：head = [1,2,3,4,5]
     * 输出：[5,4,3,2,1]
     * 在遍历链表时，将当前节点的 next\textit{next}next 指针改为指向前一个节点。由于节点没有引用其前一个节点，因此必须事先存储其前一个节点。
     * 在更改引用之前，还需要存储后一个节点。最后返回新的头引用。
     */
    public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * 输入：head = [1], n = 1
     * 输出：[]
     * 一种容易想到的方法是，我们首先从头节点开始对链表进行一次遍历，得到链表的长度 LLL。随后我们再从头节点开始对链表进行一次遍历，当遍历到第 L−n+1L-n+1L−n+1 个节点时，它就是我们需要删除的节点。
     * 为了方便删除操作，我们可以从哑节点开始遍历 L−n+1L-n+1L−n+1 个节点。当遍历到第 L−n+1L-n+1L−n+1 个节点时，它的下一个节点就是我们需要删除的节点，这样我们只需要修改一次指针，就能完成删除操作
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head != null && n > 0) {
            int len = getLength(head);
            ListNode dummy = new ListNode(0, head);
            ListNode cur = dummy;
            for (int i = 1; i < len - n + 1; ++i) {
                cur = cur.next;
            }
            cur.next = cur.next.next;
            return dummy.next;
        }
        return head;
    }

    public int getLength(ListNode head) {
        int length = 0;
        while (head != null) {
            ++length;
            head = head.next;
        }
        return length;
    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
     * 输入：l1 = [1,2,4], l2 = [1,3,4]
     * 输出：[1,1,2,3,4,4]
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//        if (l1 == null) {
//            return l2;
//        } else if (l2 == null) {
//            return l1;
//        } else if (l1.val < l2.val) {
//            l1.next = mergeTwoLists(l1.next, l2);
//            return l1;
//        } else {
//            l2.next = mergeTwoLists(l1, l2.next);
//            return l2;
//        }

        ListNode prehead = new ListNode(-1);

        ListNode prev = prehead;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        return prehead.next;

    }

    /**
     * 给你一个整数数组 nums ，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 子数组 是数组中的一个连续部分。
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * f(i)=max{f(i−1)+nums[i],nums[i]}
     */
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    /**
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * 输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
     * 输出：6
     * 解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。
     * 创建两个长度为 nnn 的数组 leftMax\textit{leftMax}leftMax 和 rightMax\textit{rightMax}rightMax。对于 0≤i<n0 \le i<n0≤i<n，leftMax[i]\textit{leftMax}[i]leftMax[i] 表示下标 iii 及其左边的位置中，height\textit{height}height 的最大高度，rightMax[i]\textit{rightMax}[i]rightMax[i] 表示下标 iii 及其右边的位置中，height\textit{height}height 的最大高度。
     * 因此可以正向遍历数组 height\textit{height}height 得到数组 leftMax\textit{leftMax}leftMax 的每个元素值，反向遍历数组 height\textit{height}height 得到数组 rightMax\textit{rightMax}rightMax 的每个元素值。
     * <p>
     * 在得到数组 leftMax\textit{leftMax}leftMax 和 rightMax\textit{rightMax}rightMax 的每个元素值之后，对于 0≤i<n0 \le i<n0≤i<n，下标 iii 处能接的雨水量等于 min⁡(leftMax[i],rightMax[i])−height[i]\min(\textit{leftMax}[i],\textit{rightMax}[i])-\textit{height}[i]min(leftMax[i],rightMax[i])−height[i]。遍历每个下标位置即可得到能接的雨水总量。
     */
    public int trap(int[] height) {
        int n = height.length;
        if (n == 0) {
            return 0;
        }

        int[] leftMax = new int[n];
        leftMax[0] = height[0];
        for (int i = 1; i < n; ++i) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; --i) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        int ans = 0;
        for (int i = 0; i < n; ++i) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        return ans;
    }

    /**
     * 给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
     * 回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     * 输入：x = 121
     * 输出：true
     * 输入：x = -121
     * 输出：false
     */
    public boolean isPalindrome(int x) {
//        if(x < 0){
//            return false;
//        }
//        String str = String.valueOf(x);
//        if(str.equals(reverseByStack(str))){
//            return true;
//        }else {
//            return false;
//        }
        // 特殊情况：
        // 如上所述，当 x < 0 时，x 不是回文数。
        // 同样地，如果数字的最后一位是 0，为了使该数字为回文，
        // 则其第一位数字也应该是 0
        // 只有 0 满足这一属性
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }
        /*
        对于数字 1221，如果执行 1221 % 10，我们将得到最后一位数字 1，要得到倒数第二位数字，我们可以先通过除以 10 把最后一位数字从 1221 中移除，
        1221 / 10 = 122，再求出上一步结果除以 10 的余数，122 % 10 = 2，就可以得到倒数第二位数字。
        如果我们把最后一位数字乘以 10，再加上倒数第二位数字，1 * 10 + 2 = 12，就得到了我们想要的反转后的数字。
        如果继续这个过程，我们将得到更多位数的反转数字。
        由于整个过程我们不断将原始数字除以 10，然后给反转后的数字乘上 10，所以，当原始数字小于或等于反转后的数字时，就意味着我们已经处理了一半位数的数字了。
         */
        int revertedNumber = 0;
        while (x > revertedNumber) {
            revertedNumber = revertedNumber * 10 + x % 10;
            x /= 10;
        }

        // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
        // 例如，当输入为 12321 时，在 while 循环的末尾我们可以得到 x = 12，revertedNumber = 123，
        // 由于处于中位的数字不影响回文（它总是与自己相等），所以我们可以简单地将其去除。
        return x == revertedNumber || x == revertedNumber / 10;
    }

    /**
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 s 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    public void reverseString(char[] s) {
//        Stack<Character> stack = new Stack<Character>();
//
//        for (char c : s) {
//            //每个字符，推进栈
//            stack.push(c);
//        }
//        for (int i = 0; i < s.length; i++) {
//            //移除这个堆栈的顶部对象
//            s[i] = stack.pop();
//        }
        int n = s.length;
        for (int left = 0, right = n - 1; left < right; ++left, --right) {
            char tmp = s[left];
            s[left] = s[right];
            s[right] = tmp;
        }
    }

    /**
     * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和并同样以字符串形式返回。
     * 你不能使用任何內建的用于处理大整数的库（比如 BigInteger）， 也不能直接将输入的字符串转换为整数形式。
     * 输入：num1 = "11", num2 = "123"
     * 输出："134"
     */
    public String addStrings(String num1, String num2) {
        int x = num1.length() - 1;
        int y = num2.length() - 1;
        int add = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (x >= 0 || y >= 0 || add != 0) {
            int i = x >= 0 ? num1.charAt(x) - '0' : 0;
            int j = y >= 0 ? num2.charAt(y) - '0' : 0;
            int result = i + j + add;
            stringBuilder.append(result % 10);
            add = result / 10;
            --x;
            --y;
        }
        stringBuilder.reverse();
        return stringBuilder.toString();
    }

    /**
     * 给你一个 升序排列 的数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。元素的 相对顺序 应该保持 一致 。然后返回 nums 中唯一元素的个数。
     * 考虑 nums 的唯一元素的数量为 k ，你需要做以下事情确保你的题解可以被通过：
     * 更改数组 nums ，使 nums 的前 k 个元素包含唯一元素，并按照它们最初在 nums 中出现的顺序排列。nums 的其余元素与 nums 的大小不重要。
     * 返回 k 。
     * int[] nums = [...]; // 输入数组
     * int[] expectedNums = [...]; // 长度正确的期望答案
     * <p>
     * int k = removeDuplicates(nums); // 调用
     * <p>
     * assert k == expectedNums.length;
     * for (int i = 0; i < k; i++) {
     * assert nums[i] == expectedNums[i];
     * }
     * 输入：nums = [1,1,2]
     * 输出：2, nums = [1,2,_]
     * 解释：函数应该返回新的长度 2 ，并且原数组 nums 的前两个元素被修改为 1, 2 。不需要考虑数组中超出新长度后面的元素。
     */
    public int removeDuplicates(int[] nums) {
//        Set<Integer> set = new HashSet<>();
//        int len = nums.length;
//        List<Integer> list = new ArrayList<>();
//        for(int i=0;i<len;i++){
//            if(!set.add(nums[i])){
//                //不成功，即为重复数字
//            }else {
//                list.add(nums[i]);
//            }
//        }
//        nums = list.toArray(new Integer[len]);
//        return nums.length;

        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int fast = 1, slow = 1;
        while (fast < n) {
            if (nums[fast] != nums[fast - 1]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }

    /**
     * 给你两个按 非递减顺序 排列的整数数组 nums1 和 nums2，另有两个整数 m 和 n ，分别表示 nums1 和 nums2 中的元素数目。
     * 请你 合并 nums2 到 nums1 中，使合并后的数组同样按 非递减顺序 排列。
     * 注意：最终，合并后数组不应由函数返回，而是存储在数组 nums1 中。为了应对这种情况，nums1 的初始长度为 m + n，其中前 m 个元素表示应合并的元素，后 n 个元素为 0 ，应忽略。nums2 的长度为 n 。
     * 输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     * 输出：[1,2,2,3,5,6]
     * 解释：需要合并 [1,2,3] 和 [2,5,6] 。
     * 合并结果是 [1,2,2,3,5,6] ，其中斜体加粗标注的为 nums1 中的元素。
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
//        if(m == 0){
//            nums1 = nums2;
//        }else if(n == 0){
//        }else {
//            int[] compare = new int[m+n];
//            int left = 0;
//            int right = 0;
//            for(int i=0;i<m+n;i++){
//                if(left < m){
//                    if(nums1[left] <= nums2[right]){
//                        compare[i] = nums1[left];
//                        left++;
//                    }else {
//                        compare[i] = nums2[right];
//                        right++;
//                    }
//                }else {
//                    compare[i] = nums2[right];
//                    right++;
//                }
//            }
//            nums1 = compare;
//            System.out.println(nums1);
//        }
        //追加num2到num1的尾部，然后重新排序
        for (int i = 0; i != n; ++i) {
            nums1[m + i] = nums2[i];
        }
        Arrays.sort(nums1);

    }

    /**
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     * 输入：n = 2
     * 输出：2
     * 解释：有两种方法可以爬到楼顶。
     * 1. 1 阶 + 1 阶
     * 2. 2 阶
     */
    public int climbStairs(int n) {
        //它意味着爬到第 xxx 级台阶的方案数是爬到第 x−1x - 1x−1 级台阶的方案数和爬到第 x−2x - 2x−2 级台阶的方案数的和。很好理解，因为每次只能爬 111 级或 222 级，所以 f(x)f(x)f(x) 只能从 f(x−1)f(x - 1)f(x−1) 和 f(x−2)f(x - 2)f(x−2) 转移过来，而这里要统计方案总数，我们就需要对这两项的贡献求和。
        //f(x)=f(x−1)+f(x−2)
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;
    }

    /**
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * 输入：[7,1,5,3,6,4]
     * 输出：5
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
     * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
     */
    public int maxProfit(int[] prices) {
        int minprice = Integer.MAX_VALUE;
        int maxprofit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minprice) {
                minprice = prices[i];
            } else if (prices[i] - minprice > maxprofit) {
                maxprofit = prices[i] - minprice;
            }
        }
        return maxprofit;
    }

    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 请注意 ，必须在不复制数组的情况下原地对数组进行操作。
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * <p>
     * 使用双指针，左指针指向当前已经处理好的序列的尾部，右指针指向待处理序列的头部。
     * 右指针不断向右移动，每次右指针指向非零数，则将左右指针对应的数交换，同时左指针右移。
     * 左指针左边均为非零数；
     * 右指针左边直到左指针处均为零。
     * 因此每次交换，都是将左指针的零与右指针的非零数交换，且非零数的相对顺序并未改变。
     */
    public void moveZeroes(int[] nums) {
        if (nums.length > 0) {
            int n = nums.length, left = 0, right = 0;
            while (right < n) {
                if (nums[right] != 0) {
                    swap(nums, left, right);
                    left++;
                }
                right++;
            }
        }
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

    /**
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
     * 如果 needle 不是 haystack 的一部分，则返回  -1 。
     * 输入：haystack = "sadbutsad", needle = "sad"
     * 输出：0
     * 解释："sad" 在下标 0 和 6 处匹配。
     * 第一个匹配项的下标是 0 ，所以返回 0 。
     */
    public static int strStr(String haystack, String needle) {
        int result = -1;
        if (haystack.length() > 0 && needle.length() > 0) {
            result = haystack.indexOf(needle);
        }
        return result;
    }

    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * 如果不存在公共前缀，返回空字符串 ""。
     * 输入：strs = ["flower","flow","flight"]
     * 输出："fl"
     */
    public static String longestCommonPrefix(String[] strs) {
//        if(strs.length == 1){
//            return strs[0];
//        }
//        if(strs.length > 0){
//            String str = strs[0];
//            String resultStr = "";
//            boolean flag = true;
//            if(str.length() > 0){
//                for(int k = 1; k < str.length();k++){
//                    String needStr = str.substring(0,k);
//                    for(int i=1;i<strs.length;i++){
//                        if(strs[i].contains(needStr)){
//                            resultStr = needStr;
//                        }else {
//                            flag = false;
//                            break;
//                        }
//                    }
//                    if(!flag){
//                        break;
//                    }
//                }
//                if(resultStr.length() > 0){
//                    return resultStr.substring(0,resultStr.length()-1);
//                }else {
//                    return str;
//                }
//            }
//        }
//        return "";


        if (strs == null || strs.length == 0) {
            return "";
        }
        String prefix = strs[0];
        int count = strs.length;
        for (int i = 1; i < count; i++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }
        return prefix;
    }

    public static String longestCommonPrefix(String str1, String str2) {
        int length = Math.min(str1.length(), str2.length());
        int index = 0;
        while (index < length && str1.charAt(index) == str2.charAt(index)) {
            index++;
        }
        return str1.substring(0, index);
    }

    /**
     * n 个朋友在玩游戏。这些朋友坐成一个圈，按 顺时针方向 从 1 到 n 编号。
     * 从第 i 个朋友的位置开始顺时针移动 1 步会到达第 (i + 1) 个朋友的位置（1 <= i < n），
     * 而从第 n 个朋友的位置开始顺时针移动 1 步会回到第 1 个朋友的位置。
     * 第 1 个朋友接球。
     * <p>
     * 接着，第 1 个朋友将球传给距离他顺时针方向 k 步的朋友。
     * 然后，接球的朋友应该把球传给距离他顺时针方向 2 * k 步的朋友。
     * 接着，接球的朋友应该把球传给距离他顺时针方向 3 * k 步的朋友，以此类推。
     * 换句话说，在第 i 轮中持有球的那位朋友需要将球传递给距离他顺时针方向 i * k 步的朋友。
     * <p>
     * 当某个朋友第 2 次接到球时，游戏结束。
     * <p>
     * 在整场游戏中没有接到过球的朋友是 输家 。
     * <p>
     * 给你参与游戏的朋友数量 n 和一个整数 k ，请按升序排列返回包含所有输家编号的数组 answer 作为答案。
     */
    public static int[] circularGameLosers(int n, int k) {
//        List<Integer> list = new ArrayList<>();
//        for(int i=1;i<=n;i++){
//            list.add(i);
//        }
//        Set set = new HashSet();
//        int index = 0;
//        for(int i = 0;i< n;i++){
//            index += i*k;
//            if(index >= n){
//                index = index % n;
//            }
//            if(set.add(index)){
//                //说明没有重复
//                list.set(index,null);
//            }else {
//                //游戏结束
//                break;
//            }
//        }
//        list.removeIf(Objects::isNull);
//        int[] ints = new int[list.size()];
//        for(int m=0;m<list.size();m++){
//            if(null != list.get(m)){
//                ints[m] = list.get(m);
//            }
//        }
//        return ints;

        boolean[] visit = new boolean[n];
        for (int i = k, j = 0; !visit[j]; i += k) {
            visit[j] = true;
            j = (j + i) % n;
        }
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (!visit[i]) {
                list.add(i + 1);
            }
        }
        int[] ans = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    /**
     * 我们定义，在以下情况时，单词的大写用法是正确的：
     * 全部字母都是大写，比如 "USA" 。
     * 单词中所有字母都不是大写，比如 "leetcode" 。
     * 如果单词不只含有一个字母，只有首字母大写， 比如 "Google" 。
     * 给你一个字符串 word 。如果大写用法正确，返回 true ；否则，返回 false 。
     */
    public boolean detectCapitalUse(String word) {
        char[] chars = word.toCharArray();
        int count = 0;
        for (char ch : chars) {
            if (0 <= ch - 'A' && ch - 'A' <= 25) {
                count++;
            }
        }
        if (count == chars.length || count == 0 || (count == 1 && 0 <= chars[0] - 'A' && chars[0] - 'A' <= 25)) {
            return true;
        }
        return false;
    }

    /**
     * 给你两个字符串 a 和 b，请返回 这两个字符串中 最长的特殊序列  的长度。如果不存在，则返回 -1 。
     * 「最长特殊序列」 定义如下：该序列为 某字符串独有的最长子序列（即不能是其他字符串的子序列） 。
     * 字符串 s 的子序列是在从 s 中删除任意数量的字符后可以获得的字符串。
     * 例如，"abc" 是 "aebdc" 的子序列。 "aebdc" 的子序列还包括 "aebdc" 、 "aeb" 和 "" (空字符串)。
     * 输入: a = "aba", b = "cdc"
     * 输出: 3
     * 解释: 最长特殊序列可为 "aba" (或 "cdc")，两者均为自身的子序列且不是对方的子序列。
     */
    public int findLUSlength(String a, String b) {
        //如果俩个字符串长度相同并且一模一样那么不存在这样的子串，
        // 如果俩个字符串长度相同并且字符串对应位置字符不一样，那么返回其中任意一个的长度即可，
        // 如果俩个字符串长度不等，返回长的那个字符串长度即可
        return a.equals(b) ? -1 : Math.max(a.length(), b.length());
    }

    /**
     * 给定一个字符串 s 和一个整数 k，从字符串开头算起，每计数至 2k 个字符，就反转这 2k 字符中的前 k 个字符。
     * 如果剩余字符少于 k 个，则将剩余字符全部反转。
     * 如果剩余字符小于 2k 但大于或等于 k 个，则反转前 k 个字符，其余字符保持原样。
     * 输入：s = "abcdefg", k = 2
     * 输出："bacdfeg"
     */
    public static String reverseStr(String s, int k) {
//        int n = s.length();
//        int count = n / k;
//        int mod = n % k;
//        if(mod > 0){
//            count++;
//        }
//        //奇数翻转，偶数不变
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < count; i++){
//            String str = s.substring(i * k,Math.min(i * k + k,n));
//            if(i % 2 == 0){
//                sb.append(new StringBuilder(str).reverse());
//            }else {
//                sb.append(str);
//            }
//        }
//        return sb.toString();
        int n = s.length();
        char[] arr = s.toCharArray();
        for (int i = 0; i < n; i += 2 * k) {
            reverse(arr, i, Math.min(i + k, n) - 1);
        }
        return new String(arr);
    }

    public static void reverse(char[] arr, int left, int right) {
        while (left < right) {
            char temp = arr[left];
            arr[left] = arr[right];
            arr[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * 给你一棵二叉树的根节点，返回该树的 直径 。
     * 二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
     * 两节点之间路径的 长度 由它们之间边数表示。
     */
    int maxd = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        //左子树最大深度+右子树最大深度
        depth(root);
        return maxd;
    }

    public int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int Left = depth(node.left);// 左儿子为根的子树的深度
        int Right = depth(node.right);// 右儿子为根的子树的深度
        maxd = Math.max(Left + Right, maxd);//将每个节点最大直径(左子树深度+右子树深度)当前最大值比较并取大者
        return Math.max(Left, Right) + 1;//返回节点深度
    }

    /**
     * 给你一个字符串 s 表示一个学生的出勤记录，其中的每个字符用来标记当天的出勤情况（缺勤、迟到、到场）。记录中只含下面三种字符：
     * 'A'：Absent，缺勤
     * 'L'：Late，迟到
     * 'P'：Present，到场
     * 如果学生能够 同时 满足下面两个条件，则可以获得出勤奖励：
     * 按 总出勤 计，学生缺勤（'A'）严格 少于两天。
     * 学生 不会 存在 连续 3 天或 连续 3 天以上的迟到（'L'）记录。
     * 如果学生可以获得出勤奖励，返回 true ；否则，返回 false 。
     * 输入：s = "PPALLP"
     * 输出：true
     * 解释：学生缺勤次数少于 2 次，且不存在 3 天或以上的连续迟到记录。
     */
    public boolean checkRecord(String s) {
//        char[] chars = s.toCharArray();
//        int n = chars.length;
//        int count = 0;
//        boolean flag = false;
//        for (int i = 1; i <= n; i++){
//            if(chars[i - 1] == 'A'){
//                count++;
//            }
//            if(i < n && i + 1 < n){
//                if(!flag && chars[i - 1] == 'L' && chars[i] == 'L' && chars[i + 1] == 'L'){
//                    flag = true;
//                }
//            }
//        }
//        if(count <= 1 && !flag){
//            return true;
//        }
//        return false;
        int absents = 0, lates = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if (c == 'A') {
                absents++;
                if (absents >= 2) {
                    return false;
                }
            }
            if (c == 'L') {
                lates++;
                if (lates >= 3) {
                    return false;
                }
            } else {
                lates = 0;
            }
        }
        return true;
    }

    /**
     * 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 输入：s = "Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     */
    public static String reverseWords(String s) {
        String[] strings = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String str : strings){
            char[] chars = str.toCharArray();
            reverse(chars,0,str.length() - 1);
            sb.append(new String(chars)).append(" ");
        }
        return sb.substring(0,sb.length() - 1);
    }

    /**
     * 给定一个 N 叉树，找到其最大深度。
     * 最大深度是指从根节点到最远叶子节点的最长路径上的节点总数。
     * N 叉树输入按层序遍历序列化表示，每组子节点由空值分隔（请参见示例）。
     */
    public int maxDepth(Node root) {
        if(root == null){
            return 0;
        }
        List<Node> children = root.children;
        int ans = 0;
        for(Node node : children){
            ans = Math.max(maxDepth(node),ans);
        }
        return ans + 1;
    }

    /**
     * 给定长度为 2n 的整数数组 nums ，你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，
     * 使得从 1 到 n 的 min(ai, bi) 总和最大。
     * 返回该 最大总和 。
     * 输入：nums = [1,4,3,2]
     * 输出：4
     * 解释：所有可能的分法（忽略元素顺序）为：
     * 1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
     * 2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
     * 3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
     * 所以最大总和为 4
     */
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0;
        for(int i = 0; i < n - 1; i += 2){
//            ans += Math.min(nums[i],nums[i + 1]);
            //因为已经排好序了
            ans += nums[i];
        }
        return ans;
    }

    /**
     * 给你一个二叉树的根节点 root ，计算并返回 整个树 的坡度 。
     * 一个树的 节点的坡度 定义即为，该节点左子树的节点之和和右子树节点之和的 差的绝对值 。
     * 如果没有左子树的话，左子树的节点之和为 0 ；没有右子树的话也是一样。空结点的坡度是 0 。
     * 整个树 的坡度就是其所有节点的坡度之和。
     * 输入：root = [1,2,3]
     * 输出：1
     * 解释：
     * 节点 2 的坡度：|0-0| = 0（没有子节点）
     * 节点 3 的坡度：|0-0| = 0（没有子节点）
     * 节点 1 的坡度：|2-3| = 1（左子树就是左子节点，所以和是 2 ；右子树就是右子节点，所以和是 3 ）
     * 坡度总和：0 + 0 + 1 = 1
     */
    int ans = 0;
    public int findTilt(TreeNode root) {
        dfs(root);
        return ans;
    }

    public int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int sumLeft = dfs(node.left);
        int sumRight = dfs(node.right);
        ans += Math.abs(sumLeft - sumRight);
        return sumLeft + sumRight + node.val;
    }

    /**
     * 在 MATLAB 中，有一个非常有用的函数 reshape ，它可以将一个 m x n 矩阵重塑为另一个大小不同（r x c）的新矩阵，但保留其原始数据。
     * 给你一个由二维数组 mat 表示的 m x n 矩阵，以及两个正整数 r 和 c ，分别表示想要的重构的矩阵的行数和列数。
     * 重构后的矩阵需要将原始矩阵的所有元素以相同的 行遍历顺序 填充。
     * 如果具有给定参数的 reshape 操作是可行且合理的，则输出新的重塑矩阵；否则，输出原始矩阵。
     * 输入：mat = [[1,2],[3,4]], r = 1, c = 4
     * 输出：[[1,2,3,4]]
     */
    public int[][] matrixReshape(int[][] mat, int r, int c) {
//        int n = mat.length;
//        int m = mat[0].length;
//        if(n * m != r * c){
//            return mat;
//        }
//        List<Integer> list = new ArrayList<>();
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < m; j++){
//                list.add(mat[i][j]);
//            }
//        }
//        int[][] array = new int[r][c];
//        int index = 0;
//        for(int i = 0; i < r; i++){
//            for(int j = 0; j < c; j++){
//                array[i][j] = list.get(index);
//                index++;
//            }
//        }
//        return array;
        int m = mat.length;
        int n = mat[0].length;
        if (m * n != r * c) {
            return mat;
        }

        int[][] ans = new int[r][c];
        for (int x = 0; x < m * n; ++x) {
            ans[x / c][x % c] = mat[x / n][x % n];
        }
        return ans;
    }

    /**
     * 给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。如果存在，返回 true ；否则，返回 false 。
     * 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。tree 也可以看做它自身的一棵子树。
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        return dfs(root, subRoot);
    }

    public boolean dfs(TreeNode s, TreeNode t) {
        if (s == null) {
            return false;
        }
        return check(s, t) || dfs(s.left, t) || dfs(s.right, t);
    }

    public boolean check(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null || s.val != t.val) {
            return false;
        }
        return check(s.left, t.left) && check(s.right, t.right);
    }

    public static void main(String[] args) {
//        System.out.println(isSubtree(new TreeNode(3,new TreeNode(4,new TreeNode(1),new TreeNode(2)),new TreeNode(5)),new TreeNode(4,new TreeNode(1),new TreeNode(2))));
//        System.out.println(reverseWords("Let's take LeetCode contest"));
//        System.out.println(reverseStr("abcdefg", 2));
//        System.out.println(strStr("sadbutsad","sad"));
//        System.out.println(longestCommonPrefix(new String[]{"f","fa"}));
//        System.out.println(circularGameLosers(3,2));
    }

    @Test
    public void test() {
//        moveZeroes(new int[]{0,1,0,3,12});
        System.out.println(strStr("sadbutsad", "sad"));
//        int[][] grid = {{1,2,4},{3,3,1}};
//        System.out.println(deleteGreatestValue(grid));
//        int[] nums = {3, 2, 4};
//        System.out.println(Arrays.toString(twoSum(nums, 6)));
//        System.out.println(lengthOfLongestSubstring("pwwkew"));
//        System.out.println(lengthOfLongestSubstring("abcabcbb"));
//        System.out.println(lengthOfLongestSubstring("bbbbb"));
//        System.out.println(lengthOfLongestSubstring(" "));
//        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
//        System.out.println(threeSum(new int[]{0, 0, 0}));
//        System.out.println(longestPalindrome("aacabdkacaa"));
//        System.out.println(longestPalindrome("rgczcpratwyqxaszbuwwcadruayhasynuxnakpmsyhxzlnxmdtsqqlmwnbxvmgvllafrpmlfuqpbhjddmhmbcgmlyeypkfpreddyencsdmgxysctpubvgeedhurvizgqxclhpfrvxggrowaynrtuwvvvwnqlowdihtrdzjffrgoeqivnprdnpvfjuhycpfydjcpfcnkpyujljiesmuxhtizzvwhvpqylvcirwqsmpptyhcqybstsfgjadicwzycswwmpluvzqdvnhkcofptqrzgjqtbvbdxylrylinspncrkxclykccbwridpqckstxdjawvziucrswpsfmisqiozworibeycuarcidbljslwbalcemgymnsxfziattdylrulwrybzztoxhevsdnvvljfzzrgcmagshucoalfiuapgzpqgjjgqsmcvtdsvehewrvtkeqwgmatqdpwlayjcxcavjmgpdyklrjcqvxjqbjucfubgmgpkfdxznkhcejscymuildfnuxwmuklntnyycdcscioimenaeohgpbcpogyifcsatfxeslstkjclauqmywacizyapxlgtcchlxkvygzeucwalhvhbwkvbceqajstxzzppcxoanhyfkgwaelsfdeeviqogjpresnoacegfeejyychabkhszcokdxpaqrprwfdahjqkfptwpeykgumyemgkccynxuvbdpjlrbgqtcqulxodurugofuwzudnhgxdrbbxtrvdnlodyhsifvyspejenpdckevzqrexplpcqtwtxlimfrsjumiygqeemhihcxyngsemcolrnlyhqlbqbcestadoxtrdvcgucntjnfavylip"));
//        System.out.println(longestPalindrome("xaabacxcabaaxcabaax"));
//        System.out.println(removeElement(new int[]{0,1,2,2,3,0,4,2},2));
//        System.out.println(threeSumClosest2(new int[]{13,252,-87,-431,-148,387,-290,572,-311,-721,222,673,538,919,483,-128,-518,7,-36,-840,233,-184,-541,522,-162,127,-935,-397,761,903,-217,543,906,-503,-826,-342,599,-726,960,-235,436,-91,-511,-793,-658,-143,-524,-609,-728,-734,273,-19,-10,630,-294,-453,149,-581,-405,984,154,-968,623,-631,384,-825,308,779,-7,617,221,394,151,-282,472,332,-5,-509,611,-116,113,672,-497,-182,307,-592,925,766,-62,237,-8,789,318,-314,-792,-632,-781,375,939,-304,-149,544,-742,663,484,802,616,501,-269,-458,-763,-950,-390,-816,683,-219,381,478,-129,602,-931,128,502,508,-565,-243,-695,-943,-987,-692,346,-13,-225,-740,-441,-112,658,855,-531,542,839,795,-664,404,-844,-164,-709,167,953,-941,-848,211,-75,792,-208,569,-647,-714,-76,-603,-852,-665,-897,-627,123,-177,-35,-519,-241,-711,-74,420,-2,-101,715,708,256,-307,466,-602,-636,990,857,70,590,-4,610,-151,196,-981,385,-689,-617,827,360,-959,-289,620,933,-522,597,-667,-882,524,181,-854,275,-600,453,-942,134},-2805));
//        System.out.println(searchInsert(new int[]{1,3},2));
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        System.out.println(removeNthFromEnd(node1, 2));
//        System.out.println(mergeTwoLists(new ListNode(1,new ListNode(2,new ListNode(4))),new ListNode(1,new ListNode(3,new ListNode(4)))));
//        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
//        System.out.println(addStrings("11","123"));
//        System.out.println(removeDuplicates(new int[]{1,1,2}));
//        merge(new int[]{1,2,3,0,0,0},3,new int[]{2,5,6},3);
    }
}
