package seedu.address.logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Append all commands that user entered into command archive
 */
public class CommandArchive {
    /**
     * Create a logger without hard-coding the class name
     */
    private static final Logger LOGGER = Logger.getLogger(
            Thread.currentThread().getStackTrace()[0].getClassName());

    /**
     * Takes in the userInputHistory string, extracts the latest command and writes it to commandFile.txt with a
     */
    public static void stringToFile (String inputString) {
        File commandHistory = new File("commandFile.txt");
        String latestUserCommand = inputString.substring(0, inputString.indexOf('\n'));
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy " + "HH:mm:ss  ").format(new java.util.Date());

        try {
            FileWriter fileWriter = new FileWriter(commandHistory, true);
            BufferedWriter buffer = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(buffer);
            printWriter.print(timeStamp);
            printWriter.print(latestUserCommand);
            printWriter.print('\n');
            printWriter.close();

        } catch (IOException e) {
            System.err.println("An IOException was caught :" + e.getMessage());
            LOGGER.log(Level.INFO, "An IOException was caught", e);
        }

        if (commandHistory.exists()) {
            System.out.println("The file already exists");
        } else {
            try {
                commandHistory.createNewFile();
                System.out.println("The file has been created");
            } catch (IOException e) {
                System.err.println("An IOException was caught :" + e.getMessage());
                LOGGER.log(Level.INFO, "An IOException was caught", e);
            }
        }
    }

}
