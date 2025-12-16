package models;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public User(String name, String email){
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public User getUser(){
        return this;
    }

    public String getName(){
        return this.name;
    }

    public  String getEmail(){
        return this.email;
    }

    public UUID getId(){
        return this.id;
    }
}
