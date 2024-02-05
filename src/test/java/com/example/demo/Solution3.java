package com.example.demo;

import com.example.demo.entity.TreeNode;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Solution3 {
    static int count = 0;
    int pre;    // 前一个节点的值
    int ans;    // 记录最小绝对差

    /**
     * 给定一种规律 pattern 和一个字符串 s ，判断 s 是否遵循相同的规律。
     * 这里的 遵循 指完全匹配，例如， pattern 里的每个字母和字符串 s 中的每个非空单词之间存在着双向连接的对应规律。
     * 输入: pattern = "abba", s = "dog cat cat dog"
     * 输出: true
     */
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");
        List<String> ls = Arrays.asList(str.split(" "));
        if (words.length != pattern.length()) {
            return false;
        }
        Map<Object, Integer> map = new HashMap<>();
        for (Integer i = 0; i < words.length; i++) {
            //如果key不存在，插入成功，返回null；如果key存在，返回之前对应的value。
//            if (!Objects.equals(map.put(pattern.charAt(i), i), map.put(words[i], i))) {
//                return false;
//            }
            if (pattern.indexOf(pattern.charAt(i)) != ls.indexOf(ls.get(i))) {
                return false;
            }

        }
        return true;
    }

    public int divide(int dividend, int divisor) {
        // 考虑被除数为最小值的情况
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
        }
        // 考虑除数为最小值的情况
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        // 考虑被除数为 0 的情况
        if (dividend == 0) {
            return 0;
        }

        // 一般情况，使用二分查找
        // 将所有的正数取相反数，这样就只需要考虑一种情况
        boolean rev = false;
        if (dividend > 0) {
            dividend = -dividend;
            rev = !rev;
        }
        if (divisor > 0) {
            divisor = -divisor;
            rev = !rev;
        }

        int left = 1, right = Integer.MAX_VALUE, ans = 0;
        while (left <= right) {
            // 注意溢出，并且不能使用除法
            int mid = left + ((right - left) >> 1);
            boolean check = quickAdd(divisor, mid, dividend);
            if (check) {
                ans = mid;
                // 注意溢出
                if (mid == Integer.MAX_VALUE) {
                    break;
                }
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return rev ? -ans : ans;
    }

    // 快速乘
    public boolean quickAdd(int y, int z, int x) {
        // x 和 y 是负数，z 是正数
        // 需要判断 z * y >= x 是否成立
        int result = 0, add = y;
        while (z != 0) {
            if ((z & 1) != 0) {
                // 需要保证 result + add >= x
                if (result < x - add) {
                    return false;
                }
                result += add;
            }
            if (z != 1) {
                // 需要保证 add + add >= x
                if (add < x - add) {
                    return false;
                }
                add += add;
            }
            // 不能使用除法
            z >>= 1;
        }
        return true;
    }

    /**
     * 给你一个整数数组 nums 和一个整数 k ，判断数组中是否存在两个 不同的索引 i 和 j ，
     * 满足 nums[i] == nums[j] 且 abs(i - j) <= k 。如果存在，返回 true ；否则，返回 false 。
     * 输入：nums = [1,0,1,1], k = 1
     * 输出：true
     * 输入：nums = [1,2,3,1,2,3], k = 2
     * 输出：false
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int num = nums[i];
            if (map.containsKey(num) && i - map.get(num) <= k) {
                return true;
            }
            map.put(num, i);
        }
        return false;
    }

    /**
     * 给定一个非空二叉树的根节点 root , 以数组的形式返回每一层节点的平均值。与实际答案相差 10-5 以内的答案可以被接受。
     */
    public static List<Double> averageOfLevels(TreeNode root) {
//        List<Double> list = new ArrayList<>();
//        list.add((double) root.val);
//        list.addAll(iterator(root));
//        return list;

        //深度遍历
//        List<Integer> counts = new ArrayList<Integer>();
//        List<Double> sums = new ArrayList<Double>();
//        dfs(root, 0, counts, sums);
//        List<Double> averages = new ArrayList<Double>();
//        int size = sums.size();
//        for (int i = 0; i < size; i++) {
//            averages.add(sums.get(i) / counts.get(i));
//        }
//        return averages;
        //广度遍历
        List<Double> averages = new ArrayList<Double>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            double sum = 0;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                TreeNode left = node.left, right = node.right;
                if (left != null) {
                    queue.offer(left);
                }
                if (right != null) {
                    queue.offer(right);
                }
            }
            averages.add(sum / size);
        }
        return averages;
    }

    public static void dfs(TreeNode root, int level, List<Integer> counts, List<Double> sums) {
        if (root == null) {
            return;
        }
        if (level < sums.size()) {
            sums.set(level, sums.get(level) + root.val);
            counts.set(level, counts.get(level) + 1);
        } else {
            sums.add(1.0 * root.val);
            counts.add(1);
        }
        dfs(root.left, level + 1, counts, sums);
        dfs(root.right, level + 1, counts, sums);
    }

    private static List<Double> iterator(TreeNode root) {
        List<Double> list = new ArrayList<>();
        Integer left = null, right = null;
        if (null == root.left && null == root.right) {
            return list;
        }
        if (null != root.left) {
            left = root.left.val;
        }
        if (null != root.right) {
            right = root.right.val;
        }
        double average = 0d;
        if (left == null && right != null) {
            average = right;
        } else if (right == null && left != null) {
            average = left;
        } else {
            average = ((double) left + (double) (right)) / 2;
        }
        list.add(average);
        if (null != root.left) {
            list.addAll(iterator(root.left));
        }
        if (null != root.right) {
            list.addAll(iterator(root.right));
        }
        return list;
    }

    /**
     * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
     * 差值是一个正数，其数值等于两值之差的绝对值。
     */
    public int getMinimumDifference(TreeNode root) {
//        List<Integer> res = new ArrayList<Integer>();
//        Deque<TreeNode> stk = new LinkedList<TreeNode>();
//        while (root != null || !stk.isEmpty()) {
//            while (root != null) {
//                stk.push(root);
//                root = root.left;
//            }
//            root = stk.pop();
//            res.add(root.val);
//            root = root.right;
//        }
//        int min = res.get(1) - res.get(0);
//        for(int i = 0; i < res.size() - 1; i++){
//            min = Math.min(min,res.get(i + 1) - res.get(i));
//        }
//        return min;

        ans = Integer.MAX_VALUE;    // 设置ans为int数据类型最大值
        pre = -1;   // 初始值设为-1，表示前一个节点还未被遍历到
        dfs(root);  // 递归遍历整个二叉搜索树
        return ans; // 返回最小绝对差
    }

    public void dfs(TreeNode root) {
        if (root == null) { // 如果当前节点为空，返回
            return;
        }
        dfs(root.left); // 遍历当前节点的左子树
        if (pre == -1) { // 如果前一个节点还未被遍历到，则当前节点的值就是前一个节点的值
            pre = root.val;
        } else { // 如果前一个节点已经被遍历到，则计算当前节点值与前一个节点值的绝对差，并更新ans
            ans = Math.min(ans, root.val - pre);
            pre = root.val; // 更新前一个节点的值
        }
        dfs(root.right);    // 遍历当前节点的右子树
    }

    private static final int M1 = 0x55555555; // 01010101010101010101010101010101
    private static final int M2 = 0x33333333; // 00110011001100110011001100110011
    private static final int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
    private static final int M8 = 0x00ff00ff; // 00000000111111110000000011111111

    /**
     * 颠倒给定的 32 位无符号整数的二进制位。
     */
    public static int reverseBits(int n) {
//        int rev = 0;
//        for (int i = 0; i < 32 && n != 0; ++i) {
//            rev |= (n & 1) << (31 - i);
//            n >>>= 1;
//        }
//        return rev;
        /*
        首先，我们知道 （单个二进制码 & 1) = 其本身，所以对于参数 M1，可以看成是用来将一串二进制码的奇数位提取出来；
        接着，n >> 1，右移，可以看作是将 n 上原来的偶数位变成奇数位，为什么不说奇数位也变成偶数位，是因为右移将第一个奇数位移除了；
        其次，(n >> 1) & M1，就是如1所述，将（n >> 1）的奇数位提取出来，也就是原 n 的偶数位；
        再次，(n & M1) << 1，就是先将 n 的奇数位提出来，然后左移，将其变成偶数位；
        然后，奇数位(原 n 的偶数位) | 偶数位(原 n 的奇数位)，相或，就达到了原 n 的奇数位和偶数位互换的目的；
         */
        n = n >>> 1 & M1 | (n & M1) << 1;
        n = n >>> 2 & M2 | (n & M2) << 2;
        n = n >>> 4 & M4 | (n & M4) << 4;
        n = n >>> 8 & M8 | (n & M8) << 8;
        return n >>> 16 | n << 16;

//        return Integer.reverse(n);
    }

    /**
     * 给你一个整数数组 nums 。如果任一值在数组中出现 至少两次 ，返回 true ；如果数组中每个元素互不相同，返回 false 。
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            if (!set.add(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给你一个二叉树的根节点 root ，按 任意顺序 ，返回所有从根节点到叶子节点的路径。
     */
    public static List<String> binaryTreePaths(TreeNode root, String path) {
        //深度遍历
//        List<String> list = new ArrayList<>();
//        StringBuilder stringBuilder = new StringBuilder(path);
//        stringBuilder.append(root.val);
//        if(root.right == null && root.left == null){
//            list.add(stringBuilder.toString());
//        }else {
//            stringBuilder.append("->");
//            if(root.left != null){
//                list.addAll(binaryTreePaths(root.left,stringBuilder.toString()));
//            }
//            if(root.right != null){
//                list.addAll(binaryTreePaths(root.right,stringBuilder.toString()));
//            }
//        }
//        return list;
        //广度遍历
        /*
        我们也可以用广度优先搜索来实现。我们维护一个队列，存储节点以及根到该节点的路径。
        一开始这个队列里只有根节点。在每一步迭代中，我们取出队列中的首节点，
        如果它是叶子节点，则将它对应的路径加入到答案中。
        如果它不是叶子节点，则将它的所有孩子节点加入到队列的末尾。当队列为空时广度优先搜索结束，我们即能得到答案。
         */
        List<String> paths = new ArrayList<String>();
        if (root == null) {
            return paths;
        }
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<String> pathQueue = new LinkedList<String>();

        nodeQueue.offer(root);
        pathQueue.offer(Integer.toString(root.val));

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            path = pathQueue.poll();

            if (node.left == null && node.right == null) {
                paths.add(path);
            } else {
                if (node.left != null) {
                    nodeQueue.offer(node.left);
                    pathQueue.offer(path + "->" + node.left.val);
                }

                if (node.right != null) {
                    nodeQueue.offer(node.right);
                    pathQueue.offer(path + "->" + node.right.val);
                }
            }
        }
        return paths;
    }


    /**
     * 给定一个非负整数 num，反复将各个位上的数字相加，直到结果为一位数。返回这个结果。
     */
    public static int addDigits(int num) {
//        if(num < 10){
//            return num;
//        }else {
//            char[] chars = String.valueOf(num).toCharArray();
//            int sum = 0;
//            for(char ch : chars){
//                sum += Integer.parseInt(String.valueOf(ch));
//            }
//            return addDigits(sum);
//        }
//        while (num >= 10) {
//            int sum = 0;
//            while (num > 0) {
//                sum += num % 10;
//                num /= 10;
//            }
//            num = sum;
//        }
//        return num;
        while (num / 10 != 0) {
            int m = num % 10;
            num = num / 10;
            num += m;
        }
        return num;
    }

    /**
     * 丑数
     */
    public boolean isUgly(int n) {
        if (n <= 0) {
            return false;
        }
        int[] factors = {2, 3, 5};
        for (int factor : factors) {
            while (n % factor == 0) {
                n /= factor;
            }
        }
        return n == 1;
    }

    /**
     * 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
     * 输入：nums = [3,0,1]
     * 输出：2
     * 解释：n = 3，因为有 3 个数字，所以所有的数字都在范围 [0,3] 内。2 是丢失的数字，因为它没有出现在 nums 中。
     */
    public static int missingNumber(int[] nums) {
//        int n = nums.length;
//        Arrays.sort(nums);
//        if(nums[0] != 0){
//            return 0;
//        }
//        for(int i = 0; i < n - 1; i++){
//            if(nums[i + 1] != nums[i] + 1){
//                return nums[i] + 1;
//            }
//        }
//        return nums[n - 1] + 1;
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return n;
    }

    /**
     * 你是产品经理，目前正在带领一个团队开发新的产品。不幸的是，你的产品的最新版本没有通过质量检测。由于每个版本都是基于之前的版本开发的，所以错误的版本之后的所有版本都是错的。
     * 假设你有 n 个版本 [1, 2, ..., n]，你想找出导致之后所有版本出错的第一个错误的版本。
     * 你可以通过调用 bool isBadVersion(version) 接口来判断版本号 version 是否在单元测试中出错。实现一个函数来查找第一个错误的版本。你应该尽量减少对调用 API 的次数。
     * 输入：n = 5, bad = 4
     * 输出：4
     * 解释：
     * 调用 isBadVersion(3) -> false
     * 调用 isBadVersion(5) -> true
     * 调用 isBadVersion(4) -> true
     * 所以，4 是第一个错误的版本。
     */
    public static int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left < right) { // 循环直至区间左右端点相同
            int mid = left + (right - left) / 2; // 防止计算时溢出
            if (isBadVersion(mid)) {
                right = mid; // 答案在区间 [left, mid] 中
            } else {
                left = mid + 1; // 答案在区间 [mid+1, right] 中
            }
        }
        System.out.println(count);
        // 此时有 left == right，区间缩为一个点，即为答案
        return left;
    }

    private static boolean isBadVersion(int n) {
        count++;
        if (n >= 5000) {
            return true;
        }
        return false;
    }

    /**
     * 你和你的朋友，两个人一起玩 Nim 游戏：
     * 桌子上有一堆石头。
     * 你们轮流进行自己的回合， 你作为先手 。
     * 每一回合，轮到的人拿掉 1 - 3 块石头。
     * 拿掉最后一块石头的人就是获胜者。
     * 假设你们每一步都是最优解。请编写一个函数，来判断你是否可以在给定石头数量为 n 的情况下赢得游戏。如果可以赢，返回 true；否则，返回 false 。
     * 输入：n = 4
     * 输出：false
     */
    public boolean canWinNim(int n) {
        return !(n % 4 == 0);
    }

    /**
     * 3的幂
     */
    public static boolean isPowerOfThree(int n) {
//        if(n == 1){
//            return true;
//        }
//        if(n == 0){
//            return false;
//        }
//        if(n % 3 == 0){
//            return isPowerOfThree(n/3);
//        }else {
//            return false;
//        }
        while (n != 0 && n % 3 == 0) {
            n /= 3;
        }
        return n == 1;
    }

    /**
     * 给你一个整数 n ，对于 0 <= i <= n 中的每个 i ，计算其二进制表示中 1 的个数 ，返回一个长度为 n + 1 的数组 ans 作为答案。
     * 输入：n = 2
     * 输出：[0,1,1]
     * 解释：
     * 0 --> 0
     * 1 --> 1
     * 2 --> 10
     */
    public static int[] countBits(int n) {
//        int[] array = new int[n + 1];
//        for(int i = 0; i <= n; i++){
//            String numStr = Integer.toBinaryString(i);
//            char[] chars = numStr.toCharArray();
//            int count = 0;
//            for(char ch : chars){
//                if(ch == '1'){
//                    count++;
//                }
//            }
//            array[i] = count;
//        }
//        return array;
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = countOnes(i);
        }
        return bits;
    }

    //对于任意整数 x，令 x = x & (x−1)，该运算将 x 的二进制表示的最后一个 1 变成 0。因此，对 x 重复该操作，直到 x 变成 0，则操作次数即为 x 的「一比特数」
    public static int countOnes(int x) {
        int ones = 0;
        while (x > 0) {
            x &= (x - 1);
            ones++;
        }
        return ones;
    }

    /**
     * 4的幂
     */
    public boolean isPowerOfFour(int n) {
//        return n > 0 && (n & (n - 1)) == 0 && n % 3 == 1;
        return n > 0 && (n & -n) == n && (n & 0xaaaaaaaa) == 0;
    }

    /**
     * 给你一个字符串 s ，仅反转字符串中的所有元音字母，并返回结果字符串。
     * 元音字母包括 'a'、'e'、'i'、'o'、'u'，且可能以大小写两种形式出现不止一次。
     * 输入：s = "hello"
     * 输出："holle"
     */
    public String reverseVowels(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();
        int i = 0, j = n - 1;
        while (i < j) {
            while (i < n && !isVowel(arr[i])) {
                ++i;
            }
            while (j > 0 && !isVowel(arr[j])) {
                --j;
            }
            if (i < j) {
                swap(arr, i, j);
                ++i;
                --j;
            }
        }
        return new String(arr);

    }

    public boolean isVowel(char ch) {
        return "aeiouAEIOU".indexOf(ch) >= 0;
    }

    public void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 取交集，但是考虑次数
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int length1 = nums1.length, length2 = nums2.length;
        int[] intersection = new int[Math.min(length1, length2)];
        int index1 = 0, index2 = 0, index = 0;
        while (index1 < length1 && index2 < length2) {
            if (nums1[index1] < nums2[index2]) {
                index1++;
            } else if (nums1[index1] > nums2[index2]) {
                index2++;
            } else {
                intersection[index] = nums1[index1];
                index1++;
                index2++;
                index++;
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }

    public boolean isPerfectSquare(int num) {
//        int n = (int) Math.sqrt(num);
//        return num == n * n;
        //暴力破解
//        long x = 1, square = 1;
//        while (square <= num) {
//            if (square == num) {
//                return true;
//            }
//            ++x;
//            square = x * x;
//        }
//        return false;
        //二分
        int low = 1, high = num;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (mid < num / mid) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return high * high == num;
    }

    /**
     * 猜数字
     *
     * @param n
     * @return
     */
    public static int guessNumber(int n) {
        int left = 0, right = n;
        while (left < right) {
            int result = guess(n);
            if (result == 0) {
                return n;
            } else if (result == 1) {
                //猜小了
                left = n + 1;
                n = left + (right - left) / 2;
            } else {
                //猜大了
                right = n - 1;
                n = left + (right - left) / 2;
            }
        }
        return left;
    }

    static int guessNum;

    static {
        Random random = new Random();
        guessNum = random.nextInt(100);
    }


    private static int guess(int num) {
        if (num == guessNum) {
            return 0;
        } else if (num < guessNum) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 给定一个字符串 s ，找到 它的第一个不重复的字符，并返回它的索引 。如果不存在，则返回 -1 。
     * 输入: s = "leetcode"
     * 输出: 0
     */
    public static int firstUniqChar(String s) {
//        char[] chars = s.toCharArray();
//        Map<Character,Integer> map = new HashMap<>();
//        Set<Character> set = new HashSet<>();
//        for(int i = 0; i < chars.length; i++){
//            if(null != map.put(chars[i],i)){
//                set.add(chars[i]);
//            }
//        }
//        set.forEach(map::remove);
//        if(map.values().size() == 0){
//            return -1;
//        }
//        List<Integer> list = new ArrayList<>(map.values());
//        Collections.sort(list);
//        return list.get(0);
        Map<Character, Integer> position = new HashMap<Character, Integer>();
        Queue<Pair> queue = new LinkedList<Pair>();
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            char ch = s.charAt(i);
            if (!position.containsKey(ch)) {
                position.put(ch, i);
                queue.offer(new Pair(ch, i));
            } else {
                position.put(ch, -1);
                while (!queue.isEmpty() && position.get(queue.peek().ch) == -1) {
                    queue.poll();
                }
            }
        }
        return queue.isEmpty() ? -1 : queue.poll().pos;
    }

    static class Pair {
        char ch;
        int pos;

        Pair(char ch, int pos) {
            this.ch = ch;
            this.pos = pos;
        }
    }

    /**
     * 给定两个字符串 s 和 t ，它们只包含小写字母。
     * 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。
     * 请找出在 t 中被添加的字母。
     * 输入：s = "", t = "y"
     * 输出："y"
     */
    public static char findTheDifference(String s, String t) {
//        if (s.length() == 0) {
//            return t.charAt(0);
//        }
//        Map<Character, Integer> map = new HashMap<>();
//        for (char ch : s.toCharArray()) {
//            map.put(ch, map.get(ch) == null ? 1 : map.get(ch) + 1);
//        }
//        for (char ch2 : t.toCharArray()) {
//            Integer count = map.get(ch2);
//            if(count == null){
//                return ch2;
//            }
//            if (0 == count - 1) {
//                map.remove(ch2);
//            } else {
//                map.put(ch2, count - 1);
//            }
//        }
//
//        return new ArrayList<>(map.keySet()).get(0);
        //26个字母数组
//        int[] cnt = new int[26];
//        for (int i = 0; i < s.length(); ++i) {
//            char ch = s.charAt(i);
//            cnt[ch - 'a']++;
//        }
//        for (int i = 0; i < t.length(); ++i) {
//            char ch = t.charAt(i);
//            cnt[ch - 'a']--;
//            if (cnt[ch - 'a'] < 0) {
//                return ch;
//            }
//        }
//        return ' ';
        // ASCII 码的值求和
//        int as = 0, at = 0;
//        for (int i = 0; i < s.length(); ++i) {
//            as += s.charAt(i);
//        }
//        for (int i = 0; i < t.length(); ++i) {
//            at += t.charAt(i);
//        }
//        return (char) (at - as);
        //如果将两个字符串拼接成一个字符串，则问题转换成求字符串中出现奇数次的字符。类似于「136. 只出现一次的数字」，我们使用位运算的技巧解决本题。
        int ret = 0;
        for (int i = 0; i < s.length(); ++i) {
            ret ^= s.charAt(i);
        }
        for (int i = 0; i < t.length(); ++i) {
            ret ^= t.charAt(i);
        }
        return (char) ret;
    }

    /**
     * 由题意可知，小时由 4 个比特表示，分钟由 6 个比特表示，比特位值为 0 表示灯灭，为 1 表示灯亮。
     * <p>
     * 我们可以枚举小时的所有可能值 [0,11]，以及分钟的所有可能值 [0,59]，并计算二者的二进制中 1 的个数之和，若为 turnedOn，则将其加入到答案中。
     */
    public static List<String> readBinaryWatch(int turnedOn) {
        List<String> ans = new ArrayList<String>();
        for (int h = 0; h < 12; ++h) {
            for (int m = 0; m < 60; ++m) {
                if (Integer.bitCount(h) + Integer.bitCount(m) == turnedOn) {
                    ans.add(h + ":" + (m < 10 ? "0" : "") + m);
                }
            }
        }
        return ans;
    }

    /**
     * 给定二叉树的根节点 root ，返回所有左叶子之和。
     */
    public static int sumOfLeftLeaves(TreeNode root) {
        return sum(root, 0);
    }

    private static int sum(TreeNode root, Integer ans) {
        if (root.left != null) {
            if (root.left.left == null && root.left.right == null) {
                ans += root.left.val;
            }

            ans = sum(root.left, ans);
        }
        if (root.right != null) {
            ans = sum(root.right, ans);
        }
        return ans;
    }

    public static String toHex(int num) {
        // 一个16进制占4位 用0x开头表示16进制符号然后和num的后四位进行与运算即可
        if (num == 0) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 7; i >= 0; i--) {
            int val = (num >> (4 * i)) & 0xf;
            if (sb.length() > 0 || val > 0) {
                char digit = val < 10 ? (char) ('0' + val) : (char) ('a' + val - 10);
                sb.append(digit);
            }
        }
        return sb.toString();
    }

    /**
     * 给定一个包含大写字母和小写字母的字符串 s ，返回 通过这些字母构造成的 最长的回文串 。
     * 在构造过程中，请注意 区分大小写 。比如 "Aa" 不能当做一个回文字符串。
     * 输入:s = "abccccdd"
     * 输出:7
     * 解释:
     * 我们可以构造的最长的回文串是"dccaccd", 它的长度是 7。
     */
    public static int longestPalindrome(String s) {
        //偶数个字符+1个奇数字符构成最长
        int[] cnt = new int[104];
        int ans = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); ++i) {
            char ch = s.charAt(i);
            cnt[ch - 'A']++;
        }
