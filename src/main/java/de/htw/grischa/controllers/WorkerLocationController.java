package de.htw.grischa.controllers;

import de.htw.grischa.models.Worker;
import de.htw.grischa.models.WorkerLocation;
import de.htw.grischa.repositories.WorkerLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class WorkerLocationController {
    private Worker worker;

    @Autowired
    private WorkerLocationRepository workerLocationRepository;

    @PostConstruct
    public void init(){
        System.out.println("generate random location");
        cleanHashFromRandomLocation();
        generateRandomLocation();
    }

    public WorkerLocationController(){
        this.worker = new Worker();
    }

    public void setWorker(Worker worker){
        this.worker = worker;
    }

    public boolean isLocationKeyExist(String hostname){
        return workerLocationRepository.existByHostName(hostname);
    }

    public void setLatitudeLongitude(){
        WorkerLocation workerLocation = workerLocationRepository.findByHostName(worker.getHostName());

        worker.setLatitude(workerLocation.getLatitude());
        worker.setLongitude(workerLocation.getLongitude());
    }

    public void generateGeoLocationAndSaveToRedis(){
        Map<String, Double> latitudeLongitude = GeolocationGenerator.getLatitudeLongitude(worker.getHostName());

        WorkerLocation workerLocation = new WorkerLocation(
                worker.getHostName(),
                latitudeLongitude.get("latitude"),
                latitudeLongitude.get("longitude"));

        workerLocationRepository.save(workerLocation);

        worker.setLatitude(workerLocation.getLatitude());
        worker.setLongitude(workerLocation.getLongitude());
    }

    public void generateRandomLocation(){
        double latLongDE[][] = randomLatLongDE();

        for (int i = 0; i < latLongDE.length; i++){
            WorkerLocation workerLocation = new WorkerLocation(
                    Integer.toString(i),
                    latLongDE[i][0],
                    latLongDE[i][1]);
            workerLocationRepository.save(workerLocation);
        }
    }

    public void cleanHashFromRandomLocation(){
        double latLongDE[][] = randomLatLongDE();

        for (int i = 0; i < latLongDE.length; i++){
            workerLocationRepository.delete(Integer.toString(i));
        }
    }

    private double[][] randomLatLongDE(){
        double latLongDE[][] = {
                {51.9455, 13.8852},
                {52.357756, 8.032782},
                {50.801353, 8.362372},
                {49.349033, 11.328680},
                {49.988955, 7.747137},
                {48.787614, 8.648016},
                {48.584530, 8.296454},
                {48.307606, 8.296454},
                {53.444489, 10.823309}
        };

        return latLongDE;
    }
}
