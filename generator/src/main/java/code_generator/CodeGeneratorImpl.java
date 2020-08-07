package code_generator;

import code_generator.CodeGenerator;
import impl.Visitor;
import statement.*;

import java.util.List;

//should probably use same logic as interpreter, use visitor
public class CodeGeneratorImpl implements CodeGenerator {

    public CodeGeneratorImpl() {

    }

    @Override
    public String generateCode(List<Statement> statements) {
        StringBuilder code = new StringBuilder();

        for (Statement statement : statements) {
            code.append(statement.getValueAsString());
        }
        return code.toString();
    }


}
