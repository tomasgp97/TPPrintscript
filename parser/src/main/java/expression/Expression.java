package expression;


import impl.ExpressionVisitor;

public interface Expression {
    Object accept(ExpressionVisitor visitor);
    String getValueAsString();
}
