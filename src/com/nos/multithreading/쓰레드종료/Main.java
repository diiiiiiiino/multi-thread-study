package com.nos.multithreading.쓰레드종료;

public class Main {

    /**
     * 우리가 인터럽트 하려는 스레드가 인터럽트 당했을 때 InterruptedException을 발생시키는 경우
     */

    public static void main(String[] args) {
        Thread thread = new Thread(new BlockingTask());

        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                System.out.println("Exiting blocking thread");
            }
        }
    }
}
