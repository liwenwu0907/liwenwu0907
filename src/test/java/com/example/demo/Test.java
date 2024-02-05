package com.example.demo;

import cn.hutool.core.date.LocalDateTimeUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Test {

    private synchronized static void methodA(String str) {
        try {
            Thread.sleep(5000);
            Map<String, Object> map = new HashMap();
            for (Map.Entry entry : map.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str);
    }

    private static void methodB(String str) {
        System.out.println(str);
    }

    private static void lockTest(String bh) {
        synchronized (bh) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(bh);
        }
    }

    private static List<String> changeCardStringToList(String begin, String end, int size) {
        List<String> list = new ArrayList<>();
        //截取前16位
        String prefix = begin.substring(0, 16);
        //截取起始卡号后4位
        String beginSuffix = begin.substring(16);
        //截取结束卡号后4位
        String endSuffix = end.substring(16);
        //将两个先放入集合中
        list.add(begin);
        //将beginSuffix转为int，不足4位补0
        int beginInt = Integer.parseInt(beginSuffix);
        for (int i = 0; i < size - 2; i++) {
            beginInt++;
            //0代表前面要补的字符,4代表字符串长度,d表示参数为整数类型
            String need = String.format("%04d", beginInt);
            //将前缀拼接好，放入集合中
            list.add(prefix + need);
        }
        list.add(end);
        return list;
    }

    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static long diffMinutes(Date before, Date after) {
        Calendar calendarBefore = Calendar.getInstance();
        Calendar calendarAfter = Calendar.getInstance();
        calendarBefore.setTime(before);
        calendarAfter.setTime(after);
        long diff = calendarAfter.getTimeInMillis() - calendarBefore.getTimeInMillis();
        long diffMinutes = diff / (60 * 1000);
        return diffMinutes;
    }



    public static String generateLengthNumber(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    static Set<String> randomNumSet = new HashSet<>();

    private static String generateNoRepeatYzm() {
        String randomNum = generateLengthNumber(2);
        if (randomNumSet.contains(randomNum)) {
            System.out.println("重复数据:" + randomNum);
            generateNoRepeatYzm();
        } else {
            randomNumSet.add(randomNum);
        }
        System.out.println(randomNum);
        return randomNum;
    }

    public static synchronized void methodA(){
        System.out.println("a");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String hex10To16(int value){
        return String.format("%08X",value);
    }


    public static String Encrypt(String sSrc, String sKey,String cKey) throws Exception {
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
        IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    public static String Decrypt(String sSrc, String sKey,String cKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(cKey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.getDecoder().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    //加密
    public static String encrypt(String data, String key) {
        String ivString = key;
        //偏移量
        byte[] iv = ivString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            //设置偏移量参数
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryped = cipher.doFinal(plaintext);
            return Base64.getEncoder().encodeToString(encryped);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String desEncrypt(String data, String key) {

        String ivString = key;
        byte[] iv = ivString.getBytes();

        try {
            byte[] encryp = Base64.getDecoder().decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(encryp);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void sorted(){
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //串行排序
        long t0 = System.nanoTime();
        long count = values.stream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));


        //并行排序
        long t2 = System.nanoTime();

        long count2 = values.parallelStream().sorted().count();
        System.out.println(count2);

        long t3 = System.nanoTime();

        long millis4 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
        System.out.println(String.format("parallel sort took: %d ms", millis4));

    }

    public static void testThread() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("22222222");
        });
        t1.start();
        t1.join();
        // 这行代码必须要等t1全部执行完毕，才会执行
        System.out.println("1111");
    }



    public static void main(String[] args) throws Exception {

        Map hashMap = new HashMap();
        hashMap.put("projectId",1);
        hashMap.put("orderNo","22");
        hashMap.put("status",22);
        Long projectId = Long.parseLong(hashMap.get("projectId").toString());
        System.out.println(projectId);
        String orderNo = (String) hashMap.get("orderNo");
        Integer status =  (Integer) hashMap.get("status");
        System.out.println(orderNo);
        System.out.println(status);
        // 新建一个 BufferedInputStream 对象
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("input.txt"));
        // 读取文件的内容并复制到 String 对象中
        int content;
        while ((content = bufferedInputStream.read()) != -1){
            System.out.println((char)content);
        }
        System.out.println(UUID.randomUUID().toString().replaceAll("-","").toUpperCase());
        for(int i=1;i<6;i++){
            System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
//            testThread();
        }
        System.out.println(Clock.systemDefaultZone().millis());
        System.out.println(System.currentTimeMillis());
        System.out.println(ZoneId.getAvailableZoneIds());
        System.out.println(Clock.systemDefaultZone().getZone());
        LocalTime localTime = LocalTime.now();
        Thread.sleep(1000);
        LocalTime localTime2 = LocalTime.now();
        System.out.println(localTime.isBefore(localTime2));
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateTimeFormatter.format(localDateTime));
        System.out.println(dayOfWeek);
        System.out.println(localDateTime);
        System.out.println(LocalDateTime.parse("2021-01-01 00:00:00",dateTimeFormatter).format(dateTimeFormatter));

        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        System.out.println(date);
        System.out.println("<<<--------------------------------------->>>");
        long hourBetween = ChronoUnit.HOURS.between(localTime,localTime2);
        long minuteBetween = ChronoUnit.MINUTES.between(localTime,localTime2);
        long secondBetween = ChronoUnit.SECONDS.between(localTime,localTime2);//1
        System.out.println("hourBetween:"+hourBetween);
        System.out.println("minuteBetween:"+minuteBetween);
        System.out.println("secondBetween:"+secondBetween);
        //sorted();
        //of()：为非null的值创建一个Optional
        Optional<String> optional = Optional.of("bam");
        // isPresent()： 如果值存在返回true，否则返回false
        System.out.println(optional.isPresent());        // true
        //get()：如果Optional有值则将其返回，否则抛出NoSuchElementException
        System.out.println(optional.get());                 // "bam"
        //orElse()：如果有值则将其返回，否则返回指定的其它值
        System.out.println(optional.orElse("fallback"));    // "bam"
        //ifPresent()：如果Optional实例有值则为其调用consumer，否则不做处理
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"


        Stream.iterate(1, i -> i + 1).limit(10).forEach(System.out::println);
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        System.out.println(names.stream().map(String::toUpperCase).sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        names.sort(Comparator.reverseOrder());
        Collections.sort(names, Comparator.reverseOrder());
        String[] s = names.toArray(new String[0]);
        names = Arrays.stream(s).collect(Collectors.toList());
        if(new BigDecimal("49.00").compareTo(new BigDecimal("50")) > 0){
            System.out.println("a大于b");
        }else {
            System.out.println("a小于b");
        }

        String iv = "0000000000000000";
        String key = "0000000000000000";
        String encrypt = Encrypt("too",key,iv);
        String decrypt = Decrypt(encrypt,key,iv);
        System.out.println(encrypt+":"+decrypt);

        String key2 = "0000000000000000";
        String encrypt2 = encrypt("too",key2);
        String decrypt2 = desEncrypt(encrypt2,key2);
        System.out.println(encrypt2+":"+decrypt2);

        BigDecimal bigDecimal2 = null;
        BigDecimal bigDecimal1 = null;
        Date date1 = new Date();
        Thread.sleep(1000);
        Date date2 = new Date();
        System.out.println(date1.compareTo(date2));
        System.out.println(date2.compareTo(date1));

        // 获取 Java 线程管理 MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的 monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，仅打印线程 ID 和线程名称信息
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.getThreadName());
        }

        Map<String,Object> map = new HashMap<>(1);
        map.put("","2");
        map.forEach((k,v)-> System.out.println(v));
        System.out.println(map.get(""));
        map.forEach((k,v)->{
            map.remove(k);
            map.put(k,"");
        });
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = iterator.next();
            entry.getKey();
            entry.getValue();
        }
        Set<Map.Entry<String, Object>> entry = map.entrySet();
//        for(Map.Entry entry:map.entrySet()){
//            entry.getKey();
//            entry.getValue();
//        }
//        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()){
//            iterator.next().getKey();
//            iterator.next().getValue();
//        }
        System.out.println("Xmx=" + Runtime.getRuntime().maxMemory() / 1024.0 / 1024 + "M");    //系统的最大空间
        System.out.println("free mem=" + Runtime.getRuntime().freeMemory() / 1024.0 / 1024 + "M");  //系统的空闲空间
        System.out.println("total mem=" + Runtime.getRuntime().totalMemory() / 1024.0 / 1024 + "M");  //当前可用的总空间
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        FutureTask<Integer> futureTask1 = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return 1;
        });
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            Thread.sleep(1000);
            return 2;
        });
        executorService.execute(futureTask1);
        executorService.execute(futureTask2);
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        executorService.shutdown();

