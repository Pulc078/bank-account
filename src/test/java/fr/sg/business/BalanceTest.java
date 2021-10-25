package fr.sg.business;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BalanceTest {

    Balance balance;
    Amount amount;

    @Test
    void should_add_10_to_balance_value() {
        // Given
        balance = new Balance();
        amount = new Amount(new BigDecimal(10));

        // When
        balance = balance.add(amount);

        // Then
        assertEquals(amount.value, balance.value);
    }


    @Test
    void should_remove_10_to_balance_value() {
        // Given
        balance = new Balance(new BigDecimal(10));
        amount = new Amount(new BigDecimal(10));

        // When
        balance = balance.minus(amount);

        // Then
        assertEquals(BigDecimal.ZERO, balance.value);
    }
}
