import code_generator.CodeGenerator;
import code_generator.CodeGeneratorImpl;
import interpreter.Interpreter;
import interpreter.InterpreterImpl;
import lexer.Lexer;
import lexer.LexerImpl;
import parser.Parser;
import parser.ParserImpl;
import statement.Statement;
import token.Token;
import token_matcher.TokenMatcher;
import token_matcher.TokenMatcherImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        try {
            Lexer lexer = new LexerImpl();

            Stream<Character> input = readFile(args).chars().mapToObj(i -> (char) i);

            List<Token> lexedTokens = lexer.lex(input);

            Parser parser = new ParserImpl(lexedTokens);

            List<Statement> statements = parser.parse();

            CodeGenerator codeGenerator = new CodeGeneratorImpl();

            String code = codeGenerator.generateCode(statements);

            Interpreter interpreter = new InterpreterImpl();
            interpreter.interpret(statements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readFile(String[] args) throws IOException {
        String path = "C:\\Users\\EQUIPO\\IdeaProjects\\Printscript\\cli\\src\\main\\resources\\example.ts";
        if(args.length > 0) path = args[0];
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
