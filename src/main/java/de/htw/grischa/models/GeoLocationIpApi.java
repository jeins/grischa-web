package de.htw.grischa.models;

import lombok.Data;

@Data
public class GeoLocationIpApi {
    private String as;
    private String city;
    private String country;
    private String countryCode;
    private String isp;
    private double lat;
    private double lon;
    private String org;
    private String query;
    private String region;
    private String reginName;
    private String status;
    private String timezone;
    private String zip;
}
