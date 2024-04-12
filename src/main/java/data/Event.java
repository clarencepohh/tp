package data;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;

import java.util.GregorianCalendar;

import static java.lang.Integer.parseInt;

public class Event extends Task {
    protected String startDate;
    protected String endDate;
    private final String startTime;
    private final String endTime;


    /**
     * Constructor for new tasks given its name.
     * Tasks are initialized as incomplete.
     * Events are also considered as tasks.
     *
     * @param name The name of the task to be created.
     * @param start The starting time/date of the task.
     * @param end The ending time/date of the task.
     */
    public Event(String name, String start, String end, String startTime, String endTime) {
        super(name);
        this.startDate = start;
        this.endDate = end;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Getter for start date of Event task.
     * Overrides super dummy function.
     *
     * @return The String representation of the start date.
     */
    @Override
    public String getStartDate() {
        return startDate;
    }

    /**
     * Getter for start time of Event task.
     * Overrides super dummy function.
     *
     * @return The String representation of the start time.
     */
    @Override
    public String getStartTime() {
        return startTime;
    }

    /**
     * Getter for end time of Event task.
     * Overrides super dummy function.
     *
     * @return The String representation of the end time.
     */
    @Override
    public String getEndTime() {
        return endTime;
    }

    /**
     * Getter for end date of Event task.
     * Overrides super dummy function.
     *
     * @return The String representation of the end date.
     */
    @Override
    public String getEndDate() {
        return endDate;
    }

    /**
     * Returns the task type of the specified task.
     * Override function of superclass Task.
     *
     * @return "E" which represents an Event task.
     */

    @Override
    public String getTaskType() {
        return "E";
    }

    /**
     * Method that creates the save format for an Event task.
     * Overrides super dummy function.
     *
     * @return The String representation of the save format for this task.
     */
    @Override
    public String getSaveFormat () {
        return getTaskType() + "|" + getMarkedStatusIcon() + "|" + getPriorityLevelIcon() + "|" + 
                getName() + "|" + getStartDate() + "|" + getEndDate() + "|" + getStartTime() + "|"
                + getEndTime();
    }

    //@@author kyhjonathan-unused
    public static VEvent eventToVEvent(Event event) {
        java.util.Calendar startDate = new GregorianCalendar();
        int intStartDate = parseInt(event.getStartDate().substring(2,5)) - 1;
        System.out.println(intStartDate);
        startDate.set(java.util.Calendar.MONTH, 3);
        startDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        startDate.set(java.util.Calendar.YEAR, 2023);
        startDate.set(java.util.Calendar.HOUR_OF_DAY, 9);
        startDate.set(java.util.Calendar.MINUTE, 0);
        startDate.set(java.util.Calendar.SECOND, 0);

        java.util.Calendar endDate = new GregorianCalendar();
        endDate.set(java.util.Calendar.MONTH, java.util.Calendar.APRIL);
        endDate.set(java.util.Calendar.DAY_OF_MONTH, 1);
        endDate.set(java.util.Calendar.YEAR, 2023);
        endDate.set(java.util.Calendar.HOUR_OF_DAY, 13);
        endDate.set(java.util.Calendar.MINUTE, 0);
        endDate.set(java.util.Calendar.SECOND, 0);

        // Create the event
        DateTime start = new DateTime(startDate.getTime());
        DateTime end = new DateTime(endDate.getTime());
        VEvent meeting = new VEvent(start, end, "Team Meeting");

        return meeting;
    }
}

