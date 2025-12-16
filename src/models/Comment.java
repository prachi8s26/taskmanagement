package models;

import java.util.UUID;
import java.time.LocalDateTime;

public class Comment {
    private UUID id;
    private String description;
    private User author;
    private LocalDateTime CreateAt;

    Comment(String description, User author){
        this.id = UUID.randomUUID();
        this.description = description;
        this.author = author;
        this.CreateAt = LocalDateTime.now();
    }

    public Comment getComment(){
        return this;
    }

    public UUID getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public User getAuthor(){
        return this.author;
    }

    public LocalDateTime getCreateAt(){
        return this.CreateAt;
    }
}
