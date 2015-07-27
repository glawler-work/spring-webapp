package starter.app.producer;

import model.JmsPropertyConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import starter.app.dao.HelloHibernateDao;

import javax.jms.Message;
import javax.jms.Session;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JmsMessageCreatorTests {

    private static final String HELLO = "hello";
    private JmsMessageCreator jmsMessageCreator;
    private @Mock Session session;
    private @Mock Message message;
    private @Mock HelloHibernateDao helloHibernateDao;

    @Before
    public void onSetUp() throws Exception {
        jmsMessageCreator = new JmsMessageCreator(helloHibernateDao);
        when(helloHibernateDao.getHello()).thenReturn(HELLO);
        when(session.createMessage()).thenReturn(message);

    }

    @Test
    public void happyPath() throws Exception {
        jmsMessageCreator.createMessage(session);
        verify(message, times(1)).setStringProperty(JmsPropertyConstants.HELLO, HELLO);
    }
}
