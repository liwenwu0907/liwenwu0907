package com.example.demo;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.example.demo.entity.TreeNode;
import com.example.demo.entity.UnionFind;

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
            if(index >= 1 && index < chars.length - 1 && character == '0' && str.charAt(index + 1) != '1' && str.charAt(index - 1) != '1'){
                ans++;
                chars[index] = 1;
            }
        }
        return ans;
    }

    /**
     * 输入描述
     * 第 1 行输入两个整数，学生人数 n 和科目数量 m。
     *
     * 0 < n < 100
     * 0 < m < 10
     * 第 2 行输入 m 个科目名称，彼此之间用空格隔开。
     *
     * 科目名称只由英文字母构成，单个长度不超过10个字符。
     * 科目的出现顺序和后续输入的学生成绩一一对应。
     * 不会出现重复的科目名称。
     * 第 3 行开始的 n 行，每行包含一个学生的姓名和该生 m 个科目的成绩（空格隔开）
     *
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
    public static void rank(){
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
    public static void main(String[] args) {
        System.out.println(findSeat("0101"));
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
