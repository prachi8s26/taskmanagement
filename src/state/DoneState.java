package state;

import enums.TaskStatus;
import models.Task;

public class DoneState implements TaskState {

    @Override
    public void startProgress(Task task){
        System.out.println("System is in done state.");
    }

    @Override
    public void completeTask(Task task){
        System.out.println("task already complete.");
    }

    @Override
    public void reopenTask(Task task){
        task.setState(new TodoState());
    }

    @Override
    public TaskStatus getTaskStatus(){
        return TaskStatus.DONE;
    }
}
