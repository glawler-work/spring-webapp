package starter.app.listener;

import model.JmsPropertyConstants;
import model.UserCredential;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import starter.app.dao.UserJdbcDao;

import javax.jms.JMSException;
import javax.jms.Message;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationMessageListenerTests {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private AuthenticationMessageListener authenticationMessageListenerListener;
    private @Mock UserJdbcDao userJdbcDao;
    private @Mock Message message;
    private UserCredential userCredential;

    @Before
    public void onSetUp() {
        authenticationMessageListenerListener = new AuthenticationMessageListener();
        userCredential = new UserCredential(USERNAME, PASSWORD);
        ReflectionTestUtils.setField(authenticationMessageListenerListener, "userJdbcDao", userJdbcDao);
    }

    @Test
    public void listeningHappyPath() throws Exception {
        when(message.getStringProperty(JmsPropertyConstants.USERNAME)).thenReturn(USERNAME);
        when(userJdbcDao.getUserCredential(USERNAME)).thenReturn(userCredential);

        assertThat(authenticationMessageListenerListener.listenToQueue(message), is(userCredential));
    }

    @Test(expected = RuntimeException.class)
    public void jmsError() throws Exception {
        when(message.getStringProperty(JmsPropertyConstants.USERNAME)).thenThrow(new JMSException("dummy"));

        authenticationMessageListenerListener.listenToQueue(message);
    }

    @Test(expected = RuntimeException.class)
    public void databaseError() throws Exception {
        when(message.getStringProperty(JmsPropertyConstants.USERNAME)).thenReturn(USERNAME);
        when(userJdbcDao.getUserCredential(USERNAME)).thenThrow(new JMSException("dummy"));

        authenticationMessageListenerListener.listenToQueue(message);
    }
}
