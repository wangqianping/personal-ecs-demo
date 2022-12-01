package sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqsMsgProducer {

    private static final Logger log = LoggerFactory.getLogger(SqsMsgProducer.class);

    private final AmazonSQS amazonSQS;

    /**
     * 目标SQS路径
     */
    private String targetSqsUrl;


    public SqsMsgProducer(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public void sendMassage(String msg){
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(targetSqsUrl)
                .withMessageBody(msg);
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
        log.info("sendSunnovaMassage : " + targetSqsUrl + " body : " + msg + " result: " +sendMessageResult.getSdkHttpMetadata().getHttpStatusCode());
    }

}
