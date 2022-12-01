package iotCore;

import org.eclipse.paho.client.mqttv3.*;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;
import java.util.UUID;

/**
 * 订阅Iot core 的消息示例程序
 *
 * @author wangqianping
 * @date 2022-12-01
 */
public class MessageSubscriberController {

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


    @PostConstruct
    public void init() {

        try {
            MqttClient client = new MqttClient(url, UUID.randomUUID().toString().substring(0, 10) + "_UNIX_ID");
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(false);
            mqttConnectOptions.setConnectionTimeout(60);
            mqttConnectOptions.setKeepAliveInterval(30);
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setServerURIs(StringUtils.split(url, ","));

            //密码
            String password = "password";

            SSLContext sslContext = SslUtil.getSSLContext(dirPath + caFile, dirPath + certFile, dirPath + keyFile, password);
            mqttConnectOptions.setSocketFactory(sslContext.getSocketFactory());


            // 设置回调函数
            client.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("handler message:" + payload);

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(mqttConnectOptions);
            //订阅消息
            String topic = "/test";
            client.subscribe(topic, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
