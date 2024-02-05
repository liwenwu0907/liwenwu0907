package com.example.demo;

import com.example.demo.entity.Node;
import com.example.demo.entity.TreeNode;

import java.util.*;

public class Solution4 {

    /**
     * Alice 有 n 枚糖，其中第 i 枚糖的类型为 candyType[i] 。Alice 注意到她的体重正在增长，所以前去拜访了一位医生。
     * 医生建议 Alice 要少摄入糖分，只吃掉她所有糖的 n / 2 即可（n 是一个偶数）。Alice 非常喜欢这些糖，她想要在遵循医生建议的情况下，尽可能吃到最多不同种类的糖。
     * 给你一个长度为 n 的整数数组 candyType ，返回： Alice 在仅吃掉 n / 2 枚糖的情况下，可以吃到糖的 最多 种类数。
     * 输入：candyType = [1,1,2,2,3,3]
     * 输出：3
     * 解释：Alice 只能吃 6 / 2 = 3 枚糖，由于只有 3 种糖，她可以每种吃一枚。
     */
    public int distributeCandies(int[] candyType) {
//        int n = candyType.length;
//        Set<Integer> set = new HashSet<>();
//        for(int i : candyType){
//            set.add(i);
//        }
//        return Math.min(n/2,set.size());
        int candyNum = candyType.length / 2;
        Set<Integer> candies = new HashSet<>();
        for (int candy : candyType) {
            if (candies.add(candy) && candies.size() >= candyNum) {
                return candyNum;
            }
        }
        return candies.size();
    }

    /**
     * 给定一个 n 叉树的根节点  root ，返回 其节点值的 前序遍历 。
     * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
     */
    static List<Integer> list = new ArrayList<>();

