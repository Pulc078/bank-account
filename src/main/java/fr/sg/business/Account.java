package fr.sg.business;

import java.math.BigDecimal;
import java.time.Clock;
import java.util.Date;

public class Account {
    private final Clock clock;
    Balance balance;
    Statement statement;

    public Account(Balance balance, Clock clock) {
        this.balance = balance;
        this.clock = clock;
        this.statement = new Statement();
    }

    public Balance getBalance() {
        return balance;
    }

    public void deposit(Amount amount) {
        throwExceptionWhenAmountIsNegative(amount);
        this.balance = balance.add(amount);

        statement.add(new StatementLine(new Operation(OperationType.DEPOSIT, Date.from(clock.instant()), amount), balance));
    }

    public void withdraw(Amount amount) {
        throwExceptionWhenAmountIsNegative(amount);
        throwExceptionIfWithdrawIsSuperiorToBalance(amount);
        this.balance = balance.minus(amount);

        statement.add(new StatementLine(new Operation(OperationType.WITHDRAW, Date.from(clock.instant()), amount), balance));
    }

    public void printStatement(StatementPrinter printer) {
        printer.print(this.statement);
    }

    private void throwExceptionWhenAmountIsNegative(Amount amount) {
        if (amount.value.compareTo(BigDecimal.ZERO) < 0) {
            throw new UnauthorizedOperationException();
        }
    }

    private void throwExceptionIfWithdrawIsSuperiorToBalance(Amount withdraw) {
        if (withdraw.value.compareTo(balance.value) > 0) {
            throw new UnauthorizedOperationException();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
