package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to reassign a booking to a checked-in room
 */
public class ReassignToCheckedInRoomException extends RuntimeException {
    public ReassignToCheckedInRoomException() {
        super("Cannot reassign booking to this room, because room is currently checked in!");
    }
}
