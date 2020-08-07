package expression;

import impl.ExpressionVisitor;
import token.Token;

public class VariableExpression implements Expression {

    private final Token variable;

    public VariableExpression(Token variable) {
        this.variable = variable;
    }

    public Token getVariable() {
        return variable;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return variable.getTokenValue();
    }
}
