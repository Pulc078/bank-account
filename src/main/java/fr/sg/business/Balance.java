package fr.sg.business;

import java.math.BigDecimal;

public class Balance {
    BigDecimal value;

    public Balance() {
        value = new BigDecimal(0);
    }

    public void add(Amount amount) {
        value = this.value.add(amount.value);
    }

    public void minus(Amount withdraw) {
        value = this.value.subtract(withdraw.value);
    }
}
