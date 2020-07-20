package expression;

import token.Token;
import visitor.ExpressionVisitor;

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
}
