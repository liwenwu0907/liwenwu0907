package com.example.demo;

import com.example.demo.entity.ListNode;
import com.example.demo.entity.TreeNode;

import java.util.*;

public class Solution2 {

    /**
     * 给定一个  无重复元素 的 有序 整数数组 nums 。
     * 返回 恰好覆盖数组中所有数字 的 最小有序 区间范围列表 。也就是说，nums 的每个元素都恰好被某个区间范围所覆盖，
     * 并且不存在属于某个范围但不属于 nums 的数字 x 。
     * 输入：nums = [0,1,2,4,5,7]
     * 输出：["0->2","4->5","7"]
     */
    public static List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        if (nums.length > 0) {
            int begin = nums[0];
            int beginIndex = 0;
            int endIndex = 0;
            for (int i = 1; i <= nums.length; i++) {
                if (i == nums.length && beginIndex == nums.length - 1) {
                    list.add(nums[beginIndex] + "");
                    break;
                }
                if (i == nums.length && beginIndex < nums.length - 1) {
                    list.add(nums[beginIndex] + "->" + nums[nums.length - 1]);
                    break;
                }
                if (begin + 1 == nums[i]) {
                    begin = begin + 1;
                } else {
                    endIndex = i - 1;
                    if (beginIndex == endIndex) {
                        list.add(nums[beginIndex] + "");
                    } else {
                        list.add(nums[beginIndex] + "->" + nums[endIndex]);
                    }
                    beginIndex = i;
                    begin = nums[i];
                }
            }
        }
        return list;
    }

    /**
     * 给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target  ，写一个函数搜索 nums 中的 target，如果目标值存在返回下标，否则返回 -1。
     * 输入: nums = [-1,0,3,5,9,12], target = 9
     * 输出: 4
     */
    public static int search(int[] nums, int target) {
        int result = -1;
        if (nums.length > 0) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                if (target == nums[left]) {
                    result = left;
                    break;
                }
                if (target == nums[right]) {
                    result = right;
                    break;
                }
                left++;
                right--;
            }
        }
        return result;
    }

    /**
     * 给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素。
     * 输入：nums = [3,2,3]
     * 输出：3
     * 如果将数组 nums 中的所有元素按照单调递增或单调递减的顺序排序，那么下标为 n/2 的元素（下标从 0 开始）一定是众数。
     */
    public static int majorityElement(int[] nums) {
//        Arrays.sort(nums);
//        return nums[nums.length/2];

        //我们证明了 count 的值一直为非负，在最后一步遍历结束后也是如此；
        //
        //由于 value 的值与真正的众数 maj 绑定，并且它表示「众数出现的次数比非众数多出了多少次」，那么在最后一步遍历结束后，value 的值为正数；
        int count = 0;
        int candidate = 0;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        return candidate;

    }

    /**
     * 给你两棵二叉树： root1 和 root2 。
     * 想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的一些节点将会重叠（而另一些不会）。你需要将这两棵树合并成一棵新二叉树。
     * 合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；否则，不为 null 的节点将直接作为新二叉树的节点。
     * 返回合并后的二叉树。
     * 注意: 合并过程必须从两个树的根节点开始。
     * 输入：root1 = [1,3,2,5], root2 = [2,1,3,null,4,null,7]
     * 输出：[3,4,5,5,4,null,7]
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        int num = root1.val + root2.val;
        TreeNode newTreeNode = new TreeNode(num);
        TreeNode newLeft = mergeTrees(root1.left, root2.left);
        TreeNode newRight = mergeTrees(root1.right, root2.right);
        newTreeNode.left = newLeft;
        newTreeNode.right = newRight;
        return newTreeNode;
    }

    /**
     * 给你一个非负整数 x ，计算并返回 x 的 算术平方根 。
     * 由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。
     * 输入：x = 8
     * 输出：2
     * 由于 x 平方根的整数部分 ans 是满足 k2≤x 的最大 k 值，因此我们可以对 k 进行二分查找，从而得到答案。
     * 二分查找的下界为 0，上界可以粗略地设定为 x。在二分查找的每一步中，我们只需要比较中间元素 mid 的平方与 x 的大小关系，
     * 并通过比较的结果调整上下界的范围。由于我们所有的运算都是整数运算，不会存在误差，因此在得到最终的答案 ans 后，也就不需要再去尝试 ans+1 了。
     */
    public static int mySqrt(int x) {
        int l = 0;
        int r = x;
        int ans = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if ((long) mid * mid <= x) {
                ans = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 给定一个二叉树的根节点 root ，返回 它的 中序 遍历 。
     * 先遍历输出左孩子节点，再遍历输出根节点，最后遍历输出右孩子节点。
     * 输入：root = [1,null,2,3]
     * 输出：[1,3,2]
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
//        List<Integer> list = new ArrayList<>();
//        if(null != root){
//            if(root.left != null){
//                List<Integer> list1;
//                list1 = inorderTraversal(root.left);
//                list.addAll(list1);
//            }
//            int mid = root.val;
//            list.add(mid);
//            if(root.right != null){
//                List<Integer> list2;
//                list2 = inorderTraversal(root.right);
//                list.addAll(list2);
//            }
//
//        }
//        return list;

        List<Integer> res = new ArrayList<Integer>();
        Deque<TreeNode> stk = new LinkedList<TreeNode>();
        while (root != null || !stk.isEmpty()) {
            while (root != null) {
                stk.push(root);
                root = root.left;
            }
            root = stk.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }

    /**
     * 给定一个二叉树 root ，返回其最大深度。
     * 二叉树的 最大深度 是指从根节点到最远叶子节点的最长路径上的节点数。
     */
    public static int maxDepth(TreeNode root) {
        int maxLen = 0;
        if (null != root) {
            int leftLen = maxDepth(root.left) + 1;
            int rightLen = maxDepth(root.right) + 1;
            maxLen = Math.max(leftLen, rightLen);
        }
        return maxLen;

    }

    /**
     * 给你一个长度为 n ，下标从 0 开始的整数数组 forts ，表示一些城堡。forts[i] 可以是 -1 ，0 或者 1 ，其中：
     * -1 表示第 i 个位置 没有 城堡。
     * 0 表示第 i 个位置有一个 敌人 的城堡。
     * 1 表示第 i 个位置有一个你控制的城堡。
     * 现在，你需要决定，将你的军队从某个你控制的城堡位置 i 移动到一个空的位置 j
     * 军队经过的位置 只有 敌人的城堡
     * 当军队移动时，所有途中经过的敌人城堡都会被 摧毁 。
     * 请你返回 最多 可以摧毁的敌人城堡数目。如果 无法 移动你的军队，或者没有你控制的城堡，请返回 0
     * 输入：forts = [1,0,0,-1,0,0,0,0,1]
     * 输出：4
     */
    public static int captureForts(int[] forts) {
        int ans = 0, pre = -1;
        int n = forts.length;
        for (int i = 0; i < n; i++) {
            if (forts[i] == 1 || forts[i] == -1) {
                if (pre >= 0 && forts[i] != forts[pre]) {
                    ans = Math.max(ans, i - pre - 1);

                }
                pre = i;

            }
        }
        return ans;
    }

    /**
     * 给你一个整数 n，请你帮忙计算并返回该整数「各位数字之积」与「各位数字之和」的差。
     * 输入：n = 234
     * 输出：15
     * 解释：
     * 各位数之积 = 2 * 3 * 4 = 24
     * 各位数之和 = 2 + 3 + 4 = 9
     * 结果 = 24 - 9 = 15
     * 通过「取模」操作 n mod 10得到此时 n 的最低位。
     * 通过「整除」操作 n=n/10 来去掉当前 n 的最低位
     */
    public int subtractProductAndSum(int n) {
//        List<Integer> list = new ArrayList<>();
//        while (n > 0){
//            int num = n%10;
//            list.add(num);
//            n = n/10;
//        }
//        int multi = 1,plus = 0;
//        for (int k : list){
//            multi = k * multi;
//            plus = k + plus;
//        }
//        return multi - plus;
        int m = 1, s = 0;
        while (n != 0) {
            int x = n % 10;
            n /= 10;
            m *= x;
            s += x;
        }
        return m - s;
    }

    /**
     * 给你一个正方形矩阵 mat，请你返回矩阵对角线元素的和。
     * 请你返回在矩阵主对角线上的元素和副对角线上且不在主对角线上元素的和。
     * 输入：mat = [[1,2,3],
     * [4,5,6],
     * [7,8,9]]
     * 输出：25
     * 解释：对角线的和为：1 + 5 + 9 + 3 + 7 = 25
     * 请注意，元素 mat[1][1] = 5 只会被计算一次。
     */
    public static int diagonalSum(int[][] mat) {
        int n = mat.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (n - i - 1 == i) {
                ans = ans + mat[i][i];
            } else {
                ans = ans + mat[i][i] + mat[n - i - 1][i];
            }

        }
        return ans;
    }

    /**
     * 给你一个二叉树的根节点 root ， 检查它是否轴对称。
     */
    public static boolean isSymmetric(TreeNode root) {
        return check(root, root);
    }

    public static boolean check(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.val == right.val && check(left.left, right.right) && check(left.right, right.left);
    }


    /**
     * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (null != root) {
            list.add(root.val);
            if (root.left != null) {
                list.addAll(preorderTraversal(root.left));
            }
            if (root.right != null) {
                list.addAll(preorderTraversal(root.right));
            }
        }
        return list;
    }

    public String replaceSpace(String s) {
        int length = s.length();
        char[] array = new char[length * 3];
        int size = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                array[size++] = '%';
                array[size++] = '2';
                array[size++] = '0';
            } else {
                array[size++] = c;
            }
        }
        return new String(array, 0, size);
    }

    /**
     * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
     * 输入：head = [1,2,6,3,4,5,6], val = 6
     * 输出：[1,2,3,4,5]
     */
    public static ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return dummyHead.next;
//        if (head == null) {
//            return head;
//        }
//        head.next = removeElements(head.next, val);
//        return head.val == val ? head.next : head;
    }

    /**
     * 给定字符串 s 和 t ，判断 s 是否为 t 的子序列。
     * <p>
     * 字符串的一个子序列是原始字符串删除一些（也可以不删除）字符而不改变剩余字符相对位置形成的新字符串。（例如，"ace"是"abcde"的一个子序列，而"aec"不是）。
     * 输入：s = "abc", t = "ahbgdc"
     * 输出：true
     */
    public static boolean isSubsequence(String s, String t) {
//        if(t.contains(s)){
//            return true;
//        }
//        char[] chars = s.toCharArray();
//        int max = -1;
//        for(char char1:chars){
//            int indexT = t.indexOf(char1);
//            if(indexT < 0){
//                return false;
//            }
//            if(max < 0){
//                max = indexT;
//            }else {
//                if(indexT <= max){
//                    return false;
//                }else {
//                    max = indexT;
//                }
//            }
//
//        }
//        return true;

        int n = s.length();
        int m = t.length();
        int i = 0, j = 0;
        while (i < n && j < m) {
            if (s.charAt(i) == t.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == n;
    }

    /**
     * 给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 输入：nums = [4,1,2,1,2]
     * 输出：4
     */
    public static int singleNumber(int[] nums) {
        //唯一的元素只能是奇数位
//        Arrays.sort(nums);
//        for(int i = 0;i<nums.length;i = i+2){
//            if(i + 1 >= nums.length || nums[i] != nums[i+1]){
//                return nums[i];
//            }
//        }
//        return nums[0];
        //任何数和 000 做异或运算，结果仍然是原来的数，即 a⊕0=aa \oplus 0=aa⊕0=a。
        //任何数和其自身做异或运算，结果是 000，即 a⊕a=0a \oplus a=0a⊕a=0。
        //异或运算满足交换律和结合律，即 a⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=ba \oplus b \oplus a=b \oplus a \oplus a=b \oplus (a \oplus a)=b \oplus0=ba⊕b⊕a=b⊕a⊕a=b⊕(a⊕a)=b⊕0=b。
        //因此，数组中的全部元素的异或运算结果即为数组中只出现一次的数字。
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }

    /**
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
     * 输入：nums = [1, 7, 3, 6, 5, 6]
     * 输出：3
     * 输入：nums = [1, 2, 3]
     * 输出：-1
     * 输入：nums = [2, 1, -1]
     * 输出：0
     */
    public static int pivotIndex(int[] nums) {
//        for (int i = 0; i < nums.length; i++) {
//            int pre = 0, next = 0;
//            int n = i;
//            while (n > 0) {
//                pre = pre + nums[n - 1];
//                n--;
//            }
//            int m = i;
//            while (m < nums.length - 1) {
//                next = next + nums[m + 1];
//                m++;
//            }
//            if(pre == next){
//                return i;
//            }
//        }
//        return -1;

        int total = Arrays.stream(nums).sum();
        int sum = 0;
        for (int i = 0; i < nums.length; ++i) {
            if (2 * sum + nums[i] == total) {
                return i;
            }
            sum += nums[i];
        }
        return -1;
    }

    /**
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
     * 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。
     * 输入：
     * [2, 3, 1, 0, 2, 5, 3]
     * 输出：2 或 3
     */
    public static int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 本题中，一棵高度平衡二叉树定义为：
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     */
    public static boolean isBalanced(TreeNode root) {
        //首先计算左右子树的高度，如果左右子树的高度差是否不超过 1，再分别递归地遍历左右子节点，并判断左子树和右子树是否平衡。这是一个自顶向下的递归的过程。
        if (null == root) {
            return true;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        int ans = Math.abs(leftDepth - rightDepth);
        if (ans <= 1) {
            return isBalanced(root.left) && isBalanced(root.right);
        } else {
            return false;
        }
    }

    /**
     * 给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。
     */
    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }

        return head;
    }

    /**
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1 。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     * <p>
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
     * 同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     * <p>
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     */
    public static int romanToInt(String s) {
//        char[] charArray = s.toCharArray();
//        int ans = 0;
//        for (int i = 0; i < charArray.length; i++) {
//            if(RomanEnum.I.getCode().equals(String.valueOf(charArray[i]))){
//                if(i + 1 < charArray.length){
//                    if(RomanEnum.V.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 4;
//                        i++;
//                        continue;
//                    }else if(RomanEnum.X.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 9;
//                        i++;
//                        continue;
//                    }
//                }
//                ans += 1;
//                continue;
//            }
//            if(RomanEnum.V.getCode().equals(String.valueOf(charArray[i]))){
//                ans += 5;
//                continue;
//            }
//            if(RomanEnum.X.getCode().equals(String.valueOf(charArray[i]))){
//                if(i + 1 < charArray.length){
//                    if(RomanEnum.L.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 40;
//                        i++;
//                        continue;
//                    }else if(RomanEnum.C.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 90;
//                        i++;
//                        continue;
//                    }
//                }
//
//                ans += 10;
//                continue;
//            }
//            if(RomanEnum.L.getCode().equals(String.valueOf(charArray[i]))){
//                ans += 50;
//                continue;
//            }
//            if(RomanEnum.C.getCode().equals(String.valueOf(charArray[i]))){
//                if(i + 1 < charArray.length){
//                    if(RomanEnum.D.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 400;
//                        i++;
//                        continue;
//                    }else if(RomanEnum.M.getCode().equals(String.valueOf(charArray[i + 1]))){
//                        ans += 900;
//                        i++;
//                        continue;
//                    }
//                }
//
//                ans += 100;
//                continue;
//            }
//            if(RomanEnum.D.getCode().equals(String.valueOf(charArray[i]))){
//                ans += 500;
//                continue;
//            }
//            if(RomanEnum.M.getCode().equals(String.valueOf(charArray[i]))){
//                ans += 1000;
//            }
//        }
//        return ans;

        //若存在小的数字在大的数字的左边的情况，根据规则需要减去小的数字。对于这种情况，我们也可以将每个字符视作一个单独的值，
        // 若一个数字右侧的数字比它大，则将该数字的符号取反。
        //例如 XIV 可视作 X−I+V=10−1+5=14
        Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
        }};
        int ans = 0;
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            int value = symbolValues.get(s.charAt(i));
            if (i < n - 1 && value < symbolValues.get(s.charAt(i + 1))) {
                ans -= value;
            } else {
                ans += value;
            }
        }
        return ans;
    }

    /**
     * 给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
     */
    public TreeNode invertTree(TreeNode root) {
        if (null == root) {
            return root;
        }
        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     * <p>
     * 字母和数字都属于字母数字字符。
     * <p>
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     * 输入: s = "A man, a plan, a canal: Panama"
     * 输出：true
     * 解释："amanaplanacanalpanama" 是回文串。
     */
    public static boolean isPalindrome(String s) {
        StringBuffer sgood = new StringBuffer();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                sgood.append(ch);
            }
        }
        StringBuffer sgood_rev = new StringBuffer(sgood).reverse();
        return sgood.toString().equalsIgnoreCase(sgood_rev.toString());
    }

    /**
     * 给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> set = new HashSet<>();
        ListNode temp = headA;
        while (temp != null) {
            set.add(temp);
            temp = temp.next;
        }
        temp = headB;
        while (temp != null) {
            if (!set.add(temp)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    /**
     * 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
     * 若 s 和 t 中每个字符出现的次数都相同，则称 s 和 t 互为字母异位词。
     * 输入: s = "anagram", t = "nagaram"
     * 输出: true
     */
    public static boolean isAnagram(String s, String t) {
        char[] sArray = s.toCharArray();
        Arrays.sort(sArray);
        char[] tArray = t.toCharArray();
        Arrays.sort(tArray);
        String sStr = String.valueOf(sArray);
        String tStr = String.valueOf(tArray);
        return sStr.equals(tStr);
    }

    /**
     * 斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     * F(0) = 0，F(1) = 1
     * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
     * 给定 n ，请计算 F(n) 。
     * 0 1 1 2 3 5 8
     * 0 1 2 3 4 5 6
     */
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }
        int p = 0, q = 0, r = 1;
        for (int i = 2; i <= n; ++i) {
            p = q;
            q = r;
            r = (p + q) % 1000000007;
        }
        return r;

