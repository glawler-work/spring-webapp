package starter.app.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

class MessageProducerThread implements Runnable {

    private Logger logger = LoggerFactory.getLogger(MessageProducerThread.class);
    private JmsTemplate jmsTemplate;
    private MessageCreator messageCreator;
    private boolean running = true;

    public MessageProducerThread(JmsTemplate jmsTemplate, MessageCreator messageCreator) {
        this.jmsTemplate = jmsTemplate;
        this.messageCreator = messageCreator;
    }

    @Override
    public void run() {
        while(running) {
            jmsTemplate.send(messageCreator);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void kill() {
        this.running = false;
    }
}