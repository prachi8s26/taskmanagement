import enums.TaskPriority;
import enums.TaskStatus;
import models.Task;
import models.TaskList;
import models.User;
import strategy.SortByDueDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        TaskManagementSystem taskManagementSystem = TaskManagementSystem.getInstance();
        User user1 = taskManagementSystem.CreateUser("Prachi", "prachiTest@email.com");
        User user2 = taskManagementSystem.CreateUser("Sumit", "sumit@email.com");

        TaskList tasklist1 = taskManagementSystem.CreateTaskList("Bug Fix");
        TaskList tasklist2 = taskManagementSystem.CreateTaskList("Feature");

        Task task1 = taskManagementSystem.CreateTask("Enhancement Task", "Launch New Feature",
                LocalDateTime.now().plusDays(2), TaskPriority.LOW, user1.getId());
        Task subtask1 = taskManagementSystem.CreateTask( "Enhancement sub task", "Design UI/UX",
                LocalDateTime.now().plusDays(1), TaskPriority.MEDIUM, user1.getId());
        Task task2 = taskManagementSystem.CreateTask("Bug Fix Task", "Fix API Bug",
                LocalDateTime.now().plusDays(3), TaskPriority.HIGH, user2.getId());

        task1.addSubTask(subtask1);

        tasklist1.addTask(task1);
        tasklist2.addTask(task2);

        subtask1.startProgress();

        // Assign task
        subtask1.setAssignee(user2);

        tasklist1.display();
        List<Task> searchResults = taskManagementSystem.searchTasks("Task", new SortByDueDate());
        System.out.println("\nTasks with keyword Task:");
        for (Task task : searchResults) {
            System.out.println(task.getTitle());
        }

        // Filter tasks by status
        List<Task> filteredTasks = taskManagementSystem.listTasksByStatus(TaskStatus.TODO);
        System.out.println("\nTODO Tasks:");
        for (Task task : filteredTasks) {
            System.out.println(task.getTitle());
        }

        // Mark a task as done
        subtask1.CompleteTask();

        // Get tasks assigned to a user
        List<Task> userTaskList = taskManagementSystem.listTasksByUser(user2.getId().toString());
        System.out.println("\nTask for " + user2.getName() + ":");
        for (Task task : userTaskList) {
            System.out.println(task.getTitle());
        }

        tasklist1.display();

        // Delete a task
        taskManagementSystem.deleteTask(task2.getId().toString());

    }
}