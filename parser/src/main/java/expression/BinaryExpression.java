package expression;

import impl.ExpressionVisitor;
import token.Token;

public class BinaryExpression implements Expression {

    private final Expression left;
    private final Token operand;
    private final Expression right;

    public BinaryExpression(Expression left, Token operand, Expression right) {
        this.left = left;
        this.operand = operand;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Token getOperand() {
        return operand;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return left.getValueAsString() + operand.getTokenValue() + right.getValueAsString();
    }
}