//        long begin = System.currentTimeMillis();
//        Thread.sleep(2000);
//        long end = System.currentTimeMillis();
//        System.out.println("时间："+(end-begin)/1000);
//        System.out.println(1<<16);
//        System.out.println(-1>>16);
//        Map map = new HashMap();
//        Integer stationUserId = (Integer) map.get("stationUserId");
//        System.out.println(stationUserId);
//        String time ="Wed May 12 09:49:37 2021\n";
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.US);
//        Date dataTime = null;
//        try {
//            dataTime = sdf.parse(time);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        // 获取Java线程管理MXBean
//        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
//// 不需要获取同步的monitor和synchronizer信息，仅获取线程和线程堆栈信息
//        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
//// 遍历线程信息，仅打印线程ID和线程名称信息
//        for (ThreadInfo threadInfo : threadInfos) {
//            System.out.println("[" + threadInfo.getThreadId() + "] " + threadInfo.
//                    getThreadName());
//        }

//        for (int i = 0; i < 30; i++) {
//            new Thread(() -> generateNoRepeatYzm()).start();
//        }
//        System.out.println("集合大小：" + randomNumSet.size());

//        List<String> oilType = new ArrayList<>();
//        oilType.add("92");
//        List<String> diff = oilType.stream().filter(x -> x !="95" ).collect(Collectors.toList());
//        System.out.println(diff);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date before =simpleDateFormat.parse("2021-03-03 11:34:00");
//        System.out.println(diffMinutes(before,new Date()));
//        System.out.println(getWeek(new Date()));
//        List<String> strlist = changeCardStringToList("01110000101029310006","01110000101029310106",101);
//        System.out.println(strlist);
//        System.out.println(System.currentTimeMillis());
//        BigDecimal money = new BigDecimal("-100.98");
//        BigDecimal balance = new BigDecimal("200321321901.00");
//        BigDecimal result = balance.add(money);
//        System.out.println(result.toString());
//        Long timeout = Math.max(10000,29500L);
//        System.out.println(timeout);
//        for(int i=0;i<99;i++){
//            String phone = "0000000000" + i;
//            if(phone.length()>11){
//                phone = phone.substring(phone.length()-11);
//            }
//            System.out.println(phone);
//        }


