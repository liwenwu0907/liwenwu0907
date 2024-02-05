package com.example.demo;

import cn.hutool.core.date.LocalDateTimeUtil;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class LocalDateTest {

    @Test
    public void test1(){
        //字符串转LocalDateTime
        LocalDateTime startTime = LocalDateTimeUtil.parse("2022-01-01 00:00:00","yyyy-MM-dd HH:mm:ss");
        //当前时间
        LocalDateTime endTime = LocalDateTime.now();
        //两个时间相差多少天
        System.out.println(LocalDateTimeUtil.between(startTime,endTime, ChronoUnit.DAYS));
        //时间是否在之前
        System.out.println(startTime.isBefore(endTime));
        //Date转LocalDateTime
        System.out.println(LocalDateTimeUtil.of(new Date()));
        //构造LocalDateTime
        System.out.println(LocalDateTime.of(2023,5,23,10,5,6));
        //获取星期几
        System.out.println(endTime.getDayOfWeek());
        //加多少天
        System.out.println(startTime.plus(10, ChronoUnit.DAYS));
        //加多少天
        System.out.println(startTime.plusDays(10));
        //减多少天
        System.out.println(startTime.minusDays(1));
        //加多少周
        System.out.println(startTime.plusWeeks(1));
        //减多少周
        System.out.println(startTime.minusWeeks(1));
        //本年当中第几天
        System.out.println(endTime.getDayOfYear());
        System.out.println(endTime.getDayOfMonth());
        System.out.println(endTime.getDayOfWeek().getValue());
        //当天零点
        LocalDateTime today_start = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        System.out.println(today_start + "  " + today_end);
        System.out.println(endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        //获取时间戳
        System.out.println(System.currentTimeMillis() + " " + endTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
    }

    @Test
    public void jdk8DateTimeFormatter() {
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

    }

}
