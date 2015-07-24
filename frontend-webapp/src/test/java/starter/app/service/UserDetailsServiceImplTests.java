package starter.app.service;

import model.UserCredential;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.userdetails.UserDetails;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceImplTests {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private UserDetailsServiceImpl userDetailsService;
    private @Mock JmsTemplate jmsTemplate;
    private @Mock Queue sendQueue;
    private @Mock Queue replyQueue;
    private @Mock ObjectMessage message;
    private UserCredential userCredential;

    @Before
    public void onSetUp() {
        userDetailsService = new UserDetailsServiceImpl(jmsTemplate, sendQueue, replyQueue);
        userCredential = new UserCredential(USERNAME, PASSWORD);
    }

    @Test
    public void happyPath() throws Exception {
        when(jmsTemplate.sendAndReceive(any(MessageCreator.class))).thenReturn(message);
        when(message.getObject()).thenReturn(userCredential);
        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);

        assertThat(userDetails.getUsername(), is(USERNAME));
        assertThat(userDetails.getPassword(), is(PASSWORD));
    }

    @Test(expected = RuntimeException.class)
    public void exceptionOnResponse() throws Exception {
        when(jmsTemplate.sendAndReceive(any(MessageCreator.class))).thenReturn(message);
        when(message.getObject()).thenThrow(new JMSException("dummy"));
        userDetailsService.loadUserByUsername(USERNAME);
    }
}
