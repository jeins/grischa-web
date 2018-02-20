package de.htw.grischa.models;

import lombok.Data;

@Data
public class Master {
    private String name;
    private double latitude;
    private double longitude;

    public Master() {
        this.name = "Master Node";
        this.latitude = 52.5074494;
        this.longitude = 13.4862395;
    }
}
