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

    public void setStatusPoint(){
        statusPoint = "win";

        if(result < 0){
            statusPoint = "lost";
        } else if(result == 0){
            statusPoint = "draw";
        }
    }
}
