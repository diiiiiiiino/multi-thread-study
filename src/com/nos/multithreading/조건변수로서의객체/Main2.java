package com.nos.multithreading.조건변수로서의객체;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Main2 {
    public static void main(String[] args) throws InterruptedException {
        SharedClass sharedClass = new SharedClass();

        WaitThread waitThread = new WaitThread(sharedClass);
        WaitThread waitThread2 = new WaitThread(sharedClass);
        SignalThread signalThread = new SignalThread(sharedClass);

        waitThread.setName("대기1");
        waitThread2.setName("대기2");
        signalThread.setName("신호1");
        
        waitThread.start();
        //waitThread2.start();
        
        Thread.sleep(2000);
        
        signalThread.start();
    }

    public static class SharedClass{
        public String loginId;
        public ReentrantLock lock = new ReentrantLock();
        public Condition condition = lock.newCondition();

        public synchronized void customWait() throws InterruptedException {
            System.out.println("customWait wait() before!!! ");
            wait();
            System.out.println("customWait wait() after!!! ");
        }

        public synchronized void customNotifyAll(){
            System.out.println("customNotifyAll notifyAll() before!!! ");
            notifyAll();
            System.out.println("customNotifyAll notifyAll() after!!! ");
        }
    }

    public static class WaitThread extends Thread{
        SharedClass sharedClass;
        ReentrantLock lock;
        Condition condition;

        public WaitThread(SharedClass sharedClass) {
            this.sharedClass = sharedClass;
            this.lock = sharedClass.lock;
            this.condition = sharedClass.condition;
        }

        @Override
        public void run() {
            lock.lock();
            try{
                System.out.println("락!!!!" + getName());
                while(sharedClass.loginId == null){
                    System.out.println("신호 대기 상태 진입 전 " + this.getName());
                    condition.await();
                    sharedClass.customWait();

                    System.out.println("Thread Sleep " + this.getName());
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("깨어나서 언락 " + getName());
                lock.unlock();
            }

            System.out.println("do Stuff!! " + getName());
        }
    }

    public static class SignalThread extends Thread{
        SharedClass sharedClass;
        ReentrantLock lock;
        Condition condition;

        public SignalThread(SharedClass sharedClass) {
            this.sharedClass = sharedClass;
            this.lock = sharedClass.lock;
            this.condition = sharedClass.condition;
        }

        @Override
        public void run() {
            lock.lock();
            try{
                System.out.println("signalAll() before!!!");
                sharedClass.loginId = "loginId";
                condition.signalAll();
                sharedClass.customNotifyAll();

                System.out.println("signalAll() after!!!");
            } finally {
                lock.unlock();
            }
        }
    }
}
