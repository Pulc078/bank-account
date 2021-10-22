package fr.sg.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCaseTest {

    Account account;

    @Test
    public void should_create_a_account_with_balance_of_zero() {
        //Given
        Balance balance = new Balance();

        //When
        account = new Account(balance);

        //Then
        assertEquals(new BigDecimal(0), account.getBalance().value);
    }

    @Test
    public void should_depose_100_on_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Date date = new Date();
        Amount deposit = new Amount(new BigDecimal(100));

        //When
        account.deposit(deposit, date);

        //Then
        assertEquals(new BigDecimal(100), account.getBalance().value);
    }

    @Test
    public void should_depose_210_2_on_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Date date = new Date();
        Amount deposit = new Amount(new BigDecimal("210.2"));

        //When
        account.deposit(deposit, date);

        //Then
        assertEquals(new BigDecimal("210.2"), account.getBalance().value);
    }

    @Test
    public void should_fail_when_deposing_negative_100_on_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Date date = new Date();
        Amount deposit = new Amount(new BigDecimal(-100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.deposit(deposit, date));
    }

    @Test
    public void should_withdraw_100_on_an_account_with_100() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(100));
        Date date = new Date();
        account.deposit(deposit, date);
        Amount withdraw = new Amount(new BigDecimal(100));

        //When
        account.withdraw(withdraw);

        //Then
        assertEquals(BigDecimal.ZERO, account.getBalance().value);
    }

    @Test
    public void should_fail_when_withdrawing_100_on_an_empty_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);

        //When
        Amount withdraw = new Amount(new BigDecimal(100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.withdraw(withdraw));
    }


    @Test
    public void should_fail_when_withdrawing_negative_100_from_an_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(1000));
        Date date = new Date();
        account.deposit(deposit, date);

        //When
        Amount withdraw = new Amount(new BigDecimal(-100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.withdraw(withdraw));
    }


    @Test
    public void should_add_an_operation_to_the_account_when_deposing_100() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(1000));
        Date date = new Date();

        //When
        account.deposit(deposit, date);

        //Then
        assertEquals(1, account.getOperationList().size());
        assertEquals(deposit.value, account.getOperationList().get(0).getAmount().value);
        assertEquals(OperationType.DEPOSIT, account.getOperationList().get(0).getOperationType());
        assertEquals(date, account.getOperationList().get(0).getDate());
    }

}
