package com.jonas.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudPullTopic;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.model.*;
import com.jonas.common.Constant;

import java.util.Vector;

/**
 * 消息服务工具类
 *
 * @author shenjy 2019/04/03
 */
public class MnsUtils {

    private static MNSClient client;

    static {
        CloudAccount cloudAccount = new CloudAccount(Constant.ACCESS_KEY_ID, Constant.ACCESS_KEY_SECRET, Constant.MNS_ENDPOINT);
        client = cloudAccount.getMNSClient();
    }

    /**
     * 消费队列消息
     *
     * @param queueName  队列名
     * @param waitSecond 消费等待时间
     */
    public static void consumeQueueMsg(String queueName, Integer waitSecond) {
        CloudQueue queue = client.getQueueRef(queueName);
        while (true) {
            try {
                Message message = queue.popMessage(waitSecond);
                if (null != message) {
                    System.out.println("message handle: " + message.getReceiptHandle());
                    System.out.println("message body: " + message.getMessageBodyAsString());
                    System.out.println("message id: " + message.getMessageId());
                    System.out.println("message dequeue count:" + message.getDequeueCount());
                    //删除已经取出消费的消息
                    queue.deleteMessage(message.getReceiptHandle());
                    System.out.println("delete message successfully.");
                } else {
                    System.out.println("message not exist in MyQueue.");
                }
            } catch (ClientException ce) {
                System.out.println("Something wrong with the network connection between client and MNS service."
                        + "Please check your network and DNS availablity.");
            } catch (ServiceException se) {
                if (se.getErrorCode() != null) {
                    if (se.getErrorCode().equals("QueueNotExist")) {
                        System.out.println("Queue is not exist.Please create before use");
                    } else if (se.getErrorCode().equals("TimeExpired")) {
                        System.out.println("The request is time expired. Please check your local machine timeclock");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void consumeTopicMsg(String topicName) {

        try {
            // build consumer name list.
            Vector<String> consumerNameList = new Vector<String>();
            String consumerName1 = "consumer001";
            String consumerName2 = "consumer002";
            String consumerName3 = "consumer003";
            consumerNameList.add(consumerName1);
            consumerNameList.add(consumerName2);
            consumerNameList.add(consumerName3);
            QueueMeta queueMetaTemplate = new QueueMeta();
            queueMetaTemplate.setPollingWaitSeconds(30);

            //producer code:
            // create pull topic which will send message to 3 queues for consumer.
            TopicMeta topicMeta = new TopicMeta();
            topicMeta.setTopicName(topicName);
            CloudPullTopic pullTopic = client.createPullTopic(topicMeta, consumerNameList, true, queueMetaTemplate);

            //publish message and consume message.
            String messageBody = "broadcast message to all the consumers:hello the world.";

            // if we sent raw message,then should use getMessageBodyAsRawString to parse the message body correctly.
            TopicMessage topicMessage = new RawTopicMessage();
            topicMessage.setBaseMessageBody(messageBody);
            pullTopic.publishMessage(topicMessage);

            // consumer code:
            //3 consumers receive the message.
            CloudQueue queueForConsumer1 = client.getQueueRef(consumerName1);
            CloudQueue queueForConsumer2 = client.getQueueRef(consumerName2);
            CloudQueue queueForConsumer3 = client.getQueueRef(consumerName3);

            Message consumer1Msg = queueForConsumer1.popMessage(30);
            if (null != consumer1Msg) {
                System.out.println("consumer1 receive message:" + consumer1Msg.getMessageBodyAsRawString());
            } else {
                System.out.println("the queue is empty");
            }

            Message consumer2Msg = queueForConsumer2.popMessage(30);
            if (consumer2Msg != null) {
                System.out.println("consumer2 receive message:" + consumer2Msg.getMessageBodyAsRawString());
            } else {
                System.out.println("the queue is empty");
            }

            Message consumer3Msg = queueForConsumer3.popMessage(30);
            if (consumer3Msg != null) {
                System.out.println("consumer3 receive message:" + consumer3Msg.getMessageBodyAsRawString());
            } else {
                System.out.println("the queue is empty");
            }

            // delete the fullTopic.
//            pullTopic.delete();
        } catch (ClientException ce) {
            System.out.println("Something wrong with the network connection between client and MNS service."
                    + "Please check your network and DNS availablity.");
            ce.printStackTrace();
        } catch (ServiceException se) {
            se.printStackTrace();
        }

        client.close();
    }

    public static void main(String[] args) {
        consumeQueueMsg("MyQueue", 1);
    }
}
