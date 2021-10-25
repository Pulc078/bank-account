package fr.sg.business;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Balance balance = (Balance) o;
        return Objects.equals(value, balance.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
