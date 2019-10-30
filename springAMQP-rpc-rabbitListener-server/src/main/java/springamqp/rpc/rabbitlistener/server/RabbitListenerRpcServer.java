package springamqp.rpc.rabbitlistener.server;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangwei on 2019/10/30 0030.
 */
@ComponentScan
@EnableRabbit
public class RabbitListenerRpcServer {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(RabbitListenerRpcServer.class);
        System.out.println("springAMQP RabbitListener RPC Server start....");
        TimeUnit.SECONDS.sleep(3600);
        applicationContext.close();

    }
}
