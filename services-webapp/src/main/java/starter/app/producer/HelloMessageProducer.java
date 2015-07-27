package starter.app.producer;

import model.JmsPropertyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import starter.app.dao.HelloHibernateDao;

import javax.jms.*;
import java.util.UUID;

@Component
public class HelloMessageProducer {

    private Logger logger = LoggerFactory.getLogger(HelloMessageProducer.class);

    private HelloHibernateDao helloHibernateDao;
    private JmsTemplate jmsTemplate;
    private Topic topic;

    @Autowired
    public HelloMessageProducer(HelloHibernateDao helloHibernateDao, JmsTemplate jmsTemplate, @Qualifier("hello.topic") Topic topic) throws InterruptedException {
        this.helloHibernateDao = helloHibernateDao;
        this.jmsTemplate = jmsTemplate;
        jmsTemplate.setDefaultDestination(topic);
        this.topic = topic;
        jmsTemplate.setPubSubDomain(true);

        new Thread(new MessageProducer()).start();
    }

    private class MessageProducer implements Runnable {
        @Override
        public void run() {
            while(true) {
                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        Message message = session.createMessage();
                        message.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
                        message.setJMSCorrelationID(UUID.randomUUID().toString());
                        message.setJMSMessageID(UUID.randomUUID().toString());
                        message.setStringProperty(JmsPropertyConstants.HELLO, helloHibernateDao.getHello());
                        logger.info("Message produced");
                        return message;
                    }
                });
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
