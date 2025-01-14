package com.mawen.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 同步生产者发送普通消息
 */
public class SyncProducerDemo {

    private static final Logger log = LoggerFactory.getLogger(SyncProducerDemo.class);

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // Namesrv 地址
        String serverAddr = "localhost:9876";
        String producerGroup = "defaultGroup";
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(serverAddr);
        producer.setSendMsgTimeout(5000);
        producer.start();

        Message message = new Message("someTopic", "someTag", "DefaultProducer -> Normal Message".getBytes());
        message.setKeys("key-1");

        SendResult sendResult = producer.send(message);

        log.info("{}", sendResult);
        ///Users/mawen/store/commitlog/00000 00000 00000 00000
        producer.shutdown();
    }
}
