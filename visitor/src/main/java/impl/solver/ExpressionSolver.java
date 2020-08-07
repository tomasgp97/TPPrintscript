package impl.solver;

import expression.Expression;
import impl.Visitor;

public interface ExpressionSolver {

    boolean equality(Object left, Object right);
    void operandIsNumber(Object... objects);
    Object evaluateExpr(Expression expression, Visitor visitor);
    boolean isBoolean(Object o);
}
