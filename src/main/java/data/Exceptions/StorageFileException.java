package data.Exceptions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class StorageFileException extends Exception{

    public StorageFileException(String errorMessage) {
        super(errorMessage);
    }

    public static void checkStorageTextDateFormat(String date) throws StorageFileException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format in tasks.txt file. " +
                    "Please use the format yyyy-MM-dd for dates.");
        }
    }
}
