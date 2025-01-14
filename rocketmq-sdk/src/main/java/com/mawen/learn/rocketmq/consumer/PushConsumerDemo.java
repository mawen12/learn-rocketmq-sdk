package com.mawen.learn.rocketmq.consumer;

import com.mawen.learn.rocketmq.producer.SyncProducerDemo;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class PushConsumerDemo {

    private static final Logger log = LoggerFactory.getLogger(PushConsumerDemo.class);

    public static void main(String[] args) throws MQClientException, InterruptedException {
        String serverAddr = "localhost:9876";
        String consumerGroup = "defaultGroup";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr(serverAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.subscribe("someTopic", "*");

        CountDownLatch isDown = new CountDownLatch(1);

        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            log.info("{}", msgs);

            msgs.forEach(msg -> log.info(new String(msg.getBody())));

            isDown.countDown();

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        isDown.await();

        Thread.sleep(10000);

        consumer.shutdown();
    }
}
