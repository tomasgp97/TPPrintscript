package statement;

import expression.Expression;
import impl.StatementVisitor;

public interface Statement {

    Expression getExpression();
    void accept(StatementVisitor visitor);
    String getValueAsString();
}
