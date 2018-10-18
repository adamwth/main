package seedu.address.model.room.booking.exceptions;

/**
 * Signals that the operation was unable to find any bookings.
 */
public class NoBookingException extends RuntimeException {
    public NoBookingException() {
        super("No booking found.");
    }
}
