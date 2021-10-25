package fr.sg.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    Account account;

    @Test
    public void should_create_a_account_with_balance_of_zero() {
        //Given
        Balance balance = new Balance();

        //When
        account = new Account(balance);

        //Then
        assertEquals(balance, account.getBalance());
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
        assertEquals(new Balance(new BigDecimal(100)).value, account.getBalance().value);
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
        assertEquals(new Balance(new BigDecimal("210.2")).value, account.getBalance().value);
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
    public void should_fail_when_withdrawing_100_on_an_account_with_balance_of_zero() {
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
    public void deposing_100_then_withdraw_100_should_leave_a_balance_of_90() {
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
        assertEquals(new Balance(new BigDecimal(90)).value, account.getBalance().value);

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
    public void should_print_one_deposit_statement() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(1000));
        Date date = new Date();
        FakeStatementPrinter fakePrinter = new FakeStatementPrinter();

        //When
        account.deposit(deposit, date);
        account.printStatement(fakePrinter);

        //Then
        assertEquals(1, fakePrinter.getLines().size());
        Assertions.assertEquals(deposit.value, fakePrinter.getLines().get(0).getAmount().value);
        Assertions.assertEquals(OperationType.DEPOSIT, fakePrinter.getLines().get(0).getType());
        assertEquals(date, fakePrinter.getLines().get(0).getDate());
    }


    @Test
    public void should_print_one_withdrawal_statement() {
        //Given
        Balance balance = new Balance(new BigDecimal(100));
        account = new Account(balance);
        Date date = new Date();
        Amount withdraw = new Amount(new BigDecimal(10));
        FakeStatementPrinter fakePrinter = new FakeStatementPrinter();

        //When
        account.withdraw(withdraw, date);
        account.printStatement(fakePrinter);

        //Then
        assertEquals(1, fakePrinter.getLines().size());
        assertEquals(withdraw.value, fakePrinter.getLines().get(0).getAmount().value);
        assertEquals(OperationType.WITHDRAW, fakePrinter.getLines().get(0).getType());
        assertEquals(date, fakePrinter.getLines().get(0).getDate());
    }


    private static class FakeStatementPrinter implements StatementPrinter {

        private final List<StatementLine> lines = new LinkedList<>();

        public void print(Statement statement) {
            lines.addAll(statement.getStatementLines());
        }

        public List<StatementLine> getLines() {
            return lines;
        }
    }

}
