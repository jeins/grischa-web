package de.htw.grischa.events;

import de.htw.grischa.models.Worker;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber {
    public static final String EVENT_RECEIVE_MESSAGE_KEY = "result:grid-xmpp-user-001";

    public void handleMessage(Worker worker, String channel){
        System.out.println("Received From Channel: " + channel);
        System.out.println(worker.toString());
    }

}
