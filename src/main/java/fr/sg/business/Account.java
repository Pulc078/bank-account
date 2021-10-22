package fr.sg.business;

public class Account {
    Balance balance;

    public Account(Balance balance) {
        this.balance = balance;

    }

    public Balance getBalance() {
        return this.balance;
    }

    public void deposit(Amount amount) {
        balance.add(amount);
    }

    public void withdraw(Amount withdraw) {
        if(withdraw.value.compareTo(balance.value) > 0){
            throw new UnauthorizedOperationException();
        }
        balance.minus(withdraw);
    }
}
