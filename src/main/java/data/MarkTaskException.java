package data;

public class MarkTaskException extends TaskManagerException {

    public static final String TASK_INDEX_OUT_OF_RANGE_FOR_DAY_WITH_TASKS_MESSAGE = 
            "The task index you attempted to mark is out of range!";
    public static final String TASK_INDEX_WITH_NO_TASKS_MESSAGE = 
            "There are no tasks to mark on this day!";

    public MarkTaskException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkIfTaskIndexIsValid(int taskListSize, int taskIndex) throws MarkTaskException {

        boolean dayHasNoTasks = (taskListSize == 0);
        if (dayHasNoTasks) {
            throw new MarkTaskException(TASK_INDEX_WITH_NO_TASKS_MESSAGE);
        }

        boolean taskIndexOutOfRange = taskIndex < 0 || taskIndex >= taskListSize;
        if (taskIndexOutOfRange) {
            throw new MarkTaskException(TASK_INDEX_OUT_OF_RANGE_FOR_DAY_WITH_TASKS_MESSAGE);
        }
    }
}
