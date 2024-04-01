package data;

public class Task {
    protected String name;
    protected TaskPriorityLevel priorityLevel;
    protected boolean isCompleted;

    /**
     * Constructor for new tasks given its name.
     * Tasks are initialized as incomplete.
     *
     * @param name The name of the task to be created.
     */

    public Task(String name) {
        this.name = name;
        this.isCompleted = false;
        this.priorityLevel = TaskPriorityLevel.LOW;
    }

    /**
     * Returns the name of the task that invokes this function.
     *
     * @return Returns the string of the task's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the task's name to the given name.
     *
     * @param name The name to set the task's name to.
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Checks if a task is complete and returns true if complete, false if incomplete.
     *
     * @return The boolean value that represents whether a task is completed:
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Sets the completeness status of a task.
     *
     * @param completed Boolean value to set the task to.
     */

    public void setCompleteness(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Checks the completeness status of a task and returns to the
     * caller the status icon of the task.
     * "X" for complete, " " for incomplete.
     *
     * @return String representation of completeness status icon.
     */
    
    public String getMarkedStatusIcon() {
        return (isCompleted ? "X" : "O");
    }

    /**
     * Sets the priority level of the task.
     */
    public void setPriorityLevel(TaskPriorityLevel priorityLevelOfTask) {
        this.priorityLevel = priorityLevelOfTask;
    }

    /**
     * Gets the icon representation of the priority level of the task.
     * "H" for high, "M" for medium, "L" for low.
     */
    public String getPriorityLevelIcon() {
        return (priorityLevel == TaskPriorityLevel.HIGH ? "H" : 
                (priorityLevel == TaskPriorityLevel.MEDIUM ? "M" :
                "L"));
    }

    /**
     * Returns the task type of the specified task.
     *
     * @return "?" which represents an unclassified task.
     */

    public String getTaskType() {
        return "?";
    }

    /**
     * Getter for start date for an Event task.
     * It is a dummy function meant for the Event subclass to override.
     *
     * @return Empty string.
     */
    public String getStartDate() {
        return "";
    }

    /**
     * Getter for end date for an Event task.
     * It is a dummy function meant for the Event subclass to override.
     *
     * @return Empty string.
     */
    public String getEndDate() {
        return "";
    }

    /**
     * Getter for start time of Event task.
     * It is a dummy function meant for the Event subclass to override.
     *
     * @return Empty string.
     */
    public String getStartTime() {
        return "";
    }

    /**
     * Getter for end time of Event task.
     * It is a dummy function meant for the Event subclass to override.
     *
     * @return Empty string.
     */
    public String getEndTime() {
        return "";
    }

    /**
     * Getter for by date for a Deadline task.
     * It is a dummy function meant for the Deadline subclass to override.
     *
     * @return Empty string.
     */
    public String getByDate() {
        return "";
    }

    /**
     * Getter for by time for a Deadline task.
     * It is a dummy function meant for the Deadline subclass to override
     *
     * @return Empty string.
     */
    public String getByTime() {
        return "";
    }

    /**
     * Method that creates the save format for a task.
     * It is a dummy function meant for the subclass to override.
     *
     * @return Empty string.
     */
    public String getSaveFormat () {
        return "";
    }

    /**
     * Method that creates the display format for a task.
     * 
     * @return The String representation of the display format for this task.
     */
    public String getDisplayFormat() {
        String displayFormat = String.format(
                "[%s][%s][%s] ", getTaskType(), getMarkedStatusIcon(), getPriorityLevelIcon());

        return displayFormat;
    }
}
