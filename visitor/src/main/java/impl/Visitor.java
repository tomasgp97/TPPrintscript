package impl;

import environment.Environment;
import environment.EnvironmentImpl;
import expression.*;
import impl.solver.ExpressionSolver;
import impl.solver.ExpressionSolverImpl;
import statement.*;
import token.TokenType;

import java.util.List;


public class Visitor implements StatementVisitor, ExpressionVisitor {

    private final ExpressionSolver expressionSolver;
    private Environment environment;

    public Visitor() {
        this.expressionSolver = new ExpressionSolverImpl();
        this.environment = new EnvironmentImpl();
    }

    @Override
    public void visit(IfStatement ifStatement) {
        if (expressionSolver.isBoolean(expressionSolver.evaluateExpr(ifStatement.getExpression(),this ))) {
            ifStatement.getThenStatement().accept(this);
        } else if (ifStatement.getElseStatement() != null) {
            ifStatement.getElseStatement().accept(this);
        }
    }

    @Override
    public void visit(PrintStatement printStatement) {
        Object value = expressionSolver.evaluateExpr(printStatement.getExpression(), this);
        System.out.println(value);
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        expressionStatement.getExpression().accept(this);
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        executeBlock(blockStatement.getStatements(), new EnvironmentImpl(environment));
    }

    private void executeBlock(List<Statement> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Statement statement : statements) {
                statement.accept(this);
            }
        } finally {
            this.environment = previous;
        }
    }

    @Override
    public void visit(VariableStatement statement) {
        Object value = null;
        if (statement.getExpression() != null){
            value = expressionSolver.evaluateExpr(statement.getExpression(), this);
        }
        if (value == null){
            environment.addValue(statement.getName().getTokenValue(), statement.getKeyword().getTokenType(), statement.getType(), null);
            return;
        }
        if (statement.getType() == TokenType.BOOLEAN){
            if (!(value instanceof Boolean)){
                throw new RuntimeException( "Expected a Boolean");
            }
        }
        if (statement.getType() == TokenType.NUMBERTYPE){
            if (!(value instanceof Number)){
                throw new RuntimeException( "Expected a Number");
            }
        }
        if (statement.getType() == TokenType.STRINGTYPE){
            if (!(value instanceof String)){
                throw new RuntimeException( "Expected a String");
            }
        }

        environment.addValue(statement.getName().getTokenValue(), statement.getKeyword().getTokenType(), statement.getType(), value);
    }


    @Override
    public Object visit(BinaryExpression binaryExpression) {
        Object left = expressionSolver.evaluateExpr(binaryExpression.getLeft(), this);
        Object right = expressionSolver.evaluateExpr(binaryExpression.getRight(), this);

        switch (binaryExpression.getOperand().getTokenType()) {
            case EQUALS:
                return expressionSolver.equality(left, right);
            case GREATER:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left > (double) right;
            case GREATEREQ:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left >= (double) right;
            case LESS:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left < (double) right;
            case LESSEQ:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left <= (double) right;
            case MINUS:
                return (double) left - (double) right;
            case PLUS:
                if (left instanceof Number && right instanceof Number) {
                    return (double) left + (double) right;
                }
                return left.toString() + right.toString();
            case SLASH:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left / (double) right;
            case STAR:
                expressionSolver.operandIsNumber(binaryExpression.getOperand(), left, right);
                return (double) left * (double) right;

        }
        return null;
    }

    @Override
    public Object visit(UnaryExpression unaryExpression) {
        Object right = expressionSolver.evaluateExpr(unaryExpression.getExpression(), this);

        if (unaryExpression.getOperand().getTokenType() == TokenType.MINUS) {
            expressionSolver.operandIsNumber(unaryExpression.getOperand(), right);
            return -(double) right;
        }
        return null;
    }

    @Override
    public Object visit(GroupedExpression groupedExpression) {
        return expressionSolver.evaluateExpr(groupedExpression.getExpression(), this);

    }

    @Override
    public Object visit(LiteralExpression literalExpression) {
        return literalExpression.getValue();
    }

    @Override
    public Object visit(VariableExpression variableExpression) {
        return environment.getValue(variableExpression.getVariable());
    }

    @Override
    public Object visit(AssignationExpression assignationExpression) {
        Object value = expressionSolver.evaluateExpr(assignationExpression.getValue(), this);

        environment.assign(assignationExpression.getName(), value);
        return value;
    }


}
