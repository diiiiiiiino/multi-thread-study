package com.nos.multithreading.ReentrantLock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main4 {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();

        ReaderThread readerThread1 = new ReaderThread(sharedClass);
        ReaderThread readerThread2 = new ReaderThread(sharedClass);
        WriterThread writerThread1 = new WriterThread(sharedClass);
        WriterThread writerThread2 = new WriterThread(sharedClass);

        readerThread1.setName("읽기 쓰레드 1");
        readerThread2.setName("읽기 쓰레드 2");
        writerThread1.setName("쓰기 쓰레드 1");
        writerThread2.setName("쓰기 쓰레드 2");

        readerThread1.start();

        Thread.sleep(1000);
        writerThread1.start();
        Thread.sleep(1000);
        readerThread2.start();

    }

    public static class SharedClass {
        private int value = 0;
        private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public ReentrantReadWriteLock getLock() {
            return lock;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value){
            this.value = value;
        }
    }

    public static class ReaderThread extends Thread{
        private SharedClass sharedClass;
        public ReaderThread(SharedClass sharedClass){
            this.sharedClass = sharedClass;
        }

        @Override
        public void run() {
            ReentrantReadWriteLock.ReadLock readLock = sharedClass.getLock().readLock();

            while(true){
                try{
                    readLock.lock();
                    System.out.println("락을 획득 후 값을 읽는다 " + this.getName());
                    System.out.println(sharedClass.getValue());

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    readLock.unlock();
                }
            }
        }
    }

    public static class WriterThread extends Thread{
        private SharedClass sharedClass;
        public WriterThread(SharedClass sharedClass){
            this.sharedClass = sharedClass;
        }

        @Override
        public void run() {
            ReentrantReadWriteLock.WriteLock writeLock = sharedClass.getLock().writeLock();

            while(true){
                try{
                    writeLock.lock();
                    System.out.println("락을 획득 후 값을 쓴다 " + this.getName());
                    sharedClass.setValue((int)(Math.random() * 10000));

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    writeLock.unlock();

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
