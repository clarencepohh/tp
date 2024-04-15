package ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AvatarUiTest {
    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    void printWelcomeMessage_displaysCorrectMessage() {
        AvatarUi.printWelcomeMessage();

        String lineSeparator = System.lineSeparator();
        String expectedOutput = "Hello there, I am CLI-nton, your CLI-based " + 
                                "personal assistant in event management!" + lineSeparator;
        assertEquals(expectedOutput, outContent.toString());
    }
}
