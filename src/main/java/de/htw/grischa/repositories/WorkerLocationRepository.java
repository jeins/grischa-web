package de.htw.grischa.repositories;

import de.htw.grischa.models.WorkerLocation;

import java.util.List;
import java.util.Map;

public interface WorkerLocationRepository {
    Map<String, WorkerLocation> findAll();
    WorkerLocation findByHostName(String hostName);
    boolean existByHostName(String hostName);
    void save(WorkerLocation workerLocation);
    void delete(String hostName);
}
