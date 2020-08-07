package code_generator;

import statement.Statement;

import java.util.List;

public interface CodeGenerator {

    String generateCode(List<Statement> statements);
}
