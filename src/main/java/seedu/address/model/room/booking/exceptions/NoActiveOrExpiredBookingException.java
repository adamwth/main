package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the operation is unable to find an active or upcoming booking.
 */
public class NoActiveOrExpiredBookingException extends RuntimeException {
    public NoActiveOrExpiredBookingException() {
        super("No active or upcoming booking found.");
    }
}
