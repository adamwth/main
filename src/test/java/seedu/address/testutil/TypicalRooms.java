package seedu.address.testutil;

import seedu.address.model.room.RoomNumber;
import seedu.address.model.room.UniqueRoomList;

/**
 * A utility class containing a list of {@code Room} objects to be used in tests.
 */
public class TypicalRooms {
    /**
     * Returns a {@code UniqueRoomList} with all the typical rooms.
     * Note: All rooms do not have bookings or expenses. Use @Before in tests to initialize bookings and expenses.
     */
    public static UniqueRoomList getTypicalUniqueRoomList() {
        return new UniqueRoomList(RoomNumber.MAX_ROOM_NUMBER);
    }
}
