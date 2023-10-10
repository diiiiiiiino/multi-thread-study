package com.nos.multithreading.세마포어;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Item item = new Item();
        Producer producer = new Producer(item);
        Consumer consumer = new Consumer(item);

        producer.start();

        Thread.sleep(1000);

        consumer.start();
    }

    public static class Item{
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(1);
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static class Producer extends Thread{
        private Item item;
        private Semaphore full;
        private Semaphore empty;
        public Producer(Item item){
            this.item = item;
            this.full = item.full;
            this.empty = item.empty;
        }

        @Override
        public void run() {
            try {
                while(true){
                    Thread.sleep(1000);
                    empty.acquire();
                    System.out.println("생산자 세마포어 획득");
                    item.setValue(1);
                    full.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class Consumer extends Thread{
        private Item item;
        private Semaphore full;
        private Semaphore empty;
        public Consumer(Item item){
            this.item = item;
            this.full = item.full;
            this.empty = item.empty;
        }

        @Override
        public void run() {
            try {
                while(true){
                    Thread.sleep(1000);
                    System.out.println("소비자!!!!!!!!!!!!!!");

                    full.acquire();
                    System.out.println("소비자 세마포어 획득 " + item.getValue());
                    item.setValue(0);
                    empty.release();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
