package seedu.address.model.room.exceptions;

/**
 * Signals that the operation is attempting to do an invalid operation on a checked-in room
 */
public class RoomAlreadyCheckedInException extends RuntimeException {
    public RoomAlreadyCheckedInException() {
        super("Invalid operation, because room is already checked in!");
    }
}
