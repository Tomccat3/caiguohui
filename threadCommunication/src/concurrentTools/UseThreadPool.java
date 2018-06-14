package concurrentTools;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cgh
 * 使用线程池 让主线程等待线程池中所有线程执行完毕。。。
 *              awaitTermination()检测ExcutorService是否关闭，关闭返回true，否则返回false；
 */
public class UseThreadPool {

    public static void main(String[] args) throws Exception{
        BlockingQueue<Runnable> queue = new LinkedBlockingDeque<>(10);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,5,1,TimeUnit.MILLISECONDS,queue);
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running...");
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        poolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2...");
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        poolExecutor.shutdown();
        while (!poolExecutor.awaitTermination(1,TimeUnit.SECONDS)){
            System.out.println("线程还在执行...");
        }
        System.out.println("main over...");
    }
}
