package fr.sg.infra;

import fr.sg.business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsolePrinterTest {


    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance";

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    private ConsolePrinter consolePrinter;

    @BeforeEach
    public void init() {
        System.setOut(new PrintStream(outputStreamCaptor));
        consolePrinter = new ConsolePrinter();
    }


    @Test
    public void printing_statement_from_empty_account_should_print_only_header() {
        // Given
        List<StatementLine> statementLineList = new LinkedList<>();
        Statement statement = new Statement(statementLineList);

        // When
        consolePrinter.print(statement);

        // Then
        assertEquals(STATEMENT_HEADER, outputStreamCaptor.toString().trim());
    }


    @Test
    public void printing_statement_from_account_with_operation_should_print_all_operation() {
        // Given

        StatementLine deposit = new StatementLine(new Operation(OperationType.DEPOSIT, new Date(), new Amount(new BigDecimal(2))),
                new Amount(new BigDecimal(2)));


        StatementLine withdraw = new StatementLine(new Operation(OperationType.WITHDRAW, new Date(), new Amount(new BigDecimal(2))),
                new Amount(new BigDecimal(0)));

        List<StatementLine> statementLineList = new LinkedList<>();
        statementLineList.add(deposit);
        statementLineList.add(0, withdraw);
        Statement statement = new Statement(statementLineList);

        // When
        consolePrinter.print(statement);


        StringBuilder builder = new StringBuilder();
        builder.append(STATEMENT_HEADER).append("\r\n");

        for (StatementLine statementLine : statement.getStatementLines()) {
            builder.append(sdf.format(statementLine.getDate()))
                    .append(" | ")
                    .append(statementLine.getType().toString())
                    .append("    | ")
                    .append(statementLine.getAmount().getValue())
                    .append("    | ")
                    .append(statementLine.getBalance().getValue())
                    .append("\r");
        }

        // Then
        assertThat(outputStreamCaptor.toString().trim())
                .isEqualTo("""
                        date       | operation   | amount    | balance\r\n25/10/2021 | WITHDRAW    | 2    | 0\r\n25/10/2021 | DEPOSIT    | 2    | 2""");
    }
}
