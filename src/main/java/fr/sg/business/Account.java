package fr.sg.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Account {
    Balance balance;

    List<Operation> operationList = new LinkedList<>();
    Statement statement;

    public Account(Balance balance) {
        this.balance = balance;
        statement = new Statement(new LinkedList<>());

    }

    public Balance getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount, Date date) {
        throwExceptionWhenAmountIsNegative(amount);
        this.balance = balance.add(amount);

        Operation operation = new Operation(OperationType.DEPOSIT, date, amount);
        operationList.add(0, operation);

        statement.add(0, new StatementLine(operation, new Amount(balance.value)));
    }

    public void withdraw(Amount amount, Date date) {
        throwExceptionWhenAmountIsNegative(amount);
        throwExceptionIfWithdrawIsSuperiorToBalance(amount);
        this.balance = balance.minus(amount);

        Operation operation = new Operation(OperationType.WITHDRAW, date, amount);
        operationList.add(0, operation);

        statement.add(0, new StatementLine(operation, new Amount(balance.value)));
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

    public List<Operation> getOperationList() {
        return this.operationList;
    }

    public void printStatement(StatementPrinter printer) {
        printer.print(this.statement);
    }
}
