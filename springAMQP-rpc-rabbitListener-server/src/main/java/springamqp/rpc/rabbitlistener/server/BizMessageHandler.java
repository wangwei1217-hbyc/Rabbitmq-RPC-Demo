package springamqp.rpc.rabbitlistener.server;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 使用 @RabbitListener 注解来监听队列，注解所作用在的方法即为具体的消息处理逻辑。
 * 该方法可以有返回值，在RPC中，该方法的返回值代表rpc服务端的处理结果（会通过MessageListener中的toMessage()方法，
 * 将处理结果包装成一个Message对象，客户端通过rabbitTemplate.sendAndReceive()方法的返回值就可以获取到该Message对象）
 * Created by wangwei on 2019/10/30 0030.
 */
@Component
public class BizMessageHandler {
    @RabbitListener(queues = {"sms"},containerFactory = "rabbitListenerContainerFactory")
    public SendStatus handleMessage(@Payload Map body, Message message, Channel channel) throws Exception{
        System.out.println("-----BizMessageHandler(Map)------");
        System.out.println(body.toString());
        System.out.println("Message: "+message);
        System.out.println("channel: "+channel);
        String phone = body.get("phone").toString();
        String content = body.get("content").toString();
        boolean result = SendSMSTool.sendSMS(phone, content);
        SendStatus sendStatus = new SendStatus();
        sendStatus.setPhone(phone);
        sendStatus.setResult(result ? "SUCCESS" : "FAIL");
        TimeUnit.SECONDS.sleep(6);
        return sendStatus;
    }
}
