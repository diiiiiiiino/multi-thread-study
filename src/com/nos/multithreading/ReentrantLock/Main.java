package com.nos.multithreading.ReentrantLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    public static void main(String[] args) {
        PricesContainer pricesContainer = new PricesContainer();

        Thread thread = new PriceUpdater(pricesContainer);
        Thread thread2 = new PriceReader(pricesContainer);

        thread.start();
        thread2.start();
    }

    public static class PricesContainer {
        private Lock lockObject = new ReentrantLock();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        Lock readLock = reentrantReadWriteLock.readLock();

        private double bitcoinPrice;
        private double etherPrice;
        private double litecoinPrice;
        private double bitcoinCashPrice;
        private double ripplePrice;

        public Lock getLockObject() {
            return lockObject;
        }

        public void setLockObject(Lock lockObject) {
            this.lockObject = lockObject;
        }

        public double getBitcoinPrice() {
            return bitcoinPrice;
        }

        public void setBitcoinPrice(double bitcoinPrice) {
            this.bitcoinPrice = bitcoinPrice;
        }

        public double getEtherPrice() {
            return etherPrice;
        }

        public void setEtherPrice(double etherPrice) {
            this.etherPrice = etherPrice;
        }

        public double getLitecoinPrice() {
            return litecoinPrice;
        }

        public void setLitecoinPrice(double litecoinPrice) {
            this.litecoinPrice = litecoinPrice;
        }

        public double getBitcoinCashPrice() {
            return bitcoinCashPrice;
        }

        public void setBitcoinCashPrice(double bitcoinCashPrice) {
            this.bitcoinCashPrice = bitcoinCashPrice;
        }

        public double getRipplePrice() {
            return ripplePrice;
        }

        public void setRipplePrice(double ripplePrice) {
            this.ripplePrice = ripplePrice;
        }
    }

    public static class PriceUpdater extends Thread {
        private PricesContainer pricesContainer;
        private Random random = new Random();

        public PriceUpdater(PricesContainer pricesContainer){
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while(true){
                pricesContainer.getLockObject().lock();

                try{
                    try{
                        Thread.sleep(1000);
                    } catch (InterruptedException e){

                    }

                    pricesContainer.setBitcoinPrice(random.nextInt(20000));
                    pricesContainer.setEtherPrice(random.nextInt(2000));
                    pricesContainer.setLitecoinPrice(random.nextInt(500));
                    pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
                    pricesContainer.setRipplePrice(random.nextDouble());
                } finally {
                    pricesContainer.getLockObject().unlock();
                }

                try{
                    Thread.sleep(2000);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public static class PriceReader extends Thread {
        private PricesContainer pricesContainer;

        public PriceReader(PricesContainer pricesContainer){
            this.pricesContainer = pricesContainer;
        }

        @Override
        public void run() {
            while(true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                if(pricesContainer.getLockObject().tryLock()){
                    try{
                        double bitcoinPrice = pricesContainer.getBitcoinPrice();
                        double etherPrice = pricesContainer.getEtherPrice();
                        double litecoinPrice = pricesContainer.getLitecoinPrice();
                        double bitcoinCashPrice = pricesContainer.getBitcoinCashPrice();
                        double ripplePrice = pricesContainer.getRipplePrice();

                        System.out.println("bitcoinPrice : " + bitcoinPrice + " etherPrice : " + etherPrice + "  litecoinPrice :" + litecoinPrice + " bitcoinCashPrice : " + bitcoinCashPrice + " ripplePrice : " + ripplePrice );
                    } finally{
                        pricesContainer.getLockObject().unlock();
                    }
                }
            }
        }
    }
}
