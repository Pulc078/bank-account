package fr.sg.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private final Instant timestamp = Instant.parse("2021-06-03T23:34:07.786Z");
    private final Clock fixedClock = Clock.fixed(timestamp, ZoneId.systemDefault());

    Account account;

    @BeforeEach
    void setUp() {
        account = new Account(new Balance(), Clock.systemDefaultZone());
    }

    @Test
    public void should_create_a_account_with_balance_of_zero() {
        //Given
        Balance balance = new Balance();
        //Then
        assertEquals(balance, account.getBalance());
    }

    @Test
    public void should_depose_100_on_an_account() {
        //Given
        Amount deposit = new Amount(new BigDecimal(100));

        //When
        account.deposit(deposit);

        //Then
        assertEquals(new Balance(new BigDecimal(100)), account.getBalance());
    }


    @Test
    public void should_depose_210_2_on_an_account() {
        //Given
        Amount deposit = new Amount(new BigDecimal("210.2"));

        //When
        account.deposit(deposit);

        //Then
        assertEquals(new Balance(new BigDecimal("210.2")), account.getBalance());
    }

    @Test
    public void should_withdraw_100_on_an_account_with_100_and_leave_an_empty_balance() {
        //Given
        Amount deposit = new Amount(new BigDecimal(100));
        account.deposit(deposit);
        Amount withdraw = new Amount(new BigDecimal(100));

        //When
        account.withdraw(withdraw);

        //Then
        assertEquals(new Balance(), account.getBalance());
    }

    @Test
    public void should_fail_when_withdrawing_100_on_an_account_with_balance_of_zero() {
        //Given
        Amount withdraw = new Amount(new BigDecimal(100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.withdraw(withdraw));
    }

    @Test
    public void deposing_100_then_withdrawing_10_should_leave_a_balance_of_90() {
        //Given
        Amount deposit = new Amount(new BigDecimal(100));
        Amount withdraw = new Amount(new BigDecimal(10));

        //When
        account.deposit(deposit);
        account.withdraw(withdraw);

        //Then
        assertEquals(new Balance(new BigDecimal(90)), account.getBalance());

    }


    @Test
    public void should_print_one_deposit_statement() {
        //Given
        account = new Account(new Balance(), fixedClock);
        Amount deposit = new Amount(new BigDecimal(1000));
        FakeStatementPrinter fakePrinter = new FakeStatementPrinter();

        //When
        account.deposit(deposit);
        account.printStatement(fakePrinter);

        //Then
        assertEquals(1, fakePrinter.getLines().size());
        Assertions.assertEquals(deposit.value, fakePrinter.getLines().get(0).getAmount().value);
        Assertions.assertEquals(OperationType.DEPOSIT, fakePrinter.getLines().get(0).getType());
        assertEquals(Date.from(fixedClock.instant()), fakePrinter.getLines().get(0).getDate());
    }


    @Test
    public void should_print_one_withdrawal_statement() {
        //Given
        Balance balance = new Balance(new BigDecimal(100));
        account = new Account(balance, fixedClock);
        Amount withdraw = new Amount(new BigDecimal(10));
        FakeStatementPrinter fakePrinter = new FakeStatementPrinter();

        //When
        account.withdraw(withdraw);
        account.printStatement(fakePrinter);

        //Then
        assertEquals(1, fakePrinter.getLines().size());
        assertEquals(withdraw.value, fakePrinter.getLines().get(0).getAmount().value);
        assertEquals(OperationType.WITHDRAW, fakePrinter.getLines().get(0).getType());
        assertEquals(Date.from(fixedClock.instant()), fakePrinter.getLines().get(0).getDate());
    }



    // Bonus Test : checking if amount is null or negative before operation
    @Test
    public void should_fail_when_deposing_negative_100_on_an_account() {
        //Given
        Amount deposit = new Amount(new BigDecimal(-100));

        //Then
        assertThrows(UnauthorizedOperationException.class, () -> account.deposit(deposit));
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
