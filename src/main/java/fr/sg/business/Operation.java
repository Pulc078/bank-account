package fr.sg.business;

import java.util.Date;

public class Operation {

    Amount amount;
    OperationType operationType;
    Date date;

    public Operation(OperationType type, Date date, Amount amount) {
        this.amount = amount;
        operationType = type;
        this.date = date;
    }

    public Amount getAmount() {
        return amount;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public Date getDate() {
        return date;
    }
}
