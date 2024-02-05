package com.example.demo;


import java.util.concurrent.CyclicBarrier;

/**
 * @author li wenwu
 * @date 2021/5/26 10:52
 */
public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2);

    public void CyclicBarrier() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        try {
            c.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }

    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new A());
    public void CyclicBarrier2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                }
                System.out.println(1);
            }
        }).start();
        try {
            cyclicBarrier.await();
        } catch (Exception e) {
        }
        System.out.println(2);
    }
    static class A implements Runnable {
        @Override
        public void run() {
            System.out.println(3);
        }
    }



}
