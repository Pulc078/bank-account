package fr.sg.infra;

import fr.sg.business.Account;
import fr.sg.business.Amount;
import fr.sg.business.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsolePrinterTest {

    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance\r\n";
    private ConsolePrinter consolePrinter;
    private Account account;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final LocalDate date = LocalDate.parse("2021-10-21", formatter);
    private final Instant timestamp = date.atStartOfDay(ZoneId.of("Europe/Paris")).toInstant();
    private final Clock fixedClock = Clock.fixed(timestamp, ZoneId.of("Europe/Paris"));

    @BeforeEach
    public void init() {
        consolePrinter = new ConsolePrinter();
        account = new Account(new Balance(), fixedClock);
    }


    @Test
    public void printing_statement_from_empty_account_should_print_only_header() {
        // Given
        // When
        account.printStatement(consolePrinter);

        // Then
        assertEquals(STATEMENT_HEADER, consolePrinter.printedLines());
    }


    @Test
    public void printing_statement_from_account_with_operation_should_print_all_operation() {
        // Given

        account.deposit(new Amount(new BigDecimal(100)));
        account.withdraw(new Amount(new BigDecimal(100)));

        // When
        account.printStatement(consolePrinter);

        // Then
        assertThat(consolePrinter.printedLines().trim())
                .isEqualTo("""
                        date       | operation   | amount    | balance\r\n21/10/2021 | WITHDRAW    | 100    | 0\r\n21/10/2021 | DEPOSIT    | 100    | 100""");
    }
}
