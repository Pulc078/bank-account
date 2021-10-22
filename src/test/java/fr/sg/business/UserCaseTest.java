package fr.sg.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCaseTest {

    Account account;

    @Test
    public void should_create_a_account_with_balance_of_zero() {
        //Given
        Balance balance = new Balance();

        //When
        account = new Account(balance);

        //Then
        assertEquals(account.getBalance().value, new BigDecimal(0));
    }

    @Test
    public void should_depose_100_on_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);

        Amount deposit = new Amount(new BigDecimal(100));

        //When
        account.deposit(deposit);

        //Then
        assertEquals(account.getBalance().value, new BigDecimal(100));
    }

    @Test
    public void should_depose_210_2_on_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);

        Amount deposit = new Amount(new BigDecimal("210.2"));

        //When
        account.deposit(deposit);

        //Then
        assertEquals(account.getBalance().value, new BigDecimal("210.2"));
    }
}
