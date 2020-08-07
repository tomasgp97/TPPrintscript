package interpreter;

import impl.Visitor;
import statement.Statement;

import java.util.List;

public class InterpreterImpl implements Interpreter {

    private final Visitor visitor;

    public InterpreterImpl() {
        this.visitor = new Visitor();
    }

    @Override
    public void interpret(List<Statement> statementList) {

        for (Statement statement : statementList) {
            statement.accept(visitor);
        }
    }
}
