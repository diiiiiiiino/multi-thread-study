package com.nos.multithreading.스레드상태;

public class TimeWaitingThread {
    public static void main(String[] args) {
        TimeWaitingThread timeWaitingThread = new TimeWaitingThread();
        timeWaitingThread.timeWaitingThread();
    }

    public void timeWaitingThread(){
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        t.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("TimedWaitingThread t : " + t.getState() + "\n");
    }
}
