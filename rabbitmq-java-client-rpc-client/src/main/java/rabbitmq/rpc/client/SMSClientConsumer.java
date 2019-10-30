package rabbitmq.rpc.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * RPC客户端需监听客户端请求中所指定的Queue（ReplyTo），以获得消息处理结果通知
 * Created by wangwei on 2019/10/28 0028.
 */
public class SMSClientConsumer extends DefaultConsumer {
    public SMSClientConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("----------收到RPC调用回复了----------");
        System.out.println("RPC调用【"+properties.getCorrelationId()+"】短信发送结果： "+new String(body));
    }
}
