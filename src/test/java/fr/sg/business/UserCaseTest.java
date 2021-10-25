package fr.sg.business;

import fr.sg.infra.ConsolePrinter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserCaseTest {

    Account account;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance";

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @BeforeEach
    public void init() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

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
        assertEquals(new BigDecimal(90), account.balance.value);

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
        assertEquals(deposit.value, fakePrinter.getLines().get(0).getAmount().value);
        assertEquals(OperationType.DEPOSIT, fakePrinter.getLines().get(0).getType());
        assertEquals(date, fakePrinter.getLines().get(0).getDate());
    }


    @Test
    public void should_print_one_withdrawal_statement() {
        //Given
        Balance balance = new Balance();
        account = new Account(balance);
        Amount deposit = new Amount(new BigDecimal(100));
        Date date = new Date();
        account.deposit(deposit, date);
        Amount withdraw = new Amount(new BigDecimal(10));
        FakeStatementPrinter fakePrinter = new FakeStatementPrinter();

        //When
        account.withdraw(withdraw, date);
        account.printStatement(fakePrinter);

        //Then
        assertEquals(2, fakePrinter.getLines().size());
        assertEquals(withdraw.value, fakePrinter.getLines().get(0).getAmount().value);
        assertEquals(OperationType.WITHDRAW, fakePrinter.getLines().get(0).getType());
        assertEquals(date, fakePrinter.getLines().get(0).getDate());
    }


    @Test
    public void printing_statement_from_empty_account_should_print_only_header() {
        // Given
        Balance balance = new Balance();
        account = new Account(balance);

        // When
        account.printStatement(new ConsolePrinter());

        // Then
        assertEquals(STATEMENT_HEADER.length(), outputStreamCaptor.toString().trim().length());
    }


    @Test
    public void printing_statement_from_account_with_operation_should_print_all_operation() {
        // Given
        //Given
        Balance balance = new Balance();
        account = new Account(balance);

        Amount deposit = new Amount(new BigDecimal(100));
        Date dateDepo = new Date();
        account.deposit(deposit, dateDepo);

        Date dateWith = new Date();
        Amount withdraw = new Amount(new BigDecimal(10));
        account.withdraw(withdraw, dateWith);


        // When
        account.printStatement(new ConsolePrinter());

        StringBuilder builder = new StringBuilder();
        builder.append(STATEMENT_HEADER).append("\r\n");

        for (StatementLine statementLine : account.statement.getStatementLines()) {
            builder.append(sdf.format(statementLine.getDate()))
                    .append(" | ")
                    .append(statementLine.getType().toString())
                    .append("    | ")
                    .append(statementLine.getAmount().value)
                    .append("    | ")
                    .append(statementLine.getBalance().value)
                    .append("\r");
        }

        // Then
        assertEquals(builder.toString().length(), outputStreamCaptor.toString().trim().length());
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
