import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;
import models.TaskList;
import models.User;
import strategy.TaskSortStrategy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class TaskManagementSystem {
    private static TaskManagementSystem instance;
    private final Map<String, Task> tasks;
    private final Map<String, User> users;
    private final Map<String, TaskList> taskLists;

    TaskManagementSystem() {
        users = new ConcurrentHashMap<>();
        tasks = new ConcurrentHashMap<>();
        taskLists = new ConcurrentHashMap<>();
    }

    public synchronized static TaskManagementSystem getInstance() {
        if (instance == null) {
            instance = new TaskManagementSystem();
        }
        return instance;
    }

    public User CreateUser(String username, String useremail){
        User user = new User(username, useremail);
        users.put(user.getId().toString(), user);
        return user;
    }

    public TaskList CreateTaskList(String listName){
        TaskList tasklist = new TaskList(listName);
        taskLists.put(listName, tasklist);
        return tasklist;
    }

    public Task CreateTask(String title, String description, LocalDateTime dueDate,
                           TaskPriority priority, UUID createdByUserId){
        User createdBy = users.get(createdByUserId.toString());
        if (createdBy == null)
            throw new IllegalArgumentException("User not found.");

        Task task = new Task.TaskBuilder(title)
                .description(description)
                .deadline(dueDate)
                .priority(priority)
                .createdBy(createdBy)
                .build();



        tasks.put(task.getId().toString(), task);
        return task;
    }

    public List<Task> listTasksByUser(String userId) {
        User user = users.get(userId);
        return tasks.values().stream()
                .filter(task -> user.equals(task.getAssignee()))
                .collect(Collectors.toList());
    }

    public List<Task> listTasksByStatus(TaskStatus status) {
        return tasks.values().stream()
                .filter(task -> task.getTaskStatus() == status)
                .collect(Collectors.toList());
    }

    public void deleteTask(String taskId) {
        tasks.remove(taskId);
    }

    public List<Task> searchTasks(String keyword, TaskSortStrategy sortingStrategy) {
        List<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getTitle().contains(keyword) || task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        sortingStrategy.sort(matchingTasks);
        return matchingTasks;
    }
}
