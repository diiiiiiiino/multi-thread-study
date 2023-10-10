package com.nos.multithreading.스레드상태;

public class NewThread {
    public static void main(String[] args) {
        newThread();
    }

    public static void newThread(){
        Thread t1 = new Thread(() -> {});
        System.out.println("New thread t1 : " + t1.getState());

        Runnable runnable1 = () -> {};
        Thread t2 = new Thread(runnable1);
        System.out.println("New thread t2: " + t2.getState());

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        System.out.println("New thread t3: " + t3.getState());

        Thread t4 = new Thread(new Thread(){
            @Override
            public void run() {

            }
        });
        System.out.println("New thread t4: " + t4.getState());
    }
}
