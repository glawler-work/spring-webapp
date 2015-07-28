package starter.app.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import starter.app.dao.HelloHibernateDao;

import javax.jms.Topic;

@Component
public class HelloMessageProducer {

    private Logger logger = LoggerFactory.getLogger(HelloMessageProducer.class);

    private HelloHibernateDao helloHibernateDao;
    private JmsTemplate jmsTemplate;
    private Topic topic;

    @Autowired
    public HelloMessageProducer(HelloHibernateDao helloHibernateDao, JmsTemplate jmsTemplate, MessageCreator messageCreator, @Qualifier("hello.topic") Topic topic) throws InterruptedException {
        this.helloHibernateDao = helloHibernateDao;
        this.jmsTemplate = jmsTemplate;
        jmsTemplate.setDefaultDestination(topic);
        this.topic = topic;
        jmsTemplate.setPubSubDomain(true);

        new Thread(new MessageProducerThread(jmsTemplate, messageCreator)).start();
    }
}
