package statement;

import expression.Expression;
import impl.StatementVisitor;

public class IfStatement implements Statement {

    private final Expression condition;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression condition, Statement thenStatement, Statement elseStatement) {
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public Expression getExpression() {
        return condition;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return condition.getValueAsString() + thenStatement.getValueAsString() + elseStatement.getValueAsString();
    }

    public Statement getThenStatement() {
        return thenStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }


}
