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
}
