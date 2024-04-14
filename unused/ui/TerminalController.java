package ui;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

//@@author NicholasTanYY-unused
// Reason: Initially wanted to have the screen cleared everytime the user presses enter, 
// to make the UI cleaner. However using keyboard inputs worked differently across different 
// terminals and different OS. This became too troublesome to manage.
public class TerminalController {

    private static Robot robot;
    private static final int KEY_ENTER = KeyEvent.VK_ENTER;

    public TerminalController() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void pressEnter() {
        robot.keyPress(KEY_ENTER);
        robot.keyRelease(KEY_ENTER);
    }

    public void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
