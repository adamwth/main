package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the operation is unable to find an active booking.
 */
public class NoActiveBookingException extends RuntimeException {
    public NoActiveBookingException() {
        super("No active booking found.");
    }
}
