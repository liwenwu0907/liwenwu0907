package com.example.demo;

import com.example.demo.entity.ListNode;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution6 {

    /**
     * 给你一组多米诺骨牌 dominoes 。
     * 形式上，dominoes[i] = [a, b] 与 dominoes[j] = [c, d] 等价 当且仅当 (a == c 且 b == d) 或者 (a == d 且 b == c) 。即一张骨牌可以通过旋转 0 度或 180 度得到另一张多米诺骨牌。
     * 在 0 <= i < j < dominoes.length 的前提下，找出满足 dominoes[i] 和 dominoes[j] 等价的骨牌对 (i, j) 的数量。
     * 输入：dominoes = [[1,2],[2,1],[3,4],[5,6]]
     * 输出：1
     */
    public static int numEquivDominoPairs(int[][] dominoes) {
//        int count = 0;
//        for (int i = 0; i < dominoes.length; i++) {
//            int[] inner = dominoes[i];
//            for (int j = i + 1; j < dominoes.length; j++) {
//                int[] nextInner = dominoes[j];
//                if ((inner[0] == nextInner[0] && inner[1] == nextInner[1]) ||
//                        (inner[0] == nextInner[1] && inner[1] == nextInner[0])) {
//                    count++;
//                }
//            }
//        }
//        return count;
        //等价为1+2+3+n
        int[] num = new int[100];
        int ret = 0;
        for (int[] domino : dominoes) {
            int val = domino[0] < domino[1] ? domino[0] * 10 + domino[1] : domino[1] * 10 + domino[0];
            ret += num[val];
            num[val]++;
        }
        return ret;

    }

    /**
     * 泰波那契序列 Tn 定义如下：
     * T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
     * 给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
     * 输入：n = 4
     * 输出：4
     * 解释：
     * T_3 = 0 + 1 + 1 = 2
     * T_4 = 1 + 1 + 2 = 4
     * 5 7
     * 6 13
     * 7 24
     */
    public static int tribonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n <= 2) {
            return 1;
        }
        int p = 0, q = 0, r = 1, s = 1;
        for (int i = 3; i <= n; ++i) {
            p = q;
            q = r;
            r = s;
            s = p + q + r;
        }
        return s;
    }

    /**
     * 根据日期得出一年的第几天
     */
    public int dayOfYear(String date) {
//        LocalDateTime startLocalDateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        return startLocalDateTime.getDayOfYear();
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));

        int[] amount = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            ++amount[1];
        }

        int ans = 0;
        for (int i = 0; i < month - 1; ++i) {
            ans += amount[i];
        }
        return ans + day;
    }

    /**
     * 给你一份『词汇表』（字符串数组） words 和一张『字母表』（字符串） chars。
     * 假如你可以用 chars 中的『字母』（字符）拼写出 words 中的某个『单词』（字符串），那么我们就认为你掌握了这个单词。
     * 注意：每次拼写（指拼写词汇表中的一个单词）时，chars 中的每个字母都只能用一次。
     * 返回词汇表 words 中你掌握的所有单词的 长度之和。
     * 输入：words = ["cat","bt","hat","tree"], chars = "atach"
     * 输出：6
     * 解释：
     * 可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。
     */
    public static int countCharacters(String[] words, String chars) {
        int ans = 0;
        int[] ints = new int[26];
        for (int i = 0; i < chars.length(); i++) {
            ints[chars.charAt(i) - 'a']++;
        }
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            boolean flag = true;
            int[] count = new int[26];
            System.arraycopy(ints, 0, count, 0, 26);
            for (int j = 0; j < word.length(); j++) {
                if (--count[word.charAt(j) - 'a'] < 0) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                ans += word.length();
            }
        }
        return ans;
    }

    static final int MOD = 1000000007;

    /**
     * 请你帮忙给从 1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上；你需要返回可能的方案总数。
     * 让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数的乘积来表示。
     * 由于答案可能会很大，所以请你返回答案 模 mod 10^9 + 7 之后的结果即可。
     * 输入：n = 5
     * 输出：12
     * 解释：举个例子，[1,2,5,4,3] 是一个有效的排列，但 [5,2,3,4,1] 不是，因为在第二种情况里质数 5 被错误地放在索引为 1 的位置上。
     */
    public static int numPrimeArrangements(int n) {
        int numPrimes = 0;
        for (int i = 1; i <= n; i++) {
            if (isPrime(i)) {
                numPrimes++;
            }
        }
        //总的方案数即为「所有质数都放在质数索引上的方案数」×「所有合数都放在合数索引上的方案数」
        return (int) (factorial(numPrimes) * factorial(n - numPrimes) % MOD);
    }

    /**
     * 判断是否为质数
     */
    public static boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 质数或合数的排列总和
     */
    public static long factorial(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
            res %= MOD;
        }
        return res;
    }

    /**
     * 环形公交路线上有 n 个站，按次序从 0 到 n - 1 进行编号。我们已知每一对相邻公交站之间的距离，distance[i] 表示编号为 i 的车站和编号为 (i + 1) % n 的车站之间的距离。
     * 环线上的公交车都可以按顺时针和逆时针的方向行驶。
     * 返回乘客从出发点 start 到目的地 destination 之间的最短距离。
     * 输入：distance = [1,2,3,4], start = 0, destination = 1
     * 输出：1
     * 解释：公交站 0 和 1 之间的距离是 1 或 9，最小值是 1。
     */
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
//        int sum = Arrays.stream(distance).sum();
//        int count = 0;
//        if(start > destination){
//            int tmp = start;
//            start = destination;
//            destination = tmp;
//        }
//        for(int i = start; i < destination; i++){
//            count += distance[i];
//        }
//        return Math.min(sum - count,count);

        if (start > destination) {
            int temp = start;
            start = destination;
            destination = temp;
        }
        int sum1 = 0, sum2 = 0;
        for (int i = 0; i < distance.length; i++) {
            if (i >= start && i < destination) {
                sum1 += distance[i];
            } else {
                sum2 += distance[i];
            }
        }
        return Math.min(sum1, sum2);

    }


    public static String dayOfTheWeek(int day, int month, int year) {
//        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
//        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};
//        /* 输入年份之前的年份的天数贡献 */
//        int days = 365 * (year - 1971) + (year - 1969) / 4;
//        /* 输入年份中，输入月份之前的月份的天数贡献 */
//        for (int i = 0; i < month - 1; ++i) {
//            days += monthDays[i];
//        }
//        if ((year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) && month >= 3) {
//            days += 1;
//        }
//        /* 输入月份中的天数贡献 */
//        days += day;
//        return week[(days + 3) % 7];
        LocalDate date = LocalDate.of(year, month, day);
        String res = date.getDayOfWeek().toString();
        return res.substring(0, 1) + res.substring(1).toLowerCase();
    }

    /**
     * 给你一个字符串 text，你需要使用 text 中的字母来拼凑尽可能多的单词 "balloon"（气球）。
     * 字符串 text 中的每个字母最多只能被使用一次。请你返回最多可以拼凑出多少个单词 "balloon"。
     * 输入：text = "nlaebolko"
     * 输出：1
     */
    public static int maxNumberOfBalloons(String text) {
//        int[] array = new int[26];
//        for(int i = 0; i < text.length(); i++){
//            array[text.charAt(i) - 'a']++;
//        }
//        int ans = 0;
//        for(int i = 0; i < 1428; i++){
//            --array[1];
//            --array[0];
//            array[11] -= 2;
//            array[14] -= 2;
//            --array[13];
//            if(array[1] < 0 || array[0] < 0 || array[11]  < 0 || array[14] < 0 | array[13] < 0){
//                break;
//            }
//            ans++;
//        }
//        return ans;
        int[] cnt = new int[5];
        for (int i = 0; i < text.length(); ++i) {
            char ch = text.charAt(i);
            if (ch == 'b') {
                cnt[0]++;
            } else if (ch == 'a') {
                cnt[1]++;
            } else if (ch == 'l') {
                cnt[2]++;
            } else if (ch == 'o') {
                cnt[3]++;
            } else if (ch == 'n') {
                cnt[4]++;
            }
        }
        cnt[2] /= 2;
        cnt[3] /= 2;
        return Arrays.stream(cnt).min().getAsInt();
    }

    /**
     * 给你个整数数组 arr，其中每个元素都 不相同。
     * 请你找到所有具有最小绝对差的元素对，并且按升序的顺序返回。
     * 每对元素对 [a,b] 如下：
     * a , b 均为数组 arr 中的元素
     * a < b
     * b - a 等于 arr 中任意两个元素的最小绝对差
     * 输入：arr = [4,2,1,3]
     * 输出：[[1,2],[2,3],[3,4]]
     */
    public static List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            List<Integer> list = new ArrayList<>();
            int minus = arr[i] - arr[i - 1];
            if (minus < min) {
                min = minus;
                result.clear();
                list.add(arr[i - 1]);
                list.add(arr[i]);
                result.add(list);
            } else if (minus == min) {
                list.add(arr[i - 1]);
                list.add(arr[i]);
                result.add(list);
            }

        }
        return result;
    }

    /**
     * 给你一个整数数组 arr，请你帮忙统计数组中每个数的出现次数。
     * 如果每个数的出现次数都是独一无二的，就返回 true；否则返回 false。
     * 输入：arr = [1,2,2,1,1,3]
     * 输出：true
     * 解释：在该数组中，1 出现了 3 次，2 出现了 2 次，3 只出现了 1 次。没有两个数的出现次数相同。
     */
    public boolean uniqueOccurrences(int[] arr) {
//        int[] array = new int[2000];
//        for(int i : arr){
//            array[i + 1000]++;
//        }
//        Set<Integer> set = new HashSet<>();
//        for(int i : array){
//            if(i != 0 && !set.add(i)){
//                return false;
//            }
//        }
//        return true;
        Map<Integer, Integer> occur = new HashMap<Integer, Integer>();
        for (int x : arr) {
            occur.put(x, occur.getOrDefault(x, 0) + 1);
        }
        Set<Integer> times = new HashSet<Integer>();
        for (Map.Entry<Integer, Integer> x : occur.entrySet()) {
            times.add(x.getValue());
        }
        return times.size() == occur.size();
    }

    /**
     * 有 n 个筹码。第 i 个筹码的位置是 position[i] 。
     * 我们需要把所有筹码移到同一个位置。在一步中，我们可以将第 i 个筹码的位置从 position[i] 改变为:
     * position[i] + 2 或 position[i] - 2 ，此时 cost = 0
     * position[i] + 1 或 position[i] - 1 ，此时 cost = 1
     * 返回将所有筹码移动到同一位置上所需要的 最小代价 。
     * 输入：position = [1,2,3]
     * 输出：1
     * 解释：第一步:将位置3的筹码移动到位置1，成本为0。
     * 第二步:将位置2的筹码移动到位置1，成本= 1。
     * 总成本是1。
     */
    public int minCostToMoveChips(int[] position) {
        //那么我们可以把初始每一个偶数位置的「筹码」看作一个整体，每一个奇数位置的「筹码」看作一个整体。
        // 因为我们的目标是最后将全部的「筹码」移动到同一个位置，那么最后的位置只有两种情况：
        //移动到某一个偶数位置，此时的开销最小值就是初始奇数位置「筹码」的数量。
        //移动到某一个奇数位置，此时的开销最小值就是初始偶数位置「筹码」的数量。
        int odd = 0;
        int even = 0;
        for (int index : position) {
            if (index % 2 == 0) {
                even++;
            } else {
                odd++;
            }
        }
        return Math.min(odd, even);
    }

    /**
     * 平衡字符串 中，'L' 和 'R' 字符的数量是相同的。
     * 给你一个平衡字符串 s，请你将它分割成尽可能多的子字符串，并满足：
     * 每个子字符串都是平衡字符串。
     * 返回可以通过分割得到的平衡字符串的 最大数量 。
     * 输入：s = "RLRRLLRLRL"
     * 输出：4
     * 解释：s 可以分割为 "RL"、"RRLL"、"RL"、"RL" ，每个子字符串中都包含相同数量的 'L' 和 'R' 。
     */
    public int balancedStringSplit(String s) {
        int ans = 0;
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 给定一个数组 coordinates ，其中 coordinates[i] = [x, y] ， [x, y] 表示横坐标为 x、纵坐标为 y 的点。
     * 请你来判断，这些点是否在该坐标系中属于同一条直线上。
     * 输入：coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
     * 输出：true
     */
    public static boolean checkStraightLine(int[][] coordinates) {
//        int deltaX = coordinates[0][0], deltaY = coordinates[0][1];
//        int n = coordinates.length;
//        for (int i = 0; i < n; i++) {
//            coordinates[i][0] -= deltaX;
//            coordinates[i][1] -= deltaY;
//        }
//        int A = coordinates[1][1], B = -coordinates[1][0];
//        for (int i = 2; i < n; i++) {
//            int x = coordinates[i][0], y = coordinates[i][1];
//            if (A * x + B * y != 0) {
//                return false;
//            }
//        }
//        return true;

        for (int i = 1; i < coordinates.length - 1; i++) {
            int res1 = (coordinates[i][0] - coordinates[i - 1][0]) * (coordinates[i + 1][1] - coordinates[i][1]);
            int res2 = (coordinates[i + 1][0] - coordinates[i][0]) * (coordinates[i][1] - coordinates[i - 1][1]);
            if (res1 != res2) {
                return false;
            }
        }
        return true;
    }

    /**
     * 给你一个 m x n 的矩阵，最开始的时候，每个单元格中的值都是 0。
     * 另有一个二维索引数组 indices，indices[i] = [ri, ci] 指向矩阵中的某个位置，其中 ri 和 ci 分别表示指定的行和列（从 0 开始编号）。
     * 对 indices[i] 所指向的每个位置，应同时执行下述增量操作：
     * ri 行上的所有单元格，加 1 。
     * ci 列上的所有单元格，加 1 。
     * 给你 m、n 和 indices 。请你在执行完所有 indices 指定的增量操作后，返回矩阵中 奇数值单元格 的数目。
     * 输入：m = 2, n = 3, indices = [[0,1],[1,1]]
     * 输出：6
     * 解释：最开始的矩阵是 [[0,0,0],[0,0,0]]。
     * 第一次增量操作后得到 [[1,2,1],[0,1,0]]。
     * 最后的矩阵是 [[1,3,1],[1,3,1]]，里面有 6 个奇数。
     */
    public static int oddCells(int m, int n, int[][] indices) {
        int[] rows = new int[m];
        int[] cols = new int[n];
        for (int[] index : indices) {
            rows[index[0]]++;
            cols[index[1]]++;
        }
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((rows[i] + cols[j]) % 2 != 0) {
                    res++;
                }
            }
        }
        return res;
