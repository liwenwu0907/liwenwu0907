package com.example.demo;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author li wenwu
 * @date 2021/5/26 10:35
 */
public class AtomicReferenceTest {
    public static AtomicReference<User> atomicUserRef = new
            AtomicReference<User>();
    
    /**
     * @author  liwenwu
     * @date  2021/9/13
     **/
    // TODO: 2021/9/13  
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        List<String> names = Arrays.asList("Larry", "Steve", "James");
        List result = names.stream().filter(s -> !s.contains("L")).collect(Collectors.toList());
        System.out.println(result);
        names.forEach(System.out::println);
        Set<String> namesSet = new HashSet<>(Arrays.asList("Larry", "Steve", "James"));
        Queue<String> namesQueue = new LinkedBlockingQueue<>(Arrays.asList("Larry", "Steve", "James"));
        namesSet.forEach(System.out::println);
        namesQueue.forEach(System.out::println);
        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list.removeIf(item -> item.equals("1"));
        for (String item : list2) {
            if ("1".equals(item)) {
                list2.remove(item);
            }
        }
        BigDecimal a = new BigDecimal("0.9");
        System.out.println(a.equals(new BigDecimal("0.9")));
//        Float a = 1.0f - 0.9f;
//        Float b = 0.9f - 0.8f;
//        if (a == b) {
//            System.out.println(a == b);
//        }
//        Float x = Float.valueOf(a);
//        Float y = Float.valueOf(b);
//        if (x.equals(y)) {
//            System.out.println(x.equals(y));
//        }

//        Integer a = 288;
//        Integer b = 127;
//        int aa = 288;
//        int bb = 127;
//        System.out.println(bb==127);
//        System.out.println(aa==288);
        User user = new User("conan", 15);
        atomicUserRef.set(user);
        System.out.println(atomicUserRef.get().getName());
        User updateUser = new User("Shinichi", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        public User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        public String getName() {
            return name;
        }

        public int getOld() {
            return old;
        }
    }
}
