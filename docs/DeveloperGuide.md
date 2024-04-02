# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

ical4J Library: https://www.ical4j.org/



## Architecture
The Calendar application is designed with a modular architecture, consisting of the following components:


#### Main Components
- **UI**: The `UiRenderer` class handles the rendering of the calendar views (week and month) to the console.
- **Logic**: The `Main` class acts as the central logic component, handling user input and dispatching commands to the appropriate components.
- **Data**: The `TaskManager` class manages the tasks and their corresponding dates, providing methods for adding, updating, and deleting tasks.
- **Storage**: The `Storage` class handles the persistence of tasks by reading from and writing to a file.
- **Time**: The `View` class and its subclasses (`WeekView` and `MonthView`) manage the rendering and navigation of the week and month views.


## Data Component
### API: [Data](https://github.com/AY2324S2-CS2113-W13-2/tp/tree/master/src/main/java/data)
![Data Class Diagram](images/class/Data.png)
The 'Data' package consists of all the classes that the commands interact with to perform various functions.
Below is a summary of the classes found in the Data package:
- The `TaskManager` class is created that contains all local copies of `Task`, creating a many-to-one relationship with `Task`.
- `Task` is the superclass of all created tasks, namely: `Todo`, `Deadline`, and `Event`.
- When an operation is requested by the user, the `TaskManager` instance calls its own methods to create/read/update/delete the tasks.
- `TaskManagerException` extends the Java class `Exception`, and used when there are exceptions to be handled.
- `TaskType` is an enumeration used in classifying the types of `Task` created. 
- `TaskPriorityLevel` is an enumeration used in classifying the priority level of a `Task`.

# Design & Implementation

## UiRenderer Component
### API: [UiRenderer.java](https://github.com/AY2324S2-CS2113-W13-2/tp/blob/master/src/main/java/ui/UiRenderer.java)

### Overview: <br> 
The UiRenderer component is responsible for rendering the user interface. It is used to display messages, week views as well as month views to the user.

### How it works:
1. When week view is requested, the UiRenderer component will render the week view.
2. This is done by printing the table in ASCII art format, with the days of the week as columns.
3. The week view will display the tasks for the week, with the tasks for each day displayed in the respective columns.
4. The UiRenderer component will also display the current date at the top of the week view.
5. Similarly, when month view is requested, the UiRenderer component will render the month view.

### printWeekHeader Method
`printWeekHeader` Method

The `printWeekHeader` method is responsible for rendering the header section of the week view, including the names of the days and optionally the dates.

#### Method signature:
```
public static void printWeekHeader(LocalDate startOfView, DateTimeFormatter dateFormatter, boolean isMonthView)
```

#### Parameters
- startOfView: The starting date of the week view.
- dateFormatter: Formatter for displaying dates.
- isMonthView: A boolean flag indicating if the month view is being rendered.

#### Method Functionality
- Prints a horizontal divider to delineate the start of the header.
- Displays the names of the days as column headers.
- If not in month view, prints the dates for the respective week.

### printWeekBody Method
The printWeekBody method displays the body of the week view, showing tasks for each day in their respective columns.

#### Method Signature
```
public static void printWeekBody(LocalDate startOfWeek, DateTimeFormatter dateFormatter, TaskManager taskManager)
```

#### Parameters
- startOfWeek: The starting date of the week for which tasks are displayed.
- dateFormatter: Formatter for displaying dates.
- taskManager: The TaskManager instance managing tasks.

#### Method Functionality
- Determines the maximum number of tasks in any day of the week to set the row count.
- Iterates through each day, displaying tasks or placeholders if there are fewer tasks on certain days.

### Month View Rendering
The month view utilizes the printWeekHeader method with the isMonthView parameter set to true, limiting the display to only include week headers without individual tasks.

### Displaying Help and User Commands
`printHelp` Method

The printHelp method provides users with a list of available commands and their descriptions, aiding in navigation and task management.

#### Method Signature
```
public static void printHelp()
```

#### Method Functionality
- Prints a structured list of commands and their purposes within the user interface.
- Highlights key functionalities like navigating views, adding, and updating tasks.

## View Switching

The Calendar application supports switching between the week and month views, allowing users to visualize their tasks in different time perspectives. The switching functionality is implemented through the `WeekView` and `MonthView` classes, which extend the `View` abstract class.

### View Hierarchy

The `View` abstract class provides a common interface for rendering and navigating different calendar views. It defines the following abstract methods:

