package com.nos.multithreading.스레드상태;

public class WaitingThread {
    private static final Thread t1 = new CodeT1();

    public static void main(String[] args) {
        WaitingThread waitingThread = new WaitingThread();
        waitingThread.waitingThread();
    }

    public void waitingThread(){
        t1.start();
    }

    private static class CodeT1 extends Thread{
        @Override
        public void run() {
            Thread t2 = new Thread(new CodeT2());
            t2.start();

            try{
                t2.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class CodeT2 implements Runnable{
        @Override
        public void run() {
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("WaitingThread t1: " + t1.getState() + " \n");
        }
    }
}
