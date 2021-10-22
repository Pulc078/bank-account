package fr.sg.business;

public enum OperationType {
    DEPOSIT() {
        public Amount apply(Amount balance, Amount amount) {
            return new Amount(balance.value.add(amount.value));
        }
    },
    WITHDRAW;


}
