package expression;


import impl.ExpressionVisitor;

public class GroupedExpression implements Expression {
    private final Expression expression;

    public GroupedExpression(Expression expression) {
        this.expression = expression;
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
        return expression.getValueAsString();
    }
}
