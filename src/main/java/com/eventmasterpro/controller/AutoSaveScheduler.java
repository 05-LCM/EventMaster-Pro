package com.eventmasterpro.controller;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoSaveScheduler {
    private final DataController dataController;
    private final ScheduledExecutorService scheduler;
    //Constructor
    public AutoSaveScheduler(DataController dataController) {
        this.dataController = dataController;
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
    }
    //Start autosave, self-guarded every minute
    public void startAutoSave() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Auto-saving data...");
                dataController.saveAllData();
            } catch (Exception e) {
                System.err.println("Auto-save failed: " + e.getMessage());
            }
        }, 1, 1, TimeUnit.MINUTES);
    }
}
