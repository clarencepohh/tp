package data;

import java.util.List;

public class MarkTaskException extends TaskManagerException {

    public static final String TASK_INDEX_OUT_OF_RANGE_FOR_DAY_WITH_TASKS_MESSAGE = 
            "The task index you attempted to mark is out of range!";
    public static final String TASK_INDEX_WITH_NO_TASKS_MESSAGE = 
            "There are no tasks to mark on this day!";

    public MarkTaskException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkIfTaskIndexIsValid(List<Task> dayTasks, int taskIndex) 
            throws MarkTaskException {
        
        boolean dayHasNoTasks = (dayTasks == null);
        if (dayHasNoTasks) {
            throw new MarkTaskException(TASK_INDEX_WITH_NO_TASKS_MESSAGE);
        }
        
        int taskListSize = dayTasks.size();
        boolean taskIndexOutOfRange = taskIndex < 0 || taskIndex >= taskListSize;
        if (taskIndexOutOfRange) {
            throw new MarkTaskException(TASK_INDEX_OUT_OF_RANGE_FOR_DAY_WITH_TASKS_MESSAGE);
        }
    }
}
