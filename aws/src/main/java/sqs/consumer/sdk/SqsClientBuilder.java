package sqs.consumer.sdk;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

/**
 * SQS客户端
 *
 * @author wangqianping
 * @date 2022-06-09
 */
public class SqsClientBuilder {


    public static SqsClient buildSqsClient() {
        SqsClient sqsClient = SqsClient.builder().region(Region.US_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build();
        return sqsClient;
    }




}
