package com.mawen.learn.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

public class SyncProducerOnewayDemo {

    public static void main(String[] args) throws RemotingException, InterruptedException, MQClientException {
        String serverAddr = "localhost:9876";
        DefaultMQProducer producer = new DefaultMQProducer("onewayGroup");
        producer.setNamesrvAddr(serverAddr);

        String topic = "oneway-topic";
        Message message = new Message(topic, ("Hello").getBytes(StandardCharsets.UTF_8));

        producer.start();

        producer.sendOneway(message);

        producer.shutdown();
    }
}
