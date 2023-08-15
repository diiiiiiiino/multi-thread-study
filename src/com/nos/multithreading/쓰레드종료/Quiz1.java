package com.nos.multithreading.쓰레드종료;

import java.io.IOException;

public class Quiz1 {
    public static void main(String[] args) {
        Thread thread = new Thread(new WaitingForUserInput());
        thread.setName("InputWaitingThread");
        //thread.setDaemon(true);
        thread.start();
    }

    private static class WaitingForUserInput implements Runnable {
        @Override
        public void run() {
            try {
                while(true){
                    char input = (char) System.in.read();
                    if(input == 'q'){
                        return;
                    }
                }
            } catch(IOException e){
                System.out.println("An exception was caught " + e);
            }
        }
    }
}
