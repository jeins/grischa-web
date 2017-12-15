package de.htw.grischa.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Worker {
    private int result;
    private String hostName;
    private LocalDateTime sendDate;
    private String latitude;
    private String longitude;

    public Worker(){}

    public Worker(int result, String hostName, LocalDateTime sendDate) {
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

        return sendDate.format(formatter);
    }

    public void setSendDate(String sendDate) {
        this.sendDate = LocalDateTime.parse(sendDate);
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
