package fr.sg.business;

import java.util.List;

public class Statement {

    List<StatementLine> statement;

    public Statement(List<StatementLine> statement) {
        this.statement = statement;
    }

    public void add(int i, StatementLine statementLine) {
        statement.add(i, statementLine);
    }

    public List<StatementLine> getStatementLines() {
        return statement;
    }
}
