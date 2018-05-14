package de.htw.grischa.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MasterWorker {
    private Worker worker;
    private Master master;
    private LocalDateTime sendAt;
    private long timeAfterPrepareData = System.currentTimeMillis();

    public MasterWorker(Worker worker, Master master) {
        this.worker = worker;
        this.master = master;
        this.sendAt = LocalDateTime.now();
    }
}
