package com.mawen.learn.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 基于Push消费者，消费消失抛出异常
 */
public class PushConsumerThrowExceptionDemo {

    private static final Logger log = LoggerFactory.getLogger(PushConsumerThrowExceptionDemo.class);

    public static void main(String[] args) throws MQClientException, InterruptedException {
        String serverAddr = "localhost:9876";
        String consumerGroup = "exceptionGroup";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(serverAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("someTopic", "*");

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info("{}", msgs);

            msgs.forEach(msg -> log.info(new String(msg.getBody())));

            throw new RuntimeException("PushConsumerThrowExceptionDemo");
        });

        consumer.start();

        TimeUnit.SECONDS.sleep(1000);

        consumer.shutdown();
    }
}
