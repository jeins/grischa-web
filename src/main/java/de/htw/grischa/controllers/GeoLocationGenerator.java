package de.htw.grischa.controllers;

import de.htw.grischa.models.GeoLocationIpApi;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GeoLocationGenerator {
    private static String IPAPI_URL = "http://ip-api.com/json/";
    public static double DE_LATITUDE = 51.165691;
    public static double DE_LONGITUDE = 10.451526;

    public static Map<String, Double> getLatitudeLongitude(String hostName){
        Map<String, Double> latitudeLongitude = new HashMap<String, Double>();

        if(hostName.contains(".")){
            String domain = hostName.substring(hostName.indexOf(".")+1);
            GeoLocationIpApi geoLocationIpApi = getDataFromGeolocationIpApi(domain);

            latitudeLongitude.put("latitude", geoLocationIpApi.getLat());
            latitudeLongitude.put("longitude", geoLocationIpApi.getLon());
        } else {
            // if hostname not contains any domain, use latitude and longitude from middle of germany
            latitudeLongitude.put("latitude", DE_LATITUDE);
            latitudeLongitude.put("longitude", DE_LONGITUDE);
        }

        return latitudeLongitude;
    }

    public static GeoLocationIpApi getDataFromGeolocationIpApi(String domain){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(IPAPI_URL + domain, GeoLocationIpApi.class);
    }
}