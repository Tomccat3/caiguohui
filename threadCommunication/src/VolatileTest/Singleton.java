package VolatileTest;

/**
 * @author cgh
 * volatile禁止指令重排序的应用：
 *              双重懒加载的单例模式
 */
public class Singleton {

    private static volatile Singleton singleton;

    public static Singleton getInstance(){
        if(singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    //防止指令重排序
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
