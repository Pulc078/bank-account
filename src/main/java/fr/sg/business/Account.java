package fr.sg.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    Balance balance;

    List<Operation> operationList = new ArrayList<>();

    public Account(Balance balance) {
        this.balance = balance;

    }

    public Balance getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount, Date date) {
        throwExceptionWhenAmountIsNegative(amount);
        balance.add(amount);
        operationList.add(new Operation(OperationType.DEPOSIT, date, amount));
    }

    public void withdraw(Amount withdraw, Date date) {
        throwExceptionWhenAmountIsNegative(withdraw);
        throwExceptionIfWithdrawIsSuperiorToBalance(withdraw);
        balance.minus(withdraw);
        operationList.add(new Operation(OperationType.WITHDRAW, date, withdraw));
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
}
