package iotCore;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 *
 */
@MessagingGateway(defaultRequestChannel = MqttConfiguration.CHANNEL_NAME_OUT)
public interface MqttSendMessageService {

    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic,String data);
}