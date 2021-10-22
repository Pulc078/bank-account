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
        account.withdraw(withdraw, date);

        //Then
        assertEquals(BigDecimal.ZERO, account.getBalance().value);
    }

    @Test
    public void should_fail_when_withdrawing_100_on_an_empty_account() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Date date = new Date();

        //When
        Amount withdraw = new Amount(new BigDecimal(100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.withdraw(withdraw, date));
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
        assertThrows(UnauthorizedOperationException.class, () -> account.withdraw(withdraw, date));
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


    @Test
    public void should_add_an_operation_to_the_account_when_withdrawing_100() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(100));
        Date date = new Date();
        account.deposit(deposit, date);

        Amount withdraw = new Amount(new BigDecimal(10));

        //When
        account.withdraw(withdraw, date);

        //Then
        assertEquals(2, account.getOperationList().size());
        assertEquals(withdraw.value, account.getOperationList().get(1).getAmount().value);
        assertEquals(OperationType.WITHDRAW, account.getOperationList().get(1).getOperationType());
        assertEquals(date, account.getOperationList().get(1).getDate());
    }

    @Test
    public void deposing_1000_then_withdraw_100_should_add_2_operation_in_chronological_order() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(100));
        Date dateDepo = new Date();
        account.deposit(deposit, dateDepo);

        Date dateWith = new Date();
        Amount withdraw = new Amount(new BigDecimal(10));

        //When
        account.withdraw(withdraw, dateWith);

        //Then
        assertEquals(2, account.getOperationList().size());

        assertEquals(deposit.value, account.getOperationList().get(1).getAmount().value);
        assertEquals(OperationType.DEPOSIT, account.getOperationList().get(1).getOperationType());
        assertEquals(dateDepo, account.getOperationList().get(1).getDate());

        assertEquals(withdraw.value, account.getOperationList().get(0).getAmount().value);
        assertEquals(OperationType.WITHDRAW, account.getOperationList().get(0).getOperationType());
        assertEquals(dateWith, account.getOperationList().get(0).getDate());
    }


}
