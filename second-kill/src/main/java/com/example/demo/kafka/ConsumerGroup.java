package com.example.demo.kafka;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author cgh
 */
public class ConsumerGroup {

    private static Logger logger = LoggerFactory.getLogger(ConsumerGroup.class);

    /**
     * 线程池
     */
    private ExecutorService threadPool;

    private List<ConsumerTask> consumers;

    public ConsumerGroup(int threadNum, String groupId, String topic, String brokerList){
        logger.info("kafka parameter={},{},{},{}",threadNum,groupId,topic,brokerList);
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-pool-%d").build();
        threadPool = new ThreadPoolExecutor(threadNum, threadNum,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        consumers = new ArrayList<ConsumerTask>(threadNum);
        for (int i = 0; i < threadNum; i++){
            ConsumerTask consumerThread = new ConsumerTask(brokerList, groupId, topic);
            consumers.add(consumerThread);
        }
    }

    /**
     * 执行任务
     */
    public void excute(){
        for (ConsumerTask runnable : consumers){
            threadPool.submit(runnable);
        }
    }
}
