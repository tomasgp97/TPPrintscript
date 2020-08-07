package impl.solver;

import expression.Expression;
import impl.Visitor;
import impl.solver.ExpressionSolver;

public class ExpressionSolverImpl implements ExpressionSolver {

    public ExpressionSolverImpl() {
    }

    @Override
    public boolean equality(Object left, Object right) {
        if (left == null && right == null) return true;
        if (left == null) return false;

        return left.equals(right);
    }

    @Override
    public void operandIsNumber(Object... objects) {
        for (Object o : objects) {
            if(!(o instanceof Double)) {
                return;
            }
        }
        throw new RuntimeException("Not a number");
    }

    @Override
    public Object evaluateExpr(Expression expression, Visitor visitor) {
        return expression.accept(visitor);
    }

    @Override
    public boolean isBoolean(Object o) {
        return false;
    }
}