    public List<Integer> preorder(Node root) {
//        if(root != null){
//            list.add(root.val);
//            List<Node> nodeList = root.children;
//            if(nodeList.size() > 0){
//                for(Node node : nodeList){
//                    preorder(node);
//                }
//            }
//        }
//        return list;

        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            res.add(node.val);
            for (int i = node.children.size() - 1; i >= 0; --i) {
                stack.push(node.children.get(i));
            }
        }
        return res;
    }

    /**
     * 给定一个 n 叉树的根节点 root ，返回 其节点值的 后序遍历 。
     * n 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
     */
    public static List<Integer> postorder(Node root) {
//        Node node = new Node(0);
//        List<Node> children = new ArrayList<>();
//        children.add(root);
//        node.children = children;
//        iter(node);
//        return list;
        //递归
//        if (root == null) {
//            return list;
//        }
//        for (Node ch : root.children) {
//            postorder(ch);
//        }
//        list.add(root.val);
//        return list;
        //迭代
//        List<Integer> res = new ArrayList<>();
//        if (root == null) {
//            return res;
//        }
//
//        Deque<Node> stack = new ArrayDeque<Node>();
//        Set<Node> visited = new HashSet<Node>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            Node node = stack.peek();
//            /* 如果当前节点为叶子节点或者当前节点的子节点已经遍历过 */
//            if (node.children.size() == 0 || visited.contains(node)) {
//                stack.pop();
//                res.add(node.val);
//                continue;
//            }
//            for (int i = node.children.size() - 1; i >= 0; --i) {
//                stack.push(node.children.get(i));
//            }
//            visited.add(node);
//        }
//        return res;
        //利用前序遍历反转
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<Node> stack = new ArrayDeque<Node>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            res.add(node.val);
            for (Node item : node.children) {
                stack.push(item);
            }
        }
        Collections.reverse(res);
        return res;
    }

    private static void iter(Node root) {
        if (root != null) {
            List<Node> nodeList = root.children;
            if (nodeList.size() > 0) {
                for (Node node : nodeList) {
                    if (null != node) {
                        iter(node);
                        list.add(node.val);
                    }
                }
            }
        }
    }

    /**
     * 和谐数组是指一个数组里元素的最大值和最小值之间的差别 正好是 1 。
     * 现在，给你一个整数数组 nums ，请你在所有可能的子序列中找到最长的和谐子序列的长度。
     * 数组的子序列是一个由数组派生出来的序列，它可以通过删除一些元素或不删除元素、且不改变其余元素的顺序而得到。
     * 输入：nums = [1,3,2,2,5,2,3,7]
     * 输出：5
     * 解释：最长的和谐子序列是 [3,2,2,2,3]
     */
    public static int findLHS(int[] nums) {
        Arrays.sort(nums);
        int begin = 0, end = 1;
        int ans = 0;
        while (begin < end && end < nums.length) {
            if (nums[begin] + 1 == nums[end]) {
                ans = Math.max(ans, end - begin + 1);
                end++;
            } else {
                if (nums[end] > nums[begin] + 1) {
                    begin = begin + 1;
                }
                if (nums[end] < nums[begin] + 1) {
                    end++;
                }
            }
        }
        return ans;
    }

    /**
     * 给你一个 m x n 的矩阵 M ，初始化时所有的 0 和一个操作数组 op ，其中 ops[i] = [ai, bi]
     * 意味着当所有的 0 <= x < ai 和 0 <= y < bi 时， M[x][y] 应该加 1。
     * 在 执行完所有操作后 ，计算并返回 矩阵中最大整数的个数 。
     */
    public static int maxCount(int m, int n, int[][] ops) {
        int mina = m, minb = n;
        for (int[] op : ops) {
            mina = Math.min(mina, op[0]);
            minb = Math.min(minb, op[1]);
        }
        return mina * minb;
    }

    /**
     * 假设 Andy 和 Doris 想在晚餐时选择一家餐厅，并且他们都有一个表示最喜爱餐厅的列表，每个餐厅的名字用字符串表示。
     * 你需要帮助他们用最少的索引和找出他们共同喜爱的餐厅。 如果答案不止一个，则输出所有答案并且不考虑顺序。 你可以假设答案总是存在。
     * 输入:list1 = ["Shogun", "Tapioca Express", "Burger King", "KFC"]，list2 = ["KFC", "Shogun", "Burger King"]
     * 输出: ["Shogun"]
     * 解释: 他们共同喜爱且具有最小索引和的餐厅是“Shogun”，它有最小的索引和1(0+1)。
     */
    public static String[] findRestaurant(String[] list1, String[] list2) {
//        int sum = 2000;
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < list1.length; i++) {
//            for (int j = 0; j < list2.length; j++) {
//                if (list1[i].equals(list2[j])) {
//                    if(i + j == sum){
//                        list.add(list1[i]);
//                    }else if(i + j < sum){
//                        sum = i + j;
//                        list.clear();
//                        list.add(list1[i]);
//                    }
//                }
//            }
//        }
//        return list.toArray(new String[0]);
        Map<String, Integer> index = new HashMap<String, Integer>();
        for (int i = 0; i < list1.length; i++) {
            index.put(list1[i], i);
        }

        List<String> ret = new ArrayList<String>();
        int indexSum = Integer.MAX_VALUE;
        for (int i = 0; i < list2.length; i++) {
            if (index.containsKey(list2[i])) {
                int j = index.get(list2[i]);
                if (i + j < indexSum) {
                    ret.clear();
                    ret.add(list2[i]);
                    indexSum = i + j;
                } else if (i + j == indexSum) {
                    ret.add(list2[i]);
                }
            }
        }
        return ret.toArray(new String[0]);
    }

    /**
     * 给你二叉树的根节点 root ，请你采用前序遍历的方式，将二叉树转化为一个由括号和整数组成的字符串，返回构造出的字符串。
     * 空节点使用一对空括号对 "()" 表示，转化后需要省略所有不影响字符串与原始二叉树之间的一对一映射关系的空括号对。
     * 输入：root = [1,2,3,4]
     * 输出："1(2(4))(3)"
     * 解释：初步转化后得到 "1(2(4)())(3()())" ，但省略所有不必要的空括号对后，字符串应该是"1(2(4))(3)" 。
     */
    StringBuilder sb = new StringBuilder();

    public String tree2str(TreeNode root) {
//        if(root != null){
//            sb.append(root.val);
//            if(root.left == null && root.right != null){
//                sb.append("(");
//                sb.append(")");
//                sb.append("(");
//                tree2str(root.right);
//                sb.append(")");
//            }else {
//                if(root.left != null){
//                    sb.append("(");
//                    tree2str(root.left);
//                    sb.append(")");
//                }
//                if(root.right != null){
//                    sb.append("(");
//                    tree2str(root.right);
//                    sb.append(")");
//                }
//            }
//        }
//        return sb.toString();
        StringBuffer ans = new StringBuffer();
        Deque<TreeNode> stack = new ArrayDeque<TreeNode>();
        stack.push(root);
        Set<TreeNode> visited = new HashSet<TreeNode>();
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (!visited.add(node)) {
                if (node != root) {
                    ans.append(")");
                }
                stack.pop();
            } else {
                if (node != root) {
                    ans.append("(");
                }
                ans.append(node.val);
                if (node.left == null && node.right != null) {
                    ans.append("()");
                }
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }
        return ans.toString();
    }

    /**
     * 给你一个整型数组 nums ，在数组中找出由三个数组成的最大乘积，并输出这个乘积。
     * 输入：nums = [-1,-2,-3]
     * 输出：-6
     */
    public int maximumProduct(int[] nums) {
//        int ans = Integer.MIN_VALUE;
//        int n = nums.length;
//        Arrays.sort(nums);
//        ans = Math.max(nums[n - 1] * nums[n - 2] * nums[n - 3],ans);
//        ans = Math.max(nums[0] * nums[1] * nums[n - 1],ans);
//        return ans;
        //线性扫描
        // 最小的和第二小的
        int min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        // 最大的、第二大的和第三大的
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE;

        for (int x : nums) {
            if (x < min1) {
                min2 = min1;
                min1 = x;
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

        return Math.max(min1 * min2 * max1, max1 * max2 * max3);
    }

    /**
     * 给你一个由 n 个元素组成的整数数组 nums 和一个整数 k 。
     * 请你找出平均数最大且 长度为 k 的连续子数组，并输出该最大平均数。
     * 任何误差小于 10-5 的答案都将被视为正确答案。
     * 输入：nums = [1,12,-5,-6,50,3], k = 4
     * 输出：12.75
     * 解释：最大平均数 (12-5-6+50)/4 = 51/4 = 12.75
     */
    public static double findMaxAverage(int[] nums, int k) {
//        int l = 0,r = l + k - 1;
//        int n = nums.length;
//        double average = Integer.MIN_VALUE;
//        while (l <= r && r < n){
//            int sum = 0;
//            int o = l;
//            for(int i = 0; i < k; i++){
//                sum += nums[o];
//                o++;
//            }
//            double result = (double) sum / k;
//            average = Math.max(average,result);
//            l++;
//            r = l + k - 1;
//        }
//        return average;
        //滑动窗口
        int sum = 0;
        int n = nums.length;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int maxSum = sum;
        for (int i = k; i < n; i++) {
            sum = sum - nums[i - k] + nums[i];
            maxSum = Math.max(maxSum, sum);
        }
        return 1.0 * maxSum / k;
    }

    /**
     * 集合 s 包含从 1 到 n 的整数。不幸的是，因为数据错误，导致集合里面某一个数字复制了成了集合里面的另外一个数字的值，导致集合 丢失了一个数字 并且 有一个数字重复 。
     * 给定一个数组 nums 代表了集合 S 发生错误后的结果。
     * 请你找出重复出现的整数，再找到丢失的整数，将它们以数组的形式返回。
     * 输入：nums = [1,2,2,4]
     * 输出：[2,3]
     */
    public static int[] findErrorNums(int[] nums) {
//        int n = nums.length;
//        int[] ans = new int[2];
//        Set<Integer> set = new HashSet<>();
//        for(int i = 1; i <= n; i++){
//            if(!set.add(nums[i - 1])){
//                ans[0] = nums[i - 1];
//            }
//        }
//        for(int i = 1; i <= n; i++){
//            if(!set.contains(i)){
//                ans[1] = i;
//                break;
//            }
//        }
//        return ans;
        int[] errorNums = new int[2];
        int n = nums.length;
        Arrays.sort(nums);
        int prev = 0;
        for (int i = 0; i < n; i++) {
            int curr = nums[i];
            if (curr == prev) {
                errorNums[0] = prev;
            } else if (curr - prev > 1) {
                errorNums[1] = prev + 1;
            }
            prev = curr;
        }
        if (nums[n - 1] != n) {
            errorNums[1] = n;
        }
        return errorNums;
    }

    /**
     * 给定一个二叉搜索树 root 和一个目标结果 k，如果二叉搜索树中存在两个元素且它们的和等于给定的目标结果，则返回 true。
     */
    static Set<Integer> set = new HashSet<Integer>();

    public static boolean findTarget(TreeNode root, int k) {
        //所有节点值均不相等
        //中序遍历
//        List<Integer> list = Solution2.inorderTraversal(root);
//        int n = list.size();
//        if(k >= list.get(n - 1) * 2 || k <= list.get(0) * 2){
//            return false;
//        }
//        for(int i = 0; i < n; i++){
//            int left = list.get(i);
//            if(list.contains(k - left) && k - left != left){
//                return true;
//            }
//        }
//        return false;
        if (root == null) {
            return false;
        }
        if (set.contains(k - root.val)) {
            return true;
        }
        set.add(root.val);
        return findTarget(root.left, k) || findTarget(root.right, k);
    }

    /**
     * 在二维平面上，有一个机器人从原点 (0, 0) 开始。给出它的移动顺序，判断这个机器人在完成移动后是否在 (0, 0) 处结束。
     * 移动顺序由字符串 moves 表示。字符 move[i] 表示其第 i 次移动。机器人的有效动作有 R（右），L（左），U（上）和 D（下）。
     * 如果机器人在完成所有动作后返回原点，则返回 true。否则，返回 false。
     * 注意：机器人“面朝”的方向无关紧要。 “R” 将始终使机器人向右移动一次，“L” 将始终向左移动等。此外，假设每次移动机器人的移动幅度相同。
     * 输入: moves = "UD"
     * 输出: true
     * 解释：机器人向上移动一次，然后向下移动一次。所有动作都具有相同的幅度，因此它最终回到它开始的原点。因此，我们返回 true。
     */
    public boolean judgeCircle(String moves) {
//        char[] chars = moves.toCharArray();
//        int[] arr = new int[4];
//        for (char ch : chars){
//            if(ch == 'R'){
//                arr[0]++;
//            }
//            if(ch == 'L'){
//                arr[1]++;
//            }
//            if(ch == 'U'){
//                arr[2]++;
//            }
//            if(ch == 'D'){
//                arr[3]++;
//            }
//        }
//        return arr[0] == arr[1] && arr[2] == arr[3];
        int x = 0, y = 0;
        int length = moves.length();
        for (int i = 0; i < length; i++) {
            char move = moves.charAt(i);
            if (move == 'U') {
                y--;
            } else if (move == 'D') {
                y++;
            } else if (move == 'L') {
                x--;
            } else if (move == 'R') {
                x++;
            }
        }
        return x == 0 && y == 0;
    }

    /**
     * 给定一个非空特殊的二叉树，每个节点都是正数，并且每个节点的子节点数量只能为 2 或 0。如果一个节点有两个子节点的话，那么该节点的值等于两个子节点中较小的一个。
     * 更正式地说，即 root.val = min(root.left.val, root.right.val) 总成立。
     * 给出这样的一个二叉树，你需要输出所有节点中的 第二小的值 。
     * 如果第二小的值不存在的话，输出 -1 。
     */
    int ans;
    int rootvalue;

    public int findSecondMinimumValue(TreeNode root) {
//        Set<Integer> set1 = new HashSet<>();
//        set1.add(root.val);
//        iter(root,set1);
//        List<Integer> list = new ArrayList<>(set1);
//        list.sort((Comparator.comparingInt(o -> o)));
//        return list.size() > 1 ? list.get(1) : -1;
        ans = -1;
        rootvalue = root.val;
        dfs(root);
        return ans;
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        if (ans != -1 && node.val >= ans) {
            return;
        }
        if (node.val > rootvalue) {
            ans = node.val;
        }
        dfs(node.left);
        dfs(node.right);
    }

    private void iter(TreeNode root, Set<Integer> set) {
        if (root == null) {
            return;
        }
        if (set.add(root.val)) {
            return;
        }
        if (root.left != null) {
            iter(root.left, set);
            iter(root.right, set);
        }
    }

    /**
     * 给定一个未经排序的整数数组，找到最长且 连续递增的子序列，并返回该序列的长度。
     * 连续递增的子序列 可以由两个下标 l 和 r（l < r）确定，如果对于每个 l <= i < r，都有 nums[i] < nums[i + 1] ，
     * 那么子序列 [nums[l], nums[l + 1], ..., nums[r - 1], nums[r]] 就是连续递增子序列。
     * 输入：nums = [1,3,5,4,7]
     * 输出：3
     * 解释：最长连续递增序列是 [1,3,5], 长度为3。
     * 尽管 [1,3,5,7] 也是升序的子序列, 但它不是连续的，因为 5 和 7 在原数组里被 4 隔开。
     */
    public static int findLengthOfLCIS(int[] nums) {
        int l = 0, r = 1, ans = 0;
        int n = nums.length;
        while (l < r && r < n) {
            if (nums[r - 1] < nums[r]) {
                ans = Math.max(ans, r - l);
                r++;
            } else {
                l = r;
                r++;
            }
        }
        return ans + 1;
    }

    /**
     * 给你一个字符串 s，最多 可以从中删除一个字符。
     * 请你判断 s 是否能成为回文字符串：如果能，返回 true ；否则，返回 false 。
     * 输入：s = "abca"
     * 输出：true
     * 解释：你可以删除字符 'c'
     */
    public boolean validPalindrome(String s) {
        //先判断本身是否为回文
//        boolean flag = Solution2.isPalindrome(s);
//        if (flag) {
//            return true;
//        }
//        for (int i = 0; i < s.length(); i++) {
//            String a = s.substring(0, i) + s.substring(i + 1);
//            if(Solution2.isPalindrome(a)){
//                return true;
//            }
//        }
//        return false;
        int low = 0, high = s.length() - 1;
        while (low < high) {
            char c1 = s.charAt(low), c2 = s.charAt(high);
            if (c1 == c2) {
                ++low;
                --high;
            } else {
                return validPalindrome(s, low, high - 1) || validPalindrome(s, low + 1, high);
            }
        }
        return true;
    }

    public boolean validPalindrome(String s, int low, int high) {
        for (int i = low, j = high; i < j; ++i, --j) {
            char c1 = s.charAt(i), c2 = s.charAt(j);
            if (c1 != c2) {
                return false;
            }
        }
        return true;
    }

    /**
     * 你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
     * 比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：
     * 整数 x - 表示本回合新获得分数 x
     * "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
     * "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * 请你返回记录中所有得分的总和。
     * 输入：ops = ["5","2","C","D","+"]
     * 输出：30
     * 解释：
     * "5" - 记录加 5 ，记录现在是 [5]
     * "2" - 记录加 2 ，记录现在是 [5, 2]
     * "C" - 使前一次得分的记录无效并将其移除，记录现在是 [5].
     * "D" - 记录加 2 * 5 = 10 ，记录现在是 [5, 10].
     * "+" - 记录加 5 + 10 = 15 ，记录现在是 [5, 10, 15].
     * 所有得分的总和 5 + 10 + 15 = 30
     */
    public int calPoints(String[] operations) {
//        List<Integer> list = new ArrayList<>();
//        for(String str : operations){
//            if(str.equals("C")){
//                list.remove(list.size() - 1);
//            }else if(str.equals("D")){
//                list.add(list.get(list.size() - 1) * 2);
//            }else if(str.equals("+")){
//                list.add(list.get(list.size() - 1) + list.get(list.size() - 2));
//            }else {
//                list.add(Integer.parseInt(str));
//            }
//        }
//        int sum = 0;
//        for(int i : list){
//            sum += i;
//        }
//        return sum;
        int ret = 0;
        List<Integer> points = new ArrayList<Integer>();
        for (String op : operations) {
            int n = points.size();
            switch (op.charAt(0)) {
                case '+':
                    ret += points.get(n - 1) + points.get(n - 2);
                    points.add(points.get(n - 1) + points.get(n - 2));
                    break;
                case 'D':
                    ret += 2 * points.get(n - 1);
                    points.add(2 * points.get(n - 1));
                    break;
                case 'C':
                    ret -= points.get(n - 1);
                    points.remove(n - 1);
                    break;
                default:
                    ret += Integer.parseInt(op);
                    points.add(Integer.parseInt(op));
                    break;
            }
        }
        return ret;
    }

    /**
     * 给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字永不相同。
     * 输入：n = 5
     * 输出：true
     * 解释：5 的二进制表示是：101
     */
    public boolean hasAlternatingBits(int n) {
//        String str = Integer.toBinaryString(n);
//        int len = str.length();
//        int l = 0;
//        while (l < len - 1){
//            if(str.charAt(l) == str.charAt(l + 1)){
//                return false;
//            }
//            l++;
//        }
//        return true;
        //从最低位至最高位，我们用对 2 取模再除以 2 的方法，依次求出输入的二进制表示的每一位，并与前一位进行比较。如果相同，则不符合条件；如果每次比较都不相同，则符合条件。
        int prev = 2;
        while (n != 0) {
            int cur = n % 2;
            if (cur == prev) {
                return false;
            }
            prev = cur;
            n /= 2;
        }
        return true;
    }

    /**
     * 给定一个字符串 s，统计并返回具有相同数量 0 和 1 的非空（连续）子字符串的数量，并且这些子字符串中的所有 0 和所有 1 都是成组连续的。
     * 重复出现（不同位置）的子串也要统计它们出现的次数。
     * 输入：s = "00110011"
     * 输出：6
     * 解释：6 个子串满足具有相同数量的连续 1 和 0 ："0011"、"01"、"1100"、"10"、"0011" 和 "01" 。
     * 注意，一些重复出现的子串（不同位置）要统计它们出现的次数。
     * 另外，"00110011" 不是有效的子串，因为所有的 0（还有 1 ）没有组合在一起。
     */
    public static int countBinarySubstrings(String s) {
        //我们可以将字符串 s 按照 0 和 1 的连续段分组，存在 counts 数组中，例如 s=00111011，可以得到这样的 counts 数组：
        // counts={2,3,1,2}。这里 counts 数组中两个相邻的数一定代表的是两种不同的字符。假设 counts 数组中两个相邻的数字为 u 或者 v，
        // 它们对应着 u 个 0 和 v 个 1，或者 u 个 1 和 v 个 0。它们能组成的满足条件的子串数目为 min{u,v}，即一对相邻的数字对答案的贡献。
        List<Integer> counts = new ArrayList<Integer>();
        int ptr = 0, n = s.length();
        while (ptr < n) {
            char c = s.charAt(ptr);
            int count = 0;
            while (ptr < n && s.charAt(ptr) == c) {
                ++ptr;
                ++count;
            }
            counts.add(count);
        }
        int ans = 0;
        for (int i = 1; i < counts.size(); ++i) {
            ans += Math.min(counts.get(i), counts.get(i - 1));
        }
        return ans;
    }

    /**
     * 给定一个非空且只包含非负数的整数数组 nums，数组的 度 的定义是指数组里任一元素出现频数的最大值。
     * 你的任务是在 nums 中找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。
     * 输入：nums = [1,2,2,3,1]
     * 输出：2
     * 解释：
     * 输入数组的度是 2 ，因为元素 1 和 2 的出现频数最大，均为 2 。
     * 连续子数组里面拥有相同度的有如下所示：
     * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
     * 最短连续子数组 [2, 2] 的长度为 2 ，所以返回 2 。
     */
    public int findShortestSubArray(int[] nums) {
        //记原数组中出现次数最多的数为 x，那么和原数组的度相同的最短连续子数组，必然包含了原数组中的全部 x，且两端恰为 x 第一次出现和最后一次出现的位置。
        //因为符合条件的 x 可能有多个，即多个不同的数在原数组中出现次数相同。所以为了找到这个子数组，我们需要统计每一个数出现的次数,
        //同时还需要统计每一个数第一次出现和最后一次出现的位置。在实际代码中，我们使用哈希表实现该功能，
        //每一个数映射到一个长度为 3 的数组，数组中的三个元素分别代表这个数出现的次数、这个数在原数组中第一次出现的位置和这个数在原数组中最后一次出现的位置.
        // 当我们记录完所有信息后，我们需要遍历该哈希表，找到元素出现次数最多，且前后位置差最小的数。
        Map<Integer, int[]> map = new HashMap<Integer, int[]>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (map.containsKey(nums[i])) {
                map.get(nums[i])[0]++;
                map.get(nums[i])[2] = i;
            } else {
                map.put(nums[i], new int[]{1, i, i});
            }
        }
        int maxNum = 0, minLen = 0;
        for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
            int[] arr = entry.getValue();
            if (maxNum < arr[0]) {
                maxNum = arr[0];
                minLen = arr[2] - arr[1] + 1;
            } else if (maxNum == arr[0]) {
                if (minLen > arr[2] - arr[1] + 1) {
                    minLen = arr[2] - arr[1] + 1;
                }
            }
        }
        return minLen;
    }

    private int finddu(int[] nums) {
        Integer[] array = new Integer[nums.length];
        for (int num : nums) {
            array[num - 1]++;
        }
        Arrays.sort(array, (o1, o2) -> o2 - o1);
        return array[0];
    }

    /**
     * 给定二叉搜索树（BST）的根节点 root 和一个整数值 val。
     * 你需要在 BST 中找到节点值等于 val 的节点。 返回以该节点为根的子树。 如果节点不存在，则返回 null 。
     */
    public TreeNode searchBST(TreeNode root, int val) {
//        if(root == null){
//            return null;
//        }
//        if(root.val == val){
//            return root;
//        }
//        return searchBST(val < root.val ? root.left : root.right, val);
        //迭代
        while (root != null) {
            if (val == root.val) {
                return root;
            }
            root = val < root.val ? root.left : root.right;
        }
        return null;
    }

    /**
     * 有两种特殊字符：
     * 第一种字符可以用一比特 0 表示
     * 第二种字符可以用两比特（10 或 11）表示
     * 给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
     * 输入：bits = [1,1,1,0]
     * 输出：false
     * 解释：唯一的解码方式是将其解析为两比特字符和两比特字符。
     * 所以最后一个字符不是一比特字符。
     */
    public boolean isOneBitCharacter(int[] bits) {
        //我们可以对 bits 数组从左到右遍历。当遍历到 bits[i] 时，如果 bits[i]=0，说明遇到了第一种字符，将 i 的值增加 1；
        // 如果 bits[i]=1，说明遇到了第二种字符，可以跳过 bits[i+1]（注意题目保证 bits 一定以 0 结尾，
        // 所以 bits[i] 一定不是末尾比特，因此 bits[i+1] 必然存在），将 i 的值增加 2。
        int n = bits.length, i = 0;
        while (i < n - 1) {
            i += bits[i] + 1;
        }
        return i == n - 1;
    }

    /**
     * 自除数 是指可以被它包含的每一位数整除的数。
     * 例如，128 是一个 自除数 ，因为 128 % 1 == 0，128 % 2 == 0，128 % 8 == 0。
     * 自除数 不允许包含 0 。
     * 给定两个整数 left 和 right ，返回一个列表，列表的元素是范围 [left, right] 内所有的 自除数 。
     * 输入：left = 1, right = 22
     * 输出：[1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
     */
    public static List<Integer> selfDividingNumbers(int left, int right) {
//        List<Integer> list = new ArrayList<>();
//        for(int i = left; i <= right; i++){
//            list.add(i);
//        }
//        List<Integer> need = new ArrayList<>();
//        for(Integer num : list){
//            if(num.toString().contains("0")){
//                continue;
//            }
//            boolean flag = true;
//            Integer n = num;
//            while (n > 0){
//                int count = n % 10;
//                if(num % count != 0){
//                    flag = false;
//                    break;
//                }
//                n = n / 10;
//            }
//            if (flag){
//                need.add(num);
//            }
//        }
//        return need;
        List<Integer> ans = new ArrayList<Integer>();
        for (int i = left; i <= right; i++) {
            if (isSelfDividing(i)) {
                ans.add(i);
            }
        }
        return ans;
    }

    public static boolean isSelfDividing(int num) {
        int temp = num;
        while (temp > 0) {
            int digit = temp % 10;
            if (digit == 0 || num % digit != 0) {
                return false;
            }
            temp /= 10;
        }
        return true;
    }

    /**
     * 给你一个字符数组 letters，该数组按非递减顺序排序，以及一个字符 target。letters 里至少有两个不同的字符。
     * 返回 letters 中大于 target 的最小的字符。如果不存在这样的字符，则返回 letters 的第一个字符。
     * 输入: letters = ["c", "f", "j"]，target = "a"
     * 输出: "c"
     * 解释：letters 中字典上比 'a' 大的最小字符是 'c'。
     */
    public char nextGreatestLetter(char[] letters, char target) {
        int ans = Integer.MAX_VALUE;
        char result = letters[0];
        for (char ch : letters) {
            if (ch > target) {
                if (ans > ch - target) {
                    ans = ch - target;
                    result = ch;
                }
            }
        }
        return result;
    }

    /**
     * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
     * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
     * 请你计算并返回达到楼梯顶部的最低花费。
     * 输入：cost = [1,100,1,1,1,100,1,1,100,1]
     * 输出：6
     * 解释：你将从下标为 0 的台阶开始。
     * - 支付 1 ，向上爬两个台阶，到达下标为 2 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 4 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 6 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达下标为 7 的台阶。
     * - 支付 1 ，向上爬两个台阶，到达下标为 9 的台阶。
     * - 支付 1 ，向上爬一个台阶，到达楼梯顶部。
     * 总花费为 6 。
     */
    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 0;
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }
        return dp[n];
    }

    /**
     * 给你一个整数数组 nums ，其中总是存在 唯一的 一个最大整数 。
     * 请你找出数组中的最大元素并检查它是否 至少是数组中每个其他数字的两倍 。如果是，则返回 最大元素的下标 ，否则返回 -1 。
     * 输入：nums = [3,6,1,0]
     * 输出：1
     * 解释：6 是最大的整数，对于数组中的其他整数，6 至少是数组中其他元素的两倍。6 的下标是 1 ，所以返回 1 。
     */
    public int dominantIndex(int[] nums) {
//        int n = nums.length;
//        int maxIndex = 0;
//        int max = nums[0];
//        for(int i = 0; i < n; i++){
//            if(max < nums[i]){
//                max = nums[i];
//                maxIndex = i;
//            }
//        }
//        boolean flag = true;
//        for(int num : nums){
//            if(max != num && max < 2 * num){
//                flag = false;
//                break;
//            }
//        }
//        return flag ? maxIndex : -1;
        //找最大值m1和次大值m2，同时记录最大值下标。m1大于等于2倍的m2即可
        int m1 = -1, m2 = -1;
        int index = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > m1) {
                m2 = m1;
                m1 = nums[i];
                index = i;
            } else if (nums[i] > m2) {
                m2 = nums[i];
            }
        }
        return m1 >= m2 * 2 ? index : -1;
    }

    /**
     * 给你一个字符串 licensePlate 和一个字符串数组 words ，请你找出 words 中的 最短补全词 。
     * 补全词 是一个包含 licensePlate 中所有字母的单词。忽略 licensePlate 中的 数字和空格 。不区分大小写。如果某个字母在 licensePlate 中出现不止一次，那么该字母在补全词中的出现次数应当一致或者更多。
     * 例如：licensePlate = "aBc 12c"，那么它的补全词应当包含字母 'a'、'b' （忽略大写）和两个 'c' 。可能的 补全词 有 "abccdef"、"caaacab" 以及 "cbca" 。
     * 请返回 words 中的 最短补全词 。题目数据保证一定存在一个最短补全词。当有多个单词都符合最短补全词的匹配条件时取 words 中 第一个 出现的那个。
     * 输入：licensePlate = "1s3 PSt", words = ["step", "steps", "stripe", "stepple"]
     * 输出："steps"
     * 解释：最短补全词应该包括 "s"、"p"、"s"（忽略大小写） 以及 "t"。
     * "step" 包含 "t"、"p"，但只包含一个 "s"，所以它不符合条件。
     * "steps" 包含 "t"、"p" 和两个 "s"。
     * "stripe" 缺一个 "s"。
     * "stepple" 缺一个 "s"。
     * 因此，"steps" 是唯一一个包含所有字母的单词，也是本例的答案。
     */
    public static String shortestCompletingWord(String licensePlate, String[] words) {
        licensePlate = licensePlate.toLowerCase();
        int n = licensePlate.length();
        int[] num = new int[26];
        for (int i = 0; i < n; i++) {
            char ch = licensePlate.charAt(i);
            if (ch >= 97 && ch <= 122) {
                num[ch - 'a']++;
            }
        }
        int ans = Integer.MAX_VALUE;
        String result = "";
        for (String word : words) {
            int[] nums = Arrays.copyOf(num, num.length);
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                nums[ch - 'a']--;
            }
            boolean flag = true;
            for (int a : nums) {
                if (a > 0) {
                    flag = false;
                    break;
                }
            }
            if (flag && ans > word.length()) {
                ans = word.length();
                result = word;
            }
        }
        return result;
    }

    /**
     * 给你一个 m x n 的矩阵 matrix 。如果这个矩阵是托普利茨矩阵，返回 true ；否则，返回 false 。
     * 如果矩阵上每一条由左上到右下的对角线上的元素都相同，那么这个矩阵是 托普利茨矩阵 。
     */
    public boolean isToeplitzMatrix(int[][] matrix) {
        //根据定义，当且仅当矩阵中每个元素都与其左上角相邻的元素（如果存在）相等时，该矩阵为托普利茨矩阵。
        // 因此，我们遍历该矩阵，将每一个元素和它左上角的元素相比对即可。
        int m = matrix.length, n = matrix[0].length;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] != matrix[i - 1][j - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 给你一个字符串 jewels 代表石头中宝石的类型，另有一个字符串 stones 代表你拥有的石头。 stones 中每个字符代表了一种你拥有的石头的类型，你想知道你拥有的石头中有多少是宝石。
     * 字母区分大小写，因此 "a" 和 "A" 是不同类型的石头。
     * 输入：jewels = "aA", stones = "aAAbbbb"
     * 输出：3
     */
    public int numJewelsInStones(String jewels, String stones) {
//        char[] chars = jewels.toCharArray();
//        char[] stonesChar = stones.toCharArray();
//        int count = 0;
//        for(char ch : chars){
//            for(char schar : stonesChar){
//                if(ch == schar){
//                    count++;
//                }
//            }
//        }
//        return count;

        int jewelsCount = 0;
        Set<Character> jewelsSet = new HashSet<Character>();
        int jewelsLength = jewels.length(), stonesLength = stones.length();
        for (int i = 0; i < jewelsLength; i++) {
            char jewel = jewels.charAt(i);
            jewelsSet.add(jewel);
        }
        for (int i = 0; i < stonesLength; i++) {
            char stone = stones.charAt(i);
            if (jewelsSet.contains(stone)) {
                jewelsCount++;
            }
        }
        return jewelsCount;
    }

    /**
     * 给你一个二叉搜索树的根节点 root ，返回 树中任意两不同节点值之间的最小差值 。
     * 差值是一个正数，其数值等于两值之差的绝对值。
     */
    int pre;

    public int minDiffInBST(TreeNode root) {
        //中序遍历获取List
//        List<Integer> list = Solution2.inorderTraversal(root);
//        int abs = Integer.MAX_VALUE;
//        for (int i = 0; i < list.size() - 1; i++) {
//            if (abs > list.get(i + 1) - list.get(i)) {
//                abs = list.get(i + 1) - list.get(i);
//            }
//        }
//        return abs;
        ans = Integer.MAX_VALUE;
        pre = -1;
        dfs2(root);
        return ans;
    }

    public void dfs2(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs2(root.left);
        if (pre == -1) {
            pre = root.val;
        } else {
            ans = Math.min(ans, root.val - pre);
            pre = root.val;
        }
        dfs2(root.right);
    }

    /**
     * 给定两个字符串, s 和 goal。如果在若干次旋转操作之后，s 能变成 goal ，那么返回 true 。
     * s 的 旋转操作 就是将 s 最左边的字符移动到最右边。
     * 例如, 若 s = 'abcde'，在旋转一次之后结果就是'bcdea' 。
     * 输入: s = "abcde", goal = "cdeab"
     * 输出: true
     */
    int count;

    public static boolean rotateString(String s, String goal) {
//        if(s.equals(goal)){
//            return true;
//        }else {
//            count++;
//        }
//        if(count >= s.length()){
//            return false;
//        }
//        return rotateString(s.substring(1) + s.charAt(0),goal);
        int m = s.length(), n = goal.length();
        if (m != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (s.charAt((i + j) % n) != goal.charAt(j)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

    /**
     * 国际摩尔斯密码定义一种标准编码方式，将每个字母对应于一个由一系列点和短线组成的字符串， 比如:
     * 'a' 对应 ".-" ，
     * 'b' 对应 "-..." ，
     * 'c' 对应 "-.-." ，以此类推。
     * 为了方便，所有 26 个英文字母的摩尔斯密码表如下：
     * [".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."]
     * 给你一个字符串数组 words ，每个单词可以写成每个字母对应摩尔斯密码的组合。
     * 例如，"cab" 可以写成 "-.-..--..." ，(即 "-.-." + ".-" + "-..." 字符串的结合)。我们将这样一个连接过程称作 单词翻译 。
     * 对 words 中所有单词进行单词翻译，返回不同 单词翻译 的数量。
     * 输入: words = ["gin", "zen", "gig", "msg"]
     * 输出: 2
     * 解释:
     * 各单词翻译如下:
     * "gin" -> "--...-."
     * "zen" -> "--...-."
     * "gig" -> "--...--."
     * "msg" -> "--...--."
     * 共有 2 种不同翻译, "--...-." 和 "--...--.".
     */
    public int uniqueMorseRepresentations(String[] words) {
        Set<String> set = new HashSet<>();
        String[] morse = new String[]{".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        for (String word : words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word.length(); i++) {
                sb.append(morse[word.charAt(i) - 'a']);
            }
            set.add(sb.toString());
        }
        return set.size();
    }

    /**
     * 我们要把给定的字符串 S 从左到右写到每一行上，每一行的最大宽度为100个单位，如果我们在写某个字母的时候会使这行超过了100 个单位，那么我们应该把这个字母写到下一行。我们给定了一个数组 widths ，这个数组 widths[0] 代表 'a' 需要的单位， widths[1] 代表 'b' 需要的单位，...， widths[25] 代表 'z' 需要的单位。
     * 现在回答两个问题：至少多少行能放下S，以及最后一行使用的宽度是多少个单位？将你的答案作为长度为2的整数列表返回。
     * 输入:
     * widths = [10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10]
     * S = "abcdefghijklmnopqrstuvwxyz"
     * 输出: [3, 60]
     * 解释:
     * 所有的字符拥有相同的占用单位10。所以书写所有的26个字母，
     * 我们需要2个整行和占用60个单位的一行。
     */
    public int[] numberOfLines(int[] widths, String s) {
        int sum = 0;
        int hang = 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int num = widths[ch - 'a'];
            if(sum + num > 100){
                hang++;
                sum = num;
            }else {
                sum += num;
            }
        }
        int[] result = new int[2];
        result[0] = hang;
        result[1] = sum;
        return result;
    }

    /**
     * 给你一个由 X-Y 平面上的点组成的数组 points ，其中 points[i] = [xi, yi] 。从其中取任意三个不同的点组成三角形，
     * 返回能组成的最大三角形的面积。与真实值误差在 10-5 内的答案将会视为正确答案。
     */
    public double largestTriangleArea(int[][] points) {
        int n = points.length;
        double ret = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    ret = Math.max(ret, triangleArea(points[i][0], points[i][1], points[j][0], points[j][1], points[k][0], points[k][1]));
                }
            }
        }
        return ret;
    }

    public double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return 0.5 * Math.abs(x1 * y2 + x2 * y3 + x3 * y1 - x1 * y3 - x2 * y1 - x3 * y2);
    }

    /**
     * 给定一个段落 (paragraph) 和一个禁用单词列表 (banned)。返回出现次数最多，同时不在禁用列表中的单词。
     * 题目保证至少有一个词不在禁用列表中，而且答案唯一。
     * 禁用列表中的单词用小写字母表示，不含标点符号。段落中的单词不区分大小写。答案都是小写字母。
     * 输入:
     * paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
     * banned = ["hit"]
     * 输出: "ball"
     * 解释:
     * "hit" 出现了3次，但它是一个禁用的单词。
     * "ball" 出现了2次 (同时没有其他单词出现2次)，所以它是段落里出现次数最多的，且不在禁用列表中的单词。
     * 注意，所有这些单词在段落里不区分大小写，标点符号需要忽略（即使是紧挨着单词也忽略， 比如 "ball,"），
     * "hit"不是最终的答案，虽然它出现次数更多，但它在禁用单词列表中。
     */
    public static String mostCommonWord(String paragraph, String[] banned) {
//        paragraph = paragraph.replace(",","").replace(".","").replace("!","")
//                .toLowerCase();
//        String[] array = paragraph.split(" ");
//        Map<String,Integer> map = new HashMap<>();
//        for(String str : array){
//            if(!Arrays.asList(banned).contains(str)){
//                map.put(str,map.getOrDefault(str,0) + 1);
//            }
//        }
//        int value = 0;
//        String result = "";
//        for(Map.Entry<String,Integer> entry : map.entrySet()){
//            if(entry.getValue() > value){
//                value = entry.getValue();
//                result = entry.getKey();
//            }
//        }
//        return result;
        Set<String> bannedSet = new HashSet<String>();
        for (String word : banned) {
            bannedSet.add(word);
        }
        int maxFrequency = 0;
        Map<String, Integer> frequencies = new HashMap<String, Integer>();
        StringBuffer sb = new StringBuffer();
        int length = paragraph.length();
        for (int i = 0; i <= length; i++) {
            if (i < length && Character.isLetter(paragraph.charAt(i))) {
                sb.append(Character.toLowerCase(paragraph.charAt(i)));
            } else if (sb.length() > 0) {
                String word = sb.toString();
                if (!bannedSet.contains(word)) {
                    int frequency = frequencies.getOrDefault(word, 0) + 1;
                    frequencies.put(word, frequency);
                    maxFrequency = Math.max(maxFrequency, frequency);
                }
                sb.setLength(0);
            }
        }
        String mostCommon = "";
        Set<Map.Entry<String, Integer>> entries = frequencies.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String word = entry.getKey();
            int frequency = entry.getValue();
            if (frequency == maxFrequency) {
                mostCommon = word;
                break;
            }
        }
        return mostCommon;
    }

    /**
     * 给你一个字符串 s 和一个字符 c ，且 c 是 s 中出现过的字符。
     * 返回一个整数数组 answer ，其中 answer.length == s.length 且 answer[i] 是 s 中从下标 i 到离它 最近 的字符 c 的 距离 。
     * 两个下标 i 和 j 之间的 距离 为 abs(i - j) ，其中 abs 是绝对值函数。
     * 输入：s = "loveleetcode", c = "e"
     * 输出：[3,2,1,0,1,0,0,1,2,2,1,0]
     * 解释：字符 'e' 出现在下标 3、5、6 和 11 处（下标从 0 开始计数）。
     * 距下标 0 最近的 'e' 出现在下标 3 ，所以距离为 abs(0 - 3) = 3 。
     * 距下标 1 最近的 'e' 出现在下标 3 ，所以距离为 abs(1 - 3) = 2 。
     * 对于下标 4 ，出现在下标 3 和下标 5 处的 'e' 都离它最近，但距离是一样的 abs(4 - 3) == abs(4 - 5) = 1 。
     * 距下标 8 最近的 'e' 出现在下标 6 ，所以距离为 abs(8 - 6) = 2 。
     */
    public static int[] shortestToChar(String s, char c) {
//        int[] ans = new int[s.length()];
//        List<Integer> list = new ArrayList<>();
//        for(int i = 0; i < s.length(); i++){
//            if(s.charAt(i) == c){
//                list.add(i);
//            }
//        }
//        int count = 0;
//        Integer pre = null;
//        Integer next = list.get(count);
//        for(int i = 0; i < s.length(); i++){
//            if(i == next){
//                ans[i] = 0;
//                pre = next;
//                if(count < list.size() - 1){
//                    next = list.get(++count);
//                }
//            }else {
//                if(null == pre){
//                    ans[i] = next - i;
//                }else {
//                    ans[i] = Math.min(i - pre,Math.abs(next - i));
//                }
//            }
//        }
//        return ans;

        int n = s.length();
        int[] ans = new int[n];

        for (int i = 0, idx = -n; i < n; ++i) {
            if (s.charAt(i) == c) {
                idx = i;
            }
            ans[i] = i - idx;
        }

        for (int i = n - 1, idx = 2 * n; i >= 0; --i) {
            if (s.charAt(i) == c) {
                idx = i;
            }
            ans[i] = Math.min(ans[i], idx - i);
        }
        return ans;
    }

    /**
     * 给你一个由若干单词组成的句子 sentence ，单词间由空格分隔。每个单词仅由大写和小写英文字母组成。
     * 请你将句子转换为 “山羊拉丁文（Goat Latin）”（一种类似于 猪拉丁文 - Pig Latin 的虚构语言）。山羊拉丁文的规则如下：
     * 如果单词以元音开头（'a', 'e', 'i', 'o', 'u'），在单词后添加"ma"。
     * 例如，单词 "apple" 变为 "applema" 。
     * 如果单词以辅音字母开头（即，非元音字母），移除第一个字符并将它放到末尾，之后再添加"ma"。
     * 例如，单词 "goat" 变为 "oatgma" 。
     * 根据单词在句子中的索引，在单词最后添加与索引相同数量的字母'a'，索引从 1 开始。
     * 例如，在第一个单词后添加 "a" ，在第二个单词后添加 "aa" ，以此类推。
     * 输入：sentence = "I speak Goat Latin"
     * 输出："Imaa peaksmaaa oatGmaaaa atinLmaaaaa"
     */
    public static String toGoatLatin(String sentence) {
        String[] strArray = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < strArray.length; i++){
            String str = strArray[i];
            char ch = str.charAt(0);
            if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u'
            || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U'){
                sb.append(str).append("ma");
                for(int j = 0; j < i + 1; j++){
                    sb.append("a");
                }
                sb.append(" ");
            }else {
                sb.append(str.substring(1)).append(ch).append("ma");
                for(int j = 0; j < i + 1; j++){
                    sb.append("a");
                }
                sb.append(" ");
            }
        }
        return sb.substring(0,sb.length() - 1);
    }

    /**
     * 在一个由小写字母构成的字符串 s 中，包含由一些连续的相同字符所构成的分组。
     * 例如，在字符串 s = "abbxxxxzyy" 中，就含有 "a", "bb", "xxxx", "z" 和 "yy" 这样的一些分组。
     * 分组可以用区间 [start, end] 表示，其中 start 和 end 分别表示该分组的起始和终止位置的下标。上例中的 "xxxx" 分组用区间表示为 [3,6] 。
     * 我们称所有包含大于或等于三个连续字符的分组为 较大分组 。
     * 找到每一个 较大分组 的区间，按起始位置下标递增顺序排序后，返回结果。
     * 输入：s = "abcdddeeeeaabbbcd"
     * 输出：[[3,5],[6,9],[12,14]]
     * 解释：较大分组为 "ddd", "eeee" 和 "bbb"
     */
    public static List<List<Integer>> largeGroupPositions(String s) {
        List<List<Integer>> list = new ArrayList<>();
        int left = 0,right = 1,n = s.length();
        while (right < n){
            List<Integer> innerList = new ArrayList<>();
            char lch = s.charAt(left);
            char rch = s.charAt(right);
            if(lch == rch){
                if(right == n - 1 && right - left >= 2){
                    innerList.add(left);
                    innerList.add(right);
                }
                right++;
            }else {
                if(right - left >= 3){
                    innerList.add(left);
                    innerList.add(right - 1);
                }
                left = right;
                right++;
            }
            if(innerList.size() > 0){
                list.add(innerList);
            }

        }

        return list;
    }

    /**
     * 给定一个 n x n 的二进制矩阵 image ，先 水平 翻转图像，然后 反转 图像并返回 结果 。
     * 水平翻转图片就是将图片的每一行都进行翻转，即逆序。
     * 例如，水平翻转 [1,1,0] 的结果是 [0,1,1]。
     * 反转图片的意思是图片中的 0 全部被 1 替换， 1 全部被 0 替换。
     * 例如，反转 [0,1,1] 的结果是 [1,0,0]。
     * 输入：image = [[1,1,0],[1,0,1],[0,0,0]]
     * 输出：[[1,0,0],[0,1,0],[1,1,1]]
     * 解释：首先翻转每一行: [[0,1,1],[1,0,1],[0,0,0]]；
     *      然后反转图片: [[1,0,0],[0,1,0],[1,1,1]]
     */
    public int[][] flipAndInvertImage(int[][] image) {
        int n = image.length;
        //翻转
        for(int[] hang : image){
            int left = 0,right = n - 1;
            while (left < right){
                int tmp = hang[left];
                hang[left] = hang[right];
                hang[right] = tmp;
                left++;
                right--;
            }
        }
        //反转
        for(int[] hang : image){
            for(int i = 0; i < hang.length; i++){
                if(hang[i] == 0){
                    hang[i] = 1;
                }else {
                    hang[i] = 0;
                }
            }
        }
        return image;
    }

    /**
     * 矩形以列表 [x1, y1, x2, y2] 的形式表示，其中 (x1, y1) 为左下角的坐标，(x2, y2) 是右上角的坐标。矩形的上下边平行于 x 轴，左右边平行于 y 轴。
     * 如果相交的面积为 正 ，则称两矩形重叠。需要明确的是，只在角或边接触的两个矩形不构成重叠。
     * 给出两个矩形 rec1 和 rec2 。如果它们重叠，返回 true；否则，返回 false 。
     * 输入：rec1 = [0,0,2,2], rec2 = [1,1,3,3]
     * 输出：true
     */
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        //面积为0，则不会重叠
        if (rec1[0] == rec1[2] || rec1[1] == rec1[3] || rec2[0] == rec2[2] || rec2[1] == rec2[3]) {
            return false;
        }
        //不考虑不规则的斜过来的矩形
        return !(rec1[2] <= rec2[0] ||   // left
                rec1[3] <= rec2[1] ||   // bottom
                rec1[0] >= rec2[2] ||   // right
                rec1[1] >= rec2[3]);    // top
    }

    /**
     * 给定 s 和 t 两个字符串，当它们分别被输入到空白的文本编辑器后，如果两者相等，返回 true 。# 代表退格字符。
     * 注意：如果对空文本输入退格字符，文本继续为空。
     * 输入：s = "ab#c", t = "ad#c"
     * 输出：true
     * 解释：s 和 t 都会变成 "ac"。
     */
    public static boolean backspaceCompare(String s, String t) {
//        return countStr(s).equals(countStr(t));
        //倒序遍历
        int i = s.length() - 1, j = t.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) {
            while (i >= 0) {
                if (s.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else {
                    break;
                }
            }
            while (j >= 0) {
                if (t.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else {
                    break;
                }
            }
            if (i >= 0 && j >= 0) {
                if (s.charAt(i) != t.charAt(j)) {
                    return false;
                }
            } else {
                if (i >= 0 || j >= 0) {
                    return false;
                }
            }
            i--;
            j--;
        }
        return true;
    }

    private String countStr(String s){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            if(Character.isLetter(s.charAt(i))){
                sb.append(s.charAt(i));
            }else {
                if(sb.length() >= 1){
                    sb.delete(sb.length() - 1,sb.length());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 给你两个字符串 s 和 goal ，只要我们可以通过交换 s 中的两个字母得到与 goal 相等的结果，就返回 true ；否则返回 false 。
     * 交换字母的定义是：取两个下标 i 和 j （下标从 0 开始）且满足 i != j ，接着交换 s[i] 和 s[j] 处的字符。
     * 例如，在 "abcd" 中交换下标 0 和下标 2 的元素可以生成 "cbad" 。
     * 输入：s = "ab", goal = "ba"
     * 输出：true
     * 解释：你可以交换 s[0] = 'a' 和 s[1] = 'b' 生成 "ba"，此时 s 和 goal 相等。
     */
    public boolean buddyStrings(String s, String goal) {
        if(s.length() != goal.length()){
            return false;
        }
        if(s.equals(goal)){
            Set<Character> set = new HashSet<>();
            for(int i = 0; i < s.length(); i++){
                if(!set.add(s.charAt(i))){
                    return true;
                }
            }
            return false;
        }
        //找出两个不等的字符，尝试交换。看是否等于goal
        int first = -1, second = -1;
        for (int i = 0; i < goal.length(); i++) {
            if (s.charAt(i) != goal.charAt(i)) {
                if (first == -1)
                    first = i;
                else if (second == -1)
                    second = i;
                else
                    return false;
            }
        }

        return (second != -1 && s.charAt(first) == goal.charAt(second) &&
                s.charAt(second) == goal.charAt(first));

//        int count = 0;
//        Integer left = null,right = null;
//        for(int i = 0; i < s.length(); i++){
//            if(count > 2){
//                return false;
//            }
//            if(s.charAt(i) != goal.charAt(i)){
//                count++;
//                if(left == null){
//                    left = i;
//                }
//                right = i;
//            }
//        }
//        if(count != 2){
//            return false;
//        }
//        if(right == s.length() - 1){
//            s = s.substring(0,left) + s.charAt(right) + s.substring(left + 1,right) + s.charAt(left);
//        }else {
//            s = s.substring(0,left) + s.charAt(right) + s.substring(left + 1,right) + s.charAt(left) + s.substring(right + 1);
//        }
//        return s.equals(goal);
    }

    /**
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     * 注意，一开始你手头没有任何零钱。
     * 给你一个整数数组 bills ，其中 bills[i] 是第 i 位顾客付的账。如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     * 输入：bills = [5,5,5,10,20]
     * 输出：true
     */
    public boolean lemonadeChange(int[] bills) {
        if(bills[0] != 5){
            return false;
        }
        int sum5 = 0,sum10 = 0;
        for(int num : bills){
            if(num == 5){
                sum5++;
            }else if(num == 10){
                //找零5块
                if(sum5 <= 0){
                    return false;
                }
                sum5--;
                sum10++;
            }else {
                //优先找10块的
                if(sum10 > 0 && sum5 > 0){
                    sum10--;
                    sum5--;
                }else if(sum5 >= 3){
                    sum5 = sum5 - 3;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 给你一个二维整数数组 matrix， 返回 matrix 的 转置矩阵 。
     * 矩阵的 转置 是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[[1,4,7],[2,5,8],[3,6,9]]
     */
    public int[][] transpose(int[][] matrix) {
        int row = matrix.length;
        int rank = matrix[0].length;
        int[][] result = new int[rank][row];
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[i].length; j++){
                result[j][i] = matrix[i][j];
            }
        }
        return result;
    }

    /**
     * 给定一个正整数 n，找到并返回 n 的二进制表示中两个 相邻 1 之间的 最长距离 。如果不存在两个相邻的 1，返回 0 。
     * 如果只有 0 将两个 1 分隔开（可能不存在 0 ），则认为这两个 1 彼此 相邻 。两个 1 之间的距离是它们的二进制表示中位置的绝对差。例如，"1001" 中的两个 1 的距离为 3 。
     * 输入：n = 22
     * 输出：2
     * 解释：22 的二进制是 "10110" 。
     * 在 22 的二进制表示中，有三个 1，组成两对相邻的 1 。
     * 第一对相邻的 1 中，两个 1 之间的距离为 2 。
     * 第二对相邻的 1 中，两个 1 之间的距离为 1 。
     * 答案取两个距离之中最大的，也就是 2 。
     */
    public int binaryGap(int n) {
//        String str = Integer.toBinaryString(n);
//        int left = 0,right = 1,len = str.length();
//        int sum = 0;
//        while (right < len){
//            if(str.charAt(left) == '1'){
//                if(str.charAt(right) == '1'){
//                    sum = Math.max(sum,right - left);
//                    left = right;
//                    right++;
//                }else {
//                    right++;
//                }
//            }
//        }
//        return sum;
        int last = -1, ans = 0;
        for (int i = 0; n != 0; ++i) {
            if ((n & 1) == 1) {
                if (last != -1) {
                    ans = Math.max(ans, i - last);
                }
                last = i;
            }
            n >>= 1;
        }
        return ans;
    }

    /**
     * 请考虑一棵二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列 。
     * 举个例子，如上图所示，给定一棵叶值序列为 (6, 7, 4, 9, 8) 的树。
     * 如果有两棵二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
     * 如果给定的两个根结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        dfs3(root1,list1);
        dfs3(root2,list2);
        return list1.equals(list2);
//        if(list1.size() != list2.size()){
//            return false;
//        }
//        for(int i = 0; i < list1.size(); i++){
//            if(!Objects.equals(list1.get(i), list2.get(i))){
//                return false;
//            }
//        }
//        return true;
    }

    private void dfs3(TreeNode root,List<Integer> list){
        if(root == null){
            return;
        }
        dfs3(root.left,list);
        if(root.left == null && root.right == null){
            list.add(root.val);
        }
        dfs3(root.right,list);
    }

    public static void main(String[] args) {
        String a = "abcde";
        System.out.println(a.substring(1,2));
//        System.out.println(backspaceCompare("a##c","#a#c"));
//        System.out.println(largeGroupPositions("aaa"));
//        System.out.println(toGoatLatin("I speak Goat Latin"));
//        System.out.println(shortestToChar("aaba",'b'));
//        System.out.println(mostCommonWord("Bob hit a ball, the hit BALL flew far after it was hit.",new String[]{"hit"}));
//        System.out.println(rotateString("abcde", "cdeab"));
//        System.out.println(shortestCompletingWord("1s3 PSt", new String[]{"step", "steps", "stripe", "stepple"}));
//        System.out.println(minCostClimbingStairs(new int[]{1,100,1,1,1,100,1,1,100,1}));
//        System.out.println(selfDividingNumbers(1,22));
//        KthLargest kthLargest = new KthLargest(3,new int[]{4, 5, 8, 2});
//        kthLargest.add(3);   // return 4
//        kthLargest.add(5);   // return 5
//        kthLargest.add(10);  // return 5
//        kthLargest.add(9);   // return 8
//        kthLargest.add(4);   // return 8
//        System.out.println(countBinarySubstrings("00110011"));
//        System.out.println(findLengthOfLCIS(new int[]{1, 3, 5, 4, 7}));
//        System.out.println(findTarget(new TreeNode(334, new TreeNode(277, null, new TreeNode(285)), new TreeNode(507, null, new TreeNode(678))), 1014));
//        System.out.println(findErrorNums(new int[]{1,5,3,2,2,7,6,4,8,9}));
//        System.out.println(findMaxAverage(new int[]{-1},1));
        //        System.out.println(maxCount(3, 3, new int[][]{{2, 2}, {3, 3}}));
//        System.out.println(findLHS(new int[]{1,2,2,1}));
//        postorder(null);
    }
}
