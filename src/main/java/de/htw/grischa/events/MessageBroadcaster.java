package de.htw.grischa.events;

import de.htw.grischa.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageBroadcaster {
    public static final String WEBSOCKET_MESSAGE_TOPIC_PATH = "/worker/data";

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    public void handleMessage(Worker worker, String channel){
        worker.setLatitude("xxxx");
        worker.setLongitude("xxxx");

        brokerMessagingTemplate.convertAndSend(WEBSOCKET_MESSAGE_TOPIC_PATH, worker);
    }

    public void setBrokerMessagingTemplate(SimpMessagingTemplate brokerMessagingTemplate){
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }
}
