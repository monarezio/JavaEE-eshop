package cz.kodytek.shop.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener {

    public void onMessage(Message m) {
        try{
            TextMessage msg=(TextMessage)m;

            System.out.println("following message is received:"+msg.getText());
        }catch(JMSException e){System.out.println(e);}
    }
}
