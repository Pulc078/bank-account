package fr.sg.features;


import fr.sg.business.*;
import fr.sg.infra.ConsolePrinter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccountTransactionSteps {

    private Account account;
    private final List<Exception> caughtExceptions = new ArrayList<>();
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate date = LocalDate.parse("2021-10-21", formatter);
    private final Instant timestamp = date.atStartOfDay(ZoneId.of("Europe/Paris")).toInstant();
    private final Clock fixedClock = Clock.fixed(timestamp, ZoneId.of("Europe/Paris"));

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance";

    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    private ConsolePrinter consolePrinter;

    @BeforeEach
    void setUp() {
        account = new Account(new Balance(new BigDecimal(100)), Clock.systemDefaultZone());
    }

    @Given("^There is an account with the following operations$")
    public void there_is_an_account_with_the_following_operations(DataTable dataTable) {
        List<Operation> operationList = formatDataTableToListOfOperation(dataTable);

        account = new Account(new Balance(), fixedClock);
        for (int i = operationList.size() - 1; i >= 0; i--) {
            switch (operationList.get(i).getOperationType()) {
                case WITHDRAW -> account.withdraw(operationList.get(i).getAmount());
                case DEPOSIT -> account.deposit(operationList.get(i).getAmount());
            }
        }
        {
        }

        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @When("I withdraw an amount of {int}")
    public void i_withdraw_an_amount_of(int amount) {
        try {
            this.account.withdraw(new Amount(new BigDecimal(amount)));
        } catch (Exception e) {
            caughtExceptions.add(e);
        }
    }

    @When("I depose an amount of {int}")
    public void i_depose_an_amount_of(Integer amount) {
        try {
            this.account.deposit(new Amount(new BigDecimal(amount)));
        } catch (Exception e) {
            caughtExceptions.add(e);
        }
    }

    @When("The user prints it's statements")
    public void the_user_prints_it_s_statements() {
        consolePrinter = new ConsolePrinter();
        try {
            this.account.printStatement(consolePrinter);
        } catch (Exception e) {
            caughtExceptions.add(e);
        }
    }

    @Then("The statement printing should have the following lines")
    public void the_statement_printing_should_have_the_following_lines(DataTable dataTable) {
        List<StatementLine> statementLines = formatDataTableToListOfStatement(dataTable);
        Statement statement = new Statement(statementLines);

        StringBuilder expected = new StringBuilder();
        expected.append(STATEMENT_HEADER).append("\r\n");

        for (StatementLine statementLine : statement.getStatementLines()) {
            expected.append(sdf.format(statementLine.getDate()))
                    .append(" | ")
                    .append(statementLine.getType())
                    .append("    | ")
                    .append(statementLine.getAmount().getValue())
                    .append("    | ")
                    .append(statementLine.getBalance().getValue())
                    .append("\r\n");
        }

        assertEquals(consolePrinter.printedLines(),
                expected.toString());
    }

    @Then("The operation should be accepted")
    public void the_operation_should_be_accepted() {
        assertTrue(caughtExceptions.isEmpty());
    }

    @Then("The operation should be rejected")
    public void the_operation_should_be_rejected() {
        assertEquals(1, caughtExceptions.size());
    }

    @Then("The account should have the following operations")
    public void the_account_should_have_the_following_operations(DataTable dataTable) {
        List<Operation> operationList = formatDataTableToListOfOperation(dataTable);

        for (int i = 0; i < operationList.size(); i++) {
            StatementLine accountStatement = account.getStatement().getStatementLines().get(i);
            assertEquals(operationList.get(i).getOperationType(), accountStatement.getType());
            assertEquals(operationList.get(i).getDate(), accountStatement.getDate());
            assertEquals(operationList.get(i).getAmount(), accountStatement.getAmount());
        }
    }

    private List<Operation> formatDataTableToListOfOperation(DataTable dataTable) {
        return dataTable.asMaps().stream().map(table -> {
            LocalDate localDate = LocalDate.parse(table.get("date"), formatter);
            return new Operation(
                    OperationType.valueOf(table.get("operation")),
                    Date.valueOf(localDate),
                    new Amount(new BigDecimal(table.get("amount")))
            );
        }).toList();
    }

    private List<StatementLine> formatDataTableToListOfStatement(DataTable dataTable) {
        return dataTable.asMaps().stream().map(table -> {
            LocalDate localDate = LocalDate.parse(table.get("date"), formatter);
            Operation operation = new Operation(OperationType.valueOf(table.get("operation")),
                    Date.valueOf(localDate),
                    new Amount(new BigDecimal(table.get("amount"))));
            return new StatementLine(operation, new Balance(new BigDecimal(table.get("balance"))));
        }).toList();
    }

}
