package VolatileTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cgh
 * 指令重排 导致线程不安全
 * volatile可以禁止指令重排序
 */
public class OrderReverse {

    private static Map<String, String> value;
    private static volatile boolean flag = false;

    /**
     * 线程A调用下面方法，初始化Map
     * 若flag没有volatile修饰，JVM对1和2进行重排，导致value没有被初始化就被线程B使用
     */
    public void initMap(){
        value = getMapValue();  // 1
        flag = true;   //2
    }

    /**
     *发生在线程B中，等到Map初始化成功后进行其他操作
     */
    public void doSomething(){
        while (!flag){
            for (;;){}
        }

        //进行一些操作
        //  doSomething(value);
    }


    public Map<String, String> getMapValue(){
        return new HashMap<>();
    }
}
