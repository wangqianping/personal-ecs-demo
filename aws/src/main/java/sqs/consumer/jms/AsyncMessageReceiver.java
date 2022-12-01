package sqs.consumer.jms;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.message.SQSTextMessage;

import javax.jms.*;

/**
 * 通过JMS消费消息示例程序
 *
 * @author wangqianping
 * @date 2022-06-10
 */
public class AsyncMessageReceiver {

    public void handleMessage() {

        try {
            // Create the connection
            SQSConnection connection = SqsHandler.getSQSConnection();
            // Create the session
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            MessageConsumer consumer = session.createConsumer(session.createQueue("Auth_Queue"));
            final int[] num = {1};
            // No messages are processed until this is called
            connection.start();
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        SQSTextMessage sqsMessage = (SQSTextMessage) message;
                        System.out.println(num[0]++);
                        System.out.println("Acknowledged message " + sqsMessage.getText());
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}