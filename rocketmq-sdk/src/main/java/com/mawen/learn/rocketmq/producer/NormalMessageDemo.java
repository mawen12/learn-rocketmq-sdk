package com.mawen.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NormalMessageDemo {

    private static final Logger log = LoggerFactory.getLogger(NormalMessageDemo.class);

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        String serverAddr = "localhost:9876";
        String producerGroup = "defaultGroup";
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(serverAddr);
        producer.start();

        Message message = new Message("someTopic", "someTag", "DefaultProducer -> Normal Message".getBytes());
        message.setKeys("key-1");

        SendResult sendResult = producer.send(message);

        log.info("{}", sendResult);

        producer.shutdown();
    }
}
