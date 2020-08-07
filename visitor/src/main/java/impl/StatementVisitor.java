package impl;

import statement.*;

public interface StatementVisitor {
    void visit(PrintStatement printStatement);
    void visit(ExpressionStatement expressionStatement);
    void visit(VariableStatement variableStatement);
    void visit(BlockStatement blockStatement);
    void visit(IfStatement ifStatement);
}
