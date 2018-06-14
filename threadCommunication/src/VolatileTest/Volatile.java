package VolatileTest;

import java.util.concurrent.TimeUnit;

/**
 * @author cgh
 */
public class Volatile implements Runnable{

    private static volatile boolean flag = true;

    @Override
    public void run() {
        while(flag){
            System.out.println(Thread.currentThread().getName() + "正在运行...");
        }
        System.out.println(Thread.currentThread().getName() + "执行完毕...");
    }

    public static void main(String[] args) throws InterruptedException{
        Volatile v = new Volatile();
        new Thread(v, "thread A").start();

        System.out.println("main线程正在运行...");

        TimeUnit.MILLISECONDS.sleep(100);
        v.stopThread();
    }

    private void stopThread(){
        flag = false;
    }
}
