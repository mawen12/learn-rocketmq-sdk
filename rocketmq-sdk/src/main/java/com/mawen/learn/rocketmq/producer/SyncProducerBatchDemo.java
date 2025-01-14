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
import java.util.ArrayList;
import java.util.List;

public class SyncProducerBatchDemo {

    private static final Logger log = LoggerFactory.getLogger(SyncProducerBatchDemo.class);

    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        String serverAddr = "localhost:9876";
        DefaultMQProducer producer = new DefaultMQProducer("batchGroup");
        producer.setNamesrvAddr(serverAddr);

        String topic = "batch-topic";
        List<Message> messageList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            messageList.add(new Message(topic, ("Hello " + i).getBytes(StandardCharsets.UTF_8)));
        }

        producer.start();

        SendResult result = producer.send(messageList);

        log.info("{}", result);

        producer.shutdown();
    }
}
