package com.lv;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: ThreadTest02
 * @author: dus
 * @description: 起两个线程交替打印1-100，一个打印奇数，一个打印偶数
 * @date: 2025/2/20 18:42
 * @version: 1.0
 */
public class ThreadTest02 {

    private final static Object lock = new Object();
    private static int MIN = 1;
    private static int MAX = 100;
    public static void main(String[] args) {

        Thread thread1 = new Thread(){
            @Override
            public void run() {
               while (MIN <= MAX) {
                   synchronized (lock) {
                       if (MIN % 2 != 0) {
                           System.out.println(Thread.currentThread().getName() + ":" + MIN++);
                           lock.notify();
                       }else {
                           try {
                               lock.wait();
                           } catch (InterruptedException e) {
                               e.printStackTrace();
                           }
                       }

                   }
               }
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                while (MIN <= MAX) {
                    synchronized (lock) {
                        if (MIN % 2 == 0) {
                            System.out.println(Thread.currentThread().getName() + ":" + MIN++);
                            lock.notify();
                        }else {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
