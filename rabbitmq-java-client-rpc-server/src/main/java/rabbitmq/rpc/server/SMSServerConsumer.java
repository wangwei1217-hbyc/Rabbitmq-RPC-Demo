package rabbitmq.rpc.server;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 消息服务端需监听客户端发送消息的Queue，以获得消息信息
 * Created by wangwei on 2019/10/28 0028.
 */
public class SMSServerConsumer extends DefaultConsumer {
    public SMSServerConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        //获取消息内容
        String phone = properties.getHeaders().get("phone").toString();
        String content = new String(body);
        /**1.执行发送短信操作*/
        boolean result = SendSMSTool.sendSMS(phone, content);
        System.out.println("消息处理成功");


        /**2.执行消息结果回复操作**/
        String replyTo = properties.getReplyTo();
        String correlationId = properties.getCorrelationId();

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(correlationId).contentEncoding("UTF-8").build();
        this.getChannel().basicPublish("",replyTo,props,(result+"").getBytes());
        System.out.println("消息处理结果-->回复成功");

    }
}
