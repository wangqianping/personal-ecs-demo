package sqs.consumer.sdk;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.List;

/**
 * 通过SDK处理消息
 * @author wangqianping
 * @date 2022-06-10
 */
public class MessageHandler {


    public static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/928445470830/Queue9";

    public  List<Message> receiveMessages(SqsClient sqsClient, String queueUrl) {
        System.out.println("\nReceive messages");
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(queueUrl).maxNumberOfMessages(10).build();
            List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
            return messages;
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return null;
    }

    public  void deleteMessages(SqsClient sqsClient, String queueUrl, List<Message> messages) {
        System.out.println("\nDelete Messages");
        try {
            for (Message message : messages) {
                DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(queueUrl).receiptHandle(message.receiptHandle()).build();
                sqsClient.deleteMessage(deleteMessageRequest);
            }
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
