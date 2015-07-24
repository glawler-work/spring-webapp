package starter.app.listener;

import model.JmsPropertyConstants;
import model.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import starter.app.dao.UserJdbcDao;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class AuthenticationMessageListener {

    private Logger logger = LoggerFactory.getLogger(AuthenticationMessageListener.class);

    @Autowired
    private UserJdbcDao userJdbcDao;

    @JmsListener(destination = "auth.queue", containerFactory = "jmsQueueListenerContainerFactory")
    @SendTo("auth.queue.reply")
    public UserCredential listenToQueue(Message message) {
        try {
            logger.info("auth request received");
            String username = message.getStringProperty(JmsPropertyConstants.USERNAME);
            return userJdbcDao.getUserCredential(username);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
