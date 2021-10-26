package fr.sg.infra;

import fr.sg.business.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsolePrinterTest {

    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance\r\n";
    private ConsolePrinter consolePrinter;

    @BeforeEach
    public void init() {
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
        assertEquals(STATEMENT_HEADER, consolePrinter.printedLines());
    }


    @Test
    public void printing_statement_from_account_with_operation_should_print_all_operation() {
        // Given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2021-10-21", formatter);
        Instant timestamp = date.atStartOfDay(ZoneId.of("Europe/Paris")).toInstant();

        StatementLine deposit = new StatementLine(new Operation(OperationType.DEPOSIT, Date.from(timestamp), new Amount(new BigDecimal(2))),
                new Balance(new BigDecimal(2)));


        StatementLine withdraw = new StatementLine(new Operation(OperationType.WITHDRAW, Date.from(timestamp), new Amount(new BigDecimal(2))),
                new Balance(new BigDecimal(0)));

        List<StatementLine> statementLineList = new LinkedList<>();
        statementLineList.add(deposit);
        statementLineList.add(0, withdraw);
        Statement statement = new Statement(statementLineList);

        // When
        consolePrinter.print(statement);

        // Then
        assertThat(consolePrinter.printedLines().trim())
                .isEqualTo("""
                        date       | operation   | amount    | balance\r\n21/10/2021 | WITHDRAW    | 2    | 0\r\n21/10/2021 | DEPOSIT    | 2    | 2""");
    }
}
