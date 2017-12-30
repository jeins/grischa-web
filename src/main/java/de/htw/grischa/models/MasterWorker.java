package de.htw.grischa.models;

import java.time.LocalDateTime;

public class MasterWorker {
    private Worker worker;
    private Master master;
    private LocalDateTime sendAt;

    public MasterWorker(Worker worker, Master master) {
        this.worker = worker;
        this.master = master;
        this.sendAt = LocalDateTime.now();
    }

    public Worker getWorker() {
        return worker;
    }

    public Master getMaster() {
        return master;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }
}
