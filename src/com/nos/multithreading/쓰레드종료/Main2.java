package com.nos.multithreading.쓰레드종료;

import java.math.BigInteger;

public class Main2 {

    /**
     * 인터럽트하려는 스레드가 신호를 명시적으로 처리하는 경우
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("10")));

        //thread.setDaemon(true); //데몬 스레드로 설정하면 메인스레드가 종료되면 해당 스레드가 애플리케이션 종료를 막지 않는다.
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable{
        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power){
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            System.out.println(base + "^" + power + " = " + pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for(BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }

                result = result.multiply(base);
            }

            return result;
        }
    }
}
