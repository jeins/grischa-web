package de.htw.grischa.controllers;

import de.htw.grischa.models.Master;
import de.htw.grischa.models.MasterWorker;
import de.htw.grischa.models.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DataController {
    public static final String REDIS_CHANNEL = "result:grid-xmpp-user-001";
    public static final String WEBSOCKET_MESSAGE_URI = "/worker/data";

    @Autowired
    private SimpMessagingTemplate brokerMessagingTemplate;

    @Autowired
    private WorkerLocationController workerLocationController;

    private Map<String, String> listOfRandomHostName = new HashMap<String, String>();

    private Worker worker;
    private String receivedChannel;

    public void handleMessage(Worker worker, String receivedChannel){
        this.worker = worker;
        this.receivedChannel = receivedChannel;

        displayRedisMessage();
        prepareWorkerData();

        MasterWorker masterWorker = new MasterWorker(worker, new Master());

        brokerMessagingTemplate.convertAndSend(WEBSOCKET_MESSAGE_URI, masterWorker);
    }

    private void displayRedisMessage()
    {
        System.out.println("Received From Channel: " + receivedChannel);
        System.out.println(worker.toString());

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

    private void prepareWorkerData(){
        // use random location
//        worker.setHostName(changeHostNameWithRandomData(worker.getHostName()));
        worker.setStatusPoint();

        workerLocationController.setWorker(worker);

        if(workerLocationController.isLocationKeyExist(worker.getHostName())){
            workerLocationController.setLatitudeLongitude();
        } else{
            workerLocationController.generateGeoLocationAndSaveToRedis();
        }
    }
}
