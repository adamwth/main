package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to reassign a booking to a checked-in room
 */
public class CheckedInRoomReassignException extends RuntimeException {
    public CheckedInRoomReassignException() {
        super("Cannot reassign booking to this room, because room is currently checked in!");
    }
}
