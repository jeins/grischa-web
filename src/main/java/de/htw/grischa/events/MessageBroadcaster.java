package de.htw.grischa.events;

import de.htw.grischa.controllers.WorkerLocationController;
import de.htw.grischa.models.Master;
import de.htw.grischa.models.MasterWorker;
import de.htw.grischa.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageBroadcaster {
    public static final String WEBSOCKET_MESSAGE_TOPIC_PATH = "/worker/data";

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @Autowired
    private WorkerLocationController workerLocationController;

    private Map<String, String> listOfRandomHostName = new HashMap<String, String>();

    public void handleMessage(Worker worker, String channel){
        // use random location
        worker.setHostName(changeHostNameWithRandomData(worker.getHostName()));
        worker.setStatusPoint();

        workerLocationController.setWorker(worker);

        if(workerLocationController.isLocationKeyExist(worker.getHostName())){
            workerLocationController.setLatitudeLongitude();
        } else{
            workerLocationController.generateGeoLocationAndSaveToRedis();
        }

        Master master = new Master();
        MasterWorker masterWorker = new MasterWorker(worker, master);

        brokerMessagingTemplate.convertAndSend(WEBSOCKET_MESSAGE_TOPIC_PATH, masterWorker);
    }

    public void setBrokerMessagingTemplate(SimpMessagingTemplate brokerMessagingTemplate){
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }

    private String changeHostNameWithRandomData(String hostName){
        if(listOfRandomHostName.containsKey(hostName)){
            return listOfRandomHostName.get(hostName);
        }

        int currentListSize = listOfRandomHostName.size();
        int newHostName = 0;

        if(currentListSize == 1) newHostName = 1;

        if(currentListSize > 1) newHostName = currentListSize - 1;

        listOfRandomHostName.put(hostName, Integer.toString(newHostName));

        return Integer.toString(newHostName);
    }
}
