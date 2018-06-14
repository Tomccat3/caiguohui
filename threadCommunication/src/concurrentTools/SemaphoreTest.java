package concurrentTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author cgh
 * 信号量使用
 * Semaphore可以用于做流量控制，特别是公用资源有限的应用场景，比如数据库连接。假
 * 如有一个需求，要读取几万个文件的数据，因为都是IO密集型任务，我们可以启动几十个线程
 * 并发地读取，但是如果读到内存后，还需要存储到数据库中，而数据库的连接数只有10个，这
 * 时我们必须控制只有10个线程同时获取数据库连接保存数据，否则会报错无法获取数据库连
 * 接。这个时候，就可以使用Semaphore来做流量控制，
 */
public class SemaphoreTest {
    private static final  int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        /**
                         * tryAcquire尝试获取许可...
                         */
//                        if(s.tryAcquire()){
//                            System.out.println(Thread.currentThread().getName() + "get acquire...");
//                        }
                        s.acquire();
                        System.out.println(Thread.currentThread().getName() + "save data");
                        s.release();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
