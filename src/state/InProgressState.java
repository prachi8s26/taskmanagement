package state;

import enums.TaskStatus;
import models.Task;

public class InProgressState implements TaskState {

    @Override
    public void startProgress(Task task){
        System.out.println("Task is already in progress state.");
    }

    @Override
    public void completeTask(Task task){
        task.setState(new DoneState());
    }

    @Override
    public void reopenTask(Task task){
        System.out.println("task is already in progress state");
    }

    @Override
    public TaskStatus getTaskStatus(){
        return TaskStatus.IN_PROGRESS;
    }
}
