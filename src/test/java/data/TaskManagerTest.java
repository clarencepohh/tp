package data;

import data.Exceptions.TaskManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static data.TaskManager.addTask;
import static data.TaskManager.updateTask;
import static data.TaskManager.deleteAllTasksOnDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @AfterEach
    void resetTaskManager() {
        LocalDate date = LocalDate.now();
        deleteAllTasksOnDate(taskManager, date);
    }

    @Test
    void addTodo_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Todo";

        // Act
        Task testTask = new Task(taskDescription);
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        addTask(date, taskDescription, testTaskType, dummyTestDates, dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
    }

    @Test
    void addTodo_invalidInput_addsTask() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Todo";

        // Act
        TaskType testInvalidTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testInvalidTaskType, dummyTestDates, dummyTestTimes));

        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }

    @Test
    void updateTodo_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial todo";
        String updatedTaskDescription = "Updated todo";
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        Scanner scanner = new Scanner(System.in);

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void getTasksForDate_validDate_returnsTasks() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test todo task";
        TaskType testTaskType = TaskType.TODO;
        String[] dummyTestDates = new String[]{null};
        String[] dummyTestTimes = new String[]{null};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);

        // Act
        List<Task> tasksForDate = taskManager.getTasksForDate(date);
        Task createdTask = tasksForDate.get(0);

        // Assert
        assertEquals(createdTask, tasksForDate.get(0));
    }

    @Test
    void addTodoFromFile_validInput_addsTasks() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        Map<LocalDate, List<Task>> tasksFromFile = new HashMap<>();
        String taskDescription = "Test todo task";
        Task testTodoTask = new Todo(taskDescription);
        tasksFromFile.put(date, List.of(testTodoTask));

        // Act
        taskManager.addTasksFromFile(tasksFromFile);

        // Assert
        assertEquals(testTodoTask.getName(), taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void addDeadline_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Deadline";
        String byDate = "05/04/2024";
        String byTime = "1800";

        // Act
        Task testTask = new Deadline(taskDescription, byDate, byTime);
        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);
        String addedTaskByDate = addedTask.getByDate();
        String addedTaskByTime = addedTask.getByTime();

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
        assertEquals(testTask.getByDate(), addedTaskByDate);
        assertEquals(testTask.getByTime(), addedTaskByTime);
    }

    @Test
    void addDeadline_invalidInput_addsTask() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Deadline";
        String byDate = "05/04/2024";
        String byTime = "1800";

        // Act
        TaskType testTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes));
        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }

    @Test
    void updateDeadlineDescriptionOnly_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Deadline";
        String updatedTaskDescription = "Updated Deadline";
        String byDate = "05/04/2024";
        String byTime = "1800";
        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};
        String simulatedUserInput = "no";

        Scanner scanner = new Scanner(simulatedUserInput);

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
    }

    @Test
    void updateDeadlineDescriptionAndByDateTime_validInput_updatesTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String initialTaskDescription = "Initial Deadline";
        String updatedTaskDescription = "Updated Deadline";
        String byDate = "05/04/2024";
        String byTime = "1800";

        TaskType testTaskType = TaskType.DEADLINE;
        String[] dummyTestDates = new String[]{byDate};
        String[] dummyTestTimes = new String[]{byTime};
        String simulatedUserInput = "yes\n06/04/2024 1500\n";
        String updatedByDate = "06/04/2024";
        String updatedByTime = "1500";

        Scanner scanner = new Scanner(simulatedUserInput);

        addTask(date, initialTaskDescription, testTaskType, dummyTestDates, dummyTestTimes);

        // Act
        updateTask(date, 0, updatedTaskDescription, scanner);

        // Assert
        assertEquals(updatedTaskDescription, taskManager.getTasksForDate(date).get(0).getName());
        assertEquals(updatedByTime, taskManager.getTasksForDate(date).get(0).getByTime());
        assertEquals(updatedByDate, taskManager.getTasksForDate(date).get(0).getByDate());
    }

    @Test
    void addEvent_validInput_addsTask() throws TaskManagerException {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Event";
        String startDate = "05/04/2024";
        String endDate = "06/04/2024";
        String startTime = "1800";
        String endTime = "2000";

        // Act
        Task testTask = new Event(taskDescription, startDate, endDate, startTime, endTime);
        TaskType testTaskType = TaskType.EVENT;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};

        addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes);
        Task addedTask = taskManager.getTasksForDate(date).get(0);
        String addedTaskStartDate = addedTask.getStartDate();
        String addedTaskEndDate = addedTask.getEndDate();
        String addedTaskStartTime = addedTask.getStartTime();
        String addedTaskEndTime = addedTask.getEndTime();

        // Assert
        assertEquals(testTask.getName(), addedTask.getName());
        assertEquals(testTask.getStartDate(), addedTaskStartDate);
        assertEquals(testTask.getEndDate(), addedTaskEndDate);
        assertEquals(testTask.getStartTime(), addedTaskStartTime);
        assertEquals(testTask.getEndTime(), addedTaskEndTime);
    }

    @Test
    void addEvent_invalidInput_addsTask() {
        // Arrange
        LocalDate date = LocalDate.now();
        String taskDescription = "Test Event";
        String startDate = "05/04/2024";
        String endDate = "06/04/2024";
        String startTime = "1800";
        String endTime = "2000";

        // Act
        TaskType testTaskType = TaskType.INVALID;
        String[] dummyTestDates = new String[]{startDate, endDate};
        String[] dummyTestTimes = new String[]{startTime, endTime};

        // Assert
        TaskManagerException thrown = assertThrows(TaskManagerException.class, () ->
                addTask(date, taskDescription, testTaskType, dummyTestDates,dummyTestTimes));
        assertEquals("Invalid task type given. T for Todo, E for event, D for deadline.", thrown.getMessage());
    }
}
