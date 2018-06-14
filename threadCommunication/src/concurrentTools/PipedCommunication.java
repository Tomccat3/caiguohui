package concurrentTools;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * @author cgh
 * 管道通信
 */
public class PipedCommunication {
    public static void main(String[] args) throws Exception{

        //面向于字符 PipedInputStream 面向于字节
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader();

        //建立连接
        writer.connect(reader);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running...");
                try{
                    for(int i = 0; i < 10; i++){
                        writer.write(i + "");
                        Thread.sleep(10);
                    }
                }catch (Exception e){}finally {
                    try{
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running2...");
                int msg = 0;
                try{
                    while ((msg = reader.read()) != -1){
                        System.out.println((char)msg);
                    }
                }catch (Exception e){}
            }
        });

        t1.start();
        t2.start();
    }
}
