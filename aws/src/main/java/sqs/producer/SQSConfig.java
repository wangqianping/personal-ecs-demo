package sqs.producer;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 和SqsHandler一致
 */
@Configuration
public class SQSConfig {

    @Value("${sqs.region:}")
    private String sqsRegion;

    @Value("${sqs.secretKey:}")
    private String secretKey;

    @Value("${sqs.accessKey:}")
    private String accessKey;

    @Bean
    public AmazonSQS initSqs() {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setConnectionTimeout(10000);
        clientConfiguration.setProtocol(Protocol.HTTP);
        clientConfiguration.setMaxConnections(800);
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonSQSClientBuilder amazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(sqsRegion)
                .withClientConfiguration(clientConfiguration);
        AmazonSQS sqs = amazonSQSClientBuilder.build();
        return sqs;
    }
}