//        Map requestData = new HashMap();
//        requestData.put("code",200);
//        System.out.println(requestData.get("code").equals(200));
//        requestData.put("gunId","-1");
//        if(requestData.get("gunId").equals(-1) || requestData.get("gunId").equals("-1")){
//            System.out.println("自助加油");
//        }else{
//            System.out.println("扫码加油");
//        }
//        BigDecimal stationPrice = new BigDecimal(1000);
//        BigDecimal orderAmount = new BigDecimal(0.4);
//        System.out.println(orderAmount.divide(stationPrice, 3, BigDecimal.ROUND_HALF_UP));
//        System.out.println(orderAmount.divide(stationPrice).toPlainString());
//
//        BigDecimal origin = new BigDecimal(4022);
//        BigDecimal need = origin.divide(new BigDecimal(100));
//        System.out.println(need.toString());
//
//        List<String> list = new ArrayList<String>();
//        list.add("1");
//        list.add("4");
//        list.add("3");
//        list.add("3");
//        list.add("杨娟");
//        list.add("李文武");
//        list.add("杨娟");
//        System.out.println(list.stream().collect(Collectors.joining(",")));
//        System.out.println(String.join(",",list));
//        for (String item : list) {
//            if ("2".equals(item)) {
//                list.remove(item);
//            }
//        }
//        list.sort(String::compareTo);
//        System.out.println(list.toString());
//        Set<String> set = new HashSet<>(list);
//        list.clear();
//        list.addAll(set);
//        System.out.println(list.toString());

//        Map<String,Object> map = new HashMap();
//        map.put("key","value");
//        map.forEach((k, v) -> System.out.println("k:" + k + "v:" + v));
//        for(Map.Entry<String,Object> entry:map.entrySet()){
//            System.out.println("k:" + entry.getKey() + "v:" + entry.getValue());
//        }
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            entry.getKey();
//        }


