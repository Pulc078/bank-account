package fr.sg.infra;

import fr.sg.business.*;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsolePrinter implements StatementPrinter {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String STATEMENT_HEADER = "date       | operation   | amount    | balance";

    private final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public void print(Statement statement) {
        PrintStream printer = System.out;
        printer.println(STATEMENT_HEADER);

        statement.getStatementLines().forEach(statementLine -> printLine(statementLine, printer));

    }

    private void printLine(StatementLine statementLine, PrintStream printer) {
        StringBuilder builder = new StringBuilder();
        addDateTo(builder, statementLine.getDate());
        addOperationTo(builder, statementLine.getType(), statementLine.getAmount());
        addCurrentBalanceTo(builder, statementLine.getBalance());
        printer.println(builder);
    }


    private void addDateTo(StringBuilder builder, Date date) {
        builder.append(sdf.format(date));
        builder.append(" | ");
    }


    private void addOperationTo(StringBuilder builder, OperationType type, Amount amount) {
        builder.append(type)
                .append("    | ");
        builder.append(amount.getValue())
                .append("    | ");
    }


    private void addCurrentBalanceTo(StringBuilder builder, Amount currentBalance) {
        builder.append(currentBalance.getValue());
    }
}


