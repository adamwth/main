package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.booking.BookingPeriod;
import seedu.address.model.room.booking.exceptions.BookingNotFoundException;
import seedu.address.model.room.booking.exceptions.OverlappingBookingException;
import seedu.address.model.room.exceptions.RoomAlreadyCheckedInException;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_START;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;

/**
 * Reassigns a room's booking to another room.
 */
public class ReassignCommand extends Command {

    public static final String COMMAND_WORD = "reassign";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reassigns a room's booking to another room.\n"
            + Messages.MESSAGE_VALID_ROOM
            + Messages.MESSAGE_VALID_DATE
            + "Parameters: "
            + PREFIX_ROOM + "ROOM_NUMBER "
            + PREFIX_DATE_START + "START_DATE "
            + PREFIX_NEW_ROOM + "NEW_ROOM_NUMBER\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_ROOM + "001 "
            + PREFIX_DATE_START + "01/01/18 "
            + PREFIX_NEW_ROOM + "002";

    public static final String MESSAGE_REASSIGN_SUCCESS =
            "Reassigned booking with start date %1$s from room %2$s to room %3$s.";
    public static final String MESSAGE_BOOKING_NOT_FOUND = "Room %1$s has no such bookings with start date %2$s.";
    public static final String MESSAGE_OVERLAPPING_BOOKING =
            "Cannot reassign booking, because it overlaps with another booking in room %s";
    public static final String MESSAGE_NEW_ROOM_CHECKED_IN =
            "Cannot reassign booking, because room %s is currently checked-in.";

    private final RoomNumber roomNumber;
    private final LocalDate startDate;
    private final RoomNumber newRoomNumber;

    public ReassignCommand(RoomNumber roomNumber, LocalDate startDate, RoomNumber newRoomNumber) {
        this.roomNumber = roomNumber;
        this.startDate = startDate;
        this.newRoomNumber = newRoomNumber;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        
        try {
            model.reassignRoom(roomNumber, startDate, newRoomNumber);
            model.commitConcierge();
            return new CommandResult(String.format(MESSAGE_REASSIGN_SUCCESS,
                startDate.format(BookingPeriod.DATE_TO_STRING_FORMAT), roomNumber, newRoomNumber));

        } catch (BookingNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_BOOKING_NOT_FOUND, roomNumber, startDate));
        } catch (OverlappingBookingException e) {
            throw new CommandException(String.format(MESSAGE_OVERLAPPING_BOOKING, newRoomNumber));
        } catch (RoomAlreadyCheckedInException e) {
            throw new CommandException(String.format(MESSAGE_NEW_ROOM_CHECKED_IN, newRoomNumber));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReassignCommand // instanceof handles nulls
                && roomNumber.equals(((ReassignCommand) other).roomNumber)); // state check
    }
}
