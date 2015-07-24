package starter.app.service;

import model.JmsPropertyConstants;
import model.UserCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.Arrays;
import java.util.UUID;

@Component(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final String ROLE_USER = "ROLE_USER";
    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private JmsTemplate jmsTemplate;
    private Queue replyQueue;
    private Queue sendQueue;

    @Autowired
    public UserDetailsServiceImpl(JmsTemplate jmsTemplate, @Qualifier("auth.queue") Queue sendQueue, @Qualifier("auth.queue.reply") Queue replyQueue) {
        this.jmsTemplate = jmsTemplate;
        this.replyQueue = replyQueue;
        this.sendQueue = sendQueue;
        jmsTemplate.setDefaultDestination(sendQueue);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Message reply = jmsTemplate.sendAndReceive(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createMessage();
                message.setJMSCorrelationID(UUID.randomUUID().toString());
                message.setJMSReplyTo(replyQueue);
                message.setStringProperty(JmsPropertyConstants.USERNAME, username);
                logger.info("requesting auth");
                return message;
            }
        });

        UserCredential userCredential = null;
        try {
            Object object = ((ObjectMessage) reply).getObject();
            userCredential = (UserCredential)object;
        } catch (JMSException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        return new User(userCredential.getUsername(), userCredential.getPassword(), Arrays.asList(new SimpleGrantedAuthority(ROLE_USER)));
    }
}
