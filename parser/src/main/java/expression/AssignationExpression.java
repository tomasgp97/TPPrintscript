package expression;

import impl.ExpressionVisitor;
import token.Token;

public class AssignationExpression implements Expression {

    private final Token name;
    private final Expression value;

    public AssignationExpression(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public Token getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return name.getTokenValue() + value.getValueAsString();
    }
}
