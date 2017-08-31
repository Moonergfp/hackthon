package com.hack.test;

public class JoinItselfTest extends Thread {

    public void run() {
        System.out.println("Inside the run method ");
        System.out.println(Thread.currentThread().isAlive());
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("Joining itself ...");
                this.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        JoinItselfTest j = new JoinItselfTest();

        System.out.println(j.isAlive());
        j.start();
        System.out.println(j.isAlive());
        System.out.println("Thread started ...");

    }

}
