package com.example.demo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.RecursiveTask;

/**
 * @author li wenwu
 * @date 2021/5/24 17:03
 */
public class CountTask extends RecursiveTask<Integer> {

    private static final int THRESHOLD = 2; // 阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
// 如果任务足够小就计算任务
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
// 如果任务大于阈值，就分裂成两个子任务计算
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);
// 执行子任务
            leftTask.fork();
            rightTask.fork();
// 等待子任务执行完，并得到其结果
            int leftResult = leftTask.join();
            int rightResult = rightTask.join();
// 合并子任务
            sum = leftResult + rightResult;
        }
        return sum;
    }


    public static void main(String[] args) {
        System.out.println("2021-01".substring(0,6));
        System.out.println(getLastDayOfMonth("2021-01-01","yyyy-MM-dd"));
//        System.out.println("1000413200000000314".length());
//        int[] value = new int[] { 1,2 };
//        AtomicIntegerArray ai = new AtomicIntegerArray(value);
//        ai.getAndSet(1,3);
//        System.out.println(ai.get(0));
//        ForkJoinPool forkJoinPool = new ForkJoinPool();
//// 生成一个计算任务，负责计算1+2+3+4
//        CountTask task = new CountTask(1, 4);
//// 执行一个任务
//        Future<Integer> result = forkJoinPool.submit(task);
//        try {
//            System.out.println(result.get());
//        } catch (InterruptedException e) {
//        } catch (ExecutionException e) {
//        }
//        if (task.isCompletedAbnormally()) {
//            System.out.println(task.getException());
//        }
    }

    public static Date getLastDayOfMonth(String date, String format) {
        Date dateTime = StringToDate(date, format);
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        int month = cal.get(Calendar.MONTH);
        //设为一个月的第一天
        cal.set(Calendar.DAY_OF_MONTH, 1);
        //月份加+1
        cal.set(Calendar.MONTH, month + 1);
        //得到一个月的最后一天
        cal.add(Calendar.DATE, -1);//获取当前日期的上一天
        return cal.getTime();
    }
    public static Date StringToDate(String date, String formatStr) {
        Date myDate = new Date();
        try {
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(formatStr);
            myDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return myDate;
    }
}
