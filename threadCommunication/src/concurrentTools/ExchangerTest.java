package concurrentTools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author cgh
 * Exchanger（交换者）是一个用于线程间协作的工具类。Exchanger用于进行线程间的数据交
 * 换。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。这两个线程通过
 * exchange方法交换数据，如果第一个线程先执行exchange()方法，它会一直等待第二个线程也
 * 执行exchange方法，当两个线程都到达同步点时，这两个线程就可以交换数据，将本线程生产
 * 出来的数据传递给对方。
 */
public class ExchangerTest {
    private static final Exchanger<String> exg = new Exchanger<String>();
    private static ExecutorService threadpool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String A = "银行A流水...";  //A录入数据
                    exg.exchange(A);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        threadpool.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    String B = "银行B流水...";  //B录入数据
                    String A = exg.exchange(B);
                    System.out.println("A 和 B数据是否一致： " + A.equals(B) +
                                ", A录入的是: " + A + ", B录入的是: " + B);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        threadpool.shutdown();
    }
}
