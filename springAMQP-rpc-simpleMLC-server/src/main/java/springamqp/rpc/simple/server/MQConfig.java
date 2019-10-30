package springamqp.rpc.simple.server;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangwei on 2019/10/30 0030.
 */
@Configuration
public class MQConfig {
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setUri("amqp://wangwei:wangwei@192.168.109.128:5672");
        //CacheModel:CHANNEL-默认，可以自动声明相关组件；CONNECTION-不会自动声明
        factory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);//true-默认，可自动声明；false-则不会自动声明
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAutoStartup(true);
        container.setQueueNames("sms");

        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(new MessageHandler());

        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        messageListenerAdapter.setMessageConverter(jsonMessageConverter);
        container.setMessageListener(messageListenerAdapter);

        return container;
    }
}
