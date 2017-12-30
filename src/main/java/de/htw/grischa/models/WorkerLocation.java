package de.htw.grischa.models;

import java.io.Serializable;

public class WorkerLocation implements Serializable{
    private String hostName;
    private double latitude;
    private double longitude;

    public WorkerLocation(String hostName, double latitude, double longitude) {
        this.hostName = hostName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
