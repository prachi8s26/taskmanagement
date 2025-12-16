package state;

import enums.TaskStatus;
import models.Task;

public interface TaskState {
    void startProgress(Task task);
    void completeTask(Task task);
    void reopenTask(Task task);

    TaskStatus getTaskStatus();
}
