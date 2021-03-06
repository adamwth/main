package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.model.expenses.Expense;
import seedu.address.model.expenses.ExpenseType;
import seedu.address.model.expenses.Money;

/**
 * A utility class to help with building Expense objects.
 */
public class ExpenseBuilder {

    public static final Money DEFAULT_COST = new Money(0, 99);
    public static final ExpenseType DEFAULT_EXPENSE_TYPE = TypicalExpenseTypes.EXPENSE_TYPE_RS01;
    public static final LocalDateTime DEFAULT_DATETIME = LocalDateTime.now();

    private Money cost;
    private ExpenseType expenseType;
    private LocalDateTime dateTime;

    public ExpenseBuilder() {
        cost = DEFAULT_COST;
        expenseType = DEFAULT_EXPENSE_TYPE;
        dateTime = DEFAULT_DATETIME;
    }

    /**
     * Initializes the ExpenseBuilder with the data of {@code bookingToCopy}.
     */
    public ExpenseBuilder(Expense expenseToCopy) {
        cost = expenseToCopy.getCost();
        expenseType = expenseToCopy.getExpenseType();
        dateTime = expenseToCopy.getDateTime();
    }

    /**
     * Sets the {@code ExpenseType} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withExpenseType(ExpenseType expenseType) {
        this.expenseType = new ExpenseType(expenseType);
        return this;
    }

    /**
     * Sets the {@code double} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withCost(Money cost) {
        this.cost = cost;
        return this;
    }

    /**
     * Sets the {@code LocalDateTime} of the {@code Expense} that we are building.
     */
    public ExpenseBuilder withDateTime(LocalDateTime localDateTime) {
        this.dateTime = localDateTime;
        return this;
    }

    public Expense build() {
        return new Expense(expenseType, cost, dateTime);
    }
}
