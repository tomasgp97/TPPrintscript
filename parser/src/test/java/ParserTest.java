import lexer.Lexer;
import lexer.LexerImpl;
import org.junit.Test;
import parser.Parser;
import parser.ParserImpl;
import statement.Statement;
import token.Token;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class ParserTest {

    @Test
    public void tbs001VariableDefiniteIsParsed() {

        Lexer lexer = new LexerImpl();
//        String code = "let a = 0; ";
        String code = "if (true) {let a = 0;} ";
        Stream<Character> characterStream = code.chars().mapToObj(i -> (char) i);

        List<Token> tokens = lexer.lex(characterStream);
//        Stream<Token> stream = tokens.stream();

        Parser parser = new ParserImpl(tokens);
        List<Statement> parse = parser.parse();
        System.out.println(parse.size());
//        parse.forEach(System.out::println);
        parse.forEach(i -> System.out.println(i.getExpression()));

    }
}