//        int[][] result = new int[m][n];
//        int[] rowArray = new int[m];
//        int[] lieArray = new int[n];
//        for(int i = 0; i < indices.length; i++){
//            rowArray[indices[i][0]]++;
//            lieArray[indices[i][1]]++;
//        }
//        for(int row = 0; row < rowArray.length; row++){
//            if(rowArray[row] > 0){
//                for(int num = 0; num < rowArray[row]; num++){
//                    for(int j = 0; j < n; j++){
//                        result[row][j] = result[row][j] + 1;
//                    }
//                }
//            }
//        }
//        for(int lie = 0; lie < lieArray.length; lie++){
//            if(lieArray[lie] > 0){
//                for(int num = 0; num < lieArray[lie]; num++){
//                    for(int j = 0; j < m; j++){
//                        result[j][lie] = result[j][lie] + 1;
//                    }
//
//                }
//            }
//        }
//        int ans = 0;
//        for(int i = 0; i < result.length; i++){
//            for(int j = 0; j < result[i].length; j++){
//                if(result[i][j] % 2 != 0){
//                    ans++;
//                }
//            }
//        }
//        return ans;
    }

    /**
     * 给你一个 m 行 n 列的二维网格 grid 和一个整数 k。你需要将 grid 迁移 k 次。
     * 每次「迁移」操作将会引发下述活动：
     * 位于 grid[i][j] 的元素将会移动到 grid[i][j + 1]。
     * 位于 grid[i][n - 1] 的元素将会移动到 grid[i + 1][0]。
     * 位于 grid[m - 1][n - 1] 的元素将会移动到 grid[0][0]。
     * 请你返回 k 次迁移操作后最终得到的 二维网格。
     * <p>
     * 输入：grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
     * 输出：[[9,1,2],[3,4,5],[6,7,8]]
     */
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        for (int i = 0; i < k; i++) {
            int shift[][] = new int[m][n];
            for (int j = 1; j < n; j++) {
                for (int p = 0; p < m; p++) {
                    shift[p][j] = grid[p][j - 1];
                }
            }
            for (int j = 1; j < m; j++) {
                shift[j][0] = grid[j - 1][n - 1];
            }
            shift[0][0] = grid[m - 1][n - 1];
            grid = shift;
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                list.add(grid[i][j]);
            }
            ans.add(list);
        }
        return ans;
