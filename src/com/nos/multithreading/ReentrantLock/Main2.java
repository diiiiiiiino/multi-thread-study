package com.nos.multithreading.ReentrantLock;

import java.util.StringTokenizer;
import java.util.concurrent.locks.ReentrantLock;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();

        SomeThread someThread1 = new SomeThread(sharedClass);
        SomeThread someThread2 = new SomeThread(sharedClass);

        someThread1.start();
        someThread2.start();

        Thread.sleep(3000);

        someThread2.interrupt();

        someThread1.join();
        someThread2.join();
    }

    public static class SharedClass {
        private ReentrantLock lock = new ReentrantLock();

        public ReentrantLock getLock() {
            return lock;
        }
    }

    public static class SomeThread extends Thread{
        SharedClass sharedClass;

        public SomeThread(SharedClass sharedClass){
            this.sharedClass = sharedClass;
        }

        @Override
        public void run() {
            ReentrantLock lock = sharedClass.getLock();

            while(true){
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("unLock!! : " + this.getName());
                }

                System.out.println("processing!!" + this.getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
