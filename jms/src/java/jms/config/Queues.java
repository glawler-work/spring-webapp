package jms.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class Queues {

    @Bean(name = "auth.queue.reply")
    public Queue getAuthReplyQueue() {
        return new ActiveMQQueue("auth.queue.reply");
    }

    @Bean(name = "auth.queue")
    public Queue getAuthQueue() {
        return new ActiveMQQueue("auth.queue");
    }
}
