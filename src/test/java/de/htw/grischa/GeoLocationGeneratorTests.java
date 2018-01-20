package de.htw.grischa;


import de.htw.grischa.controllers.GeoLocationGenerator;
import de.htw.grischa.models.GeoLocationIpApi;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoLocationGeneratorTests {

    @SpringBootApplication
    static class Config{}

    @Test
    public void shouldReturnTrueReturnHTWLocation(){
        String hostName = "node.htw-berlin.de";

        Map<String, Double> htwLocation = GeoLocationGenerator.getLatitudeLongitude(hostName);

        Assert.assertEquals(52.5167, htwLocation.get("latitude"), 0);
        Assert.assertEquals(13.4, htwLocation.get("longitude"), 0);
    }

    @Test
    public void shouldReturnTrueReturnDeLocation(){
        String hostName = "node";

        Map<String, Double> deLocation = GeoLocationGenerator.getLatitudeLongitude(hostName);

        Assert.assertEquals(GeoLocationGenerator.DE_LATITUDE, deLocation.get("latitude"), 0);
        Assert.assertEquals(GeoLocationGenerator.DE_LONGITUDE, deLocation.get("longitude"), 0);
    }

    @Test
    public void shouldRetrunTrueCheckResponseFromIpApi(){
        GeoLocationIpApi geoLocationIpApi = GeoLocationGenerator.getDataFromGeolocationIpApi("htw-berlin.de");

        Assert.assertNotNull(geoLocationIpApi.getQuery());
    }
}
