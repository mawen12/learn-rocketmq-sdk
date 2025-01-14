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

public class SyncProducerScheduledDemo {

    private static final Logger log = LoggerFactory.getLogger(SyncProducerScheduledDemo.class);

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("scheduled-group");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        Message message = new Message("TestTopic", "Scheduled Time".getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(3);

        SendResult result = producer.send(message);

        log.info("{}", result);

        producer.shutdown();
    }
}
