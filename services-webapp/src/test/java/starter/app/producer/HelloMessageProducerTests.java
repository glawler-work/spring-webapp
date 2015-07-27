package starter.app.producer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import starter.app.dao.HelloHibernateDao;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class HelloMessageProducerTests {

    private MessageProducerThread messageProducerThread;
    private @Mock JmsTemplate jmsTemplate;
    private @Mock HelloHibernateDao helloHibernateDao;
    private @Mock JmsMessageCreator jmsMessageCreator;

    @Before
    public void onSetUp() throws Exception {
        messageProducerThread = new MessageProducerThread(jmsTemplate, helloHibernateDao, jmsMessageCreator);
        Thread thread = new Thread(messageProducerThread);
        thread.start();
    }

    @Test
    public void run() throws Exception {
        verify(jmsTemplate, times(1)).send(jmsMessageCreator);
    }

    @After
    public void onTearDown() throws Exception {
        messageProducerThread.kill();
    }
}
