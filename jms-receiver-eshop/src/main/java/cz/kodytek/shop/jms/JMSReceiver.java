package cz.kodytek.shop.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

public class JMSReceiver {

    public static void main(String[] args) {
        Context namingContext = null;
        JMSContext context = null;

        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jboss.naming.remote.client.InitialContextFactory");
        env.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        env.put(Context.SECURITY_PRINCIPAL, "logger");
        env.put(Context.SECURITY_CREDENTIALS, "123");

        try
        {   //Create and start connection
            namingContext = new InitialContext(env);
            InitialContext ctx=new InitialContext();

            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext
                    .lookup("jms/RemoteConnectionFactory");
            System.out.println("Got ConnectionFactory jms/RemoteConnectionFactory");

            Destination destination = (Destination) namingContext
                    .lookup("jms/queue/loggerQueue");
            System.out.println("Got JMS Endpoint jms/queue/loggerQueue");

            context = connectionFactory.createContext("logger", "123");

            // Create the JMS consumer
            JMSConsumer consumer = context.createConsumer(destination);

            consumer.setMessageListener(new MyListener());

            System.out.println("Receiver1 is ready, waiting for messages...");
            System.out.println("press Ctrl+c to shutdown...");
            while(true){
                Thread.sleep(1000);
            }
        }catch(Exception e){System.out.println(e);}
    }

}
