package ui;

import java.io.IOException;

public class TerminalSize {
    
    private static int terminalWidth = 0;
    private static int terminalHeight = 0;
    
    //@@author remkop-reused
    //Reused from https://github.com/remkop/picocli/issues/634
    // with minor modifications
    public TerminalSize(String... args) throws IOException {
        String[] signals = new String[] {
            "\u001b[s",            // save cursor position
            "\u001b[5000;5000H",   // move to col 5000 row 5000
            "\u001b[6n",           // request cursor position
            "\u001b[u",            // restore cursor position
        };
        for (String s : signals) {
            System.out.print(s);
        }

        TerminalController terminalController = new TerminalController();
        terminalController.pressEnter();
        
        int read = -1;
        StringBuilder sb = new StringBuilder();
        byte[] buff = new byte[1];
        while ((read = System.in.read(buff, 0, 1)) != -1) {
            sb.append((char) buff[0]);
            //System.err.printf("Read %s chars, buf size=%s%n", read, sb.length());
            if ('R' == buff[0]) {
                break;
            }
        }
        
        String size = sb.toString();
        terminalHeight = Integer.parseInt(size.substring(size.indexOf("\u001b[") + 2, size.indexOf(';')));
        terminalWidth = Integer.parseInt(size.substring(size.indexOf(';') + 1, size.indexOf('R')));
    }
    //@@author

    public static int getTerminalWidth() {
        return terminalWidth;
    }

    public static int getTerminalHeight() {
        return terminalHeight;
    }
}

