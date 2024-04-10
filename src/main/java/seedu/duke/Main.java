package seedu.duke;

import commandparser.CommandHandler;
import data.Task;
import data.TaskManager;
import data.TaskManagerException;
import log.FileLogger;
import net.fortuna.ical4j.data.ParserException;
import storage.Storage;
import time.DateUtils;
import time.MonthView;
import time.WeekView;
import ui.AvatarUi;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static storage.Storage.createNewFile;

public class Main {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws IOException, TaskManagerException, ParserException {
        FileLogger.setupLogger();
        Scanner scanner = new Scanner(System.in);
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = DateUtils.getStartOfWeek(today);
        WeekView weekView = new WeekView(startOfWeek, dateFormatter);
        TaskManager taskManager = new TaskManager();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        MonthView monthView = new MonthView(startOfMonth, dateFormatter);

        createNewFile(Storage.FILE_PATH); //Creates directory and tasks.txt file if it does not exist
        Map<LocalDate, List<Task>> tasksFromFile =
                Storage.loadTasksFromFile(Storage.FILE_PATH); //Reads tasks from txt file
        taskManager.addTasksFromFile(tasksFromFile); //Loads tasks from txt file

        AvatarUi.printWelcomeMessage();
        //IcsHandler.generateICS(); //uncomment when developed

        CommandHandler commandHandler = new CommandHandler(scanner, taskManager, weekView, monthView);

        while (true) {
            commandHandler.handleCommand();
        }
    }
}
