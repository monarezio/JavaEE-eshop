package cz.kodytek.shop.jms;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

@RequestScoped
public class JMSService {

    @Resource(mappedName = "java:jboss/exported/jms/queue/loggerQueue")
    private Queue queueExample;

    @Inject
    private JMSContext context;

    public void sendMessage(String txt) {
        try {
            context.createProducer().send(queueExample, txt);
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
