package models;

import java.time.LocalDateTime;

public class ActivityLog {
    private final String description;
    private final LocalDateTime timeStamp;

    ActivityLog(String description){
        this.description = description;
        this.timeStamp = LocalDateTime.now();
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDateTime getTimeStamp(){
        return this.timeStamp;
    }
}
