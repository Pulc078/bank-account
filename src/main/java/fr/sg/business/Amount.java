package fr.sg.business;

import java.math.BigDecimal;

public class Amount {

    BigDecimal value;


    public Amount(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValue() {
        return this.value;
    }

}
