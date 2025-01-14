package com.mawen.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 异步生产者发送普工消息
 */
public class AsyncProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(AsyncProducerDemo.class);

    private static CountDownLatch success = new CountDownLatch(1);
    private static CountDownLatch failed = new CountDownLatch(1);

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        // Namesrv 地址
        String serverAddr = "localhost:9876";
        String producerGroup = "ASYNC_PRODUCER";
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(serverAddr);
        producer.setSendMsgTimeout(5000);
        producer.start();

        Message message = new Message("someTopic", "someTag", ("AsyncProducer -> Normal Message" + System.currentTimeMillis()) .getBytes());
        message.setKeys("key-1");

        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.warn("{}", sendResult);
                success.countDown();
            }

            @Override
            public void onException(Throwable e) {
                log.warn("Send Exception {}", e);
                failed.countDown();
            }
        });

        success.await();

        producer.shutdown();
    }
}
