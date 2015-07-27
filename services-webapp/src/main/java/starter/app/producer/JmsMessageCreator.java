package starter.app.producer;

import model.JmsPropertyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import starter.app.dao.HelloHibernateDao;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
class JmsMessageCreator implements MessageCreator {

    private Logger logger = LoggerFactory.getLogger(JmsMessageCreator.class);
    private HelloHibernateDao helloHibernateDao;

    @Autowired
    public JmsMessageCreator(HelloHibernateDao helloHibernateDao) {
        this.helloHibernateDao = helloHibernateDao;
    }

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
}
