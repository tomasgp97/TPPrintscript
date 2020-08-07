package expression;

import impl.ExpressionVisitor;
import token.Token;

public class UnaryExpression implements Expression {

    private final Token operand;
    private final Expression expression;

    public UnaryExpression(Token operand, Expression expression) {
        this.operand = operand;
        this.expression = expression;
    }

    public Token getOperand() {
        return operand;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return operand.getTokenValue() + expression.getValueAsString();
    }
}
