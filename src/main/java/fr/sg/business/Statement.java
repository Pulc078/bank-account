package fr.sg.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Statement {

    List<StatementLine> statement;

    public Statement() {
        this.statement = new ArrayList<>();
    }

    public void add(StatementLine statementLine) {
        statement.add(0, statementLine);
    }

    public List<StatementLine> getStatementLines() {
        return statement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statement statement1 = (Statement) o;
        return Objects.equals(statement, statement1.statement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statement);
    }
}
