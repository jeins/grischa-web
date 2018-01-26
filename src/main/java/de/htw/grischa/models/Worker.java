package de.htw.grischa.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Worker {
    private int result;
    private String hostName;
    private String sendDate;
    private double latitude;
    private double longitude;
    private String statusPoint;

    public Worker(){}

    public Worker(int result, String hostName, String sendDate) {
        this.result = result;
        this.hostName = hostName;
        this.sendDate = sendDate;
    }

    public int getResult() {
        return result / 10000;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getSendDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
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

    public void setStatusPoint(){
        int value = getResult();
        statusPoint = "win";

        if(value < 0){
            statusPoint = "lost";
        } else if(value == 0){
            statusPoint = "draw";
        }
    }

    public String getStatusPoint(){
        return statusPoint;
    }
}
