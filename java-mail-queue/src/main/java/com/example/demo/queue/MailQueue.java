package com.example.demo.queue;

import com.example.demo.entity.Email;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MailQueue {
    //队列大小
    static final int QUEUE_MAX_SIZE = 1000;

    static BlockingQueue<Email> blockingQueue = new LinkedBlockingQueue<Email>(QUEUE_MAX_SIZE);

    //私有化构造器
    private MailQueue(){};
    //使用单例模式实例化 延迟加载
    private static class QueueHolder{
        private static MailQueue queue = new MailQueue();
    }
    //单例队列
    public static MailQueue getMailQueue(){
        return QueueHolder.queue;
    }

    //生产入队
    public void produce(Email mail) throws InterruptedException{
        blockingQueue.put(mail);
    }
    //消费出队
    public Email consume() throws InterruptedException{
        return blockingQueue.take();
    }

    //获取队列的大小
    public int size(){
        return blockingQueue.size();
    }
}
