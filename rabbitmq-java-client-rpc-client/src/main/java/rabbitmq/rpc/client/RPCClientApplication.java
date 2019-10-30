package rabbitmq.rpc.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangwei on 2019/10/28 0028.
 */
public class RPCClientApplication {
    public static void main(String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUri("amqp://wangwei:wangwei@192.168.109.128:5672");
        Connection connection = connectionFactory.newConnection("SMSRpcClient");
        Channel channel = connection.createChannel();

        /**
         * 创建一个自动删除的Queue（replyTo）
         */
        /*
         name随机，durable=false，exclusive=true,autoDelete=true
         即queueDeclare("", false, true, true, null);
         */
//        String replyTo = channel.queueDeclare().getQueue();

        /**使用伪队列（不需要提前创建.不会声明该队列）*/
        String replyTo = "amq.rabbitmq.reply-to";
        /**
         *  监听replyTo对应的Queue
         */
        channel.basicConsume(replyTo,true,"SMSRpcClient",new SMSClientConsumer(channel));

        /**
         * 发送消息
         */
        for(int i = 1;i <= 5; i++){
            String correlationId = UUID.randomUUID().toString();

            Map<String,Object> headers = new HashMap<>();
            headers.put("phone","1761234566"+i);
            AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().headers(headers).correlationId(correlationId).replyTo(replyTo).deliveryMode(2).contentEncoding("UTF-8").build();
            channel.basicPublish("smsClient","sms",props,(i+"---周年庆6折大促，只剩三天。详情登陆xxx.com/xxx/xxx").getBytes());
        }

        TimeUnit.SECONDS.sleep(3000);
        channel.close();
        connection.close();
    }
}
