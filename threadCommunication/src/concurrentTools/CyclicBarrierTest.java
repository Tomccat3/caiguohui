package concurrentTools;

import java.util.concurrent.CyclicBarrier;

/**
 * @author cgh
 * CyclicBarrier可以多次使用... reset();
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "run..");
                try{
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("thread end ...");
            }
        }).start();

        /**
         * 该线程休眠5秒 ， 调用await()将会使所有参与者线程互相等待,
         * 直到所有线程都调用了await()方法后，所有线程返回并继续执行...
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "run..");
                try{
                    Thread.sleep(5000);
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("thread end ...");
            }
        }).start();

        System.out.println("main thread...");
    }
}