//        for(Integer num : cnt){
//            if(num == 0){
//                continue;
//            }
//            if(num % 2 == 0){
//                ans += num;
//            }else {
//                flag = true;
//                ans += num / 2 * 2;
//            }
//        }
//        return flag ? ++ans : ans;
        for (int v : cnt) {
            ans += v / 2 * 2;
            if (v % 2 == 1 && ans % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }

    public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                list.add("FizzBuzz");
            } else if (i % 3 == 0) {
                list.add("Fizz");
            } else if (i % 5 == 0) {
                list.add("Buzz");
            } else {
                list.add(String.valueOf(i));
            }
        }
        return list;
    }

    /**
     * 给你一个非空数组，返回此数组中 第三大的数 。如果不存在，则返回数组中最大的数。
     * 输入：[2, 2, 3, 1]
     * 输出：1
     * 解释：注意，要求返回第三大的数，是指在所有不同数字中排第三大的数。
     * 此例中存在两个值为 2 的数，它们都排第二。在所有不同数字中排第三大的数为 1 。
     */
    public static int thirdMax(int[] nums) {
//        Long[] array = new Long[nums.length];
//        for(int i = 0; i < nums.length; i++){
//            array[i] = (long) nums[i];
//        }
//        Arrays.sort(array, (o1, o2) -> (int) (o2 - o1));
//        Set<Long> set = new HashSet<>();
//        for(Long i : array){
//            if(set.size() <= 2){
//                set.add(i);
//            }else {
//                break;
//            }
//        }
//        List<Long> list = new ArrayList<>(set);
//        list.sort(((o1, o2) -> (int) (o2 - o1)));
//        return list.size() < 3 ? list.get(0).intValue() : list.get(2).intValue();
        //Treeset
//        TreeSet<Integer> s = new TreeSet<Integer>();
//        for (int num : nums) {
//            s.add(num);
//            if (s.size() > 3) {
//                s.remove(s.first());
//            }
//        }
//        return s.size() == 3 ? s.first() : s.last();

        Arrays.sort(nums);
        reverse(nums);
        for (int i = 1, diff = 1; i < nums.length; ++i) {
            if (nums[i] != nums[i - 1] && ++diff == 3) { // 此时 nums[i] 就是第三大的数
                return nums[i];
            }
        }
        return nums[0];
    }

    public static void reverse(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    public static int countSegments(String s) {
//        if (s.length() == 0) {
//            return 0;
//        }
//        String[] strings = s.split(" ");
//        List<String> list = Arrays.stream(strings).filter(s1 -> s1.length()>0).collect(Collectors.toList());
//        return list.size();

        int segmentCount = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((i == 0 || s.charAt(i - 1) == ' ') && s.charAt(i) != ' ') {
                segmentCount++;
            }
        }
        return segmentCount;
    }

    public int arrangeCoins(int n) {
//        return (int) ((Math.sqrt((long) 8 * n + 1) - 1) / 2);
        //total= k*(k+1) / 2
        int left = 1, right = n;
        while (left < right) {
            int mid = (right - left + 1) / 2 + left;
            if ((long) mid * (mid + 1) <= (long) 2 * n) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * 给你一个含 n 个整数的数组 nums ，其中 nums[i] 在区间 [1, n] 内。请你找出所有在 [1, n] 范围内但没有出现在 nums 中的数字，并以数组的形式返回结果。
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
//        int n = nums.length;
//        Set<Integer> set = new HashSet<>();
//        for(int i : nums){
//            set.add(i);
//        }
//        List<Integer> list = new ArrayList<>();
//        for(int i = 1; i<= n; i++){
//            if(!set.contains(i)){
//                list.add(i);
//            }
//        }
//        return list;
        //具体来说，遍历 nums，每遇到一个数 x，就让 nums[x−1] 增加 n。由于 nums 中所有数均在 [1,n] 中，增加以后，这些数必然大于 n。
        // 最后我们遍历 nums，若 nums[i] 未大于 n，就说明没有遇到过数 i+1。这样我们就找到了缺失的数字。
        int n = nums.length;
        for (int num : nums) {
            int x = (num - 1) % n;
            nums[x] += n;
        }
        List<Integer> ret = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            if (nums[i] <= n) {
                ret.add(i + 1);
            }
        }
        return ret;
    }

    /**
     * 假设你是一位很棒的家长，想要给你的孩子们一些小饼干。但是，每个孩子最多只能给一块饼干。
     * 对每个孩子 i，都有一个胃口值 g[i]，这是能让孩子们满足胃口的饼干的最小尺寸；并且每块饼干 j，都有一个尺寸 s[j] 。
     * 如果 s[j] >= g[i]，我们可以将这个饼干 j 分配给孩子 i ，这个孩子会得到满足。你的目标是尽可能满足越多数量的孩子，并输出这个最大数值。
     * 输入: g = [1,2,3], s = [1,1]
     * 输出: 1
     * 解释:
     * 你有三个孩子和两块小饼干，3个孩子的胃口值分别是：1,2,3。
     * 虽然你有两块小饼干，由于他们的尺寸都是1，你只能让胃口值是1的孩子满足。
     * 所以你应该输出1。
     */
    public int findContentChildren(int[] g, int[] s) {
//        Arrays.sort(g);
//        Arrays.sort(s);
//        int ans = 0;
//        for(int size : s){
//            for(int i = 0; i < g.length; i++){
//                if(g[i] > 0 && size >= g[i]){
//                    ans++;
//                    g[i] = 0;
//                    break;
//                }
//            }
//        }
//        return ans;
        /**
         * 首先对数组 ggg 和 sss 排序，然后从小到大遍历 ggg 中的每个元素，对于每个元素找到能满足该元素的 sss 中的最小的元素。具体而言，令 iii 是 ggg 的下标，jjj 是 sss 的下标，初始时 iii 和 jjj 都为 000，进行如下操作。
         *
         * 对于每个元素 g[i]g[i]g[i]，找到未被使用的最小的 jjj 使得 g[i]≤s[j]g[i] \le s[j]g[i]≤s[j]，则 s[j]s[j]s[j] 可以满足 g[i]g[i]g[i]。由于 ggg 和 sss 已经排好序，因此整个过程只需要对数组 ggg 和 sss 各遍历一次。当两个数组之一遍历结束时，说明所有的孩子都被分配到了饼干，或者所有的饼干都已经被分配或被尝试分配（可能有些饼干无法分配给任何孩子），此时被分配到饼干的孩子数量即为可以满足的最多数量。
         */
        Arrays.sort(g);
        Arrays.sort(s);
        int m = g.length, n = s.length;
        int count = 0;
        for (int i = 0, j = 0; i < m && j < n; i++, j++) {
            while (j < n && g[i] > s[j]) {
                j++;
            }
            if (j < n) {
                count++;
            }
        }
        return count;
    }

    /**
     * 给定一个非空的字符串 s ，检查是否可以通过由它的一个子串重复多次构成。
     * 输入: s = "abab"
     * 输出: true
     * 解释: 可由子串 "ab" 重复两次构成。
     */
    public static boolean repeatedSubstringPattern(String s) {
//        return (s + s).indexOf(s, 1) != s.length();
        int n = s.length();
        for (int i = 1; i * 2 <= n; ++i) {
            if (n % i == 0) {
                boolean match = true;
                for (int j = i; j < n; ++j) {
                    if (s.charAt(j) != s.charAt(j - i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 两个整数之间的 汉明距离 指的是这两个数字对应二进制位不同的位置的数目。
     * <p>
     * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
     */
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }

    static int[] dx = {0, 1, 0, -1};
    static int[] dy = {1, 0, -1, 0};

    /**
     * 给定一个 row x col 的二维网格地图 grid ，其中：grid[i][j] = 1 表示陆地， grid[i][j] = 0 表示水域。
     * 网格中的格子 水平和垂直 方向相连（对角线方向不相连）。整个网格被水完全包围，但其中恰好有一个岛屿（或者说，一个或多个表示陆地的格子相连组成的岛屿）。
     * 岛屿中没有“湖”（“湖” 指水域在岛屿内部且不和岛屿周围的水相连）。格子是边长为 1 的正方形。网格为长方形，且宽度和高度均不超过 100 。计算这个岛屿的周长。
     */
    public int islandPerimeter(int[][] grid) {
        /*
        对于一个陆地格子的每条边，它被算作岛屿的周长当且仅当这条边为网格的边界或者相邻的另一个格子为水域。
         因此，我们可以遍历每个陆地格子，看其四个方向是否为边界或者水域，如果是，将这条边的贡献（即 1）加入答案 ans 中即可。
         */
        //n 为网格的高度，m 为网格的宽度
        int n = grid.length, m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (grid[i][j] == 1) {
                    int add = 4;    //方格初始周长
                    if (i - 1 >= 0 && grid[i - 1][j] == 1) add--;  //上边有岛
                    if (i + 1 < n && grid[i + 1][j] == 1) add--;   //下边有岛
                    if (j - 1 >= 0 && grid[i][j - 1] == 1) add--;  //左边有岛
                    if (j + 1 < m && grid[i][j + 1] == 1) add--;   //右边有岛
                    ans += add;
                }
            }
        }
        return ans;
    }


    /**
     * 数字的补数
     */
    public int findComplement(int num) {
        int highbit = 0;
        for (int i = 1; i <= 30; ++i) {
            if (num >= 1 << i) {
                highbit = i;
            } else {
                break;
            }
        }
        int mask = highbit == 30 ? 0x7fffffff : (1 << (highbit + 1)) - 1;
        return num ^ mask;
    }

    /**
     * 给定一个许可密钥字符串 s，仅由字母、数字字符和破折号组成。字符串由 n 个破折号分成 n + 1 组。你也会得到一个整数 k 。
     * 我们想要重新格式化字符串 s，使每一组包含 k 个字符，除了第一组，它可以比 k 短，但仍然必须包含至少一个字符。此外，两组之间必须插入破折号，并且应该将所有小写字母转换为大写字母。
     * 返回 重新格式化的许可密钥 。
     * 输入：S = "5F3Z-2e-9-w", k = 4
     * 输出："5F3Z-2E9W"
     * 解释：字符串 S 被分成了两个部分，每部分 4 个字符；
     *      注意，两个额外的破折号需要删掉。
     */
    public static String licenseKeyFormatting(String s, int k) {
        s = s.replace("-","").toUpperCase();
        int n = s.length();
        int i = n / k , j = n % k;
        StringBuilder sb = new StringBuilder();
        sb.append(s, 0, j);
        for(int m = 0; m < i ; m++){
            if(sb.length() > 0){
                sb.append("-");
            }
            sb.append(s, j, j + k);
            j = j + k;
        }
        return sb.toString();
    }

    /**
     * 给定一个二进制数组 nums ， 计算其中最大连续 1 的个数。
     * 输入：nums = [1,1,0,1,1,1]
     * 输出：3
     * 解释：开头的两位和最后的三位都是连续 1 ，所以最大连续 1 的个数是 3.
     */
    public static int findMaxConsecutiveOnes(int[] nums) {
//        int ans = 0;
//        int n = nums.length;
//        int left = 0,right = 1;
//        if(nums.length == 1 && nums[0] == 1){
//            ans = 1;
//        }
//        while (right < n){
//            if(nums[left] == 1){
//                if(nums[right] == 1){
//                    right++;
//                }else {
//                    int count = right - left;
//                    ans = Math.max(ans,count);
//                    left = right;
//                    right++;
//                }
//            }else {
//                left++;
//                right++;
//            }
//        }
//        if(nums[right - 1] == 1){
//            int count = right - left;
//            ans = Math.max(ans,count);
//        }
//        return ans;

        int maxCount = 0, count = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                maxCount = Math.max(maxCount, count);
                count = 0;
            }
        }
        maxCount = Math.max(maxCount, count);
        return maxCount;
    }

    /**
     * 作为一位web开发者， 懂得怎样去规划一个页面的尺寸是很重要的。 所以，现给定一个具体的矩形页面面积，你的任务是设计一个长度为 L 和宽度为 W 且满足以下要求的矩形的页面。要求：
     *
     * 你设计的矩形页面必须等于给定的目标面积。
     * 宽度 W 不应大于长度 L ，换言之，要求 L >= W 。
     * 长度 L 和宽度 W 之间的差距应当尽可能小。
     * 返回一个 数组 [L, W]，其中 L 和 W 是你按照顺序设计的网页的长度和宽度。
     * 输入: 4
     * 输出: [2, 2]
     */
    public static int[] constructRectangle(int area) {
        int w = (int) Math.sqrt(area);
        while (area % w != 0) {
            --w;
        }
        return new int[]{area / w, w};
    }

    /**
     * 在《英雄联盟》的世界中，有一个叫 “提莫” 的英雄。他的攻击可以让敌方英雄艾希（编者注：寒冰射手）进入中毒状态。
     * 当提莫攻击艾希，艾希的中毒状态正好持续 duration 秒。
     * 正式地讲，提莫在 t 发起攻击意味着艾希在时间区间 [t, t + duration - 1]（含 t 和 t + duration - 1）处于中毒状态。如果提莫在中毒影响结束 前 再次攻击，中毒状态计时器将会 重置 ，在新的攻击之后，中毒影响将会在 duration 秒后结束。
     * 给你一个 非递减 的整数数组 timeSeries ，其中 timeSeries[i] 表示提莫在 timeSeries[i] 秒时对艾希发起攻击，以及一个表示中毒持续时间的整数 duration 。
     * 返回艾希处于中毒状态的 总 秒数。
     * 输入：timeSeries = [1,4], duration = 2
     * 输出：4
     * 解释：提莫攻击对艾希的影响如下：
     * - 第 1 秒，提莫攻击艾希并使其立即中毒。中毒状态会维持 2 秒，即第 1 秒和第 2 秒。
     * - 第 4 秒，提莫再次攻击艾希，艾希中毒状态又持续 2 秒，即第 4 秒和第 5 秒。
     * 艾希在第 1、2、4、5 秒处于中毒状态，所以总中毒秒数是 4 。
     */
    public int findPoisonedDuration(int[] timeSeries, int duration) {
//        Set<Integer> set = new HashSet<>();
//        for(int i : timeSeries){
//            if(duration > 0){
//                for(int j = i; j <= i + duration - 1; j++){
//                    set.add(j);
//                }
//            }
//        }
//        return set.size();
        int ans = 0;
        int expired = 0;
        for (int i = 0; i < timeSeries.length; ++i) {
            if (timeSeries[i] >= expired) {
                ans += duration;
            } else {
                ans += timeSeries[i] + duration - expired;
            }
            expired = timeSeries[i] + duration;
        }
        return ans;

    }

    /**
     * nums1 中数字 x 的 下一个更大元素 是指 x 在 nums2 中对应位置 右侧 的 第一个 比 x 大的元素。
     * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，下标从 0 开始计数，其中nums1 是 nums2 的子集。
     * 对于每个 0 <= i < nums1.length ，找出满足 nums1[i] == nums2[j] 的下标 j ，并且在 nums2 确定 nums2[j] 的 下一个更大元素 。如果不存在下一个更大元素，那么本次查询的答案是 -1 。
     * 返回一个长度为 nums1.length 的数组 ans 作为答案，满足 ans[i] 是如上所述的 下一个更大元素 。
     * 输入：nums1 = [4,1,2], nums2 = [1,3,4,2].
     * 输出：[-1,3,-1]
     * 解释：nums1 中每个值的下一个更大元素如下所述：
     * - 4 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     * - 1 ，用加粗斜体标识，nums2 = [1,3,4,2]。下一个更大元素是 3 。
     * - 2 ，用加粗斜体标识，nums2 = [1,3,4,2]。不存在下一个更大元素，所以答案是 -1 。
     */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
//        int[] ans = new int[nums1.length];
//        for(int i = 0; i < nums1.length; i++){
//            for(int j = 0; j < nums2.length; j++){
//                if(nums1[i] == nums2[j]){
//                    if(j + 1 < nums2.length){
//                        for(int m = j + 1; m < nums2.length; m++){
//                            if(nums2[m] > nums2[j]){
//                                ans[i] = nums2[m];
//                                break;
//                            }
//                        }
//                        if(ans[i] == 0){
//                            ans[i] = -1;
//                        }
//
//                    }else {
//                        ans[i] = -1;
//                    }
//                    break;
//                }
//            }
//        }
//        return ans;
        //暴力
//        int m = nums1.length, n = nums2.length;
//        int[] res = new int[m];
//        for (int i = 0; i < m; ++i) {
//            int j = 0;
//            while (j < n && nums2[j] != nums1[i]) {
//                ++j;
//            }
//            int k = j + 1;
//            while (k < n && nums2[k] < nums2[j]) {
//                ++k;
//            }
//            res[i] = k < n ? nums2[k] : -1;
//        }
//        return res;
        //单调栈
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int i = nums2.length - 1; i >= 0; --i) {
            int num = nums2[i];
            while (!stack.isEmpty() && num >= stack.peek()) {
                stack.pop();
            }
            map.put(num, stack.isEmpty() ? -1 : stack.peek());
            stack.push(num);
        }
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; ++i) {
            res[i] = map.get(nums1[i]);
        }
        return res;
    }

    /**
     * 给你一个字符串数组 words ，只返回可以使用在 美式键盘 同一行的字母打印出来的单词。键盘如下图所示。
     * 第一行由字符 "qwertyuiop" 组成。
     * 第二行由字符 "asdfghjkl" 组成。
     * 第三行由字符 "zxcvbnm" 组成。
     * 输入：words = ["Hello","Alaska","Dad","Peace"]
     * 输出：["Alaska","Dad"]
     */
    public String[] findWords(String[] words) {
        String line1 = "qwertyuiop";
        String line2 = "asdfghjkl";
        String line3 = "zxcvbnm";
        List<String> list = new ArrayList<>();
        for(String str : words){
            boolean line1Flag = false;
            boolean line2Flag = false;
            boolean line3Flag = false;
            char[] chars = str.toCharArray();
            for(char ch : chars){
                if(line1.contains(String.valueOf(ch).toLowerCase())){
                    line1Flag = true;
                }else if(line2.contains(String.valueOf(ch).toLowerCase())){
                    line2Flag = true;
                }else if(line3.contains(String.valueOf(ch).toLowerCase())){
                    line3Flag = true;
                }
            }
            int count = 0;
            if(line1Flag){
                count++;
            }
            if(line2Flag){
                count++;
            }
            if(line3Flag){
                count++;
            }
            if(count == 1){
                list.add(str);
            }
        }
        return list.toArray(new String[0]);
    }

    /**
     * 给你一个含重复值的二叉搜索树（BST）的根节点 root ，找出并返回 BST 中的所有 众数（即，出现频率最高的元素）。
     * 如果树中有不止一个众数，可以按 任意顺序 返回。
     * 假定 BST 满足如下定义：
     * 结点左子树中所含节点的值 小于等于 当前节点的值
     * 结点右子树中所含节点的值 大于等于 当前节点的值
     * 左子树和右子树都是二叉搜索树
     */
