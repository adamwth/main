package seedu.address.logic.commands;

import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.room.RoomNumber;
import seedu.address.testutil.TypicalBookingPeriods;
import seedu.address.testutil.TypicalConcierge;
import seedu.address.testutil.TypicalRoomNumbers;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code ReassignCommand}.
 */
public class ReassignCommandTest {

    private Model model = new ModelManager(TypicalConcierge.getTypicalConcierge(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_nonExistentBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_001;
        LocalDate startDate = LocalDate.now().minusYears(10);
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_BOOKING_NOT_FOUND, roomNumber,
                ParserUtil.parseDateToString(startDate));
        
        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_expiredBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_002;
        LocalDate startDate = TypicalBookingPeriods.LASTWEEK_YESTERDAY.getStartDate();
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_EXPIRED_BOOKING_REASSIGN;
        
        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_originalRoomReassign_throwsCommandException() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_011;
        LocalDate startDate = TypicalBookingPeriods.TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_011;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = ReassignCommand.MESSAGE_REASSIGN_TO_ORIGINAL_ROOM;
        
        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_oheckedInRoomReassign_throwsCommandException() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_011;
        LocalDate startDate = TypicalBookingPeriods.TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_012;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_TO_CHECKED_IN_ROOM, newRoomNumber);
        
        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_overlappingBookingReassign_throwsCommandException() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_011;
        LocalDate startDate = TypicalBookingPeriods.TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_020;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);

        String expectedMessage = String.format(ReassignCommand.MESSAGE_OVERLAPPING_BOOKING, newRoomNumber);
        
        assertCommandFailure(reassignCommand, model, commandHistory, expectedMessage);
    }
    
    @Test
    public void execute_validReassign_success() {
        RoomNumber roomNumber = TypicalRoomNumbers.ROOM_NUMBER_011;
        LocalDate startDate = TypicalBookingPeriods.TODAY_TOMORROW.getStartDate();
        RoomNumber newRoomNumber = TypicalRoomNumbers.ROOM_NUMBER_031;
        ReassignCommand reassignCommand = new ReassignCommand(roomNumber, startDate, newRoomNumber);
        
        String expectedMessage = String.format(ReassignCommand.MESSAGE_REASSIGN_SUCCESS,
            ParserUtil.parseDateToString(startDate), roomNumber, newRoomNumber);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        model.reassignRoom(roomNumber, startDate, newRoomNumber);
        expectedModel.commitConcierge();
        
        assertCommandSuccess(reassignCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validCheckout_success() throws Exception {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_022;
        LocalDate startDate = TypicalBookingPeriods.TOMORROW_NEXTWEEK.getStartDate();
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, startDate);

        Model expectedModel = new ModelManager(model.getConcierge(), new UserPrefs());
        expectedModel.checkoutRoom(roomNumberToCheckout, startDate);
        expectedModel.commitConcierge();

        // checkout -> room booking checked out
        checkoutCommand.execute(model, commandHistory);

        // undo -> reverts concierge back to previous state and filtered room to show all rooms
        expectedModel.undoConcierge();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same room booking checked out
        expectedModel.redoConcierge();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidCheckout_failure() {
        RoomNumber roomNumberToCheckout = TypicalRoomNumbers.ROOM_NUMBER_010;
        LocalDate startDate = TypicalBookingPeriods.TODAY_TOMORROW.getStartDate();
        CheckoutCommand checkoutCommand = new CheckoutCommand(roomNumberToCheckout, startDate);

        String expectedMessage = String.format(CheckoutCommand.MESSAGE_BOOKING_NOT_FOUND,
                roomNumberToCheckout, ParserUtil.parseDateToString(startDate));

        assertCommandFailure(checkoutCommand, model, commandHistory, expectedMessage);

        // single Concierge state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        CheckoutCommand checkoutFirstCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        CheckoutCommand checkoutSecondCommand = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_002);

        // same object -> returns true
        assertTrue(checkoutFirstCommand.equals(checkoutFirstCommand));

        // same values -> returns true
        CheckoutCommand checkoutFirstCommandCopy = new CheckoutCommand(TypicalRoomNumbers.ROOM_NUMBER_001);
        assertTrue(checkoutFirstCommand.equals(checkoutFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkoutFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkoutFirstCommand.equals(null));

        // different room number -> returns false
        assertFalse(checkoutFirstCommand.equals(checkoutSecondCommand));
    }
}