//        int[] sums = new int[30];
//        sums[0] = 0;
//        sums[1] = 1;
//        for (int i = 2; i < n + 1; i++) {
//            sums[i] = sums[i - 1] + sums[i - 2];
//        }
//        return sums[n];
    }

    /**
     * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
     * 给你一个整数数组 flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。
     * 另有一个数 n ，能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false 。
     * 输入：flowerbed = [1,0,0,0,1], n = 1
     * 输出：true
     */
    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
//        boolean flag = true;
//        int len = flowerbed.length;
//        int zeroCount = 0;
//        for(int i : flowerbed){
//            if(i == 0){
//                zeroCount++;
//            }
//        }
//        int oneCount = len - zeroCount;
//        if(len%2 == 1){
//            if(flowerbed[0] == 1 && flowerbed[len - 1] == 1 && zeroCount <= oneCount + n){
//                return false;
//            }
//        }
//        if(zeroCount < oneCount + n){
//            return false;
//        }
//        return flag;


        if (flowerbed == null || flowerbed.length == 0)
            return n == 0;

        int countOfZero = 1; // 当前全0区段中连续0的数量，刚开始预设1个0，因为开头花坛的最左边没有花，可以认为存在一个虚无的0
        int canPlace = 0; // 可以种的花的数量
        for (int bed : flowerbed) {
            if (bed == 0) { // 遇到0，连续0的数量+1
                countOfZero++;
            } else { // 遇到1，结算上一段连续的0区间，看能种下几盆花：(countOfZero-1)/2
                canPlace += (countOfZero - 1) / 2;
                if (canPlace >= n)
                    return true;
                countOfZero = 0; // 0的数量清零，开始统计下一个全0分区
            }
        }
        // 最后一段0区还未结算：
        countOfZero++; // 最后再预设1个0，因为最后花坛的最右边没有花，可以认为存在一个虚无的0
        canPlace += (countOfZero - 1) / 2;

        return canPlace >= n;
    }

    /**
     * 给定两个数组 nums1 和 nums2 ，返回 它们的交集 。输出结果中的每个元素一定是 唯一 的。我们可以 不考虑输出结果的顺序 。
     */
    public static int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums1) {
            set.add(num);
        }
        Set<Integer> needSet = new HashSet<>();
        for (int num : nums2) {
            if (set.contains(num)) {
                needSet.add(num);
            }
        }
        int[] result = new int[needSet.size()];
        int i = 0;
        for (int num : needSet) {
            result[i] = num;
            i++;
        }
        return result;
    }

    /**
     * 编写一个算法来判断一个数 n 是不是快乐数。
     * 「快乐数」 定义为：
     * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
     * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
     * 如果这个过程 结果为 1，那么这个数就是快乐数。
     * 如果 n 是 快乐数 就返回 true ；不是，则返回 false 。
     * 输入：n = 19
     * 输出：true
     * 解释：
     * 12 + 92 = 82
     * 82 + 22 = 68
     * 62 + 82 = 100
     * 12 + 02 + 02 = 1
     */
    public static boolean isHappy(int n) {
//        Set<Integer> set = new HashSet<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("n",n);
//        map.put("set",set);
//        Map<String,Object> result = judgeLoop(map);
//        return (boolean) result.get("flag");

        Set<Integer> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            n = getNext(n);
        }
        return n == 1;
    }

    private static Map<String, Object> judgeLoop(Map<String, Object> map) {
        int n = (int) map.get("n");
        Set<Integer> set = (Set<Integer>) map.get("set");
        if (!set.add(n)) {
            map.put("flag", false);
            return map;
        }
        if (n == 1) {
            map.put("flag", true);
            return map;
        }
        String num = String.valueOf(n);
        char[] chars = num.toCharArray();
        int ans = 0;
        for (char char1 : chars) {
            ans += Math.pow(Integer.parseInt(String.valueOf(char1)), 2);
        }
        map.put("n", ans);
        map.put("set", set);
        return judgeLoop(map);
    }

    private static int getNext(int n) {
        int totalSum = 0;
        while (n > 0) {
            int d = n % 10;
            n = n / 10;
            totalSum += d * d;
        }
        return totalSum;
    }

    /**
     * 给你一棵二叉树的根节点 root ，返回其节点值的 后序遍历 。
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
//        if(root == null){
//            return null;
//        }
//        List<Integer> list = new ArrayList<>();
//        if(root.left != null){
//            list.addAll(postorderTraversal(root.left));
//        }
//        if(root.right != null){
//            list.addAll(postorderTraversal(root.right));
//        }
//        list.add(root.val);
//        return list;
        List<Integer> ans = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        //主要思想：
        //由于在某颗子树访问完成以后，接着就要回溯到其父节点去
        //因此可以用prev来记录访问历史，在回溯到父节点时，可以由此来判断，上一个访问的节点是否为右子树
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            //从栈中弹出的元素，左子树一定是访问完了的
            root = stack.pop();
            //现在需要确定的是是否有右子树，或者右子树是否访问过
            //如果没有右子树，或者右子树访问完了，也就是上一个访问的节点是右子节点时
            //说明可以访问当前节点
            if (root.right == null || prev == root.right) {
                ans.add(root.val);
                //更新历史访问记录，这样回溯的时候父节点可以由此判断右子树是否访问完成
                prev = root;
                root = null;
            } else {
                //如果右子树没有被访问，那么将当前节点压栈，访问右子树
                stack.push(root);
                root = root.right;
            }
        }
        return ans;
    }

    /**
     * 给定一个二叉树，找出其最小深度。
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     * 说明：叶子节点是指没有子节点的节点。
     */
    public static int minDepth(TreeNode root) {
//        if(root != null){
//            int minLeftLen = 1,minRightLen = 1;
//            if(null != root.left){
//                minLeftLen = minDepth(root.left) + 1;
//            }
//            if(null != root.right){
//                minRightLen = minDepth(root.right) + 1;
//            }
//            if(minLeftLen == 1){
//                return minRightLen;
//            }else if(minRightLen == 1){
//                return minLeftLen;
//            }else {
//                return Math.min(minLeftLen,minRightLen);
//            }
//        }
//        return 0;
        if (root == null) {
            return 0;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        int min_depth = Integer.MAX_VALUE;
        if (root.left != null) {
            min_depth = Math.min(minDepth(root.left), min_depth);
        }
        if (root.right != null) {
            min_depth = Math.min(minDepth(root.right), min_depth);
        }

        return min_depth + 1;
    }

    /**
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * 输入：digits = [1,2,3]
     * 输出：[1,2,4]
     * 解释：输入数组表示数字 123。
     */
    public static int[] plusOne(int[] digits) {
//        StringBuilder sb = new StringBuilder();
//        for (int i : digits) {
//            sb.append(i);
//        }
//        Long num = Long.parseLong(sb.toString()) + 1;
//        char[] chars = String.valueOf(num).toCharArray();
//        int[] result = new int[chars.length];
//        for (int i = 0; i < chars.length; i++) {
//            result[i] = (int) chars[i] - (int)'0';
//        }
//        return result;
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            digits[i] = (digits[i] + 1) % 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[len + 1];
        digits[0] = 1;
        return digits;
    }

    /**
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     * 输入：nums = [-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     */
    public int[] sortedSquares(int[] nums) {
//        int[] result = new int[nums.length];
//        for (int i = 0; i < nums.length; i++) {
//            result[i] = nums[i] * nums[i];
//        }
//        Arrays.sort(result);
//        return result;
        /*
                显然，如果数组 nums 中的所有数都是非负数，那么将每个数平方后，数组仍然保持升序；
                如果数组 nums 中的所有数都是负数，那么将每个数平方后，数组会保持降序。
                这样一来，如果我们能够找到数组 nums 中负数与非负数的分界线，那么就可以用类似「归并排序」的方法了。
                具体地，我们设 neg 为数组 nums 中负数与非负数的分界线，也就是说，nums[0] 到 nums[neg]均为负数，
                而 nums[neg+1] 到 nums[n−1] 均为非负数。当我们将数组 nums 中的数平方后，那么 nums[0] 到 nums[neg] 单调递减，
                nums[neg+1] 到 nums[n−1] 单调递增
         */
        int n = nums.length;
        int negative = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] < 0) {
                negative = i;
            } else {
                break;
            }
        }

        int[] ans = new int[n];
        int index = 0, i = negative, j = negative + 1;
        while (i >= 0 || j < n) {
            if (i < 0) {
                ans[index] = nums[j] * nums[j];
                ++j;
            } else if (j == n) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else if (nums[i] * nums[i] < nums[j] * nums[j]) {
                ans[index] = nums[i] * nums[i];
                --i;
            } else {
                ans[index] = nums[j] * nums[j];
                ++j;
            }
            ++index;
        }

        return ans;
    }

    /**
     * 给你一个 二叉树 的根结点 root，该二叉树由恰好 3 个结点组成：根结点、左子结点和右子结点。
     * 如果根结点值等于两个子结点值之和，返回 true ，否则返回 false 。
     * 输入：root = [10,4,6]
     * 输出：true
     */
    public boolean checkTree(TreeNode root) {
        if (root == null) {
            return false;
        }
        if (root.val == root.left.val + root.right.val) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     * 输入：head = [1,2,2,1]
     * 输出：true
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int left = 0, right = list.size() - 1;
        while (left < right) {
            if (!Objects.equals(list.get(left), list.get(right))) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    //反转链表
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        return prev;
    }

    /**
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum 。判断该树中是否存在 根节点到叶子节点 的路径，
     * 这条路径上所有节点值相加等于目标和 targetSum 。如果存在，返回 true ；否则，返回 false 。
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        /*
        观察要求我们完成的函数，我们可以归纳出它的功能：询问是否存在从当前节点 root 到叶子节点的路径，满足其路径和为 sum。
        假定从根节点到当前节点的值之和为 val，我们可以将这个大问题转化为一个小问题：是否存在从当前节点的子节点到叶子的路径，满足其路径和为 sum - val。
        不难发现这满足递归的性质，若当前节点就是叶子节点，那么我们直接判断 sum 是否等于 val 即可（因为路径和已经确定，就是当前节点的值，我们只需要判断该路径和是否满足条件）。
        若当前节点不是叶子节点，我们只需要递归地询问它的子节点是否能满足条件即可。
         */
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return targetSum == root.val;
        }
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    public int lengthOfLastWord(String s) {
        String[] strArray = s.split(" ");
        return strArray[strArray.length - 1].length();
    }

    public static int findDelayedArrivalTime(int arrivalTime, int delayedTime) {
        return (arrivalTime + delayedTime) % 24;
    }

    /**
     * 给你两个只包含 1 到 9 之间数字的数组 nums1 和 nums2 ，每个数组中的元素 互不相同 ，请你返回 最小 的数字，两个数组都 至少 包含这个数字的某个数位。
     * 输入：nums1 = [4,1,3], nums2 = [5,7]
     * 输出：15
     * 输入：nums1 = [3,5,2,6], nums2 = [3,1,7]
     * 输出：3
     */
    public static int minNumber(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int min1 = nums1[0];
        int min2 = nums2[0];
        for (int i : nums1) {
            for (int j : nums2) {
                if (i == j) {
                    return i;
                }
            }
        }
        if (min1 <= min2) {
            return 10 * min1 + min2;
        } else {
            return 10 * min2 + min1;
        }
    }

    /**
     * 给你两个二进制字符串 a 和 b ，以二进制字符串的形式返回它们的和。
     * 输入:a = "11", b = "1"
     * 输出："100"
     * 输入：a = "1010", b = "1011"
     * 输出："10101"
     */
    public String addBinary(String a, String b) {
        int lenA = a.length();
        int lenB = b.length();
        int indexA = lenA - 1;
        int indexB = lenB - 1;
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        while (indexA >= 0 || indexB >= 0 || flag) {
            char charA = '0';
            char charB = '0';
            if (indexA > -1) {
                charA = a.charAt(indexA);
            }
            if (indexB > -1) {
                charB = b.charAt(indexB);
            }
            if (charA == '1' && charB == '1') {
                if (flag) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
                flag = true;
            } else if (charA == '1' || charB == '1') {
                if (flag) {
                    sb.append("0");
                } else {
                    sb.append("1");
                }
            } else if (charA == '0' && charB == '0') {
                if (flag) {
                    sb.append("1");
                } else {
                    sb.append("0");
                }
                flag = false;
            } else {
                sb.append("1");
            }
            indexA--;
            indexB--;
        }
        //再反转sb
        sb.reverse();
        return sb.toString();
    }

    /**
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        } else {
            //都不为null，比较值
            return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        }
    }

    /**
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
     * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
     * 输入：nums = [-10,-3,0,5,9]
     * 输出：[0,-3,9,-10,null,5]
     * 解释：[0,-10,5,null,-3,null,9] 也将被视为正确答案：
     */
    public static TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length <= 0) {
            return null;
        }
        //二分法，将中间的数作为中间节点
        int len = nums.length;
        int midIndex = len / 2;
        TreeNode root = new TreeNode(nums[midIndex]);
        int[] leftNums = new int[midIndex];
        for (int i = 0; i < midIndex; i++) {
            leftNums[i] = nums[i];
        }
        int[] rightNums = new int[len - midIndex - 1];
        int lenB = len - midIndex - 1;
        for (int i = 0; i < lenB; i++) {
            rightNums[i] = nums[midIndex + 1];
            midIndex++;
        }
        root.left = sortedArrayToBST(leftNums);
        root.right = sortedArrayToBST(rightNums);
        return root;
    }

    /**
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * 输入: numRows = 6
     * 输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1],[1,5,10,10,5,1],[1,6,15,20,15,6,1]]
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ret = new ArrayList<List<Integer>>();
        for (int i = 0; i < numRows; ++i) {
            List<Integer> row = new ArrayList<Integer>();
            for (int j = 0; j <= i; ++j) {
                if (j == 0 || j == i) {
                    row.add(1);
                } else {
                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
                }
            }
            ret.add(row);
        }
        return ret;
    }

    /**
     * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * 输入: rowIndex = 3
     * 输出: [1,3,3,1]
     */
    public static List<Integer> getRow(int rowIndex) {
//        List<List<Integer>> ret = new ArrayList<List<Integer>>();
//        for (int i = 0; i <= rowIndex; ++i) {
//            List<Integer> row = new ArrayList<Integer>();
//            for (int j = 0; j <= i; ++j) {
//                if (j == 0 || j == i) {
//                    row.add(1);
//                } else {
//                    row.add(ret.get(i - 1).get(j - 1) + ret.get(i - 1).get(j));
//                }
//            }
//            ret.add(row);
//        }
//        return ret.get(rowIndex);
        List<Integer> row = new ArrayList<Integer>();
        row.add(1);
        for (int i = 1; i <= rowIndex; ++i) {
            row.add((int) ((long) row.get(i - 1) * (rowIndex - i + 1) / i));
        }
        return row;
    }

    /**
     * 给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。
     * 如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。
     * 假设环境不允许存储 64 位整数（有符号或无符号）。
     * 输入：x = 123
     * 输出：321
     * 输入：x = -123
     * 输出：-321
     */
    public static int reverse(int x) {
//        boolean negative = false;
//        if (x < 0) {
//            negative = true;
//        }
//        if(negative){
//            x = -x;
//        }
//        StringBuilder sb = new StringBuilder();
//        String str = String.valueOf(x);
//        char[] chars = str.toCharArray();
//        for (char char1 : chars) {
//            sb.append(char1);
//        }
//        String result = negative ? "-" + sb.reverse() : sb.reverse().toString();
//        return Integer.parseInt(result);

        int rev = 0;
        while (x != 0) {
            if (rev < Integer.MIN_VALUE / 10 || rev > Integer.MAX_VALUE / 10) {
                return 0;
            }
            int digit = x % 10;
            x /= 10;
            rev = rev * 10 + digit;
        }
        return rev;
    }

    /**
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     * 请你实现这个将字符串进行指定行数变换的函数：
     */
    public static String convert(String s, int numRows) {
        /*
        根据题意，当我们在矩阵上填写字符时，会向下填写 r 个字符，然后向右上继续填写 r−2个字符，最后回到第一行，因此 Z 字形变换的周期 t=r+r−2=2r−2，
        每个周期会占用矩阵上的 1+r−2=r−1 列。
         */
        int n = s.length(), r = numRows;
        if (r == 1 || r >= n) {
            return s;
        }
        //周期t
        int t = r * 2 - 2;
        //矩阵的列数c
        int c = (n / t + 1) * (r - 1);
        //创建二位矩阵
        char[][] mat = new char[r][c];
        //若当前字符下标 i 满足 i mod t<r−1，则向下移动，否则向右上移动。
        for (int i = 0, x = 0, y = 0; i < n; ++i) {
            mat[x][y] = s.charAt(i);
            if (i % t < r - 1) {
                ++x; // 向下移动
            } else {
                --x;
                ++y; // 向右上移动
            }
        }
        StringBuffer ans = new StringBuffer();
        for (char[] row : mat) {
            for (char ch : row) {
                if (ch != 0) {
                    ans.append(ch);
                }
            }
        }
        return ans.toString();
    }

    /**
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     * 函数 myAtoi(string s) 的算法如下：
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−231,  231 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，小于 −231 的整数应该被固定为 −231 ，大于 231 − 1 的整数应该被固定为 231 − 1 。
     * 返回整数作为最终结果。
     */
    public static int myAtoi(String s) {
        // 处理前置空格
        s = s.trim();
        if (s.length() == 0) {
            return 0;
        }
        //判断首字符
        if (!Character.isDigit(s.charAt(0)) && s.charAt(0) != '-' && s.charAt(0) != '+') {
            return 0;
        }
        long ans = 0L;
        //正负数
        boolean neg = s.charAt(0) == '-';
        //判断起始位置
        int i = !Character.isDigit(s.charAt(0)) ? 1 : 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            ans = ans * 10 + (s.charAt(i++) - '0');
            //原本是正数
            if (!neg && ans > Integer.MAX_VALUE) {
                ans = Integer.MAX_VALUE;
                break;
            }
            //原本是负数
            if (neg && ans > Integer.MAX_VALUE + 1L) {
                ans = Integer.MAX_VALUE + 1L;
                break;
            }
        }
        return neg ? (int) -ans : (int) ans;
    }

    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     * 输入：digits = ""
     * 输出：[]
     * 输入：digits = "23"
     * 输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
     */
    // 数字到号码的映射
    private static String[] map = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    // 路径
    private static StringBuilder sb = new StringBuilder();

    // 结果集
    private static List<String> res = new ArrayList<>();

    public static List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) return res;
        backtrack(digits, 0);
        return res;
    }

    // 回溯函数
    private static void backtrack(String digits, int index) {
        if (sb.length() == digits.length()) {
            res.add(sb.toString());
            return;
        }
        String val = map[digits.charAt(index) - '2'];
        for (char ch : val.toCharArray()) {
            sb.append(ch);
            backtrack(digits, index + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 给你一个字符串数组 tokens ，表示一个根据 逆波兰表示法 表示的算术表达式。
     * 请你计算该表达式。返回一个表示表达式值的整数。
     * 输入：tokens = ["2","1","+","3","*"]
     * 输出：9
     * 解释：该算式转化为常见的中缀算术表达式为：((2 + 1) * 3) = 9
     * 输入：tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
     * 输出：22
     * 解释：该算式转化为常见的中缀算术表达式为：
     * ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
     * = ((10 * (6 / (12 * -11))) + 17) + 5
     * = ((10 * (6 / -132)) + 17) + 5
     * = ((10 * 0) + 17) + 5
     * = (0 + 17) + 5
     * = 17 + 5
     * = 22
     */
    public static int evalRPN(String[] tokens) {
//        String suanshufu = "+-*/";
//        if(tokens.length == 1){
//            return Integer.parseInt(tokens[0]);
//        }
//        for(int i = 0; i < tokens.length; i++){
//            //找第一个算数符号，然后往前找两个数字
//            if(suanshufu.contains(tokens[i])){
//                Integer ans = null;
//                if(tokens[i].equals("+")){
//                    ans = Integer.parseInt(tokens[i-1]) + Integer.parseInt(tokens[i-2]);
//                }else if(tokens[i].equals("-")){
//                    ans = Integer.parseInt(tokens[i-2]) - Integer.parseInt(tokens[i-1]);
//                }else if(tokens[i].equals("*")){
//                    ans = Integer.parseInt(tokens[i-1]) * Integer.parseInt(tokens[i-2]);
//                }else if(tokens[i].equals("/")){
//                    ans = Integer.parseInt(tokens[i-2]) / Integer.parseInt(tokens[i-1]);
//                }
//                //置空i-1
//                tokens[i-2] = String.valueOf(ans);
//                tokens[i-1] = null;
//                tokens[i] = null;
//                List<String> list = Arrays.asList(tokens);
//                List<String> newList = new ArrayList<>(list);
//                newList.removeIf(Objects::isNull);
//                tokens = newList.toArray(new String[1]);
//                return evalRPN(tokens);
//            }
//        }
//        return 0;
        Deque<Integer> stack = new LinkedList<Integer>();
        int n = tokens.length;
        for (int i = 0; i < n; i++) {
            String token = tokens[i];
            if (isNumber(token)) {
                stack.push(Integer.parseInt(token));
            } else {
                int num2 = stack.pop();
                int num1 = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(num1 + num2);
                        break;
                    case "-":
                        stack.push(num1 - num2);
                        break;
                    case "*":
                        stack.push(num1 * num2);
                        break;
                    case "/":
                        stack.push(num1 / num2);
                        break;
                    default:
                }
            }
        }
        return stack.pop();
    }

    public static boolean isNumber(String token) {
        return !("+".equals(token) || "-".equals(token) || "*".equals(token) || "/".equals(token));
    }


    public static String convertToTitle(int columnNumber) {
        StringBuffer sb = new StringBuffer();
        while (columnNumber != 0) {
            columnNumber--;
            sb.append((char) (columnNumber % 26 + 'A'));
            columnNumber /= 26;
        }
        return sb.reverse().toString();
    }

    public static int titleToNumber(String columnTitle) {
        char[] chars = columnTitle.toCharArray();
        int ans = 0;
        int num = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            ans = ans + ((chars[i] - 'A' + 1) * (int) Math.pow(26, num));
            num++;
        }
        return ans;
    }

    /**
     * 给你一个整数 n，请你判断该整数是否是 2 的幂次方。如果是，返回 true ；否则，返回 false 。
     * 如果存在一个整数 x 使得 n == 2x ，则认为 n 是 2 的幂次方。
     * 输入：n = 1
     * 输出：true
     * 解释：20 = 1
     * 输入：n = 6
     * 输出：false
     */
    public static boolean isPowerOfTwo(int n) {
//        if(n <= 0){
//            return false;
//        }
//        for (int i = 0; i < 31; i++) {
//            if (n == 1 || n % 2 == 0) {
//                n = n / 2;
//            } else {
//                return false;
//            }
//        }
//        return true;
        return n > 0 && (n & -n) == n;
    }

    public static int hammingWeight(int n) {
//        int ret = 0;
//        for (int i = 0; i < 32; i++) {
//            if ((n & (1 << i)) != 0) {
//                ret++;
//            }
//        }
//        return ret;
        int ret = 0;
        while (n != 0) {
            ret++;
            n = n & (n - 1);
        }
        return ret;
    }

    /**
     * 在初始时，左右指针分别指向数组的左右两端，它们可以容纳的水量为 min(1,7)∗8=8。
     * 此时我们需要移动一个指针。移动哪一个呢？直觉告诉我们，应该移动对应数字较小的那个指针（即此时的左指针）。这是因为，由于容纳的水量是由
     * 两个指针指向的数字中较小值∗指针之间的距离决定的。如果我们移动数字较大的那个指针，那么前者「两个指针指向的数字中较小值」不会增加，
     * 后者「指针之间的距离」会减小，那么这个乘积会减小。因此，我们移动数字较大的那个指针是不合理的。因此，我们移动 数字较小的那个指针。
     */
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int ans = 0;
        while (left < right) {
            int result = Math.min(height[left], height[right]) * (right - left);
            if (result > ans) {
                ans = result;
            }
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return ans;
    }

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使得出现次数超过两次的元素只出现两次 ，返回删除后数组的新长度。
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * 由于是保留 k 个相同数字，对于前 k 个数字，我们可以直接保留
     * 对于后面的任意数字，能够保留的前提是：与当前写入的位置前面的第 k 个元素进行比较，不相同则保留
     */
    public int removeDuplicates(int[] nums) {
        return process(nums, 2);
    }

    int process(int[] nums, int k) {
        int u = 0;
        for (int x : nums) {
            if (u < k || nums[u - k] != x) {
                nums[u++] = x;
            }
        }
        return u;
    }

    /**
     * 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。
     * 输入：nums = [-1,-100,3,99], k = 2
     * 输出：[3,99,-1,-100]
     * 解释:
     * 向右轮转 1 步: [99,-1,-100,3]
     * 向右轮转 2 步: [3,99,-1,-100]
     */
    public static void rotate(int[] nums, int k) {
//        int n = nums.length;
//        List<Integer> pre = new ArrayList<>();
//        List<Integer> next = new ArrayList<>();
//        for (int i = 0; i < nums.length; i++) {
//            if(i < n - k){
//                pre.add(nums[i]);
//            }else {
//                next.add(nums[i]);
//            }
//        }
//        next.addAll(pre);
//        for(int i = 0; i < next.size(); i++){
//            nums[i] = next.get(i);
//        }

        int n = nums.length;
        int[] newArr = new int[n];
        for (int i = 0; i < n; ++i) {
            newArr[(i + k) % n] = nums[i];
        }
        System.arraycopy(newArr, 0, nums, 0, n);
    }

    /**
     * 给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
     * 在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。你也可以先购买，然后在 同一天 出售。
     * 返回 你能获得的 最大 利润 。
     * 输入：prices = [7,1,5,3,6,4]
     * 输出：7
     * 解释：在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     *      随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
     *      总利润为 4 + 3 = 7 。
     */
//    public int maxProfit(int[] prices) {
//        int slow = 0,fast = 1;
//        for(int i = 0; i < prices.length; i++){
//
//        }
//    }

    /**
     * 给你一个字符串 s 和一个字符串列表 wordDict 作为字典。请你判断是否可以利用字典中出现的单词拼接出 s 。
     * 注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
     * 示例 3：
     * 输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
     * 输出: false
     */
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    /**
     * 给定两个字符串 s 和 t ，判断它们是否是同构的。
     * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，相同字符只能映射到同一个字符上，字符可以映射到自己本身。
     * 示例 1:
     * 输入：s = "egg", t = "add"
     * 输出：true
     */
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        if (s.length() != t.length()) {
            return false;
        }
        char[] chars = s.toCharArray();
        char[] chart = t.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (map.containsKey(chars[i])) {
                if (map.get(chars[i]) != chart[i]) {
                    return false;
                }
            } else {
                if (map2.containsKey(chart[i])) {
                    if (map2.get(chart[i]) != chars[i]) {
                        return false;
                    }
                } else {
                    map2.put(chart[i], chars[i]);
                    map.put(chars[i], chart[i]);
                }

            }
        }
        return true;
    }

    /**
     * n 个人站成一排，按从 1 到 n 编号。
     * 最初，排在队首的第一个人拿着一个枕头。每秒钟，拿着枕头的人会将枕头传递给队伍中的下一个人。
     * 一旦枕头到达队首或队尾，传递方向就会改变，队伍会继续沿相反方向传递枕头。
     * 输入：n = 4, time = 5
     * 输出：2
     * 解释：队伍中枕头的传递情况为：1 -> 2 -> 3 -> 4 -> 3 -> 2 。
     * 5 秒后，枕头传递到第 2 个人手中。
     */
    public int passThePillow(int n, int time) {
        int round = time / (n - 1);
        int mod = time % (n - 1);
        if (round % 2 == 0) {
            return 1 + mod;
        }
        return n - mod;
    }

    /**
     * 给你一个下标从 0 开始的字符串数组 words 和两个整数：left 和 right 。
     * 如果字符串以元音字母开头并以元音字母结尾，那么该字符串就是一个 元音字符串 ，其中元音字母是 'a'、'e'、'i'、'o'、'u' 。
     * 返回 words[i] 是元音字符串的数目，其中 i 在闭区间 [left, right] 内。
     * 输入：words = ["are","amy","u"], left = 0, right = 2
     * 输出：2
     */
    public int vowelStrings(String[] words, int left, int right) {
        int ans = 0;
        for (int i = left; i <= right; i++) {
            if (words[i].matches("[aeiou](.*[aeiou])?")) ans++;
//            String str = words[i];
//            if((str.startsWith("a") || str.startsWith("e") || str.startsWith("i") || str.startsWith("o")
//            || str.startsWith("u")) && (str.endsWith("a") || str.endsWith("e") || str.endsWith("i")
//            || str.endsWith("o") || str.endsWith("u"))){
//                ans++;
//            }
        }
        return ans;
    }

    /**
     * 整数转罗马数字
     */
    public String intToRoman(int num) {
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        StringBuffer roman = new StringBuffer();
        roman.append(thousands[num / 1000]);
        roman.append(hundreds[num % 1000 / 100]);
        roman.append(tens[num % 100 / 10]);
        roman.append(ones[num % 10]);
        return roman.toString();
    }


    public static List<String> generateParenthesis(int n) {
//        List<String> combinations = new ArrayList<String>();
//        generateAll(new char[2 * n], 0, combinations);
//        return combinations;

        if (n <= 0) {
            return res;
        }
        getParenthesis("", n, n);
        return res;
    }

    private static void getParenthesis(String str, int left, int right) {
        if (left == 0 && right == 0) {
            res.add(str);
            return;
        }
        if (left == right) {
            //剩余左右括号数相等，下一个只能用左括号
            getParenthesis(str + "(", left - 1, right);
        } else {
            //剩余左括号小于右括号，下一个可以用左括号也可以用右括号
            if (left > 0) {
                getParenthesis(str + "(", left - 1, right);
            }
            getParenthesis(str + ")", left, right - 1);
        }
    }

    public static void generateAll(char[] current, int pos, List<String> result) {
        if (pos == current.length) {
            if (valid(current)) {
                result.add(new String(current));
            }
        } else {
            current[pos] = '(';
            generateAll(current, pos + 1, result);
            current[pos] = ')';
            generateAll(current, pos + 1, result);
        }
    }

    public static boolean valid(char[] current) {
        int balance = 0;
        for (char c : current) {
            if (c == '(') {
                ++balance;
            } else {
                --balance;
            }
            if (balance < 0) {
                return false;
            }
        }
        return balance == 0;
    }

    /**
     * 给你两个字符串：ransomNote 和 magazine ，判断 ransomNote 能不能由 magazine 里面的字符构成。
     * 如果可以，返回 true ；否则返回 false 。
     * magazine 中的每个字符只能在 ransomNote 中使用一次。
     * 输入：ransomNote = "aa", magazine = "ab"
     * 输出：false
     */
    public boolean canConstruct(String ransomNote, String magazine) {
//        boolean flag = true;
//        Map<Character,Integer> ransomNoteMap = new HashMap<>();
//        Map<Character,Integer> magazineMap = new HashMap<>();
//        char[] ransomNoteChars = ransomNote.toCharArray();
//        char[] magazineChars = magazine.toCharArray();
//        for(char chars : magazineChars){
//            if(null != magazineMap.get(chars)){
//                Integer count = magazineMap.get(chars);
//                magazineMap.put(chars,++count);
//            }else {
//                magazineMap.put(chars,1);
//            }
//        }
//        for(char chars : ransomNoteChars){
//            if(null != ransomNoteMap.get(chars)){
//                Integer count = ransomNoteMap.get(chars);
//                ransomNoteMap.put(chars,++count);
//            }else {
//                ransomNoteMap.put(chars,1);
//            }
//        }
//        for(Map.Entry<Character,Integer> entry : ransomNoteMap.entrySet()){
//            Character key = entry.getKey();
//            Integer value = entry.getValue();
//            if(null == magazineMap.get(key)){
//                return false;
//            }else {
//                if(magazineMap.get(key) < value){
//                    return false;
//                }
//            }
//        }
//        return flag;
        if (ransomNote.length() > magazine.length()) {
            return false;
        }
        int[] cnt = new int[26];
        for (char c : magazine.toCharArray()) {
            cnt[c - 'a']++;
        }
        for (char c : ransomNote.toCharArray()) {
            cnt[c - 'a']--;
            if(cnt[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
//        System.out.println(generateParenthesis(1));
//        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
//        List<String> wordDict = new ArrayList<>();
//        wordDict.add("apple");
//        wordDict.add("pen");
//        System.out.println(wordBreak("applepenapple",wordDict));
//        rotate(new int[]{-1,-100,3,99},2);
//        System.out.println(hammingWeight(00000000000000000000000000001011));
//        System.out.println(isPowerOfTwo(256));
//        System.out.println(titleToNumber("ZY"));
//        System.out.println(convertToTitle(2147483647));
//        System.out.println(evalRPN(new String[]{"10","6","9","3","+","-11","*","/","*","17","+","5","+"}));
//        System.out.println(letterCombinations("22"));
//        System.out.println(myAtoi("   -42"));
//        System.out.println(convert("PAYPALISHIRING", 3));
//        System.out.println(reverse(1534236469));
//        System.out.println(getRow(3));
//        System.out.println(generate(5));
//        System.out.println(sortedArrayToBST(new int[]{-10,-3,0,5,9}));
//        System.out.println(Integer.toBinaryString(
//                Integer.parseInt("11", 2) + Integer.parseInt("1", 2)));
//        System.out.println(minNumber(new int[]{3,5,2,6},new int[]{3,1,7}));
//        System.out.println(findDelayedArrivalTime(1,1));
//        System.out.println(plusOne(new int[]{1, 2, 3}));
//        System.out.println(minDepth(new TreeNode(2,null,new TreeNode(3,null,new TreeNode(4,null,new TreeNode(5,null,new TreeNode(6)))))));
//        System.out.println(postorderTraversal(new TreeNode()));
//        System.out.println(isHappy(19));
//        System.out.println(intersection(new int[]{4,7,9,7,6,7},new int[]{5,0,0,6,1,6,2,2,4}));
//        System.out.println(canPlaceFlowers(new int[]{1,0,0,0,0,0,1},3));
//        System.out.println(fib(95));
//        System.out.println(isAnagram("anagram", "nagaram"));
//        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));
//        System.out.println(romanToInt("MCMXCIV"));
//        System.out.println(deleteDuplicates(new ListNode(1,new ListNode(1,new ListNode(2)))));
//        System.out.println(deleteDuplicates(new ListNode(1, new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(3)))))));
//        System.out.println(pivotIndex(new int[]{1, 7, 3, 6, 5, 6}));
//        System.out.println(singleNumber(new int[]{4, 1, 2, 1, 2}));
//        System.out.println(isSubsequence("ab", "baab"));
//        System.out.println(removeElements(new ListNode(1,new ListNode(6,new ListNode(2))),6));
//        System.out.println(diagonalSum(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}}));
//        System.out.println(captureForts(new int[]{1, 0, 0, 1, 0, -1}));
//        System.out.println(inorderTraversal(new TreeNode(1, null, new TreeNode(2, new TreeNode(3), null))));
//        System.out.println(mySqrt(8));
//        System.out.println(summaryRanges(new int[]{0,2,3,4,6,8,9}));
//        System.out.println(search(new int[]{-1,0,3,5,9,12},9));
//        System.out.println(majorityElement(new int[]{3, 3, 3}));
    }
}
