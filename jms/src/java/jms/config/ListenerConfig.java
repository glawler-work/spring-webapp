package jms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class ListenerConfig {

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Autowired
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory =  new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setTransactionManager(new JmsTransactionManager(connectionFactory));
        factory.setSubscriptionDurable(true);
        return factory;
    }

    @Bean(name = "jmsQueueListenerContainerFactory")
    @Autowired
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(DefaultJmsListenerContainerFactory factory) {
        factory.setSubscriptionDurable(false);
        return factory;
    }
}