//    public static int[] findMode(TreeNode root) {
//        Map<Integer,Integer> map = new HashMap<>();
//        map = findModeIterator(root,map);
//        int count = 0;
//        List<Integer> list = new ArrayList<>();
//        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
//            if(entry.getValue() > count){
//                list.clear();
//                list.add(entry.getKey());
//                count = entry.getValue();
//            }else if(entry.getValue() == count){
//                list.add(entry.getKey());
//            }
//        }
//        int[] ans = new int[list.size()];
//        for(int i = 0; i < list.size(); i++){
//            ans[i] = list.get(i);
//        }
//        return ans;
//    }
//
//    private static Map<Integer,Integer> findModeIterator(TreeNode root,Map<Integer,Integer> map){
//        if (root != null){
//            if(!map.containsKey(root.val)){
//                map.put(root.val, 1);
//            }else {
//                map.put(root.val, map.get(root.val) + 1);
//            }
//            map.putAll(findModeIterator(root.left,map));
//            map.putAll(findModeIterator(root.right,map));
//        }
//        return map;
//    }

    static List<Integer> answer = new ArrayList<Integer>();
    static int base, maxCount;

    public static int[] findMode(TreeNode root) {
        //首先，我们考虑在寻找出现次数最多的数时，不使用哈希表。
        // 这个优化是基于二叉搜索树中序遍历的性质：一棵二叉搜索树的中序遍历序列是一个非递减的有序序列。
        dfs2(root);
        int[] mode = new int[answer.size()];
        for (int i = 0; i < answer.size(); ++i) {
            mode[i] = answer.get(i);
        }
        return mode;
    }

    public static void dfs2(TreeNode o) {
        if (o == null) {
            return;
        }
        dfs2(o.left);
        update(o.val);
        dfs2(o.right);
    }

    public static void update(int x) {
        if (x == base) {
            ++count;
        } else {
            count = 1;
            base = x;
        }
        if (count == maxCount) {
            answer.add(base);
        }
        if (count > maxCount) {
            maxCount = count;
            answer.clear();
            answer.add(base);
        }
    }

    /**
     * 7进制
     */
    public static String convertToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        boolean negative = num < 0;
        num = Math.abs(num);
        StringBuffer digits = new StringBuffer();
        while (num > 0) {
            digits.append(num % 7);
            num /= 7;
        }
        if (negative) {
            digits.append('-');
        }
        return digits.reverse().toString();
    }

    /**
     * 给你一个长度为 n 的整数数组 score ，其中 score[i] 是第 i 位运动员在比赛中的得分。所有得分都 互不相同 。
     * 运动员将根据得分 决定名次 ，其中名次第 1 的运动员得分最高，名次第 2 的运动员得分第 2 高，依此类推。运动员的名次决定了他们的获奖情况：
     * 名次第 1 的运动员获金牌 "Gold Medal" 。
     * 名次第 2 的运动员获银牌 "Silver Medal" 。
     * 名次第 3 的运动员获铜牌 "Bronze Medal" 。
     * 从名次第 4 到第 n 的运动员，只能获得他们的名次编号（即，名次第 x 的运动员获得编号 "x"）。
     * 使用长度为 n 的数组 answer 返回获奖，其中 answer[i] 是第 i 位运动员的获奖情况。
     * 输入：score = [10,3,8,9,4]
     * 输出：["Gold Medal","5","Bronze Medal","Silver Medal","4"]
     * 解释：名次为 [1st, 5th, 3rd, 2nd, 4th] 。
     */
    public static String[] findRelativeRanks(int[] score) {
        int n = score.length;
        String[] desc = {"Gold Medal", "Silver Medal", "Bronze Medal"};
        int[][] arr = new int[n][2];

        for (int i = 0; i < n; ++i) {
            arr[i][0] = score[i];
            arr[i][1] = i;
        }
        Arrays.sort(arr, (a, b) -> b[0] - a[0]);
        String[] ans = new String[n];
        for (int i = 0; i < n; ++i) {
            if (i >= 3) {
                ans[arr[i][1]] = Integer.toString(i + 1);
            } else {
                ans[arr[i][1]] = desc[i];
            }
        }
        return ans;
    }

    /**
     * 对于一个 正整数，如果它和除了它自身以外的所有 正因子 之和相等，我们称它为 「完美数」。
     *
     * 给定一个 整数 n， 如果是完美数，返回 true；否则返回 false。
     * 输入：num = 28
     * 输出：true
     * 解释：28 = 1 + 2 + 4 + 7 + 14
     * 1, 2, 4, 7, 和 14 是 28 的所有正因子。
     */
    public boolean checkPerfectNumber(int num) {
//        if(num == 1){
//            return false;
//        }
//        Set<Integer> set = new HashSet<>();
//        set.add(1);
//        for(int i = 2; i < num; i++){
//            if(num % i == 0){
//                if(!set.contains(i)){
//                    set.add(i);
//                    set.add(num / i);
//                }else {
//                    break;
//                }
//            }
//        }
//        int count = 0;
//        for(int n : set){
//            count += n;
//        }
//        return count == num;
        if (num == 1) {
            return false;
        }

        int sum = 1;
        for (int d = 2; d * d <= num; ++d) {
            if (num % d == 0) {
                sum += d;
                if (d * d < num) {
                    sum += num / d;
                }
            }
        }
        return sum == num;

    }

    public static void main(String[] args) {
        System.out.println(findRelativeRanks(new int[]{10,3,8,9,4}));
//        System.out.println(convertToBase7(7));
//        System.out.println(Integer.toString(7,2));
//        System.out.println(findMode(new TreeNode(1,null,new TreeNode(2,new TreeNode(2),null))));
//        System.out.println(nextGreaterElement(new int[]{4,1,2},new int[]{1,3,4,2}));
//        System.out.println(constructRectangle(37));
//        System.out.println(findMaxConsecutiveOnes(new int[]{1,1,0,1,1,1}));
//        System.out.println(licenseKeyFormatting("5F3Z-2e-9-w",4));
//        System.out.println(repeatedSubstringPattern("abab"));
//        System.out.println(findDisappearedNumbers(new int[]{4,3,2,7,8,2,3,1}));
//        System.out.println(countSegments(", , , ,        a, eaefa"));
//        System.out.println(thirdMax(new int[]{-21,1,1}));
//        System.out.println(longestPalindrome("zeusnilemacaronimaisanitratetartinasiaminoracamelinsuez"));
//        System.out.println(toHex(2000000000));
//        System.out.println(sumOfLeftLeaves(new TreeNode(1,new TreeNode(2,new TreeNode(4),new TreeNode(5)),new TreeNode(3))));
//        System.out.println(readBinaryWatch(2));
//        System.out.println(findTheDifference("ymbgaraibkfmvocpizdydugvalagaivdbfsfbepeyccqfepzvtpyxtbadkhmwmoswrcxnargtlswqemafandgkmydtimuzvjwxvlfwlhvkrgcsithaqlcvrihrwqkpjdhgfgreqoxzfvhjzojhghfwbvpfzectwwhexthbsndovxejsntmjihchaotbgcysfdaojkjldprwyrnischrgmtvjcorypvopfmegizfkvudubnejzfqffvgdoxohuinkyygbdzmshvyqyhsozwvlhevfepdvafgkqpkmcsikfyxczcovrmwqxxbnhfzcjjcpgzjjfateajnnvlbwhyppdleahgaypxidkpwmfqwqyofwdqgxhjaxvyrzupfwesmxbjszolgwqvfiozofncbohduqgiswuiyddmwlwubetyaummenkdfptjczxemryuotrrymrfdxtrebpbjtpnuhsbnovhectpjhfhahbqrfbyxggobsweefcwxpqsspyssrmdhuelkkvyjxswjwofngpwfxvknkjviiavorwyfzlnktmfwxkvwkrwdcxjfzikdyswsuxegmhtnxjraqrdchaauazfhtklxsksbhwgjphgbasfnlwqwukprgvihntsyymdrfovaszjywuqygpvjtvlsvvqbvzsmgweiayhlubnbsitvfxawhfmfiatxvqrcwjshvovxknnxnyyfexqycrlyksderlqarqhkxyaqwlwoqcribumrqjtelhwdvaiysgjlvksrfvjlcaiwrirtkkxbwgicyhvakxgdjwnwmubkiazdjkfmotglclqndqjxethoutvjchjbkoasnnfbgrnycucfpeovruguzumgmgddqwjgdvaujhyqsqtoexmnfuluaqbxoofvotvfoiexbnprrxptchmlctzgqtkivsilwgwgvpidpvasurraqfkcmxhdapjrlrnkbklwkrvoaziznlpor", "qhxepbshlrhoecdaodgpousbzfcqjxulatciapuftffahhlmxbufgjuxstfjvljybfxnenlacmjqoymvamphpxnolwijwcecgwbcjhgdybfffwoygikvoecdggplfohemfypxfsvdrseyhmvkoovxhdvoavsqqbrsqrkqhbtmgwaurgisloqjixfwfvwtszcxwktkwesaxsmhsvlitegrlzkvfqoiiwxbzskzoewbkxtphapavbyvhzvgrrfriddnsrftfowhdanvhjvurhljmpxvpddxmzfgwwpkjrfgqptrmumoemhfpojnxzwlrxkcafvbhlwrapubhveattfifsmiounhqusvhywnxhwrgamgnesxmzliyzisqrwvkiyderyotxhwspqrrkeczjysfujvovsfcfouykcqyjoobfdgnlswfzjmyucaxuaslzwfnetekymrwbvponiaojdqnbmboldvvitamntwnyaeppjaohwkrisrlrgwcjqqgxeqerjrbapfzurcwxhcwzugcgnirkkrxdthtbmdqgvqxilllrsbwjhwqszrjtzyetwubdrlyakzxcveufvhqugyawvkivwonvmrgnchkzdysngqdibhkyboyftxcvvjoggecjsajbuqkjjxfvynrjsnvtfvgpgveycxidhhfauvjovmnbqgoxsafknluyimkczykwdgvqwlvvgdmufxdypwnajkncoynqticfetcdafvtqszuwfmrdggifokwmkgzuxnhncmnsstffqpqbplypapctctfhqpihavligbrutxmmygiyaklqtakdidvnvrjfteazeqmbgklrgrorudayokxptswwkcircwuhcavhdparjfkjypkyxhbgwxbkvpvrtzjaetahmxevmkhdfyidhrdeejapfbafwmdqjqszwnwzgclitdhlnkaiyldwkwwzvhyorgbysyjbxsspnjdewjxbhpsvj"));
//        System.out.println(firstUniqChar("aabb"));
//        Map<String,String> map = new HashMap<>();
//        System.out.println(map.put("1","1"));
//        System.out.println(map.put("1","12"));
//        System.out.println(guessNumber(100));
//        System.out.println(guessNum);
//        int[] result = countBits(2);
//        System.out.println(result);
//        System.out.println(isPowerOfThree(0));
//        NumArray numArray = new NumArray(new int[]{-2, 0, 3, -5, 2, -1});
//        int param_1 = numArray.sumRange(0,2);
//        int param_2 = numArray.sumRange(2,5);
//        int param_3 = numArray.sumRange(0,5);

//        System.out.println(firstBadVersion(5));
//        System.out.println(missingNumber(new int[]{0,1}));
//        System.out.println(addDigits(38));
//        System.out.println(binaryTreePaths(new TreeNode(1,new TreeNode(2),new TreeNode(3)),""));
//        MyStack myStack = new MyStack();
//        myStack.push(1);
//        myStack.push(2);
//        myStack.push(3);

//        int n = 0b01010101_01010101_01010101_01010101;
//        String res = Integer.toBinaryString(n);
//        String res = "00000010100101000001111010011100";
//        System.out.println(res);
//        BigInteger bigInteger = new BigInteger(res, 2);
//
//        System.out.println(bigInteger);
//        System.out.println(averageOfLevels(new TreeNode(1,new TreeNode(1),null)));
//        System.out.println(reverseBits(43261596));
    }
}
