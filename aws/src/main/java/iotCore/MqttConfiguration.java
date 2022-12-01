package iotCore;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageHandler;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;

/**
 * MQTT配置
 */
@Data
@Configuration
@IntegrationComponentScan
public class MqttConfiguration {

    Logger logger = LoggerFactory.getLogger(MqttConfiguration.class);

    /**
     * mqtt连接url
     */
    private String url;

    /**
     * 超时时间（毫秒）
     */
    private int completionTimeout;

    /**
     * 生产者默认主题
     */
    private String defaultTopic;

    /**
     * 证书所在地址
     */
    private String dirPath;

    /**
     * 证书文件名
     */
    private String certFile;

    /**
     * ca文件名
     */
    private String caFile;

    /**
     * key文件名
     */
    private String keyFile;

    /**
     * 订阅的bean名称
     */
    public static final String CHANNEL_NAME_IN = "mqttInboundChannel";

    /**
     * 发布的bean名称
     */
    public static final String CHANNEL_NAME_OUT = "mqttOutboundChannel";

    /**
     * MQTT连接器选项
     *
     */
    @Bean
    public MqttConnectOptions getMqttConnectOptions() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setConnectionTimeout(20);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setServerURIs(StringUtils.split(url, ","));


        try {
            String password = "password";
            SSLContext sslContext = SslUtil.getSSLContext(dirPath + caFile, dirPath + certFile, dirPath + keyFile, password);
            mqttConnectOptions.setSocketFactory(sslContext.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mqttConnectOptions;
    }

    /**
     * MQTT客户端
     *
     * @return {@link MqttPahoClientFactory}
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }

    /**
     * MQTT消息发布
     *
     * @return {@link MessageHandler}
     */
    @Bean
    @ServiceActivator(inputChannel = CHANNEL_NAME_OUT)
    public MessageHandler mqttOutbound() {
        String producerClientId = "test" + "-publisher-server";
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(producerClientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        messageHandler.setDefaultRetained(false);
        return messageHandler;
    }

}