- `printView(TaskManager taskManager)`: This method is responsible for rendering the view to the console based on the provided `TaskManager` instance.
- `next()`: This method advances the view to the next period (e.g., next week or next month).
- `previous()`: This method moves the view to the previous period (e.g., previous week or previous month).

The `View` class also has a protected `startOfView` field, which represents the starting date of the current view period, and a `dateFormatter` field for formatting dates.

### WeekView

The `WeekView` class extends the `View` abstract class and provides an implementation for rendering and navigating the week view. It overrides the following methods from the `View` class:

- `printView(TaskManager taskManager)`: This method first calculates the end of the week based on the `startOfView` date. It then prints the week header using the `UiRenderer.printWeekHeader` method and the week body using the `UiRenderer.printWeekBody` method.
- `next()`: This method advances the `startOfView` date to the start of the next week by adding one week.
- `previous()`: This method moves the `startOfView` date to the start of the previous week by subtracting one week.

The `WeekView` class also provides the following utility methods:

- `getStartOfWeek()`: Returns the `startOfView` date, which represents the start of the current week.
- `getDateForDay(int dayOfWeek)`: Returns the date for a specific day of the week based on the `startOfView` date. The `dayOfWeek` parameter is expected to be a value between 1 (Monday) and 7 (Sunday).

### MonthView

The `MonthView` class extends the `View` abstract class and provides an implementation for rendering and navigating the month view. It overrides the following methods from the `View` class:

- `printView(TaskManager taskManager)`: This method first calculates the start of the month and the first day of the month that falls on a Sunday. It then prints the month header using the `UiRenderer.printWeekHeader` method with the `isMonthView` flag set to `true`. The method iterates over the weeks in the month and calls the `printWeek` method for each week.
- `next()`: This method advances the `startOfView` date to the start of the next month.
- `previous()`: This method moves the `startOfView` date to the start of the previous month.

The `MonthView` class also provides the following private methods:

- `printWeek(LocalDate current, TaskManager taskManager)`: This method prints a single week within the month view. It iterates over the days of the week and calls the `printDay` method for each day. It also prints the tasks for the week using the `printTasksForWeek` method.
- `printDay(LocalDate current)`: This method prints the day number for a given date within the month view.
- `getMaxTasksForWeek(LocalDate weekStart, TaskManager taskManager)`: This method calculates the maximum number of tasks for a given week by iterating over the days of the week and finding the maximum number of tasks for any day.
- `printTasksForWeek(LocalDate weekStart, int maxTasks, TaskManager taskManager)`: This method prints the tasks for a given week within the month view. It iterates over the tasks and calls the `printTaskForDay` method for each task.
- `printTaskForDay(List<Task> dayTasks, int taskIndex)`: This method prints a single task for a given day within the month view.

### View Switching in Main

The `Main` class is responsible for handling user input and dispatching commands to the appropriate components. It manages the switching between the week and month views based on the user's input.

When the user enters the `month` command, the `Main` class toggles the `inMonthView` flag and calls the `printView` method of the `MonthView` instance, rendering the month view. Similarly, when the user enters the `week` command, the `Main` class sets the `inMonthView` flag to `false` and calls the `printView` method of the `WeekView` instance, rendering the week view.

The following code snippet illustrates the view switching logic in the `Main` class:

```
while (true) {
    // ... (User input handling)

    switch (command) {
        case "month":
            monthView.printView(taskManager);
            inMonthView = !inMonthView; // Toggle month view mode
            printWeek = false;
            break;
        case "week":
            inMonthView = false;
            break;
        // ... (Other command handling)
    }

    if (printWeek) {
        if (!inMonthView) {
            weekView.printView(taskManager);
        } else {
            monthView.printView(taskManager);
        }
    }
}

```

### Logging
The `FileLogger` class sets up a logger that writes log messages to the `logs.log` file in the project directory. The logger is configured to use the `SimpleFormatter` and to write to the file instead of the console.

### Utilities
The `DateUtils` class provides a utility method `getStartOfWeek` that returns the start date of the week for a given date.

### Task Types
The `TaskType` enum defines the different types of tasks supported by the application: `TODO`, `EVENT`, and `DEADLINE`.

### Error Handling <br>
The application handles various exceptions, such as `TaskManagerException`, `DateTimeParseException`, and `NumberFormatException`. When an exception occurs, an error message is printed to the console, and the application continues to run.

## TaskManager Component

### Overview: 
The TaskManager is responsible for handling the creation, reading, updating and deletion [CRUD] of tasks.

