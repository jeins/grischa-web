package de.htw.grischa.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Worker {
    private int result;
    private String hostName;
    private String sendDate;
    private double latitude;
    private double longitude;
    private String statusPoint;

    public int getResult() {
        return result / 10000;
    }

    public String getSendDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
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
}
