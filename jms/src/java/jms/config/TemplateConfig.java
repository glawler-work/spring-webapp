package jms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

@Configuration
@ComponentScan(basePackages = {"starter.app"})
public class TemplateConfig {

    @Bean(name = "jmsTemplate")
    @Autowired
    public JmsTemplate getJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);

        return jmsTemplate;
    }
}
