package com.example.demo;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.example.demo.entity.TreeNode;
import com.example.demo.entity.UnionFind;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class Solution7 {

    static Set<Integer> countSet = new HashSet<>();

    /**
     * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。当你位于下标 i 处时，你可以跳到 i + arr[i] 或者 i - arr[i]。
     * 请你判断自己是否能够跳到对应元素值为 0 的 任一 下标处。
     * 注意，不管是什么情况下，你都无法跳到数组之外。
     * 输入：arr = [4,2,3,0,3,1,2], start = 5
     * 输出：true
     * 解释：
     * 到达值为 0 的下标 3 有以下可能方案：
     * 下标 5 -> 下标 4 -> 下标 1 -> 下标 3
     * 下标 5 -> 下标 6 -> 下标 4 -> 下标 1 -> 下标 3
     */
    public static boolean canReach(int[] arr, int start) {
        if (start >= arr.length || start < 0) {
            return false;
        }
        if (0 == arr[start]) {
            return true;
        }
        if (!countSet.add(start)) {
            return false;
        }
        return canReach(arr, start + arr[start]) || canReach(arr, start - arr[start]);

//        boolean[] visit = new boolean[arr.length];
//        return assist(visit, arr, start);
    }

    public static boolean assist(boolean[] visit, int[] arr, int start) {
        //不满足条件中断递归，返回false
        if (start >= arr.length || start < 0) return false;
        //找到满足条件的结果直接结束
        if (arr[start] == 0) return true;
        //[3,0,2,1,2]
        //2
        //此段为了解决元素跳跃重复的问题
        if (visit[start]) {
            return false;
        } else {
            visit[start] = true;
        }
        return assist(visit, arr, start + arr[start]) || assist(visit, arr, start - arr[start]);
    }

    /**
     * 给你 root1 和 root2 这两棵二叉搜索树。请你返回一个列表，其中包含 两棵树 中的所有整数并按 升序 排序。.
     * 输入：root1 = [2,1,4], root2 = [1,0,3]
     * 输出：[0,1,1,2,3,4]
     */
    public static List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list = Solution2.inorderTraversal(root1);
        list.addAll(Solution2.inorderTraversal(root2));
        list.sort(Comparator.comparingInt(e -> e));
        return list;
    }

    /**
     * 有 n 个人，每个人都有一个  0 到 n-1 的唯一 id 。
     * <p>
     * 给你数组 watchedVideos  和 friends ，其中 watchedVideos[i]  和 friends[i] 分别表示 id = i 的人观看过的视频列表和他的好友列表。
     * <p>
     * Level 1 的视频包含所有你好友观看过的视频，level 2 的视频包含所有你好友的好友观看过的视频，以此类推。一般的，Level 为 k 的视频包含所有从你出发，最短距离为 k 的好友观看过的视频。
     * <p>
     * 给定你的 id  和一个 level 值，请你找出所有指定 level 的视频，并将它们按观看频率升序返回。如果有频率相同的视频，请将它们按字母顺序从小到大排列。
     */
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        /*
        我们可以使用广度优先搜索的方法，从编号为 id 的节点开始搜索，得到从 id 到其余所有节点的最短路径，则所有到 id 的最短路径为 k 的节点都是 Level k 的好友。
        具体地，我们使用一个队列帮助我们进行搜索。队列中初始只有编号为 id 的节点。我们进行 k 轮搜索，在第 i 轮搜索开始前，队列中的节点是所有 Level i-1 的好友，
        而我们希望从这些节点得到所有 Level i 的好友。我们依次取出这些 Level i-1 的节点，设当前取出的节点为 x，我们遍历 x 的所有好友 friends[x]，
        如果某个好友未被访问过，那么我们就能知道其为 Level i 的好友，可以将其加入队列。在 k 轮搜索结束之后，队列中就包含了所有 Level k 的好友。
         */
        /*
        我们使用一个哈希映射（HashMap）来统计 Level k 的好友观看过的视频。对于哈希映射中的每个键值对，键表示视频的名称，值表示视频被好友观看过的次数。
        对于队列中的每个节点 x，我们将 watchedVideos[x] 中的所有视频依次加入哈希映射，就可以完成这一步骤。
         */
        /*
        在统计完成之后，我们将哈希映射中的所有键值对存储进数组中，并将它们按照观看次数为第一关键字、视频名称为第二关键字生序排序，即可得到最终的结果。
         */
        int n = friends.length;
        boolean[] visited = new boolean[n];
        Map<String, Integer> map = new HashMap<>();

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{id, 0});
        visited[id] = true;
        while (queue.size() > 0) {
            int[] poi = queue.poll();
            if (poi[1] < level) {
                for (int friend : friends[poi[0]]) {
                    if (visited[friend]) continue;
                    visited[friend] = true;
                    queue.offer(new int[]{friend, poi[1] + 1});
                }
            } else {
                List<String> videos = watchedVideos.get(poi[0]);
                for (String video : videos) {
                    int count = map.getOrDefault(video, 0) + 1;
                    map.put(video, count);
                }
            }
        }

        List<String> res = new ArrayList<>(map.keySet());
        res = res.stream().sorted((x, y) -> {
            if (Objects.equals(map.get(x), map.get(y))) {
                return x.compareTo(y);
            } else {
                return map.get(x) - map.get(y);
            }
        }).collect(Collectors.toList());

        return res;
    }

    /**
     * 有一个正整数数组 arr，现给你一个对应的查询数组 queries，其中 queries[i] = [Li, Ri]。
     * <p>
     * 对于每个查询 i，请你计算从 Li 到 Ri 的 XOR 值（即 arr[Li] xor arr[Li+1] xor ... xor arr[Ri]）作为本次查询的结果。
     * <p>
     * 并返回一个包含给定查询 queries 所有结果的数组。
     * 输入：arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
     * 输出：[2,7,14,8]
     * 解释：
     * 数组中元素的二进制表示形式是：
     * 1 = 0001
     * 3 = 0011
     * 4 = 0100
     * 8 = 1000
     * 查询的 XOR 值为：
     * [0,1] = 1 xor 3 = 2
     * [1,2] = 3 xor 4 = 7
     * [0,3] = 1 xor 3 xor 4 xor 8 = 14
     * [3,3] = 8
     */
    public static int[] xorQueries(int[] arr, int[][] queries) {
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int result = 0;
            int[] queriesArr = queries[i];
            int l = queriesArr[0];
            int r = queriesArr[1];
            for (int j = l; j <= r; j++) {
                result ^= arr[j];
            }
            ans[i] = result;
        }
        return ans;
    }

    static int sumAns = 0;

    /**
     * 给你一棵二叉树，请你返回满足以下条件的所有节点的值之和：
     * 该节点的祖父节点的值为偶数。（一个节点的祖父节点是指该节点的父节点的父节点。）
     * 如果不存在祖父节点值为偶数的节点，那么返回 0 。
     */
    public static int sumEvenGrandparent(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.val % 2 == 0 && null != root.left && (root.left.left != null || root.left.right != null)) {
            sumAns += (root.left.left != null ? root.left.left.val : 0) + (root.left.right != null ? root.left.right.val : 0);
        }
        if (root.val % 2 == 0 && null != root.right && (root.right.left != null || root.right.right != null)) {
            sumAns += (root.right.left != null ? root.right.left.val : 0) + (root.right.right != null ? root.right.right.val : 0);
        }
        sumEvenGrandparent(root.left);
        sumEvenGrandparent(root.right);
        return sumAns;
    }

    /**
     * 给你三个正整数 a、b 和 c。
     * <p>
     * 你可以对 a 和 b 的二进制表示进行位翻转操作，返回能够使按位或运算   a OR b == c  成立的最小翻转次数。
     * <p>
     * 「位翻转操作」是指将一个数的二进制表示任何单个位上的 1 变成 0 或者 0 变成 1 。
     * 输入：a = 2, b = 6, c = 5
     * 输出：3
     * 解释：翻转后 a = 1 , b = 4 , c = 5 使得 a OR b == c
     */
    public static int minFlips(int a, int b, int c) {
     /*
        逐位分析+分类讨论
        由按位或的性质可以:
        0 | 0 = 0
        0 | 1 = 1
        1 | 1 = 1
        1.当c该位为0时，必须将a与b的该位均翻转为0，翻转次数为1的个数
        2.当c该位为1时，当且仅当a与b该位均为0时需要翻转1次，其余情况均不需要翻转
         */
        int res = 0;
        for (int i = 0; i < 30; i++) {
            int x = (a >> i) & 1, y = (b >> i) & 1, z = (c >> i) & 1;
            if (z == 0) {
                res += x + y;
            } else {
                res += (x | y) ^ 1;
            }
        }
        return res;
    }

    /**
     * 用以太网线缆将 n 台计算机连接成一个网络，计算机的编号从 0 到 n-1。线缆用 connections 表示，其中 connections[i] = [a, b] 连接了计算机 a 和 b。
     * <p>
     * 网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。
     * <p>
     * 给你这个计算机网络的初始布线 connections，你可以拔开任意两台直连计算机之间的线缆，并用它连接一对未直连的计算机。请你计算并返回使所有计算机都连通所需的最少操作次数。如果不可能，则返回 -1 。
     * 输入：n = 4, connections = [[0,1],[0,2],[1,2]]
     * 输出：1
     * 解释：拔下计算机 1 和 2 之间的线缆，并将它插到计算机 1 和 3 上。
     */
    public static int makeConnected(int n, int[][] connections) {
//        int len = connections.length;
//        if(len < n - 1){
//            return -1;
//        }
//        int count = 0;
//        Set<Integer> set = new HashSet<>();
//        for(int i = 0; i < connections.length; i++){
//            for(int j = 0; j < connections[i].length; j++){
//                int num = connections[i][j];
//                if(set.add(num)){
//                    count++;
//                }
//            }
//        }
//        return n - count;
        //样例中，有两个连通分量 {0,1,2} 和 {3}
        /*
        如果一个节点数为 m 的连通分量中的边数超过 m−1，就一定可以找到一条多余的边，且移除这条边之后，连通性保持不变。
        此时，我们就可以用这条边来连接两个连通分量，使得图中连通分量的个数减少 1。
         */
        /*
        因此我们可以设计一个迭代的过程：每次在图中找出一条多余的边，将其断开，并连接图中的两个连通分量。
        将这个过程重复 k−1 次，最终就可以使得整个图连通
         */
        /*
        我们如何保证一定能找出「一条」多余的边呢？我们需要证明的是，在任意时刻，如果图中有 k′ 个连通分量且 k′>1，
        即整个图还没有完全连通，那么一定存在一个连通分量，使得其有一条多余的边
         */
        if (connections.length < n - 1) {
            return -1;
        }

        UnionFind uf = new UnionFind(n);
        for (int[] conn : connections) {
            uf.unite(conn[0], conn[1]);
        }

        return uf.setCount - 1;
    }

    boolean[] vis;//用于标记访问过的节点
    int edge;//每个连通分量中边的数量

    public int makeConnected2(int n, int[][] connections) {
        //初始化标记数组
        vis = new boolean[n];
        //邻接表法存储图
        ArrayList[] tb = new ArrayList[n];
        //初始化邻接表
        Arrays.setAll(tb, e -> new ArrayList<>());
        for (int i = 0; i < connections.length; i++) {
            int x = connections[i][0];
            int y = connections[i][1];
            tb[x].add(y);
            tb[y].add(x);
        }
        //g_cnt:连通分量的数量
        int g_cnt = 0;
        //r_e:多余的边数
        int r_e = 0;
        //遍及每个连通分量
        for (int i = 0; i < tb.length; i++) {
            //初始化每个连通分量的边数
            edge = 0;
            //n_cnt:连通分量的节点数
            int n_cnt = 0;
            //当前节点没访问过，dfs一次找到一个连通分量
            if (!vis[i]) {
                //连通分量数加1
                g_cnt += 1;
                //dfs计算一个连通分量的节点数
                n_cnt = dfs(tb, i);
                //一个连通分量所有的边减去n_cnt个节点连通最少的边数即为多余边数，累加每一个连通分量多余的边
                r_e += edge - n_cnt + 1;
            }
        }
        //剩余边足够将其他连通分量相连接
        return r_e >= g_cnt - 1 ? g_cnt - 1 : -1;
    }

    //深度优先搜索
    int dfs(List<Integer>[] a, int x) {
        vis[x] = true;//表示访问过当前节点
        int n_cnt = 1;//初始节点为1
        for (int e : a[x]) {
            //累加一个连通分量的边数
            if (e > x) {
                edge += 1;
            }
            if (!vis[e]) {
                n_cnt += dfs(a, e);
            }
        }
        return n_cnt;
    }

    /**
     * 给定用户密码输入流 input，输入流中字符 '<' 表示退格，可以清除前一个输入的字符，请你编写程序，输出最终得到的密码字符，并判断密码是否满足如下的密码安全要求。
     * <p>
     * 密码安全要求如下：
     * <p>
     * 密码长度 ≥ 8；
     * 密码至少需要包含 1 个大写字母；
     * 密码至少需要包含 1 个小写字母；
     * 密码至少需要包含 1 个数字；
     * 密码至少需要包含 1 个字母和数字以外的非空白特殊字符；
     * 注意空串退格后仍然为空串，且用户输入的字符串不包含 '<' 字符和空白字符。
     * ABC<c89%000<
     * ABc89%00,true
     */
    public static String custom1(String str) {
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        LinkedList<Character> stack = new LinkedList<>();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char character = str.charAt(i);
            if (character == '<') {
                if (!stack.isEmpty()) {
                    stack.removeLast();
                }
            } else {
                stack.addLast(character);
            }
        }
        int number = 0;
        int upper = 0;
        int lower = 0;
        int other = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : stack) {
            stringBuilder.append(character);
            if (character >= 'a' && character <= 'z') {
                lower++;
            } else if (character >= 'A' && character <= 'Z') {
                upper++;
            } else if (character >= '0' && character <= '9') {
                number++;
            } else {
                other++;
            }
        }
        if (number >= 1 && upper >= 1 && lower >= 1 && other >= 1) {
            stringBuilder.append(",true");
        } else {
            stringBuilder.append(",false");
        }
        return stringBuilder.toString();
    }

    /**
     * 从前有个村庄，村民们喜欢在各种田地上插上小旗子，旗子上标识了各种不同的数字。
     * 某天集体村民决定将覆盖相同数字的最小矩阵形的土地分配给村里做出巨大贡献的村民，请问此次分配土地，做出贡献的村民种最大会分配多大面积?
     * 输入描述
     * 第一行输入 m 和 n，
     * <p>
     * m 代表村子的土地的长
     * n 代表土地的宽
     * 第二行开始输入地图上的具体标识
     * 输入	33
     * 101
     * 000
     * 010
     * 输出	9
     * 说明	土地上的旗子为1，其坐标分别
     * 为(0,0)，(2,1)以及(0,2)，为了
     * 覆盖所有旗子，矩阵需要覆盖
     * 的横坐标为0和2，纵坐标为0和
     * 2，所以面积为9，
     * 即（2-0+1）*（2-0+1）= 9
     */
    public static int insertFlag() {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt(); // 长（行数）
        int cols = scanner.nextInt(); // 宽（列数）
        int[] array = new int[9];
        int[][] numArray = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int number = scanner.nextInt();
                numArray[i][j] = number;
                if (number > 0) {
                    array[number - 1]++;
                }

            }
        }
        int min = Integer.MAX_VALUE;
        for (int index = 0; index < array.length; index++) {
            if (array[index] > 1) {
                min = index + 1;
                break;
            }
        }
        if (min > 9) {
            return 1;
        }
        int maxX = 0;
        int minX = rows;
        int maxY = 0;
        int minY = cols;
        //找数字为min的最大最小横轴和最大最小纵轴
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int num = numArray[i][j];
                if (num == min) {
                    maxX = Math.max(maxX, i);
                    minX = Math.min(minX, i);
                    maxY = Math.max(maxY, j);
                    minY = Math.min(minY, j);
                }
            }
        }
        return (maxX - minX + 1) * (maxY - minY + 1);
    }

    /**
     * 在一个大型体育场内举办了一场大型活动，由于疫情防控的需要，要求每位观众的必须间隔至少一个空位才允许落座。
     * 现在给出一排观众座位分布图，座位中存在已落座的观众，请计算出，在不移动现有观众座位的情况下，最多还能坐下多少名观众。
     * 一个数组，用来标识某一排座位中，每个座位是否已经坐人。0表示该座位没有坐人，1表示该座位已经坐人。
     * 1 ≤ 数组长度 ≤ 10000
     * 输入	10001
     * 输出	1
     */
    public static int findSeat(String str) {
        //index的值为0时，则index+1不能为1
        int ans = 0;
        char[] chars = str.toCharArray();
        for (int index = 0; index < chars.length; index++) {
            char character = chars[index];
            if (index >= 1 && index < chars.length - 1 && character == '0' && str.charAt(index + 1) != '1' && str.charAt(index - 1) != '1') {
                ans++;
                chars[index] = 1;
            }
        }
        return ans;
    }

    /**
     * 输入描述
     * 第 1 行输入两个整数，学生人数 n 和科目数量 m。
     * <p>
     * 0 < n < 100
     * 0 < m < 10
     * 第 2 行输入 m 个科目名称，彼此之间用空格隔开。
     * <p>
     * 科目名称只由英文字母构成，单个长度不超过10个字符。
     * 科目的出现顺序和后续输入的学生成绩一一对应。
     * 不会出现重复的科目名称。
     * 第 3 行开始的 n 行，每行包含一个学生的姓名和该生 m 个科目的成绩（空格隔开）
     * <p>
     * 学生不会重名。
     * 学生姓名只由英文字母构成，长度不超过10个字符。
     * 成绩是0\~100的整数，依次对应第2行种输入的科目。
     * 第n+2行，输入用作排名的科目名称。若科目不存在，则按总分进行排序。
     * 输出描述
     * 输出一行，按成绩排序后的学生名字，空格隔开。成绩相同的按照学生姓名字典顺序排序。
     * 输 入	3 2
     * yuwen shuxue
     * fangfang 95 90
     * xiaohua 88 95
     * minmin 100 82
     * shuxue
     * 输 出	xiaohua fangfang minmin
     */
    public static void rank() {
        Scanner scanner = new Scanner(System.in);

        // 学生人数, 科目数量
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // key是科目名称，val是科目出现顺序的序号
        Map<String, Integer> subjectRankIdx = new HashMap<>();

        // 输入的m个科目
        String[] subjects = scanner.nextLine().split(" ");
        for (int i = 0; i < m; i++) {
            subjectRankIdx.put(subjects[i], i);
        }

        List<Student> students = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] tmp = scanner.nextLine().split(" ");

            // 学生姓名
            String name = tmp[0];
            // 学生给定科目的分数（m个）
            int[] scores = new int[m];
            for (int j = 0; j < m; j++) {
                scores[j] = Integer.parseInt(tmp[j + 1]);
            }

            // 排名要素，0~m-1索引上的是给定科目成绩，m索引上的是总分
            int[] rank = new int[m + 1];
            System.arraycopy(scores, 0, rank, 0, m);
            int totalScore = Arrays.stream(scores).sum();
            rank[m] = totalScore;

            students.add(new Student(name, rank));
        }

        // 用作排名的科目名称
        String subject = scanner.nextLine();

        // 用作排名的科目名称的排名要素序号, 如果用作排名的科目名称不存在，则按总分排名，对应序号是m
        int rankIdx = subjectRankIdx.getOrDefault(subject, m);

        // 按照排名要素排名，如果排名要素值相同，则按照学生姓名字典序排序
        students.sort((s1, s2) -> {
            if (s1.rank[rankIdx] != s2.rank[rankIdx]) {
                return Integer.compare(s2.rank[rankIdx], s1.rank[rankIdx]);
            } else {
                return s1.name.compareTo(s2.name);
            }
        });

        // Print sorted student names
        for (Student student : students) {
            System.out.print(student.name + " ");
        }

    }

    /**
     * 题目描述
     * 现代计算机系统中通常存在多级的存储设备，针对海量 workload 的优化的一种思路是将热点内存页优先放到快速存储层级，这就需要对内存页进行冷热标记。
     * 一种典型的方案是基于内存页的访问频次进行标记，如果统计窗口内访问次数大于等于设定阈值，则认为是热内存页，否则是冷内存页。
     * 对于统计窗口内跟踪到的访存序列和阈值，现在需要实现基于频次的冷热标记。内存页使用页框号作为标识。
     * 输入描述
     * 第一行输入为 N，表示访存序列的记录条数，0 < N ≤ 10000。
     * 第二行为访存序列，空格分隔的 N 个内存页框号，页面号范围 0 \~ 65535，同一个页框号可能重复出现，出现的次数即为对应框号的频次。
     * 第三行为热内存的频次阈值 T，正整数范围 1 ≤ T ≤ 10000。
     * 输出描述
     * 第一行输出标记为热内存的内存页个数，如果没有被标记的热内存页，则输出 0 。
     * 如果第一行 > 0，则接下来按照访问频次降序输出内存页框号，一行一个，频次一样的页框号，页框号小的排前面。
     * 输入	10
     * 1 2 1 2 1 2 1 2 1 2
     * 5
     * 输出	2
     * 1
     * 2
     * 说明	内存页1和内存页2均被访问了5次，达到了
     * 阈值5，因此热内存页有2个。内存页1和内
     * 存页2的访问频次相等，页框号小的排前面。
     */
    public static void neicun() {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < count; i++) {
            int number = scanner.nextInt();
            map.put(number, map.getOrDefault(number, 0) + 1);
        }
        int threshold = scanner.nextInt();
        int ans = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() >= threshold) {
                ans++;
            }
        }
        System.out.println(ans);
        if (ans > 0) {
            for (int i = 0; i < ans; i++) {
                List<Map.Entry<Integer, Integer>> entryList = new ArrayList(map.entrySet());
                entryList.sort((o1, o2) -> {
                    if (Objects.equals(o1.getValue(), o2.getValue())) {
                        return o1.getKey() - o2.getKey();
                    } else {
                        return o2.getValue() - o1.getValue();
                    }
                });
                System.out.println(entryList.get(i).getKey());
            }
        }
    }

    /**
     * 题目描述
     * 给出数字个数 n （0 < n ≤ 999）和行数 m（0 < m ≤ 999），从左上角的 1 开始，按照顺时针螺旋向内写方式，依次写出2,3,....,n，最终形成一个 m 行矩阵。
     * 小明对这个矩阵有些要求：
     * 每行数字的个数一样多
     * 列的数量尽可能少
     * 填充数字时优先填充外部
     * 数字不够时，使用单个 * 号占位
     * 输入描述
     * 两个整数，空格隔开，依次表示 n、m
     * <p>
     * 输出描述
     * 符合要求的唯一矩阵
     * 输入	9  4
     * 输出	1 2 3
     * * * 4
     * 9 * 5
     * 8 7 6
     * 说明	9个数字写出4行，最少需要3列
     */
    public static void customSort() {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = 0;
        if (n % m == 0) {
            k = n / m;
        } else {
            k = n / m + 1;
        }

        int step = 1;
        int x = 0;
        int y = 0;
        int[][] data = new int[m][k];

        while (step <= n) {
            while (y < k && data[x][y] == 0 && step <= n) {
                data[x][y++] = step++;
            }
            y -= 1;
            x += 1;

            while (x < m && data[x][y] == 0 && step <= n) {
                data[x++][y] = step++;
            }
            x -= 1;
            y -= 1;

            while (y >= 0 && data[x][y] == 0 && step <= n) {
                data[x][y--] = step++;
            }
            y += 1;
            x -= 1;

            while (x >= 0 && data[x][y] == 0 && step <= n) {
                data[x--][y] = step++;
            }
            x += 1;
            y += 1;
        }

        for (int i = 0; i < m; i++) {
            StringJoiner row = new StringJoiner(" ");
            for (int j = 0; j < k; j++) {
                if (data[i][j] == 0) {
                    row.add("*");
                } else {
                    row.add(String.valueOf(data[i][j]));
                }
            }
            System.out.println(row);
        }

    }

    /**
     * 围棋棋盘由纵横各19条线垂直相交组成，棋盘上一共19 x 19 = 361 个交点，对弈双方一方执白棋，一方执黑棋，落子时只能将棋子置于交点上。
     * <p>
     * “气”是围棋中很重要的一个概念，某个棋子有几口气，是指其上下左右方向四个相邻的交叉点中，有几个交叉点没有棋子，由此可知：
     * <p>
     * 在棋盘的边缘上的棋子最多有 3 口气（黑1），在棋盘角点的棋子最多有2口气（黑2），其他情况最多有4口气（白1）
     * 所有同色棋子的气之和叫做该色棋子的气，需要注意的是，同色棋子重合的气点，对于该颜色棋子来说，只能计算一次气，比如下图中，黑棋一共4口气，而不是5口气，因为黑1和黑2中间红色三角标出来的气是两个黑棋共有的，对于黑棋整体来说只能算一个气。
     * 本题目只计算气，对于眼也按气计算，如果您不清楚“眼”的概念，可忽略，按照前面描述的规则计算即可。
     * 输入描述
     * 输入包含两行数据，如：
     * <p>
     * 0 5 8 9 9 10
     * 5 0 9 9 9 8
     * 每行数据以空格分隔，数据个数是2的整数倍，每两个数是一组，代表棋子在棋盘上的坐标；
     * 坐标的原点在棋盘左上角点，第一个值是行号，范围从0到18；第二个值是列号，范围从0到18。
     * 举例说明：第一行数据表示三个坐标（0, 5）、(8, 9)、(9, 10)
     * 第一行表示黑棋的坐标，第二行表示白棋的坐标。
     * 题目保证输入两行数据，无空行且每行按前文要求是偶数个，每个坐标不会超出棋盘范围。
     * 输出描述
     * 8 7
     */
    public static void qi() {
        Scanner scanner = new Scanner(System.in);
        String black = scanner.nextLine();
        String white = scanner.nextLine();
        int[][] array = new int[19][19];
        String[] blackArray = black.split(" ");
        String[] whiteArray = white.split(" ");
        for (int i = 0; i < blackArray.length; i = i + 2) {
            int x = Integer.parseInt(blackArray[i]);
            int y = Integer.parseInt(blackArray[i + 1]);
            array[x][y] = 1;
        }
        for (int i = 0; i < whiteArray.length; i = i + 2) {
            int x = Integer.parseInt(whiteArray[i]);
            int y = Integer.parseInt(whiteArray[i + 1]);
            array[x][y] = 2;
        }
        int blackCount = 0;
        int whiteCount = 0;
        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++) {
                if (array[i][j] == 1) {
                    //黑子
                    if (i - 1 >= 0 && (array[i - 1][j] == 0 || array[i - 1][j] == 4)) {
                        blackCount++;
                        array[i - 1][j] = 3;
                    }
                    if (array[i + 1][j] == 0 || array[i + 1][j] == 4) {
                        blackCount++;
                        array[i + 1][j] = 3;
                    }
                    if (j - 1 >= 0 && (array[i][j - 1] == 0 || array[i][j - 1] == 4)) {
                        blackCount++;
                        array[i][j - 1] = 3;
                    }
                    if (array[i][j + 1] == 0 || array[i][j + 1] == 4) {
                        blackCount++;
                        array[i][j + 1] = 3;
                    }
                } else if (array[i][j] == 2) {
                    //白子
                    if (i - 1 >= 0 && (array[i - 1][j] == 0 || array[i - 1][j] == 3)) {
                        whiteCount++;
                        array[i - 1][j] = 4;
                    }
                    if (array[i + 1][j] == 0 || array[i + 1][j] == 3) {
                        whiteCount++;
                        array[i + 1][j] = 4;
                    }
                    if (j - 1 >= 0 && (array[i][j - 1] == 0 || array[i][j - 1] == 3)) {
                        whiteCount++;
                        array[i][j - 1] = 4;
                    }
                    if (array[i][j + 1] == 0 || array[i][j + 1] == 3) {
                        whiteCount++;
                        array[i][j + 1] = 4;
                    }
                }

            }
        }
        System.out.println(blackCount + " " + whiteCount);
    }

    /**
     * 均衡串定义：字符串中只包含两种字符，且这两种字符的个数相同。
     * <p>
     * 给定一个均衡字符串，请给出可分割成新的均衡子串的最大个数。
     * <p>
     * 约定：字符串中只包含大写的 X 和 Y 两种字符。
     * <p>
     * 输入描述
     * 输入一个均衡串。
     * <p>
     * 字符串的长度：[2， 10000]。
     * 给定的字符串均为均衡字符串
     * 输出描述
     * 输出可分割成新的均衡子串的最大个数。
     * 输入	XXYYXY
     * 输出	2
     * 说明	XXYYXY可分割为2个均衡子串，分别为：XXYY、XY
     */
    public static void equilibriumStr() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if ((i + 1 < str.length()) && ((str.charAt(i) == 'X' && str.charAt(i + 1) == 'Y') || (str.charAt(i) == 'Y' && str.charAt(i + 1) == 'X'))) {
                count++;
                i++;
            }
        }
        System.out.println(count);
    }

    /**
     * [机器人搬砖，一共有 N 堆砖存放在 N 个不同的仓库中，第 i 堆砖中有 bricks[i] 块砖头，要求在 8 小时内搬完。
     * <p>
     * 机器人每小时能搬砖的数量取决于有多少能量格，机器人一个小时中只能在一个仓库中搬砖，机器人的能量格只在这一个小时有效，为使得机器人损耗最小化，应尽量减小每次补充的能量格数。
     * <p>
     * 为了保障在 8 小时内能完成搬砖任务，请计算每小时给机器人充能的最小能量格数。
     * <p>
     * 无需考虑机器人补充能力格的耗时；
     * 无需考虑机器人搬砖的耗时；
     * 机器人每小时补充能量格只在这一个小时中有效；
     * 输入描述
     * 第一行为一行数字，空格分隔
     * <p>
     * 输出描述
     * 机器人每小时最少需要充的能量格，若无法完成任务，输出 -1
     * 输入	30 12 25 8 19
     * 输出	15
     * 说明	无
     */
    public static void movingBricks() {
        int hours = 8;
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] array = str.split(" ");
        if (array.length > hours) {
            System.out.println(-1);
            return;
        }
        int sumBricks = 0;
        for (String s : array) {
            sumBricks += Integer.parseInt(s);
        }
        int min = (int) Math.ceil((double) sumBricks / hours);
        while (true) {
            int count = 0;
            for (String s : array) {
                count += Math.ceil((double) Integer.parseInt(s) / min);
            }
            if (count <= 8) {
                break;
            }else {
                min++;
            }
        }
        System.out.println(min);
    }

    /**
     * 题目描述
     * 寿司店周年庆，正在举办优惠活动回馈新老客户。
     *
     * 寿司转盘上总共有 n 盘寿司，prices[i] 是第 i 盘寿司的价格，
     *
     * 如果客户选择了第 i 盘寿司，寿司店免费赠送客户距离第 i 盘寿司最近的下一盘寿司 j，前提是 prices[j] < prices[i]，如果没有满足条件的 j，则不赠送寿司。
     *
     * 每个价格的寿司都可无限供应。
     *
     * 输入描述
     * 输入的每一个数字代表每盘寿司的价格，每盘寿司的价格之间使用空格分隔，例如
     *
     * 3 15 6 14
     * 输出描述
     * 输出享受优惠后的一组数据，每个值表示客户选择第 i 盘寿司时实际得到的寿司的总价格。使用空格进行分隔，
     *
     * 3 21 9 17
     * 本题其实就是要我们求解数组中每个元素的下一个更小值元素，另外数组是循环的，即：如果数组某个元素之后没有比其更小的，那么可以循环到数组头部继续找。
     */
    public static void sushi(){
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strArray = str.split(" ");
        int length = strArray.length;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++){
            int number = Integer.parseInt(strArray[i]);
            boolean flag = false;
            for (int j = 1; j < length ; j++){
                int next = 0;
                if(i + j >= length){
                    next = Integer.parseInt(strArray[i + j - length]);
                }else {
                    next = Integer.parseInt(strArray[i + j]);
                }
                if(next < number){
                    stringBuilder.append(next + number).append(" ");
                    flag = true;
                    break;
                }
            }
            if(!flag){
                stringBuilder.append(number).append(" ");
            }
        }
        System.out.println(stringBuilder);
    }

    /**
     * 提取字符串中的最长合法简单数学表达式，字符串长度最长的，并计算表达式的值。如果没有，则返回 0 。
     *
     * 简单数学表达式只能包含以下内容：
     *
     * 0-9数字，符号+-*
     * 说明：
     *
     * 所有数字，计算结果都不超过long
     * 如果有多个长度一样的，请返回第一个表达式的结果
     * 数学表达式，必须是最长的，合法的
     * 操作符不能连续出现，如 +--+1 是不合法的
     *
     * 输入	1-2abcd
     * 输出	-1
     * 说明	最长合法简单数学表达式是"1-2"，结果是-1
     */
    public static void getLongest(){
//        Scanner scanner = new Scanner(System.in);
//        String str = scanner.nextLine();
//        //左右指针
//        int left = Integer.MAX_VALUE,right = 0;
//        //前一位是否为数字
//        boolean preIsNumber = false;
//        //符合条件的字符串
//        String result = "";
//        int index = 0;
//        //正确的数学表达式应该是以数字开头，有运算符，运算符的下一位必须是数字。
//        while (index < str.length()){
//            if(judgeNumber(str.charAt(index)) && (judgeNumber(str.charAt(index + 1)) || judgeOperator(str.charAt(index + 1)))){
//                //数字
//                left = Math.min(left,index);
//                right++;
//            }else if(judgeOperator(str.charAt(index)) && judgeNumber(str.charAt(index + 1))){
//                //运算符
//                right++;
//            }else {
//                //不符合条件
//                if(right > left + 1 && judgeNumber(str.charAt(right - 1)) && result.length() < right - left+ 1){
//                    result = str.substring(left,right);
//                }
//                left = index + 1;
//                right = left;
//            }
//            index++;
//        }
//        System.out.println(result);


        Scanner sc = new Scanner(System.in);

        String s = sc.nextLine();

        // 最长合法表达式长度
        int maxExpLen = 0;
        // 最长合法表达式的结果
        long ans = 0;

        int len = s.length();

        for (int i = 0; i < len; i++) {

            // 本题有大数量级用例，因此需要此步优化，不然通过率不高
            if (len - i <= maxExpLen) {
                break;
            }

            for (int j = i; j < len; j++) {
                // 截取 [i, j] 范围子串sub
                String sub = s.substring(i, j + 1);

                // 判断sub子串是否为合法表达式，若是，则继续看sub子串长度是否比maxLen更长，若是，则找到更长的合法表达式
                int subLen = j - i + 1;
                if (isValid(sub) && subLen > maxExpLen) {
                    maxExpLen = subLen;

                    // 找到运算符位置k，k从1开始探索，因为索引0可能是正负号
                    int k = 1;
                    while (sub.charAt(k) != '\0') {
                        if (sub.charAt(k) == '+' || sub.charAt(k) == '-' || sub.charAt(k) == '*') {
                            break;
                        } else {
                            k++;
                        }
                    }

                    // 操作数1
                    String num1 = sub.substring(0, k);

                    // 运算符
                    char op = sub.charAt(k);

                    // 操作数2
                    String num2 = sub.substring(k + 1);

                    long opNum1 = Long.parseLong(num1);
                    long opNum2 = Long.parseLong(num2);

                    if (op == '+') {
                        ans = opNum1 + opNum2;
                    } else if (op == '-') {
                        ans = opNum1 - opNum2;
                    } else if (op == '*') {
                        ans = opNum1 * opNum2;
                    }
                }
            }
        }

        System.out.println(ans);
    }

    private static boolean judgeOperator(char ch){
        return ch == '+' || ch == '-' || ch == '*';
    }

    private static boolean judgeNumber(char ch){
        return ch >= '0' && ch <= '9';
    }

    // 校验字符串exp是否为合法表达式，本题合法表达式可能是：a+b，a*b，a-b，其中操作数a可能带有正负号
    static boolean isValid(String exp) {
        // 合法表达式开头可以是+,-,数字，如果不是这些字符，则是非法表达式
        if (!(exp.charAt(0) == '+' || exp.charAt(0) == '-' || (exp.charAt(0) >= '0' && exp.charAt(0) <= '9'))) {
            return false;
        }

        // 运算符位置
        int opIdx = -1;

        for (int i = 1; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // 表达式内容只能是+, -, *, 数字, 如果包含其他字符，则是非法表达式
            if (c != '+' && c != '-' && c != '*' && !(c >= '0' && c <= '9')) {
                return false;
            }

            // 如果是运算符，则记录其出现位置
            if (c == '+' || c == '-' || c == '*') {
                if (opIdx != -1) {
                    // 如果表达式内含有多个运算符，则是非法表达式
                    return false;
                } else {
                    opIdx = i;
                }
            }
        }

        // 避免 ++123
        if ((exp.charAt(0) == '+' || exp.charAt(0) == '-') && opIdx == 1) {
            return false;
        }

        // 避免 123+
        if (opIdx == exp.length() - 1) {
            return false;
        }

        return opIdx != -1;
    }

    public static void main(String[] args) {
        getLongest();
//        sushi();
//        movingBricks();
//        equilibriumStr();
//        TreeMap<String,Integer> treeMap = new TreeMap<String,Integer>((o1, o2) -> -1);
//        qi();
//        customSort();
//        neicun();
//        System.out.println(findSeat("0101"));
//        System.out.println(insertFlag());
//        System.out.println(custom1(""));
//        System.out.println(makeConnected(4,new int[][]{{0,1},{0,2},{1,2}}));
//        System.out.println(minFlips(2, 6, 5));
//        System.out.println(sumEvenGrandparent(new TreeNode(6,new TreeNode(7,new TreeNode(2,new TreeNode(9),null),new TreeNode(7,new TreeNode(1),new TreeNode(4))),new TreeNode(8,new TreeNode(1),new TreeNode(3,null,new TreeNode(5))))));
//        System.out.println(xorQueries(new int[]{16},new int[][]{{0,0},{0,0},{0,0}}));
//        System.out.println(getAllElements(new TreeNode(2,new TreeNode(1),new TreeNode(4)),new TreeNode(1,new TreeNode(0),new TreeNode(3))));
//        System.out.println(canReach(new int[]{4, 2, 3, 0, 3, 1, 2}, 0));
    }

}
