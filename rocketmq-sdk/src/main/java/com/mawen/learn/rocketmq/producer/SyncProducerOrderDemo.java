package com.mawen.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * 同步生成者发送有序消息
 */
public class SyncProducerOrderDemo {

    private static final Logger log = LoggerFactory.getLogger(SyncProducerOrderDemo.class);

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // Namesrv 地址
        String serverAddr = "localhost:9876";
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(serverAddr);

        producer.start();

        Message message = new Message("order-topic", "someTag", "DefaultProducer -> Order Message".getBytes(StandardCharsets.UTF_8));

        SendResult result = producer.send(message);

        log.info("{}", result);

        producer.shutdown();
    }
}
