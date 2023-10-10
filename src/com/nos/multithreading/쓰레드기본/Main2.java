package com.nos.multithreading.쓰레드기본;

public class Main2 {

    public static void main(String[] args) throws InterruptedException {
        ThreadTest threadTest = new ThreadTest();

        Thread thread = new Thread(() -> {
            threadTest.method1();
        });

        thread.setName("method1");

        Thread thread2 = new Thread(() -> {
            threadTest.method2();
        });

        thread2.setName("method2");

        thread.start();
        thread2.start();

        thread.join();
        thread2.join();

        System.out.println(threadTest.value);

        System.out.println("========================");
        SharedClass sharedClass = new SharedClass();

        Thread thread3 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++){
                sharedClass.increment();
            }
        });
        Thread thread4 = new Thread(() -> {
            for(int i = 0; i < Integer.MAX_VALUE; i++){
                sharedClass.checkDataRace();
            }
        });

        thread3.start();
        thread4.start();

        thread3.join();
        thread4.join();
    }

    public static class ThreadTest{
        int value = 0;

        public void method1(){
            System.out.println("method1!! " + Thread.currentThread().getName());
            for(int i = 0; i < 10000; i++){
                value++;
            }
        }

        public void method2(){
            System.out.println("method2!!" + Thread.currentThread().getName());
            for(int i = 0; i < 10000; i++){
                value--;
            }
        }
    }

    public static class SharedClass{
        volatile int x;
        volatile int y;

        public void increment(){
            x++;
            y++;
        }

        public void checkDataRace(){
            if(y > x){
                System.out.println("y > x data race detected!!");
            }
        }
    }
}
