package de.htw.grischa.controllers;

import de.htw.grischa.models.GeoLocationIpApi;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class GeoLocationController {
    private static String IPAPI_URL = "http://ip-api.com/json/";
    public static double MIDDLE_DE_LATITUDE = 51.165691;
    public static double MIDDLE_DE_LONGITUDE = 10.451526;

    /**
     * get latitude and longitude
     * if a domain exist from hostname with generate from API
     * else get latitude and longitude from middle of germany
     *
     * @param hostName
     * @return
     */
    public static Map<String, Double> getLatitudeLongitude(String hostName){
        Map<String, Double> latitudeLongitude = new HashMap<String, Double>();
        boolean setLatLonToMiddleDe = true;

        if(hostName.contains(".")){
            String domain = hostName.substring(hostName.indexOf(".")+1);
            GeoLocationIpApi geoLocationIpApi = getDataFromGeolocationIpApi(domain);

            if(geoLocationIpApi.getStatus().equals("success")){
                latitudeLongitude.put("latitude", geoLocationIpApi.getLat());
                latitudeLongitude.put("longitude", geoLocationIpApi.getLon());

                setLatLonToMiddleDe = false;
            }
        }

        if(setLatLonToMiddleDe){
            latitudeLongitude.put("latitude", MIDDLE_DE_LATITUDE);
            latitudeLongitude.put("longitude", MIDDLE_DE_LONGITUDE);
        }

        return latitudeLongitude;
    }

    /**
     * convert domain to latitude and longitude with ip-api.com
     *
     * @param domain
     * @return
     */
    public static GeoLocationIpApi getDataFromGeolocationIpApi(String domain){
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(IPAPI_URL + domain, GeoLocationIpApi.class);
    }
}
