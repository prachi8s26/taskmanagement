package models;

import enums.TaskPriority;
import enums.TaskStatus;
import observer.TaskObserver;
import state.TaskState;
import state.TodoState;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class Task {
    private final UUID id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private final User createdBy;
    private LocalDateTime deadline;
    private TaskPriority priority;
    private User assignee;
    private TaskState currentTaskState;
    private final Set<Tag> tags;
    private List<Comment> comments;
    private List<Task> subTasks;
    private final List<ActivityLog> activityLogs;
    private final List<TaskObserver> observers;

    public Task(TaskBuilder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.createdAt = LocalDateTime.now();
        this.createdBy = builder.createdBy;
        this.deadline = builder.deadline;
        this.priority = builder.priority;
        this.assignee = builder.assignee;
        this.tags = builder.tags;
        this.currentTaskState = new TodoState();
        this.comments = new ArrayList<>();
        this.subTasks = new ArrayList<>();
        this.activityLogs = new ArrayList<>();
        this.observers = new ArrayList<>();
        addLog("Task created with title: " + title);
    }

    public synchronized void AddOrUpdateAssignee(User assignee){
        this.assignee = assignee;
    }

    public synchronized void RemoveAssignee(User assignee){
        this.assignee = null;
    }

    public synchronized void updatePriority(TaskPriority priority){
        this.priority = priority;
    }

    public void AddComment(Comment comment){
        this.comments.add(comment);
        addLog("Comment added by" + comment.getAuthor().getUser().getName());
    }

    public synchronized void addSubTask(Task subTask){
        this.subTasks.add(subTask);
        addLog("Subtask Added: "+ subTask.getTitle());
    }
    public void addLog(String logDescription) {
        this.activityLogs.add(new ActivityLog(logDescription));
        notifyObservers("status");
    }

//    State Pattern Methods
    public void setState(TaskState state){
        this.currentTaskState = state;
        addLog("Status changed to: " + state.getTaskStatus());
        notifyObservers("status");
    }

    public void startProgress(){
        currentTaskState.startProgress(this);
    }

    public void CompleteTask(){
        currentTaskState.completeTask(this);
    }

    public void ReopenTask(){
        currentTaskState.reopenTask(this);
    }

    public TaskState getTaskState(){
        return this.currentTaskState;
    }
    public TaskStatus getTaskStatus() {
        return currentTaskState.getTaskStatus();
    }
    //    observers
    public void addObserver(TaskObserver observer){
        this.observers.add(observer);
    }

    public void removeObserver(TaskObserver observer){
        this.observers.remove(observer);
    }

    public void notifyObservers(String changeType){
        for(TaskObserver observer : observers){
            observer.update(this, changeType);
        }
    }

    public boolean isComposite() { return !subTasks.isEmpty(); }

    public void display(String indent) {
        System.out.println(indent + "- " + title + " [" + getTaskState() + ", " + priority + ", Due: " + deadline + "]");
        if (isComposite()) {
            for (Task subtask : subTasks) {
                subtask.display(indent + "  ");
            }
        }
    }

    //    getters and setters
    public UUID getId(){
        return this.id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public LocalDateTime getCreatedAt(){
        return this.createdAt;
    }

    public TaskPriority getPriority(){
        return this.priority;
    }

    public User getAssignee(){
        return this.assignee;
    }

    public TaskState getCurrentTaskState(){
        return this.currentTaskState;
    }

    public LocalDateTime getDeadline(){
        return this.deadline;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAssignee(User assignee){
        this.assignee = assignee;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public static class TaskBuilder{
        private final UUID id;
        private String title;
        private String description = "";
        private User createdBy;
        private LocalDateTime deadline;
        private TaskPriority priority;
        private User assignee;
        private Set<Tag> tags;

        public TaskBuilder(String title){
            this.id = UUID.randomUUID();
            this.title = title;
        }

        public TaskBuilder description(String description){this.description = description; return this;}
        public TaskBuilder createdBy(User createdBy){this.createdBy = createdBy; return this;}
        public TaskBuilder deadline(LocalDateTime deadline){this.deadline = deadline; return this;}
        public TaskBuilder priority(TaskPriority priority){this.priority = priority; return this;}
        public TaskBuilder assignee(User assignee){this.assignee = assignee; return this;}
        public TaskBuilder tags(Set<Tag> tags){this.tags = tags; return this;}
        public Task build() {
            return new Task(this);
        }

    }

}
