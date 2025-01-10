package com.mawen.learn.rocketmq.consumer;

import com.mawen.learn.rocketmq.producer.NormalMessageDemo;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class ConsumerDemo {

    private static final Logger log = LoggerFactory.getLogger(NormalMessageDemo.class);

    public static void main(String[] args) throws MQClientException, InterruptedException {
        String serverAddr = "localhost:9876";
        String consumerGroup = "defaultGroup";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(serverAddr);

        consumer.subscribe("someTopic", "*");

        CountDownLatch isDown = new CountDownLatch(5);

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info("{}", msgs);

            msgs.forEach(msg -> log.info(new String(msg.getBody())));

            isDown.countDown();

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        isDown.await();

        consumer.shutdown();
    }
}
