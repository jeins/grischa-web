package de.htw.grischa.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WorkerLocation implements Serializable{
    private String hostName;
    private double latitude;
    private double longitude;

    public WorkerLocation(String hostName, double latitude, double longitude) {
        this.hostName = hostName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