//        new Thread(() -> lockTest("222222222")).start();
//        new Thread(() -> lockTest("222222222")).start();
//
//        new Thread(() -> lockTest("111111111")).start();
//        jdk8DateTimeFormatter();
//        System.getProperty("user.dir");
//        String identityUrl = "e2cac528393211eba58fd45d64f4048b.jpg";
//        String fileName = identityUrl.substring(identityUrl.lastIndexOf("/")+1);
//        System.out.println(fileName);
//        String path = "E:\\idcardTest\\dir\\e2cac528393211eba58fd45d64f4048b.jpg";
//        System.out.println(path.substring(0,0));
//        String dir = path.substring(0,path.lastIndexOf("/")<0?path.lastIndexOf("\\"):path.lastIndexOf("/"));
//        File dirFile = new File(dir);
//        if(!dirFile.exists()){
//            dirFile.mkdirs();
//        }

        //javacvTest();
//        Integer a = new Integer(3);
//        int b = 3;
//        Integer c = 3;
//        System.out.println(a==b);
//        System.out.println(a==c);
//        System.out.println(b==c);
//        List<String> list = new ArrayList<>();
//        list.add("2");
//        list.add("1");
//        list.add("6");
//        Collections.sort(list);
//        System.out.println(list.toString());

//        int a1=100,a2=100,a3=150,a4=150;
//        System.out.println(a1==a2);
//        System.out.println(a3==a4);


//        char a = '中';
//        System.out.println(a);
//        String s1 = "Programming";
//        String s2 = new String("Programming");
//        String s3 = "Program";
//        String s4 = "ming";
//        String s5 = "Program" + "ming";
//        String s6 = s3 + s4;
//        System.out.println(s1 == s2); //false
//        System.out.println(s1 == s5); //true
//        System.out.println(s1 == s6); //false
//        System.out.println(s1 == s6.intern()); //true
//        System.out.println(s2 == s2.intern()); //false
//


//        new Thread(()-> methodA("a")).start();
//        Thread.sleep(1000);
//        methodB("bb");
//        new Thread(()-> methodB("b")).start();
//        new Thread(()-> methodA("c")).start();
    }

    static void javacvTest() {
        String originPath = "D://c4073baffd254c3d8a6642b8cd0bcbbb.mp4";
        String jpgPath = "D://test.jpg";
        String ffmpeg_path = "D:\\ffmpeg\\bin\\ffmpeg.exe";
        File videoFile = new File(originPath);
        if (videoFile.exists()) {
            List<String> commands = new java.util.ArrayList<String>();
            commands.add(ffmpeg_path);
            commands.add("-i");
            commands.add(originPath);
            commands.add("-y");
            commands.add("-f");
            commands.add("image2");
            commands.add("-ss");
            commands.add("2");// 这个参数是设置截取视频多少秒时的画面
            //commands.add("-t");
            //commands.add("0.001");
            commands.add("-s");
            commands.add("700x525");
            commands.add(jpgPath);
            try {
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(commands);
                builder.start();
                System.out.println("截取成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("视频不存在");
        }
    }

    public static void jdk8DateTimeFormatter() {
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date2 = LocalDateTime.now();
        System.out.println(date2.format(newFormatter));
        LocalDate date = LocalDate.now();
        LocalDate second = LocalDate.of(date.getYear(), date.getMonth(), 20);
        LocalDate a0 = date.with(TemporalAdjusters.lastDayOfMonth());
        LocalDate a1 = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate a2 = date.with(TemporalAdjusters.firstDayOfNextMonth());
        LocalDate a3 = date.with(TemporalAdjusters.firstDayOfNextYear());
        LocalDate a4 = date.with(TemporalAdjusters.firstDayOfYear());
        LocalDate a6 = date.with(TemporalAdjusters.lastDayOfYear());
        System.out.println(second);
        System.out.println(a0);
        System.out.println(a1);
        System.out.println(a2);
        System.out.println(a3);
        System.out.println(a4);
        System.out.println(a6);
        LocalDateTime dt = LocalDateTime.now();
        System.out.println(dt);
        System.out.println(Calendar.getInstance().getTimeInMillis());
        System.out.println(Clock.systemDefaultZone().millis());
        System.out.println(System.currentTimeMillis());
        LocalDateTime startTime = LocalDateTimeUtil.parse("2022-01-01");
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTimeUtil.between(startTime,endTime);
    }

}
