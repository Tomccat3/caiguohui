package waitNotify;

/**
 * @author cgh
 * 等待通知模式实现java线程通信
 * 两个线程对同一对象调用wait和notify通信
 */
public class TwoThreadWaitNotify {

    private int start = 1;

    private boolean flag = false;

    public static void main(String[] args) {
        TwoThreadWaitNotify twoThread = new TwoThreadWaitNotify();

        Thread t1 = new Thread(new OddThread(twoThread));
        t1.setName("偶数线程");

        Thread t2 = new Thread(new EvenThread(twoThread));
        t2.setName("奇数线程");

        t1.start();
        t2.start();
    }

    /**
     * 偶数线程
     */
    public static class OddThread implements Runnable{

        private TwoThreadWaitNotify waitNotify;

        public OddThread(TwoThreadWaitNotify waitNotify){
            this.waitNotify = waitNotify;
        }
        @Override
        public void run() {
            while(waitNotify.start <= 100){
                synchronized (TwoThreadWaitNotify.class){
                    System.out.println("偶数线程获得锁...");
                    if (waitNotify.flag){
                        System.out.println(Thread.currentThread().getName() + "--> 偶数 -->" + waitNotify.start);
                        waitNotify.start++;
                        waitNotify.flag = false;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try{
                            TwoThreadWaitNotify.class.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * 奇数线程
     */
    public static class EvenThread implements Runnable{

        private TwoThreadWaitNotify waitNotify;

        public EvenThread(TwoThreadWaitNotify waitNotify){
            this.waitNotify = waitNotify;
        }
        @Override
        public void run() {
            while(waitNotify.start <= 100){
                synchronized (TwoThreadWaitNotify.class){
                    System.out.println("奇数线程获得锁...");
                    if (!waitNotify.flag){
                        System.out.println(Thread.currentThread().getName() + "--> 奇数 -->" + waitNotify.start);
                        waitNotify.start++;
                        waitNotify.flag = true;
                        TwoThreadWaitNotify.class.notify();
                    }else {
                        try{
                            TwoThreadWaitNotify.class.wait();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
