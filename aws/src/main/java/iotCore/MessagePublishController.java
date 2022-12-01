package iotCore;

import javax.annotation.Resource;

/**
 * IOT CORE 本质上是通过一个消息代理对象（message broker）来管理设备与后台的通信的
 * 发消息到IOT CORE 的示例程序
 *
 * @author wangqianping
 * @date 2022-12-01
 */
public class MessagePublishController {

    @Resource
    private MqttSendMessageService mqttSendMessageService;


    public void sendMessage() {
        String topic = "/test";
        mqttSendMessageService.sendToMqtt(topic, String.valueOf("message Body"));
    }

}
