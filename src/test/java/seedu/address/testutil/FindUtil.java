package seedu.address.testutil;

import seedu.address.logic.commands.FindCommand;

import static seedu.address.logic.parser.CliSyntax.FLAG_GUEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * A utility class for finding.
 * @author adamwth
 */
public class FindUtil {

    /**
     * Returns a find command string to find guests by their name.
     */
    public static String getFindGuestCommand(String name) {
        return FindCommand.COMMAND_WORD + " " + FLAG_GUEST + " " + PREFIX_NAME + " " + name;
    }

    /**
     * Returns a find command string to find rooms by the given specified keyword(s).
     */
    public static String getFindRoomCommand(String roomNumber, String capacity, String tags, String) {
        return FindCommand.COMMAND_WORD + " " + FLAG_ROOM;
    }

}
