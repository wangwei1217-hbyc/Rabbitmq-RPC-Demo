package rabbitmq.rpc.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.concurrent.TimeUnit;

/**
 * RPCServer启动处理类
 * Created by wangwei on 2019/10/28 0028.
 */
public class RPCServerApplication {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://wangwei:wangwei@192.168.109.128:5672");
        Connection connection = connectionFactory.newConnection("SMSRpcServer");
        Channel channel = connection.createChannel();

        String consumerTag = channel.basicConsume("sms", true,"SMSRpcServer", new SMSServerConsumer(channel));
        System.out.println(consumerTag + "短信服务启动成功");
        TimeUnit.SECONDS.sleep(3600);
        channel.close();
        connection.close();
    }
}