### How it works:
1. When the program starts, a TaskManager instance is created.
2. Saved tasks are retrieved by interfacing with the Storage class to create required tasks and save them to this 
instance of TaskManager.  
3. When the user requests an operation, the main function parses the input and hands it to TaskManager if it is related
4. The TaskManager will handle all requests relating to tasks, using methods detailed in the following sections.
   to tasks.
5. The TaskManager will also create INFO-level logs when making changes to tasks.

## Retrieving tasks from the TaskManager
### getTasksForDate Method
The `getTasksForDate` method is responsible for retrieving all tasks on a specified date.
This is a helper function that is called whenever tasks relating to a certain date is needed.

#### Method signature:
```
public List<Task> getTasksForDate(LocalDate date)
```

#### Parameters
- date: The date to retrieve tasks for.

#### Method Functionality
- Gets the LocalDate requested by the caller.
- Returns an ArrayList of Tasks for the specified date. It **is** possible that an empty ArrayList is returned, given
that there are no tasks on that specified date.

## Updating a Task

### Overview

Updating a task involves modifying its description or other attributes such as date or time. This guide outlines the process and considerations for updating tasks within the application.

### Update Manager Method

The `updateManager` method facilitates the updating of tasks. It prompts the user for an updated task description and handles tasks of different types (Todo, Event, Deadline) while ensuring the changes are correctly reflected in the application.

#### Method Signature

```
public void updateManager(Scanner scanner, WeekView weekView, MonthView monthView, boolean inMonthView,
                          TaskManager taskManager, int day, int taskIndex, String newDescription)
        throws TaskManagerException, DateTimeParseException
```

### Parameters

- `scanner`: Scanner object for user input.
- `weekView`: Current week being viewed.
- `monthView`: Current month being viewed.
- `inMonthView`: Boolean indicating whether the month is being viewed.
- `taskManager`: TaskManager instance managing tasks.
- `day`: Day of the task.
- `taskIndex`: Index of the task to update.
- `newDescription`: Updated description of the task.

### Exceptions

- `TaskManagerException`: Thrown if not in the correct week/month view.
- `DateTimeParseException`: Thrown if there is an error parsing the date.

### Method Functionality

1. Converts the specified day to a `LocalDate`.
2. Verifies if the date is in the current week/month view.
3. Updates the task description based on its type.
4. Saves the updated tasks to the appropriate file.
5. Provides feedback to the user upon successful update.

### Update Task Method

The `updateTask` method is responsible for modifying the details of a task based on the user input.

### Method Signature

```
public static void updateTask(LocalDate date, int taskIndex, String newTaskDescription, Scanner scanner)
        throws IndexOutOfBoundsException
```

### Parameters

- `date`: Date of the task.
- `taskIndex`: Index of the task to update.
- `newTaskDescription`: Updated description of the task.
- `scanner`: Scanner object for user input.

### Exceptions

- `IndexOutOfBoundsException`: Thrown if the task index is out of bounds.

### Method Functionality

1. Retrieves the tasks for the specified date.
2. Validates if the task index is within bounds.
3. Handles different task types (Todo, Event, Deadline).
4. Updates the task description accordingly.
5. Logs the changes if applicable.

## Adding a Task/Event/Deadline

### Overview

The Add Task/Event/Deadline feature enhances the TaskManager application by enabling users to create various types of tasks such as Todo, Event, and Deadline. This section elaborates on the implementation details of this feature, encompassing methods for task creation, user input handling, and task addition management.

### `addTask` Method

The `addTask` method orchestrates the creation and addition of a new task to the TaskManager. It accepts parameters including the task date, description, type, and relevant dates/times for events and deadlines. Depending on the task type, it constructs the corresponding task object (Todo, Event, or Deadline) and integrates it into the tasks map.

#### Method Signature

```
public static void addTask(LocalDate date, String taskDescription, TaskType taskType, String[] dates,
                           String[] times) throws TaskManagerException
```

#### Parameters

- `date`: The date for the task.
- `taskDescription`: The description of the task.
- `taskType`: The type of the task (Todo, Event, or Deadline).
- `dates`: An array containing relevant dates for events and deadlines.
- `times`: An array containing relevant times for events and deadlines.

### `addManager` Method

The `addManager` method facilitates the management of adding tasks from user input along with the specified date. It prompts users to input task details, validates the input, and delegates the task creation process to the `addTask` method.

#### Method Signature

```
public void addManager(Scanner scanner, WeekView weekView, MonthView monthView, boolean inMonthView, String action,
                       String day, String taskTypeString, String taskDescription)
        throws TaskManagerException, DateTimeParseException
```

#### Parameters

