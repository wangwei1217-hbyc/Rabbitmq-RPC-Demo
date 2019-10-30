package springamqp.rpc.simple.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangwei on 2019/10/30 0030.
 */
@ComponentScan
public class SimpleMLCServer {
    public static void main(String[] args) throws Exception{
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SimpleMLCServer.class);
        System.out.println("springAMQP simpleMLC Server start....");
        TimeUnit.SECONDS.sleep(3600);
        applicationContext.close();

    }
}
