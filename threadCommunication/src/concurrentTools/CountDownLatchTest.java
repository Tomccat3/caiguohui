package concurrentTools;


import java.awt.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author cgh
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception{
        int thread = 3;
        long start = System.currentTimeMillis();
        final CountDownLatch countDownLatch = new CountDownLatch(thread);
        for(int i = 0; i < thread; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "-- run");
                    try{
                        Thread.sleep(2000);
                        countDownLatch.countDown();
                        System.out.println(Thread.currentThread().getName() + " -- stopped");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDownLatch.await();
        long stop = System.currentTimeMillis();
        System.out.println("total time : " + (stop - start));
    }
}
