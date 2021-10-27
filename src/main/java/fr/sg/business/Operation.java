package fr.sg.business;

import java.util.Date;
import java.util.Objects;

public class Operation {

    private Amount amount;
    private OperationType operationType;
    private Date date;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(amount, operation.amount) && operationType == operation.operationType && Objects.equals(date, operation.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, operationType, date);
    }
}
