package fr.sg.business;

import java.util.Date;

public class StatementLine {
    Operation operation;
    Balance balance;
    public StatementLine(Operation operation, Balance balance) {
        this.operation = operation;
        this.balance = balance;
    }

    public Date getDate() {
        return operation.date;
    }

    public OperationType getType() {
        return operation.operationType;
    }

    public Amount getAmount() {
       return operation.getAmount();
    }

    public Balance getBalance() {
        return balance;
    }
}
