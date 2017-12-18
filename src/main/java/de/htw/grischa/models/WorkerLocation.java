package de.htw.grischa.models;

import java.io.Serializable;

public class WorkerLocation implements Serializable{
    private String hostName;
    private String latitude;
    private String longitude;

    public WorkerLocation(String hostName, String latitude, String longitude) {
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
