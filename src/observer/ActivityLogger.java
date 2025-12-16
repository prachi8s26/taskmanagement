package observer;

import models.Task;

public class ActivityLogger implements TaskObserver {
    @Override
    public void update(Task task, String ChangeType){
        System.out.println("ActivityLogger: Task" + task.getTitle() + "Change " + ChangeType);
    }
}
