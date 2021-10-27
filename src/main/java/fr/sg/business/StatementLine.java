package fr.sg.business;

import java.util.Date;
import java.util.Objects;

public class StatementLine {
    Operation operation;
    Balance balance;
    public StatementLine(Operation operation, Balance balance) {
        this.operation = operation;
        this.balance = balance;
    }

    public Date getDate() {
        return operation.getDate();
    }

    public OperationType getType() {
        return operation.getOperationType();
    }

    public Amount getAmount() {
       return operation.getAmount();
    }

    public Balance getBalance() {
        return balance;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatementLine that = (StatementLine) o;
        return Objects.equals(operation, that.operation) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation, balance);
    }
}
