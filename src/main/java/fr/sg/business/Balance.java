package fr.sg.business;

import java.math.BigDecimal;

public class Balance{
    BigDecimal value;

    public Balance() {
        value = new BigDecimal(0);
    }

    public Balance( BigDecimal value){
        this.value = value;
    }

    public Balance add(Amount amount) {
        return new Balance(this.value.add(amount.value));
    }

    public Balance minus(Amount amount) {
        return new Balance(this.value.subtract(amount.value));
    }
}
