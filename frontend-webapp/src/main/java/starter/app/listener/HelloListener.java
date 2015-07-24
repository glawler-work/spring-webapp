package starter.app.listener;

import model.JmsPropertyConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;

@Component
public class HelloListener {

    private Logger logger = LoggerFactory.getLogger(HelloListener.class);

    private List<String> hellos = new ArrayList<>();

    @JmsListener(destination = "hello.topic")
    public void getHello(Message message) {
        try {
            String hello = message.getStringProperty(JmsPropertyConstants.HELLO);
            logger.info("Message received:" + hello);
            hellos.add(hello);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getHellos() {
        return hellos;
    }
}
