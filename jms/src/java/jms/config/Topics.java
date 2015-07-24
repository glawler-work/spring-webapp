package jms.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Topic;

@Configuration
public class Topics {

    @Bean(name = "hello.topic")
    public Topic getHelloTopic() {
        return new ActiveMQTopic("hello.topic");
    }
}
