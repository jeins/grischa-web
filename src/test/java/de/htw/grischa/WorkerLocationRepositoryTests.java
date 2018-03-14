package de.htw.grischa;

import de.htw.grischa.models.WorkerLocation;
import de.htw.grischa.repositories.WorkerLocationRepository;
import de.htw.grischa.repositories.WorkerLocationRepositoryImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkerLocationRepositoryTests {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    WorkerLocationRepository workerLocationRepository;

    private String hostNameTest = "abc";
    private double latitudeTest = 1.234;
    private double longitudeTest = 1.2345;

    @SpringBootApplication
    static class Config{}


    @Before
    public void setUp(){
        WorkerLocation workerLocation = new WorkerLocation("abc", latitudeTest, longitudeTest);
        workerLocationRepository.save(workerLocation);
    }

    @Test
    public void hostNameExistShouldReturnTrue(){
        boolean isExist = workerLocationRepository.existByHostName(hostNameTest);
        Assert.assertEquals(true, isExist);
    }

    @Test
    public void hostNameNotFoundShouldReturnFalse(){
        boolean isExist = workerLocationRepository.existByHostName("def");
        Assert.assertEquals(false, isExist);
    }

    @Test
    public void findByHostNameShouldReturnTrue(){
        WorkerLocation workerLocation = workerLocationRepository.findByHostName(hostNameTest);
        Assert.assertEquals(latitudeTest, workerLocation.getLatitude(), 0);
        Assert.assertEquals(longitudeTest, workerLocation.getLongitude(), 0);
    }
}