//        int m = grid.length;
//        int n = grid[0].length;
//        List<List<Integer>> ret = new ArrayList<List<Integer>>();
//        for (int i = 0; i < m; i++) {
//            List<Integer> row = new ArrayList<Integer>();
//            for (int j = 0; j < n; j++) {
//                row.add(0);
//            }
//            ret.add(row);
//        }
//        //元素 grid[i][j] 在该一维数组的下标为 index=i×n+j。
//        //那么 k 次迁移操作之后，元素 grid[i][j] 在该一维数组的下标变为 index1=(index+k)mod(m×n)
//        //在网格的位置变为 grid[⌊index1/n⌋][index mod n]
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                int index1 = (i * n + j + k) % (m * n);
//                ret.get(index1 / n).set(index1 % n, grid[i][j]);
//            }
//        }
//        return ret;
    }

    /**
     * 平面上有 n 个点，点的位置用整数坐标表示 points[i] = [xi, yi] 。请你计算访问所有这些点需要的 最小时间（以秒为单位）。
     * 你需要按照下面的规则在平面上移动：
     * 每一秒内，你可以：
     * 沿水平方向移动一个单位长度，或者
     * 沿竖直方向移动一个单位长度，或者
     * 跨过对角线移动 sqrt(2) 个单位长度（可以看作在一秒内向水平和竖直方向各移动一个单位长度）。
     * 必须按照数组中出现的顺序来访问这些点。
     * 在访问某个点时，可以经过该点后面出现的点，但经过的那些点不算作有效访问
     * 输入：points = [[1,1],[3,4],[-1,0]]
     * 输出：7
     * 解释：一条最佳的访问路径是： [1,1] -> [2,2] -> [3,3] -> [3,4] -> [2,3] -> [1,2] -> [0,1] -> [-1,0]
     * 从 [1,1] 到 [3,4] 需要 3 秒
     * 从 [3,4] 到 [-1,0] 需要 4 秒
     * 一共需要 7 秒
     */
    public int minTimeToVisitAllPoints(int[][] points) {
        int ans = 0;
        for (int i = 0; i < points.length - 1; i++) {
            int[] array = points[i];
            int[] next = points[i + 1];
            int row = Math.abs(next[0] - array[0]);
            int lie = Math.abs(next[1] - array[1]);
            ans += Math.max(row, lie);
        }
        return ans;
    }

    /**
     * A 和 B 在一个 3 x 3 的网格上玩井字棋。
     * 井字棋游戏的规则如下：
     * 玩家轮流将棋子放在空方格 (" ") 上。
     * 第一个玩家 A 总是用 "X" 作为棋子，而第二个玩家 B 总是用 "O" 作为棋子。
     * "X" 和 "O" 只能放在空方格中，而不能放在已经被占用的方格上。
     * 只要有 3 个相同的（非空）棋子排成一条直线（行、列、对角线）时，游戏结束。
     * 如果所有方块都放满棋子（不为空），游戏也会结束。
     * 游戏结束后，棋子无法再进行任何移动。
     * 给你一个数组 moves，其中每个元素是大小为 2 的另一个数组（元素分别对应网格的行和列），它按照 A 和 B 的行动顺序（先 A 后 B）记录了两人各自的棋子位置。
     * 如果游戏存在获胜者（A 或 B），就返回该游戏的获胜者；如果游戏以平局结束，则返回 "Draw"；如果仍会有行动（游戏未结束），则返回 "Pending"。
     * 你可以假设 moves 都 有效（遵循井字棋规则），网格最初是空的，A 将先行动。
     * 输入：moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
     * 输出："A"
     * 解释："A" 获胜，他总是先走。
     * "X  "    "X  "    "X  "    "X  "    "X  "
     * "   " -> "   " -> " X " -> " X " -> " X "
     * "   "    "O  "    "O  "    "OO "    "OOX"
     */
    public String tictactoe(int[][] moves) {
        int len = moves.length;
        if (len < 5) {
            return "Pending";
        }
        String[][] array = new String[3][3];
        for (int i = 0; i < moves.length; i++) {
            //odd为true表示A，否则表示B
            boolean odd = i % 2 == 0;
            if (odd) {
                array[moves[i][0]][moves[i][1]] = "X";
            } else {
                array[moves[i][0]][moves[i][1]] = "O";
            }
            //可以开始判断是否有赢家
            if (i >= 4) {
                int row = moves[i][0];
                int lie = moves[i][1];
                if (array[row][0] != null && array[row][1] != null && array[row][2] != null && array[row][0].equals(array[row][1]) && array[row][1].equals(array[row][2])) {
                    return odd ? "A" : "B";
                }
                if (array[0][lie] != null && array[1][lie] != null && array[2][lie] != null && array[0][lie].equals(array[1][lie]) && array[1][lie].equals(array[2][lie])) {
                    return odd ? "A" : "B";
                }
                if (array[0][0] != null && array[1][1] != null && array[2][2] != null && array[0][0].equals(array[1][1]) && array[1][1].equals(array[2][2])) {
                    return odd ? "A" : "B";
                }
                if (array[0][2] != null && array[1][1] != null && array[2][0] != null && array[0][2].equals(array[1][1]) && array[1][1].equals(array[2][0])) {
                    return odd ? "A" : "B";
                }
            }
        }
        if (len == 9) {
            return "Draw";
        }
        return "Pending";
    }

    /**
     * 给你一个非递减的 有序 整数数组，已知这个数组中恰好有一个整数，它的出现次数超过数组元素总数的 25%。
     * 请你找到并返回这个整数
     * 输入：arr = [1,2,2,6,6,6,6,7,10]
     * 输出：6
     */
    public int findSpecialInteger(int[] arr) {
        int count = 0;
        int pre = arr[0];
        for (int num : arr) {
            if (num == pre) {
                count++;
                if (count > arr.length / 4) {
                    return num;
                }
            } else {
                pre = num;
                count = 1;
            }
        }
        return count;
    }

    /**
     * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
     * 请你返回该链表所表示数字的 十进制值 。
     * 输入：head = [1,0,1]
     * 输出：5
     * 解释：二进制数 (101) 转化为十进制数 (5)
     */
    public int getDecimalValue(ListNode head) {
        StringBuilder stringBuilder = new StringBuilder();
        dfs(head, stringBuilder);
        return Integer.parseInt(stringBuilder.toString(), 2);
    }

    public void dfs(ListNode head, StringBuilder stringBuilder) {
        stringBuilder.append(head.val);
        if (head.next != null) {
            dfs(head.next, stringBuilder);
        }
    }

    /**
     * 给你一个整数数组 nums，请你返回其中位数为 偶数 的数字的个数。
     * 输入：nums = [12,345,2,6,7896]
     * 输出：2
     * 解释：
     * 12 是 2 位数字（位数为偶数）
     * 345 是 3 位数字（位数为奇数）
     * 2 是 1 位数字（位数为奇数）
     * 6 是 1 位数字 位数为奇数）
     * 7896 是 4 位数字（位数为偶数）
     * 因此只有 12 和 7896 是位数为偶数的数字
     */
    public int findNumbers(int[] nums) {
//        int ans = 0;
//        for(int num : nums){
//            if(String.valueOf(num).length() % 2 == 0){
//                ans++;
//            }
//        }
//        return ans;
        int count = 0;
        for (int num : nums) {
            if (getLength(num) % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public int getLength(int num) {
        int length = 0;
        while (num > 0) {
            length++;
            num /= 10;
        }
        return length;
    }

    /**
     * 给你一个数组 arr ，请你将每个元素用它右边最大的元素替换，如果是最后一个元素，用 -1 替换。
     * 完成所有替换操作后，请你返回这个数组。
     * 输入：arr = [17,18,5,4,6,1]
     * 输出：[18,6,6,6,1,-1]
     * 解释：
     * - 下标 0 的元素 --> 右侧最大元素是下标 1 的元素 (18)
     * - 下标 1 的元素 --> 右侧最大元素是下标 4 的元素 (6)
     * - 下标 2 的元素 --> 右侧最大元素是下标 4 的元素 (6)
     * - 下标 3 的元素 --> 右侧最大元素是下标 4 的元素 (6)
     * - 下标 4 的元素 --> 右侧最大元素是下标 5 的元素 (1)
     * - 下标 5 的元素 --> 右侧没有其他元素，替换为 -1
     */
    public int[] replaceElements(int[] arr) {
//        for(int i = 0; i < arr.length; i++){
//            int max = -1;
//            for(int j = i + 1; j < arr.length; j++){
//                max = Math.max(max,arr[j]);
//            }
//            arr[i] = max;
//        }
//        return arr;
        int n = arr.length;
        int[] replaced = new int[n];
        replaced[n - 1] = -1;
        for (int i = n - 2; i >= 0; i--) {
            replaced[i] = Math.max(replaced[i + 1], arr[i + 1]);
        }
        return replaced;
    }

    /**
     * 给你一个整数 n，请你返回 任意 一个由 n 个 各不相同 的整数组成的数组，并且这 n 个数相加和为 0 。
     * 输入：n = 3
     * 输出：[-1,0,1]
     */
    public static int[] sumZero(int n) {
        int[] array = new int[n];
        int num = n / 2;
        for (int i = 0; i < n; i++) {
            if (n % 2 == 0 && num == 0) {
                num--;
            }
            array[i] = num;
            num--;
        }
        return array;
    }

    /**
     * 给你一个字符串 s，它由数字（'0' - '9'）和 '#' 组成。我们希望按下述规则将 s 映射为一些小写英文字符：
     * 字符（'a' - 'i'）分别用（'1' - '9'）表示。
     * 字符（'j' - 'z'）分别用（'10#' - '26#'）表示。
     * 返回映射之后形成的新字符串。
     * 题目数据保证映射始终唯一。
     * 输入：s = "10#11#12"
     * 输出："jkab"
     * 解释："j" -> "10#" , "k" -> "11#" , "a" -> "1" , "b" -> "2".
     */
    public String freqAlphabets(String s) {
        String[] aiKey = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i"};
        String[] aiValue = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] jzKey = new String[]{"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] jzValue = new String[]{"10#", "11#", "12#", "13#", "14#", "15#", "16#", "17#", "18#", "19#", "20#", "21#", "22#", "23#", "24#", "25#", "26#"};
        for (int i = 0; i < jzValue.length; i++) {
            s = s.replace(jzValue[i], jzKey[i]);
        }
        for (int i = 0; i < aiValue.length; i++) {
            s = s.replace(aiValue[i], aiKey[i]);
        }
        return s;
    }

    /**
     * 给你一个以行程长度编码压缩的整数列表 nums 。
     * 考虑每对相邻的两个元素 [freq, val] = [nums[2*i], nums[2*i+1]] （其中 i >= 0 ），每一对都表示解压后子列表中有 freq 个值为 val 的元素，你需要从左到右连接所有子列表以生成解压后的列表。
     * 请你返回解压后的列表。
     * 输入：nums = [1,2,3,4]
     * 输出：[2,4,4,4]
     * 解释：第一对 [1,2] 代表着 2 的出现频次为 1，所以生成数组 [2]。
     * 第二对 [3,4] 代表着 4 的出现频次为 3，所以生成数组 [4,4,4]。
     * 最后将它们串联到一起 [2] + [4,4,4] = [2,4,4,4]。
     */
    public int[] decompressRLElist(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length / 2; i++) {
            int count = nums[2 * i];
            int num = nums[2 * i + 1];
            for (int j = 0; j < count; j++) {
                list.add(num);
            }
        }
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 「无零整数」是十进制表示中 不含任何 0 的正整数。
     * 给你一个整数 n，请你返回一个 由两个整数组成的列表 [A, B]，满足：
     * A 和 B 都是无零整数
     * A + B = n
     * 题目数据保证至少有一个有效的解决方案。
     * 如果存在多个有效解决方案，你可以返回其中任意一个。
     * 输入：n = 2
     * 输出：[1,1]
     * 解释：A = 1, B = 1. A + B = n 并且 A 和 B 的十进制表示形式都不包含任何 0 。
     */
    public int[] getNoZeroIntegers(int n) {
        int[] array = new int[2];
        for (int i = 1; i < n / 2 + 1; i++) {
            if (String.valueOf(i).contains("0") || String.valueOf(n - i).contains("0")) {
                continue;
            }
            array[0] = i;
            array[1] = n - i;
            break;
        }
        return array;
    }

    /**
     * 给你两个字符串数组 words1 和 words2 ，请你返回在两个字符串数组中 都恰好出现一次 的字符串的数目。
     * 输入：words1 = ["leetcode","is","amazing","as","is"], words2 = ["amazing","leetcode","is"]
     * 输出：2
     */
    public int countWords(String[] words1, String[] words2) {
        // 统计字符串出现频率
        Map<String, Integer> freq1 = new HashMap<>();
        Map<String, Integer> freq2 = new HashMap<>();
        for (String w : words1) {
            freq1.put(w, freq1.getOrDefault(w, 0) + 1);
        }
        for (String w : words2) {
            freq2.put(w, freq2.getOrDefault(w, 0) + 1);
        }
        // 遍历 words1 出现的字符并判断是否满足要求
        int res = 0;
        for (String w : freq1.keySet()) {
            if (freq1.get(w) == 1 && freq2.getOrDefault(w, 0) == 1) {
                res++;
            }
        }
        return res;
    }

    /**
     * 给你一个仅由数字 6 和 9 组成的正整数 num。
     * 你最多只能翻转一位数字，将 6 变成 9，或者把 9 变成 6 。
     * 请返回你可以得到的最大数字。
     */
    public int maximum69Number(int num) {
        String number = String.valueOf(num);
        number = number.replaceFirst("6", "9");
        return Integer.parseInt(number);
    }

    /**
     * 给你一个整数数组 arr ，请你将数组中的每个元素替换为它们排序后的序号。
     * 序号代表了一个元素有多大。序号编号的规则如下：
     * 序号从 1 开始编号。
     * 一个元素越大，那么序号越大。如果两个元素相等，那么它们的序号相同。
     * 每个数字的序号都应该尽可能地小。
     * 输入：arr = [40,10,20,30]
     * 输出：[4,1,2,3]
     * 解释：40 是最大的元素。 10 是最小的元素。 20 是第二小的数字。 30 是第三小的数字。
     */
    public static int[] arrayRankTransform(int[] arr) {
//        int[] copy = new int[arr.length];
//        System.arraycopy(arr,0,copy,0,arr.length);
//        Arrays.sort(copy);
//        int[] result = new int[arr.length];
//        int count  = 1;
//        int pre = 0;
//        for(int i : copy){
//            if(i == pre){
//                continue;
//            }
//            pre = i;
//            for(int j = 0; j < arr.length; j++){
//                if(i == arr[j]){
//                    result[j] = count;
//                }
//            }
//            count++;
//        }
//        return result;

        int[] sortedArr = new int[arr.length];
        System.arraycopy(arr, 0, sortedArr, 0, arr.length);
        Arrays.sort(sortedArr);
        Map<Integer, Integer> ranks = new HashMap<Integer, Integer>();
        int[] ans = new int[arr.length];
        for (int a : sortedArr) {
            if (!ranks.containsKey(a)) {
                ranks.put(a, ranks.size() + 1);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            ans[i] = ranks.get(arr[i]);
        }
        return ans;
    }

    /**
     * 给你一个字符串 s，它仅由字母 'a' 和 'b' 组成。每一次删除操作都可以从 s 中删除一个回文 子序列。
     * 返回删除给定字符串中所有字符（字符串为空）的最小删除次数。
     * 「子序列」定义：如果一个字符串可以通过删除原字符串某些字符而不改变原字符顺序得到，那么这个字符串就是原字符串的一个子序列。
     * 「回文」定义：如果一个字符串向后和向前读是一致的，那么这个字符串就是一个回文。
     * 输入：s = "ababa"
     * 输出：1
     * 解释：字符串本身就是回文序列，只需要删除一次。
     */
    public int removePalindromeSub(String s) {
        /*
            如果该字符串本身为回文串，此时只需删除 1 次即可，删除次数为 1。
            如果该字符串本身不是回文串，此时只需删除 2 次即可，比如可以先删除所有的 ‘a’，再删除所有的 ‘b’，删除次数为 2。
         */
        int n = s.length();
        for (int i = 0; i < n / 2; ++i) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
                return 2;
            }
        }
        return 1;
    }

    /**
     * 给你一个大小为 m * n 的矩阵 mat，矩阵由若干军人和平民组成，分别用 1 和 0 表示。
     * 请你返回矩阵中战斗力最弱的 k 行的索引，按从最弱到最强排序。
     * 如果第 i 行的军人数量少于第 j 行，或者两行军人数量相同但 i 小于 j，那么我们认为第 i 行的战斗力比第 j 行弱。
     * 军人 总是 排在一行中的靠前位置，也就是说 1 总是出现在 0 之前。
     * 输入：mat =
     * [[1,1,0,0,0],
     * [1,1,1,1,0],
     * [1,0,0,0,0],
     * [1,1,0,0,0],
     * [1,1,1,1,1]],
     * k = 3
     * 输出：[2,0,3]
     */
    public int[] kWeakestRows(int[][] mat, int k) {
//        Map<Integer,Integer> map = new HashMap<>();
//        int[] result = new int[k];
//        for(int i = 0; i < mat.length; i++){
//            int count = 0;
//            for(int j = 0; j < mat[0].length; j++){
//                if(mat[i][j] == 0){
//                    break;
//                }else {
//                    count++;
//                }
//            }
//            map.put(i,count);
//        }
//        map = map.entrySet().stream().sorted(((item1, item2) -> {
//            int compare = item1.getValue().compareTo(item2.getValue());
//            if (compare == 0) {
//                if (item1.getKey() < item2.getKey()) {
//                    compare = -1;
//                } else if (item1.getKey() > item2.getKey()) {
//                    compare = 1;
//                }
//            }
//            return compare;
//        })).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//        List<Integer> keyList = new ArrayList<>(map.keySet());
//        for(int i = 0; i < k; i++){
//            result[i] = keyList.get(i);
//        }
//        return result;
        int[] list = new int[mat.length];
        int[] result = new int[k];
        for (int i = 0; i < mat.length; i++) {
            list[i] = count(mat[i]) * 100 + i;
        }
        Arrays.sort(list);
        for (int i = 0; i < k; i++) {
            result[i] = list[i] % 100;
        }
        return result;
    }

    public int count(int[] nums) {
        int sum = 0;
        for (int n : nums) {
            if (n != 1) {
                break;
            }
            sum += n;
        }
        return sum;
    }

    static int stepNum = 0;

    /**
     * 给你一个非负整数 num ，请你返回将它变成 0 所需要的步数。 如果当前数字是偶数，你需要把它除以 2 ；否则，减去 1 。
     * 输入：num = 8
     * 输出：4
     * 解释：
     * 步骤 1） 8 是偶数，除以 2 得到 4 。
     * 步骤 2） 4 是偶数，除以 2 得到 2 。
     * 步骤 3） 2 是偶数，除以 2 得到 1 。
     * 步骤 4） 1 是奇数，减 1 得到 0 。
     */
    public static int numberOfSteps(int num) {
        if (num == 0) {
            return stepNum;
        }
        stepNum++;
        if (num % 2 == 0) {
            numberOfSteps(num / 2);
        } else {
            numberOfSteps(num - 1);
        }
        return stepNum;
    }

    /**
     * 给你一个整数数组 arr，请你检查是否存在两个整数 N 和 M，满足 N 是 M 的两倍（即，N = 2 * M）。
     * 输入：arr = [10,2,5,3]
     * 输出：true
     * 解释：N = 10 是 M = 5 的两倍，即 10 = 2 * 5 。
     */
    public boolean checkIfExist(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for(int j = i + 1; j < arr.length; j++){
                if(arr[i] == 2 * arr[j] || 2 * arr[i] == arr[j]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 给你一个 m * n 的矩阵 grid，矩阵中的元素无论是按行还是按列，都以非递增顺序排列。 请你统计并返回 grid 中 负数 的数目。
     * 输入：grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
     * 输出：8
     * 解释：矩阵中共有 8 个负数。
     */
    public int countNegatives(int[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = grid[0].length - 1; j >= 0; j--){
                if(grid[i][j] < 0){
                    count++;
                }else {
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 给你一个整数数组 arr 。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排序。
     * 如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。
     * 请你返回排序后的数组。
     * 输入：arr = [0,1,2,3,4,5,6,7,8]
     * 输出：[0,1,2,4,8,3,5,6,7]
     * 解释：[0] 是唯一一个有 0 个 1 的数。
     * [1,2,4,8] 都有 1 个 1 。
     * [3,5,6] 有 2 个 1 。
     * [7] 有 3 个 1 。d
     * 按照 1 的个数排序得到的结果数组为 [0,1,2,4,8,3,5,6,7]
     */
    public static int[] sortByBits(int[] arr) {
        Map<Integer,Integer> outerMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++){
            int num = arr[i];
            int count = 0;
            while (num != 0){
                count += num % 2;
                num = num / 2;
            }
            outerMap.put(i,count);
        }
        List<Integer> keyList = outerMap.entrySet().stream().sorted((o1, o2) -> {
            if(Objects.equals(o1.getValue(), o2.getValue())){
                return arr[o1.getKey()] - arr[o2.getKey()];
            }else {
                return o1.getValue() - o2.getValue();
            }
        }).map(Map.Entry::getKey).collect(Collectors.toList());
        int[] result = new int[arr.length];
        for(int i = 0; i < arr.length; i++){
            result[i] = arr[keyList.get(i)];
        }
        return result;
    }


    public static void main(String[] args) {
//        System.out.println(sortByBits(new int[]{1024,512,256,128,64,32,16,8,4,2,1}));
//        System.out.println(numberOfSteps(8));
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate start = new Date().toInstant().atZone(zoneId).toLocalDate();
        Date end = Date.from(start.atStartOfDay(zoneId).plusDays(1L).minusSeconds(1L).toInstant());
        Date begin = Date.from(start.atStartOfDay(zoneId).toInstant());
        System.out.println(begin);
        System.out.println(start);
        System.out.println(end);
        Stream<String> nullStream = Stream.of();
        System.out.println(nullStream.count());
//        System.out.println(arrayRankTransform(new int[]{40,10,20,30}));
//        System.out.println(sumZero(4));
//        System.out.println(oddCells(48, 37, new int[][]{{40, 5}}));
//        System.out.println(checkStraightLine(new int[][]{{0,1},{1,3},{-4,-7},{5,11}}));
//        System.out.println(minimumAbsDifference(new int[]{40,11,26,27,-20}));
//        System.out.println(maxNumberOfBalloons("krhizmmgmcrecekgyljqkldocicziihtgpqwbticmvuyznragqoyrukzopfmjhjjxemsxmrsxuqmnkrzhgvtgdgtykhcglurvppvcwhrhrjoislonvvglhdciilduvuiebmffaagxerjeewmtcwmhmtwlxtvlbocczlrppmpjbpnifqtlninyzjtmazxdbzwxthpvrfulvrspycqcghuopjirzoeuqhetnbrcdakilzmklxwudxxhwilasbjjhhfgghogqoofsufysmcqeilaivtmfziumjloewbkjvaahsaaggteppqyuoylgpbdwqubaalfwcqrjeycjbbpifjbpigjdnnswocusuprydgrtxuaojeriigwumlovafxnpibjopjfqzrwemoinmptxddgcszmfprdrichjeqcvikynzigleaajcysusqasqadjemgnyvmzmbcfrttrzonwafrnedglhpudovigwvpimttiketopkvqw"));
//        System.out.println(dayOfTheWeek(4,1,2024));
//        System.out.println(factorial(3));
//        System.out.println(countCharacters(new String[]{"cat","bt","hat","tree"},"atach"));
//        System.out.println(tribonacci(7));
//        System.out.println(numEquivDominoPairs(new int[][]{{1, 2}, {1,2}, {1,1}, {1,2},{2,2}}));
    }
}
