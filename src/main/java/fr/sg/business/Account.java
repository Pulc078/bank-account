package fr.sg.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;

public class Account {
    Balance balance;
    Statement statement;

    public Account(Balance balance) {
        this.balance = balance;
        statement = new Statement(new LinkedList<>());

    }

    public Balance getBalance() {
        return balance;
    }

    public void deposit(Amount amount, Date date) {
        throwExceptionWhenAmountIsNegative(amount);
        this.balance = balance.add(amount);

        statement.add(0, new StatementLine(new Operation(OperationType.DEPOSIT, date, amount), balance));
    }

    public void withdraw(Amount amount, Date date) {
        throwExceptionWhenAmountIsNegative(amount);
        throwExceptionIfWithdrawIsSuperiorToBalance(amount);
        this.balance = balance.minus(amount);

        statement.add(0, new StatementLine(new Operation(OperationType.WITHDRAW, date, amount), balance));
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

    public void printStatement(StatementPrinter printer) {
        printer.print(this.statement);
    }
}
