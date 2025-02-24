package com.lv;

/**
 * @projectName: wangzai
 * @package: com.lv
 * @className: ThreadTest
 * @author: dus
 * @description: 两个线程交替打印1-100
 * @version: 1.0
 */
public class ThreadTest {

    //锁 对象
    private static final Object lock = new Object();
    //当前要打印的数字
    private static int number = 1;
    //标志位
    private static boolean isThread1Turn = true;
    public static void main(String[] args) {
//        Thread thread1 = new Thread(new Printer(1));
//        Thread thread2 = new Thread(new Printer(2));

        Thread thread1 = new Thread(){
            @Override
            public void run() {

                while (true) {
                    synchronized (lock) {
                        // 判断当前线程是否轮到打印
                        if (!isThread1Turn) {
                            try {
                                // 如果不是当前线程的轮次，线程进入等待状态
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // 检查数字是否已经超过 100
                        if (number > 100) {
                            // 唤醒另一个线程
                            lock.notify();
                            break;
                        }
                        // 打印当前数字
                        System.out.println("线程 " + 1 + " 打印: " + number);
                        // 数字加 1
                        number++;
                        // 切换轮次
                        isThread1Turn =!isThread1Turn;
                        // 唤醒另一个线程
                        lock.notify();
                    }
                }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {

                while (true) {
                    synchronized (lock) {
                        // 判断当前线程是否轮到打印
                        if (isThread1Turn) {
                            try {
                                // 如果不是当前线程的轮次，线程进入等待状态
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // 检查数字是否已经超过 100
                        if (number > 100) {
                            // 唤醒另一个线程
                            lock.notify();
                            break;
                        }
                        // 打印当前数字
                        System.out.println("线程 " + 2 + " 打印: " + number);
                        // 数字加 1
                        number++;
                        // 切换轮次
                        isThread1Turn =!isThread1Turn;
                        // 唤醒另一个线程
                        lock.notify();
                    }
                }
            }
        };
        thread1.start();
        thread2.start();

        /*try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    static class Printer implements Runnable{

        private final int threadId;

        public Printer(int threadId) {
            this.threadId = threadId;
        }

        @Override
        public void run() {

            while (true) {
                synchronized (lock) {
                    // 判断当前线程是否轮到打印
                    while ((threadId == 1 &&!isThread1Turn) || (threadId == 2 && isThread1Turn)) {
                        try {
                            // 如果不是当前线程的轮次，线程进入等待状态
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 检查数字是否已经超过 100
                    if (number > 100) {
                        // 唤醒另一个线程
                        lock.notify();
                        break;
                    }
                    // 打印当前数字
                    System.out.println("线程 " + threadId + " 打印: " + number);
                    // 数字加 1
                    number++;
                    // 切换轮次
                    isThread1Turn =!isThread1Turn;
                    // 唤醒另一个线程
                    lock.notify();
                }
            }
        }
    }
}
