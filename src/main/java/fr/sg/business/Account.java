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
        balance.minus(withdraw);
    }
}
