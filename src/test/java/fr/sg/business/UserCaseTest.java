package fr.sg.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class UserCaseTest {

    @Test
    public void should_create_a_account_with_balance_of_zero(){
        //Given
        Account account;
        Balance balance = new Balance(BigDecimal.ZERO);

        //When
        account = new Account(balance);

        //Then
        assertEquals(account.getBalance(), balance);
    }

    @Test
    public void should_create_a_account_with_balance_of_100(){
        //Given
        Account account;
        BigDecimal value = new BigDecimal(100);
        Balance balance = new Balance(value);

        //When
        account = new Account(balance);

        //Then
        assertEquals(account.getBalance(), balance);
    }
}
