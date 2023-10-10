package com.nos.multithreading.스레드상태;

public class TerminatedThread {
    public static void main(String[] args) {
        TerminatedThread terminatedThread = new TerminatedThread();
        terminatedThread.terminatedThread();
    }

    public void terminatedThread(){
        Thread t = new Thread(() -> {});
        t.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("TerminatedThread t : " + t.getState() + "\n");
    }
}
