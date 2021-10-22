package fr.sg.business;

import java.math.BigDecimal;

public class Amount {

    BigDecimal value;


    public Amount(BigDecimal value) {
        this.value = value;
    }

    public void add(Amount amount) {
        this.value = this.value.add(amount.value);
    }

}
