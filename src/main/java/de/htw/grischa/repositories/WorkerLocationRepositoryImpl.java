package de.htw.grischa.repositories;

import de.htw.grischa.models.WorkerLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

@Repository
public class WorkerLocationRepositoryImpl implements WorkerLocationRepository {
    private RedisTemplate<String, WorkerLocation> redisTemplateWorkerLocation;

    private HashOperations hashOperations;

    public static final String KEY = "WORKER_LOCATION";

    @Autowired
    public WorkerLocationRepositoryImpl(RedisTemplate<String, WorkerLocation> redisTemplateWorkerLocation){
        this.redisTemplateWorkerLocation = redisTemplateWorkerLocation;
    }

    @PostConstruct
    private void init(){
        hashOperations = redisTemplateWorkerLocation.opsForHash();
    }

    @Override
    public Map<String, WorkerLocation> findAll() {
        return hashOperations.entries(KEY);
    }

    @Override
    public WorkerLocation findByHostName(String hostName) {
        return (WorkerLocation) hashOperations.get(KEY, hostName);
    }

    @Override
    public boolean existByHostName(String hostName) {
        return hashOperations.hasKey(KEY, hostName);
    }

    @Override
    public void save(WorkerLocation workerLocation) {
        hashOperations.put(KEY, workerLocation.getHostName(), workerLocation);
    }

    @Override
    public void delete(String hostName) {
        hashOperations.delete(KEY, hostName);
    }
}
