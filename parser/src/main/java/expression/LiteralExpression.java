package expression;


import impl.ExpressionVisitor;

public class LiteralExpression implements Expression {
    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}
