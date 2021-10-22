package fr.sg.business;

import java.math.BigDecimal;

public class Account {
    Balance balance;

    public Account(Balance balance) {
        this.balance = balance;

    }

    public Balance getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount) {
        throwExceptionWhenAmountIsNegative(amount);
        balance.add(amount);
    }

    public void withdraw(Amount withdraw) {
        throwExceptionWhenAmountIsNegative(withdraw);
        throwExceptionIfWithdrawIsSuperiorToBalance(withdraw);
        balance.minus(withdraw);
    }

    private void throwExceptionWhenAmountIsNegative(Amount amount) {
        if(amount.value.compareTo(BigDecimal.ZERO) < 0){
            throw new UnauthorizedOperationException();
        }
    }
    private void throwExceptionIfWithdrawIsSuperiorToBalance(Amount withdraw) {
        if(withdraw.value.compareTo(balance.value) > 0){
            throw new UnauthorizedOperationException();
        }
    }
}
