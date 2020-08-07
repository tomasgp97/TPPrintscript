package statement;

import expression.Expression;
import impl.StatementVisitor;
import impl.Visitor;

import java.util.List;

public class BlockStatement implements Statement {
    private List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }


    @Override
    public Expression getExpression() {
        return null;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        StringBuilder s = new StringBuilder();
        statements.forEach(st -> s.append(st.getValueAsString()));
        return s.toString();
    }
}
