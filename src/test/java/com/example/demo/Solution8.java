package com.example.demo;

import cn.hutool.core.util.IdUtil;
import com.example.demo.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Solution8 {

    /**
     * 小朋友出操，按学号从小到大排成一列；
     * <p>
     * 小明来迟了，请你给小明出个主意，让他尽快找到他应该排的位置。
     * <p>
     * 算法复杂度要求不高于nLog(n)；学号为整数类型，队列规模 ≤ 10000；
     * <p>
     * 第一行：输入已排成队列的小朋友的学号（正整数），以","隔开；例如：
     * 93,95,97,100,102,123,155,166
     * 第二行：小明学号，如：
     * 110
     * 输出一个数字，代表队列位置（从1开始）。例如：
     * 6
     */
    public static int chuCao() {
        Scanner scanner = new Scanner(System.in);
        String arrayStr = scanner.nextLine();
        int number = Integer.parseInt(scanner.nextLine());
        String[] array = arrayStr.split(",");
        int length = array.length;
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = Integer.parseInt(array[i]);
        }
        int middle = length / 2;
        int ans = 0;
        if (number < ints[0]) {
            ans = 0;
        } else if (number > ints[length - 1]) {
            ans = length;
        } else {
            while (true) {
                if (number > ints[middle] && number < ints[middle + 1]) {
                    ans = middle + 1;
                    break;
                } else if (number < ints[middle]) {
                    middle = (middle - 1) / 2;
                } else if (number > ints[middle]) {
                    middle = (middle + 1 + length - 1) / 2;
                }
            }
        }
        return ans + 1;
//        Scanner scanner = new Scanner(System.in);
//        List<Integer> list = Arrays.stream(scanner.nextLine().split(",")).map(Integer::valueOf).collect(Collectors.toList());
//        int no = scanner.nextInt();
//
//        list.add(no);
//        Collections.sort(list);
//        int index = Collections.binarySearch(list, no);
//        // 因为索引是从 0 开始的，需要 +1
//        return index + 1;
    }

    /**
     * 有一个字符串数组words 和一个字符串 chars。
     * <p>
     * 假如可以用 chars 中的字母拼写出 words 中的某个“单词”（字符串），那么我们就认为你掌握了这个单词。
     * <p>
     * words 的字符仅由 a-z 英文小写字母组成，例如 "abc"
     * <p>
     * chars 由 a-z 英文小写字母和 "?" 组成。其中英文 "?" 表示万能字符，能够在拼写时当作任意一个英文字母。例如："?" 可以当作 "a" 等字母。
     * <p>
     * 注意：每次拼写时，chars 中的每个字母和万能字符都只能使用一次。
     * <p>
     * 输出词汇表 words 中你掌握的所有单词的个数。没有掌握任何单词，则输出0。
     * 输入描述
     * 第一行：输入数组 words 的个数，记作N。
     * <p>
     * 第二行 \~ 第N+1行：依次输入数组words的每个字符串元素
     * <p>
     * 第N+2行：输入字符串chars
     * <p>
     * 输出描述
     * 输出一个整数，表示词汇表 words 中你掌握的单词个数
     * 4
     * cat
     * bt
     * hat
     * tree
     * atach??
     */
    public static int masterMostWord(String[] words, String has) {
        int ans = 0;
        int[] ints = new int[26];
        int special = 0;
        char[] charArray = has.toCharArray();
        for (char ch : charArray) {
            if (ch == '?') {
                special++;
                continue;
            }
            ints[ch - 'a']++;
        }
        for (String word : words) {
            int needSpecial = special;
            int[] needInts = new int[26];
            System.arraycopy(ints, 0, needInts, 0, 26);
            char[] wordChars = word.toCharArray();
            boolean flag = true;
            for (char ch : wordChars) {
                if (needInts[ch - 'a'] > 0) {
                    needInts[ch - 'a']--;
                } else if (needSpecial > 0) {
                    needSpecial--;
                } else {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 现有N个任务需要处理，同一时间只能处理一个任务，处理每个任务所需要的时间固定为1。
     * <p>
     * 每个任务都有最晚处理时间限制和积分值，在最晚处理时间点之前处理完成任务才可获得对应的积分奖励。
     * <p>
     * 可用于处理任务的时间有限，请问在有限的时间内，可获得的最多积分。
     * 第一行为一个数N，表示有N个任务，1<=N<=100
     * 第二行为一个数T，表示可用于处理任务的时间。1<=T<=100
     * 接下来N行，每行两个空格分隔的整数（SLA和V），SLA表示任务的最晚处理时间，V表示任务对应的积分。1<=SLA<=100, 0<=V<=100000
     * 输入	4
     * 3
     * 1 2
     * 1 3
     * 1 4
     * 1 5
     * 输出	5
     */
    private static int handleMission(int hour, int[][] missionArray) {
        int ans = 0;
        //用来存放每个小时的最大积分
        int[] maxIntegral = new int[hour];
        for (int i = 0; i < missionArray.length; i++) {
            int[] detail = missionArray[i];
            int timeIndex = detail[0];
            int integral = detail[1];
            if (maxIntegral[timeIndex - 1] < integral) {
                maxIntegral[timeIndex - 1] = integral;
            }
        }
        ans = Arrays.stream(maxIntegral).sum();
        return ans;
    }

    /**
     * 给定一个正整数n，如果能够分解为m(m > 1)个连续正整数之和，请输出所有分解中，m最小的分解。
     * <p>
     * 如果给定整数无法分解为连续正整数，则输出字符串"N"。
     * 输入	21
     * 输出	21=10+11
     * 说明	21可以分解的连续正整数组合的形式有多种：
     * 21=1+2+3+4+5+6
     * 21=6+7+8
     * 21=10+11
     * 其中 21=10+11，是最短的分解序列。
     */
    public static void decomposeInteger(int number) {
        if (number == 1 || number == 2) {
            System.out.println(number);
        }
        //如果是奇数，则一定返回2。如果为偶数：1：分解为奇数个，则必须能被整除。2：分解为偶数个，则一定不能被整除。
        if (number % 2 != 0) {
            System.out.println(number + "=" + number / 2 + "+" + number / 2 + 1);
        }
        //20=2+3+4+5+6; 21 = 10+11;22 = 4+5+6+7；23 = 11 + 12；24= 7+8+9；25=12+13；26=5+6+7+8；27=13+14
        /*
            1.假设我们的分解中有m个连续正整数，起始数字为x。那么这m个连续正整数的和可以表示为：x + (x+1) + (x+2) + ... + (x+m-1)。
            2.根据等差数列求和公式，这个和可以简化为：(2x + m - 1) * m / 2。
            3.我们的目标是找到满足上述等式的最小m和x的组合，使得(2x + m - 1) * m / 2 = n，并且m最小。

            (1)循环遍历可能的m值，从2到sqrt(2 * n)。这是因为当m大于sqrt(2 * n)时，(2x + m - 1) * m / 2的结果将超过n。
            (2)对于每个m值，计算(2x + m - 1) * m / 2的结果，并检查是否等于n。
            (3)如果等于n，我们找到了一个分解。我们可以计算起始数字x，并将连续的m个数字添加到结果列表中。
            (4)如果不等于n，继续尝试下一个m值。
            (5)返回找到的所有分解，其中m最小。
         */

        List<List<Integer>> decompositions = new ArrayList<>();

        for (int i = 2; i < Math.sqrt(2 * number); i++) {

            if ((2 * number) % i == 0) {
                int k = (2 * number) / i;

                int start = k - i + 1;

                if (start % 2 == 0) {
                    start = start / 2;
                    List<Integer> decomposition = new ArrayList<>();

                    for (int j = 0; j < i; j++) {
                        decomposition.add(start + j);
                    }

                    decompositions.add(decomposition);
                }
            }

        }
        List<Integer> shortestDecomposition = decompositions.get(0);

        for (int i = 0; i < shortestDecomposition.size(); i++) {
            System.out.print(shortestDecomposition.get(i));

            if (i < shortestDecomposition.size() - 1) {
                System.out.print(" + ");
            }
        }

    }

    /**
     * 在一颗树中，每个节点代表一个家庭成员，节点的数字表示其个人的财富值，一个节点及其直接相连的子节点被定义为一个小家庭。
     * <p>
     * 现给你一颗树，请计算出最富裕的小家庭的财富和。
     * <p>
     * 输入描述
     * 第一行为一个数 N，表示成员总数，成员编号 1\~N。1 ≤ N ≤ 1000
     * <p>
     * 第二行为 N 个空格分隔的数，表示编号 1\~N 的成员的财富值。0 ≤ 财富值 ≤ 1000000
     * <p>
     * 接下来 N -1 行，每行两个空格分隔的整数（N1, N2），表示 N1 是 N2 的父节点。
     * 输    入	4
     * 100     200      300      500
     * 1          2
     * 1          3
     * 2          4
     * 输     出	700
     */
    public static int ans = 0;

    public static void countTreeNode(TreeNode treeNode) {
        if (treeNode != null) {
            int selfValue = treeNode.val;
            if (treeNode.left != null) {
                selfValue += treeNode.left.val;
            }
            if (treeNode.right != null) {
                selfValue += treeNode.right.val;
            }
            ans = Math.max(selfValue, ans);
            countTreeNode(treeNode.left);
            countTreeNode(treeNode.right);
        }
    }

    /**
     * 某个产品的RESTful API集合部署在服务器集群的多个节点上，近期对客户端访问日志进行了采集，需要统计各个API的访问频次，根据热点信息在服务器节点之间做负载均衡，现在需要实现热点信息统计查询功能。
     * RESTful API的由多个层级构成，层级之间使用 / 连接，如 /A/B/C/D 这个地址，A属于第一级，B属于第二级，C属于第三级，D属于第四级。
     * 现在负载均衡模块需要知道给定层级上某个名字出现的频次，未出现过用0次表示，实现这个功能。
     * 第一行为N，表示访问历史日志的条数.
     * <p>
     * 接下来N行，每一行为一个RESTful API的URL地址，约束地址中仅包含英文字母和连接符/，最大层级为10，每层级字符串最大长度为10。
     * <p>
     * 最后一行为层级L和要查询的关键字。
     * 输入	5
     * /huawei/computing/no/one
     * /huawei/computing
     * /huawei
     * /huawei/cloud/no/one
     * /huawei/wireless/no/one
     * 4 two
     * 输出	0
     * 说明	存在第四层级的URL上，没有出现two，因此频次是0
     */
    public static void countRestful() {
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());
        List<List<String>> outterList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String uri = scanner.nextLine();
            String[] strings = uri.split("/");
            List<String> innerList = new ArrayList<>(Arrays.asList(strings).subList(1, strings.length));
            outterList.add(innerList);
        }
        int level = Integer.parseInt(scanner.nextLine());
        String findChars = scanner.nextLine();
        int ans = 0;
        for (List<String> list : outterList) {
            if (list.size() >= level) {
                if (findChars.equals(list.get(level - 1))) {
                    ans++;
                }
            }

        }
        System.out.println(ans);
    }

    /**
     * 小华按照地图去寻宝，地图上被划分成 m 行和 n 列的方格，横纵坐标范围分别是 [0, n-1] 和 [0, m-1]。
     * <p>
     * 在横坐标和纵坐标的数位之和不大于 k 的方格中存在黄金（每个方格中仅存在一克黄金），但横坐标和纵坐标之和大于 k 的方格存在危险不可进入。小华从入口 (0,0) 进入，任何时候只能向左，右，上，下四个方向移动一格。
     * <p>
     * 请问小华最多能获得多少克黄金？
     * 输入中包含3个字数，分别是m, n, k
     * 输入	5 4 7
     * 输出	20
     */
    public static void treasureHunting() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] strings = str.split(" ");
        int ans = 0;
        int m = Integer.parseInt(strings[0]);
        int n = Integer.parseInt(strings[1]);
        int k = Integer.parseInt(strings[2]);
        int[][] arrays = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i + j <= k) {
                    ans++;
                }
            }
        }
        System.out.println(ans);
    }

    /**
     * 请实现一个简易内存池,根据请求命令完成内存分配和释放。
     * 内存池支持两种操作命令，REQUEST和RELEASE，其格式为：
     * REQUEST=请求的内存大小 表示请求分配指定大小内存，如果分配成功，返回分配到的内存首地址；如果内存不足，或指定的大小为0，则输出error。
     * RELEASE=释放的内存首地址 表示释放掉之前分配的内存，释放成功无需输出，如果释放不存在的首地址则输出error。
     * 注意：
     * <p>
     * 内存池总大小为100字节。
     * 内存池地址分配必须是连续内存，并优先从低地址分配。
     * 内存释放后可被再次分配，已释放的内存在空闲时不能被二次释放。
     * 不会释放已申请的内存块的中间地址。
     * 释放操作只是针对首地址所对应的单个内存块进行操作，不会影响其它内存块。
     * 输入描述
     * 首行为整数 N , 表示操作命令的个数，取值范围：0 < N <= 100。
     * <p>
     * 接下来的N行, 每行将给出一个操作命令，操作命令和参数之间用 “=”分割。
     * <p>
     * 输出描述
     * 请求分配指定大小内存时，如果分配成功，返回分配到的内存首地址；如果内存不足，或指定的大小为0，则输出error
     * <p>
     * 释放掉之前分配的内存时，释放成功无需输出，如果释放不存在的首地址则输出error。
     * 示例：
     * <p>
     * 输入	2
     * REQUEST=10
     * REQUEST=20
     * 输出	0
     * 10
     * 说明	无
     */
    public static void memoryModel() {
        int allSize = 100;
        Scanner scanner = new Scanner(System.in);
        //内存数组，使用则为1，未使用为0
        int[] useArray = new int[allSize];
        int commandCount = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < commandCount; i++) {
            String command = scanner.nextLine();
            String[] strings = command.split("=");
            String order = strings[0];
            int size = Integer.parseInt(strings[1]);
            if ("REQUEST".equals(order)) {
                //申请内存
                if (size <= 0) {
                    System.out.println("error");
                    continue;
                }
                boolean judge = false;
                //判断内存是否足够，从头开始遍历,找数组下标为0的，然后长度满足的，内存必须连续
                for (int j = 0; j < useArray.length; j++) {
                    //占用起始位置
                    int fromIndex = j;
                    //占用结束位置
                    int endIndex = fromIndex + size - 1;
                    if (fromIndex >= allSize - 1 || endIndex >= allSize) {
                        break;
                    }
                    //是否能占用
                    boolean flag = true;
                    for (int k = 0; k < size; k++) {
                        if (useArray[j + k] == 0) {
                            fromIndex = Math.min(fromIndex, j + k);
                        } else {
                            //不连续，不能占用
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        //能占用，修改数组下标
                        for (int m = fromIndex; m < endIndex + 1; m++) {
                            if (m == fromIndex) {
                                //开始的标记，申请和释放应该都是一整个连续的内存
                                useArray[m] = 4;
                            } else if (m == endIndex) {
                                //结束的标记,内存释放的时候需要
                                useArray[m] = 2;
                            } else {
                                useArray[m] = 1;
                            }
                        }
                        //打印fromIndex
                        System.out.println(fromIndex);
                        judge = true;
                        break;
                    }
                }
                //循环完了,没有空间
                if (!judge) {
                    System.out.println("error");
                }
            } else {
                //释放内存,size是首地址，fromIndex
                int fromIndex = size;
                //要释放的下标不存在
                if (useArray[fromIndex] != 4) {
                    System.out.println("error");
                    continue;
                }
                //找到数组下标为2的，然后修改下标为0
                for (int p = fromIndex; p < allSize; p++) {
                    useArray[p] = 0;
                    if (2 == useArray[p]) {
                        //下标结束
                        break;
                    }
                }
            }
        }
    }

    /**
     * 实现一个模拟目录管理功能的软件，输入一个命令序列，输出最后一条命令运行结果。
     * <p>
     * 支持命令：
     * <p>
     * 创建目录命令：mkdir 目录名称，如 mkdir abc 为在当前目录创建abc目录，如果已存在同名目录则不执行任何操作。此命令无输出。
     * 进入目录命令：cd 目录名称，如 cd abc 为进入abc目录，特别地，cd … 为返回上级目录，如果目录不存在则不执行任何操作。此命令无输出。
     * 查看当前所在路径命令：pwd，输出当前路径字符串。
     * 约束：
     * <p>
     * 目录名称仅支持小写字母；mkdir 和 cd 命令的参数仅支持单个目录，如：mkdir abc 和 cd abc；不支持嵌套路径和绝对路径，如 mkdir abc/efg，cd abc/efg，mkdir /abc/efg，cd /abc/efg 是不支持的。
     * 目录符号为/，根目录/作为初始目录。
     * 任何不符合上述定义的无效命令不做任何处理并且无输出。
     * 输入	mkdir abc
     * cd abc
     * pwd
     * 输出	/abc/
     * 说明	在根目录创建一个abc的目录并进入abc目录中查看当前目录路径，输出当前路径/abc/。
     */
    public static void directory() {
        DirectoryNode current = new DirectoryNode("/", new ArrayList<>());
        StringBuilder path = new StringBuilder("/");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String str = scanner.nextLine();
            String directoryName = str.startsWith("pwd") ? "" : str.split(" ")[1];
            if (str.startsWith("mkdir")) {
                //创建目录，如果存在同名的则不操作
                if (current.getNext().stream().noneMatch(e -> e.getValue().equals(directoryName))) {
                    DirectoryNode node = new DirectoryNode(directoryName, new ArrayList<>());
                    current.getNext().add(node);
                    node.setPrev(current);
                }
            } else if (str.startsWith("cd")) {
                //不存在的目录不操作
                if (directoryName.equals("..") && !current.getValue().equals("/")) {
                    //上一级
                    current = current.getPrev();
                    path.deleteCharAt(path.length() - 1);
                    int fromIndex = path.toString().lastIndexOf("/");
                    path.delete(fromIndex + 1, path.length());
                } else if (current.getNext().stream().anyMatch(e -> e.getValue().equals(directoryName))) {
                    //进入目录
                    current = current.getNext().stream().filter(e -> e.getValue().equals(directoryName)).findFirst().get();
                    path.append(directoryName).append("/");
                }
            } else if (str.startsWith("pwd")) {
                System.out.println(path);
            }
        }
    }

    /**
     * 有一辆汽车需要从 m * n 的地图左上角（起点）开往地图的右下角（终点），去往每一个地区都需要消耗一定的油量，加油站可进行加油。
     * <p>
     * 请你计算汽车确保从从起点到达终点时所需的最少初始油量。
     * <p>
     * 说明：
     * <p>
     * 智能汽车可以上下左右四个方向移动
     * 地图上的数字取值是 0 或 -1 或 正整数：
     * <p>
     * -1 ：表示加油站，可以加满油，汽车的油箱容量最大为100；
     * 0 ：表示这个地区是障碍物，汽车不能通过
     * 正整数：表示汽车走过这个地区的耗油量
     * <p>
     * 如果汽车无论如何都无法到达终点，则返回 -1
     * 输入描述
     * 第一行为两个数字，M，N，表示地图的大小为 M * N
     * <p>
     * 0 < M,N ≤ 200
     * 后面一个 M * N 的矩阵，其中的值是 0 或 -1 或正整数，加油站的总数不超过 200 个
     * <p>
     * 输出描述
     * 如果汽车无论如何都无法到达终点，则返回 -1
     * <p>
     * 如果汽车可以到达终点，则返回最少的初始油量
     * <p>
     * 示例：
     * <p>
     * 输入	4,5
     * 10,0,30,-1,10
     * 30,0,20,0,20
     * 10,0,10,0,30
     * 10,-1,30,0,10
     * 60
     *
     * @param map
     * @return
     */
    public static int calculateMinimumFuel(int[][] map) {
        int m = map.length;
        int n = map[0].length;
        int ans = map[0][0];
        //是否访问过，访问过的节点已经不能再访问，防止循环
        boolean[][] visited = new boolean[m][n];
        int i = 0, j = 0;

        while (i < m && j < n) {
            visited[i][j] = true;
            if (i == m - 1 && j == n - 1) {
                return ans;
            }
            //上
            int up = i > 0 && map[i - 1][j] != 0 && !visited[i - 1][j] ? map[i - 1][j] : Integer.MAX_VALUE;
            //下
            int down = i < m - 1 && map[i + 1][j] != 0 && !visited[i + 1][j] ? map[i + 1][j] : Integer.MAX_VALUE;
            //左
            int left = j > 0 && map[i][j - 1] != 0 && !visited[i][j - 1] ? map[i][j - 1] : Integer.MAX_VALUE;
            //右
            int right = j < n - 1 && map[i][j + 1] != 0 && !visited[i][j + 1] ? map[i][j + 1] : Integer.MAX_VALUE;
            int min1 = Math.min(up, down);
            int min2 = Math.min(left, right);
            int min = Math.min(min1, min2);
            if (min == Integer.MAX_VALUE) {
                //无法到达终点
                return -1;
            }
            if (map[i][j] == -1) {
                ans = 0;
            } else {
                ans += min;
                if (ans > 100) {
                    //无法到达终点
                    return -1;
                }
            }

            if (up == min) {
                i = i - 1;
            } else if (down == min) {
                i = i + 1;
            } else if (left == min) {
                j = j - 1;
            } else {
                j = j + 1;
            }
        }
        return ans;
    }

    /**
     * 题目描述
     * 给定一个矩阵，包含 N * M 个整数，和一个包含 K 个整数的数组。
     * <p>
     * 现在要求在这个矩阵中找一个宽度最小的子矩阵，要求子矩阵包含数组中所有的整数。
     * <p>
     * 输入描述
     * 第一行输入两个正整数 N，M，表示矩阵大小。
     * <p>
     * 接下来 N 行 M 列表示矩阵内容。
     * <p>
     * 下一行包含一个正整数 K。
     * <p>
     * 下一行包含 K 个整数，表示所需包含的数组，K 个整数可能存在重复数字。
     * <p>
     * 所有输入数据小于1000。
     * 输出描述
     * 输出包含一个整数，表示满足要求子矩阵的最小宽度，若找不到，输出-1。
     * <p>
     * 示例：
     * <p>
     * 输入	2 5
     * 1 2 2  3 1
     * 2 3 2  3 2
     * 3
     * 1 2 3
     * 输出	2
     * 说明	矩阵第0、3列包含了1，2，3，矩阵第3，4列包含了1，2，3
     */
    public static int subMatrix(int[][] numberArray, int[] subArray) {
        int ans = Integer.MAX_VALUE;
        int subLength = subArray.length;
        //行
        int n = numberArray.length;
        //列
        int m = numberArray[0].length;
        //子矩阵有多少个数字，因为需要全部包含
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : subArray) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int left = 0; left < m; left++) {
            Map<Integer, Integer> countMap = new HashMap<>(map);
            //从左边开始遍历
            int right = left;
            //计数器，用来计数subArray的个数
            int count = 0;
            //右边界逐渐扩散
            while (right < m && count < subLength) {
                for (int j = 0; j < n; j++) {
                    int num = numberArray[j][right];
                    if (countMap.get(num) != null && countMap.get(num) > 0) {
                        countMap.put(num, countMap.get(num) - 1);
                        count++;
                    }
                }
                right++;
            }
            if (right == m && count < subLength) {
                //因为到达边界而出循环
                right++;
            }
            ans = Math.min(ans, right - left);

        }
        return ans;
    }

    /**
     * 题目描述
     * 请设计一个文件缓存系统，该文件缓存系统可以指定缓存的最大值（单位为字节）。
     * <p>
     * 文件缓存系统有两种操作：
     * <p>
     * 存储文件（put）
     * 读取文件（get）
     * 操作命令为：
     * <p>
     * put fileName fileSize
     * get fileName
     * 存储文件是把文件放入文件缓存系统中；
     * <p>
     * 读取文件是从文件缓存系统中访问已存在，如果文件不存在，则不作任何操作。
     * <p>
     * 当缓存空间不足以存放新的文件时，根据规则删除文件，直到剩余空间满足新的文件大小位置，再存放新文件。
     * <p>
     * 具体的删除规则为：文件访问过后，会更新文件的最近访问时间和总的访问次数，当缓存不够时，按照第一优先顺序为访问次数从少到多，第二顺序为时间从老到新的方式来删除文件。
     * <p>
     * 输入描述
     * 第一行为缓存最大值m（整数，取值范围为 0 < m ≤ 52428800）
     * <p>
     * 第二行为文件操作序列个数 n（0 ≤ n ≤ 300000）
     * <p>
     * 从第三行起为文件操作序列，每个序列单独一行，文件操作定义为：
     * <p>
     * op file\_name file\_size
     * file\_name 是文件名，file\_size 是文件大小
     * <p>
     * 输出描述
     * 输出当前文件缓存中的文件名列表，文件名用英文逗号分隔，按字典顺序排序，如：
     * <p>
     * a,c
     * 如果文件缓存中没有文件，则输出NONE
     * <p>
     * 备注
     * <p>
     * 如果新文件的文件名和文件缓存中已有的文件名相同，则不会放在缓存中
     * 新的文件第一次存入到文件缓存中时，文件的总访问次数不会变化，文件的最近访问时间会更新到最新时间
     * 每次文件访问后，总访问次数加1，最近访问时间更新到最新时间
     * 任何两个文件的最近访问时间不会重复
     * 文件名不会为空，均为小写字母，最大长度为10
     * 缓存空间不足时，不能存放新文件
     * 每个文件大小都是大于 0 的整数
     * 示例：
     * <p>
     * 输入	50
     * 1
     * get file
     * 输出	NONE
     * 输入	50
     * 6
     * put a 10
     * put b 20
     * get a
     * get a
     * get b
     * put c 30
     * 输出	a,c
     */
    public static String hdfs(int maxSize, List<String> commandList) {
        List<FileAttribute> fileAttributeList = new ArrayList<>();
        for (String command : commandList) {
            String[] strings = command.split(" ");
            String getOrPut = strings[0];
            String fileName = strings[1];
            long timestamp = System.currentTimeMillis();
            FileAttribute findFileAttribute = fileAttributeList.stream().filter(e -> fileName.equals(e.getFileName())).findFirst().orElse(null);

            if ("get".equals(getOrPut)) {
                //访问。次数加1，更新时间
                if (null != findFileAttribute) {
                    findFileAttribute.setTimestamp(timestamp);
                    findFileAttribute.setVisits(findFileAttribute.getVisits() + 1);
                }
            } else {
                //如果fileAttribute已存在，则不操作
                if (null != findFileAttribute) {
                    continue;
                }
                //存入
                int size = Integer.parseInt(strings[2]);
                /*
                    1.判断是否放得下文件。放得下则更新timestamp。否则判断是否超过总的大小，不超过则需要删除老的访问得少的。超过则不做操作
                 */
                if (size > maxSize) {
                    continue;
                }
                int usedSize = fileAttributeList.stream().mapToInt(FileAttribute::getSize).sum();
                if (size > maxSize - usedSize) {
                    //需要删除老文件
                    while (size > maxSize - usedSize) {
                        List<FileAttribute> result = fileAttributeList.stream().sorted((o1, o2) -> {
                            if (o1.getVisits() == o2.getVisits()) {
                                return (int) (o1.getTimestamp() - o2.getTimestamp());
                            } else {
                                return o1.getVisits() - o2.getVisits();
                            }
                        }).collect(Collectors.toList());
                        usedSize = usedSize - result.get(0).getSize();
                        fileAttributeList.remove(result.get(0));
                    }
                }
                //添加文件
                FileAttribute fileAttribute = new FileAttribute();
                fileAttribute.setSize(size);
                fileAttribute.setFileName(fileName);
                fileAttribute.setVisits(0);
                fileAttribute.setTimestamp(timestamp);
                fileAttributeList.add(fileAttribute);
            }
        }
        if (CollectionUtils.isEmpty(fileAttributeList)) {
            return "NONE";
        }
        List<String> stringList = fileAttributeList.stream().sorted((o1, o2) -> o1.getFileName().compareToIgnoreCase(o2.getFileName())).map(FileAttribute::getFileName).collect(Collectors.toList());
        return String.join(",", stringList);
    }

    /**
     * 题目描述
     * 给定一个有向图，图中可能包含有环，图使用二维矩阵表示，每一行的第一列表示起始节点，第二列表示终止节点，如 [0, 1] 表示从 0 到 1 的路径。
     * <p>
     * 每个节点用正整数表示。求这个数据的首节点与尾节点，题目给的用例会是一个首节点，但可能存在多个尾节点。同时图中可能含有环。如果图中含有环，返回 [-1]。
     * <p>
     * 说明：入度为0是首节点，出度为0是尾节点。
     * <p>
     * <p>
     * 输入描述
     * 第一行为后续输入的键值对数量N（N ≥ 0）
     * <p>
     * 第二行为2N个数字。每两个为一个起点，一个终点.
     * <p>
     * 输出描述
     * 输出一行头节点和尾节点。如果有多个尾节点，按从小到大的顺序输出
     * <p>
     * 备注
     * 如果图有环，输出为 -1
     * 所有输入均合法，不会出现不配对的数据
     * 示例：
     * <p>
     * 输入	4
     * 0 1 0 2 1 2 2 3
     * 输出	0 3
     */
    public static String findHeadAndTail(List<String> stringList) {
        List<EnterAndOutNode> enterAndOutNodeList = new ArrayList<>();
        for (String str : stringList) {
            String[] strings = str.split(" ");
            int begin = Integer.parseInt(strings[0]);
            int end = Integer.parseInt(strings[1]);
            //value都是唯一的
            EnterAndOutNode beginNode = enterAndOutNodeList.stream().filter(e -> begin == e.getValue()).findFirst().orElse(null);
            if (null == beginNode) {
                EnterAndOutNode node = new EnterAndOutNode();
                node.setValue(begin);
                node.setOutNum(1);
                node.setEnterNum(0);
                enterAndOutNodeList.add(node);
            } else {
                beginNode.setOutNum(beginNode.getOutNum() + 1);
            }
            EnterAndOutNode endNode = enterAndOutNodeList.stream().filter(e -> end == e.getValue()).findFirst().orElse(null);
            if (null == endNode) {
                EnterAndOutNode node = new EnterAndOutNode();
                node.setValue(end);
                node.setOutNum(0);
                node.setEnterNum(1);
                enterAndOutNodeList.add(node);
            } else {
                endNode.setEnterNum(endNode.getEnterNum() + 1);
            }
        }
        //必须要有首位节点，否则返回-1；
        List<EnterAndOutNode> head = enterAndOutNodeList.stream().filter(e -> e.getEnterNum() == 0).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(head)) {
            return "-1";
        }
        List<EnterAndOutNode> tail = enterAndOutNodeList.stream().filter(e -> e.getOutNum() == 0).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(head.get(0).getValue());
        for (EnterAndOutNode enterAndOutNode : tail) {
            stringBuilder.append(" ").append(enterAndOutNode.getValue());
        }
        return stringBuilder.toString();
    }

    /**
     * 有一种特殊的加密算法，明文为一段数字串，经过密码本查找转换，生成另一段密文数字串。
     * <p>
     * 规则如下：
     * <p>
     * 明文为一段数字串由 0\~9 组成
     * 密码本为数字 0\~9 组成的二维数组
     * 需要按明文串的数字顺序在密码本里找到同样的数字串，密码本里的数字串是由相邻的单元格数字组成，上下和左右是相邻的，注意：对角线不相邻，同一个单元格的数字不能重复使用。
     * 每一位明文对应密文即为密码本中找到的单元格所在的行和列序号（序号从0开始）组成的两个数宇。
     * 如明文第 i 位 Data[i] 对应密码本单元格为 Bookx，则明文第 i 位对应的密文为X Y，X和Y之间用空格隔开。
     * 如果有多条密文，返回字符序最小的密文。
     * <p>
     * 如果密码本无法匹配，返回"error"。
     * 输入
     * 0 3
     * 0 0 2
     * 1 3 4
     * 6 6 4
     * 输出	0 1 1 1
     */
    public static String secret(int[] plaintext, int[][] book) {
        List<Integer> list = new ArrayList<>();
        int m = book.length;
        //用来存储哪些已经被访问过了
        boolean[][] visited = new boolean[m][m];
        for (int num : plaintext) {
            boolean flag = false;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if (num == book[i][j] && !visited[i][j]) {
                        //说明找到对应的数字
                        list.add(i);
                        list.add(j);
                        visited[i][j] = true;
                        flag = true;
                        break;
                    }
                }
                //找到对应的数字，直接下一个数字
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                //说明循环完了也没找到
                return "error";
            }
        }
        return StringUtils.join(list, " ");
    }

    /**
     * 给你一个整数数组 arr 和一个整数 difference，请你找出并返回 arr 中最长等差子序列的长度，该子序列中相邻元素之间的差等于 difference 。
     * <p>
     * 子序列 是指在不改变其余元素顺序的情况下，通过删除一些元素或不删除任何元素而从 arr 派生出来的序列。
     * <p>
     * 输入：arr = [1,5,7,8,5,3,4,2,1], difference = -2
     * 输出：4
     * 解释：最长的等差子序列是 [7,5,3,1]。
     */
    public static int longestSubsequence(int[] arr, int difference) {
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int j : arr) {
            map.put(j, map.getOrDefault(j - difference, 0) + 1);
            ans = Math.max(ans, map.getOrDefault(j, 0));
        }
        return ans;
    }

    /**
     * 你有一个下标从 0 开始、长度为 偶数 的整数数组 nums ，同时还有一个空数组 arr 。Alice 和 Bob 决定玩一个游戏，游戏中每一轮 Alice 和 Bob 都会各自执行一次操作。游戏规则如下：
     * <p>
     * 每一轮，Alice 先从 nums 中移除一个 最小 元素，然后 Bob 执行同样的操作。
     * 接着，Bob 会将移除的元素添加到数组 arr 中，然后 Alice 也执行同样的操作。
     * 游戏持续进行，直到 nums 变为空。
     * 返回结果数组 arr 。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [5,4,2,3]
     * 输出：[3,2,5,4]
     * 解释：第一轮，Alice 先移除 2 ，然后 Bob 移除 3 。然后 Bob 先将 3 添加到 arr 中，接着 Alice 再将 2 添加到 arr 中。于是 arr = [3,2] 。
     * 第二轮开始时，nums = [5,4] 。Alice 先移除 4 ，然后 Bob 移除 5 。接着他们都将元素添加到 arr 中，arr 变为 [3,2,5,4] 。
     */
    public static int[] numberGame(int[] nums) {
        //排序之后，n和n+1交换位置
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i = i + 2) {
            int temp = nums[i];
            nums[i] = nums[i + 1];
            nums[i + 1] = temp;
        }
        return nums;
    }

    /**
     * 有一个大文件，记录一段时间内百度所有的搜索记录，每行放一个搜索词，因为搜索量很大，文件非常大，搜索词数量也很多，内存放不下，求搜索次数最多的TopN个搜索词。
     * 输入：大文件
     * 输出：TopN搜索词列表
     */
    public static List<String> findTopWords(File inputFile, int n) {
        List<String> topNWords = new ArrayList<>();
        Map<String, Integer> wordCountMap = new HashMap<>();
        //读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            //一行行读取数据，避免一次性加载不了
            while ((line = reader.readLine()) != null) {
                //每行放一个搜索词，没说是不是句子，假设按照  aa bb  cc对语句按照单词拆分
                String[] words = line.split("\\s+");
                for (String word : words) {
                    //单词对应次数+1
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PriorityQueue<Map.Entry<String, Integer>> maxHeap =
                new PriorityQueue<>((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        for (Map.Entry<String, Integer> entry : wordCountMap.entrySet()) {
            //这里内存太大的话，可以优化只存前n个
//            if (maxHeap.size() == n) {
//                assert maxHeap.peek() != null;
//                if (maxHeap.peek().getValue() < entry.getValue()) {
//                    maxHeap.poll();
//                    maxHeap.offer(entry);
//                }
//            } else {
//                maxHeap.offer(entry);
//            }
            maxHeap.offer(entry);
        }
        if (!maxHeap.isEmpty()) {
            for (int i = 0; i < n; i++) {
                topNWords.add(Objects.requireNonNull(maxHeap.poll()).getKey());
            }
        }
        return topNWords;
    }
    static PriorityQueue<Status> queue = new PriorityQueue<Status>();
    /**
     * 合并 K 个升序链表
     * 困难
     * 相关标签
     * 相关企业
     * 给你一个链表数组，每个链表都已经按升序排列。
     *
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
     * 输入：lists = [[1,4,5],[1,3,4],[2,6]]
     * 输出：[1,1,2,3,4,4,5,6]
     * 解释：链表数组如下：
     * [
     *   1->4->5,
     *   1->3->4,
     *   2->6
     * ]
     * 将它们合并到一个有序链表中得到。
     * 1->1->2->3->4->4->5->6
     */
    public static ListNode mergeKLists(ListNode[] lists) {
        for (ListNode node: lists) {
            if (node != null) {
                queue.offer(new Status(node.val, node));
            }
        }
        ListNode head = new ListNode(0);
        ListNode tail = head;
        while (!queue.isEmpty()) {
            Status f = queue.poll();
            tail.next = f.ptr;
            tail = tail.next;
            if (f.ptr.next != null) {
                queue.offer(new Status(f.ptr.next.val, f.ptr.next));
            }
        }
        return head.next;
    }

    static class Status implements Comparable<Status> {
        int val;
        ListNode ptr;

        Status(int val, ListNode ptr) {
            this.val = val;
            this.ptr = ptr;
        }

        public int compareTo(Status status2) {
            return this.val - status2.val;
        }
    }

    /**
     * 数组中的第K个最大元素
     *
     * 给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
     *
     * 请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     *
     * 你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
     *
     * 输入: [3,2,1,5,6,4], k = 2
     * 输出: 5
     */
    public int findKthLargest(int[] nums, int k) {
//        Arrays.sort(nums);
//        return nums[nums.length - k];
        PriorityQueue<Integer> integerPriorityQueue = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        for (int num : nums) {
            integerPriorityQueue.offer(num);
        }
        int ans = 0;
        for (int i = 0; i < k; i++) {
            ans = integerPriorityQueue.poll();
        }
        return ans;
    }


    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];
        lists[0] = new ListNode(1,new ListNode(4,new ListNode(5)));
        lists[1] = new ListNode(1,new ListNode(3,new ListNode(4)));
        lists[2] = new ListNode(2,new ListNode(6));
        System.out.println(mergeKLists(lists));
//        int[] arr = numberGame(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
//        for (int i = 0; i < 2; i++) {
//            System.out.println(IdUtil.fastSimpleUUID());
//        }
//        System.out.println(longestSubsequence(new int[]{1, 5, 7, 8, 5, 3, 4, 2, 1}, -2));
//        System.out.println(secret(new int[]{0,3},new int[][]{{0,0,2},{1,3,4},{6,6,4}}));
//        List<String> stringList = new ArrayList<>();
//        stringList.add("0 1");
//        stringList.add("0 2");
//        stringList.add("1 2");
//        stringList.add("2 3");
//        System.out.println(findHeadAndTail(stringList));
//        List<String> commandList = new ArrayList<>();
//        commandList.add("put c 10");
//        commandList.add("put b 20");
//        commandList.add("get c");
//        commandList.add("get c");
//        commandList.add("get b");
//        commandList.add("put a 30");
//        System.out.println(hdfs(50,commandList));
//        System.out.println(subMatrix(new int[][]{{1,2,2,3,1},{2,3,2,3,2}},new int[]{1,2,3}));
//        System.out.println(calculateMinimumFuel(new int[][]{{10,0,30,-1,10},{30,0,20,0,20},{10,0,10,0,30},{10,-1,30,0,10}}));
//        directory();
//        memoryModel();
//        treasureHunting();
//        countRestful();
//        countTreeNode(new TreeNode(100,new TreeNode(200,new TreeNode(500),null),new TreeNode(300)));
//        System.out.println(ans);
//        decomposeInteger(22222222);
//        System.out.println(handleMission(3, new int[][]{{3, 2}, {2, 3}, {1, 4}, {1, 5}}));
//        System.out.println(chuCao());
//        System.out.println(masterMostWord(new String[]{"cat","bt","hat","tree","atach"},"atach??"));
    }
}
