package starter.app.listener;

import model.JmsPropertyConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.jms.JMSException;
import javax.jms.Message;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HelloListenerTests {

    private HelloListener helloListener;
    private @Mock Message message;

    @Before
    public void onSetUp() {
        helloListener = new HelloListener();
    }

    @Test
    public void listeningHappyPath() throws Exception {
        assertThat(helloListener.getHellos().size(), is(0));
        when(message.getStringProperty(JmsPropertyConstants.HELLO)).thenReturn("hello");
        helloListener.getHello(message);
        assertThat(helloListener.getHellos().size(), is(1));
    }

    @Test(expected = RuntimeException.class)
    public void listeningException() throws Exception {
        assertThat(helloListener.getHellos().size(), is(0));
        when(message.getStringProperty(JmsPropertyConstants.HELLO)).thenThrow(new JMSException("dummy"));
        helloListener.getHello(message);
        assertThat(helloListener.getHellos().size(), is(0));
    }
}
