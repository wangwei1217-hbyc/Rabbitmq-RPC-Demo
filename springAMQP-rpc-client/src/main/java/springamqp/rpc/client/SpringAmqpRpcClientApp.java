package springamqp.rpc.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * springAMQP-RPC-Client
 * 使用步骤：
 *   1：使用sendAndReceive方法发送消息，该方法返回一个Message对象，该对象就是server返回的结果
 *   2： sendAndReceive如果超过5秒还没收到结果，则返回null，这个超时时间可以通过RabbitTemplate.setReplyTimeout(10000)设置

 * Created by wangwei on 2019/10/30 0030.
 */
@ComponentScan
public class SpringAmqpRpcClientApp {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAmqpRpcClientApp.class);
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);

        String phone = "15211334455";
        String content = "双十一购物狂欢节，五折限量秒杀，优惠空前，详情点击：http://www.xxx.com/xxx";
        Map<String,Object> map = new HashMap<>();
        map.put("phone",phone);
        map.put("content",content);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");

        ObjectMapper objectMapper = new ObjectMapper();
        byte[] body = objectMapper.writeValueAsBytes(map);
        Message message = new Message(body,messageProperties);

        /**
         * rabbitTemplate.sendAndReceive()方法的返回值就是Server端返回的处理结果
         * springAMQP内部也是采用的伪队列（"amq.rabbitmq.reply-to",不产生临时队列，）
         * 来避免rpc过程中产生过多的临时队列，影响rabbitmq的性能。
         * rpc服务端将处理结果直接发送到当前等待的channel中。
         *
         * sendAndReceive如果超过5秒还没收到结果，则返回null，
         * 这个超时时间可以通过RabbitTemplate.setReplyTimeout(10000)设置
         */
        Message replyMessage = rabbitTemplate.sendAndReceive("", "sms", message, new CorrelationData(UUID.randomUUID().toString()));
        System.out.println(replyMessage.getBody().length);
        System.out.println(new String(replyMessage.getBody()));
        System.out.println(replyMessage.getMessageProperties());

        TimeUnit.SECONDS.sleep(10);
        applicationContext.close();
    }
}
