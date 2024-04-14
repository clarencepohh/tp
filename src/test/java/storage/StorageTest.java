package storage;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;


class StorageTest {

    private static final Path TEST_FILE_PATH = Path.of("save", "test_tasks.txt");

    void setUp() throws IOException {
        // Delete the test file before each test
        Files.deleteIfExists(TEST_FILE_PATH);
    }

    void tearDown() throws IOException {
        // Delete the test file after each test
        Files.deleteIfExists(TEST_FILE_PATH);
    }


    @Test
    void saveTasksToFile_throwsIOException() {
        // Arrange
        Path nonExistentPath = Path.of("nonexistent/directory/tasks.txt");

        // Act and Assert
        assertThrows(IOException.class, () -> Files.write(nonExistentPath, "test data".getBytes()));
    }

    @Test
    void readFromFile_fileDoesNotExist_throwsIOException() {
        Path nonExistentPath = Path.of("nonexistent/directory/tasks.txt");
        assertThrows(IOException.class, () -> Files.readString(nonExistentPath));
    }

}
