package sqs.consumer.jms;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

import javax.jms.JMSException;

/**
 * 连接SQS
 *
 * @author wangqianping
 * @date 2022-06-10
 */
public class SqsHandler {


    public static AmazonSQSClientBuilder getAmazonSQSClientBuilder() {
        String accessKey = "";
        String secretKey = "";
        String region = "";
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(3000);
        clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setMaxConnections(1000);
        return AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withClientConfiguration(clientConfiguration).withRegion(region);
    }


    public static SQSConnectionFactory buildSQSConnectionFactory() {
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(new ProviderConfiguration(), getAmazonSQSClientBuilder());
        return connectionFactory;
    }


    public static SQSConnection getSQSConnection() throws JMSException {
        return buildSQSConnectionFactory().createConnection();
    }


}