- `scanner`: Scanner object for user input.
- `weekView`: WeekView object for date validation.
- `monthView`: MonthView object for date validation.
- `inMonthView`: Boolean indicating whether the view is in month view.
- `action`: Action to be performed.
- `day`: Day for the task.
- `taskTypeString`: String representing the task type.
- `taskDescription`: Description of the task.

### Handling User Input

This segment prompts users to input essential details for task creation, encompassing descriptions, dates, and times for events and deadlines. It utilizes the `Scanner` class to capture and validate user input before initiating task creation.

### Task Type Switch Statement

Within the `addTask` method, a switch statement delineates the type of task being created based on the `taskType` parameter. Corresponding actions are executed to create and add the task to the tasks map based on the identified type.

### Error Handling

The implementation incorporates error handling mechanisms to effectively manage scenarios involving invalid inputs or unsupported task types. Exceptions such as `TaskManagerException` are employed to convey descriptive error messages, ensuring user guidance and application integrity.

### Saving Tasks to File

Upon task creation, the `addTask` method guarantees the preservation of the updated task list to a file (`tasks.txt`) by invoking the `saveTasksToFile` method from the `Storage` class. This serves to persist task data across application sessions.

### Exceptions

`IndexOutOfBoundsException`: Thrown if the task index is out of bounds.

### Method Functionality

1. Retrieves the tasks for the specified date.
2. Validates if the task index is within bounds.
3. Handles different task types (Todo, Event, Deadline).
4. Updates the task description accordingly.
5. Logs the changes if applicable.

## Deleting a Task/Event/Deadline
### deleteTask Method
The `deleteTask` method is responsible for deleting a task specified by the user, on a specified date.

#### Method signature:
```
public void deleteTask(LocalDate date, int taskIndex)
```

#### Parameters
- date: The date of the task.
- taskIndex: The index of the task to delete.

#### Method Functionality
- Gets the LocalDate requested by the user.
- Checks if there is are active tasks on the selected date, and checks if the date is within the period currently shown
  on the UI [week or month view].
- Deletes the task if the above two checks pass, else highlights to the user through a console-printed error message.

### deleteAllTasksOnDate Method
The `deleteAllTasksOnDate` method is responsible for deleting **ALL** tasks on a date specified by the user.
> Do note that this method is **NOT** used is normal operation of the application. It is currently only being used in 
> testing, for easier delete operations for dummy tasks created on a date. 

#### Method signature:
```
public static void deleteAllTasksOnDate (TaskManager taskManager, LocalDate specifiedDate)
```

#### Parameters
- taskManager: The taskManager class in use.
- specifiedDate: The date on which all tasks are to be deleted.

#### Method Functionality
- Gets the LocalDate requested by the caller.
- Calls the `deleteTask` method on all tasks on the date.

## Interfacing with Storage class
### `addTasksFromFile` method
The `addTasksFromFile` method is responsible for parsing save data generated by the `Storage` class.
> See more details on the `Storage` class below.

#### Method signature:
```
public void addTasksFromFile(Map<LocalDate, List<Task>> tasksFromFile) throws TaskManagerException
```

#### Parameters
- tasksFromFile: A map containing tasks read from a file.

#### Method Functionality
- Gets the map that contains all tasks from the save file, generated by `Storage.loadFilesFromFile`.
- Attempts to parse each individual task found in the list and add it to the currently-active instance of TaskManager.
- Logs any important messages to log file, if any.

## Storage component
**API** : [Storage.java](https://github.com/AY2324S2-CS2113-W13-2/tp/blob/master/src/main/java/storage/Storage.java)

The 'storage' component:
* Reads tasks from the formatted `./save/tasks.txt` file and appends to task hashmap.
* Identifies unique tasks stored in task hashmap, parses and writes to `./save/tasks.txt` file
* Handles exception if `./save/tasks.txt` is in corrupted format

The `saveTasksToFile` method writes the tasks in a HashMap to the file in the following format:

```
<date>|<taskType>|<taskDescription>|<additionalData>
```

For example:

```
2023-06-01|TODO|Buy groceries
2023-06-02|EVENT|Meeting|2023-06-02|2023-06-03|09:00|11:00
2023-06-05|DEADLINE|Submit report|2023-06-10|23:59
```

The `loadTasksFromFile` method reads the tasks from the file and populates the `TaskManager` with the loaded tasks.

## Exporting .ics File Component

The 'ics' component:
* Exports the tasks in the task hashmap to a .ics file that can be imported into calendar applications
* Import tasks from external .ics file into the task hashmap


## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
