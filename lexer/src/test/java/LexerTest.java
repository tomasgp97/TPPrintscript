import lexer.Lexer;
import lexer.LexerImpl;
import org.junit.Test;
import token.Token;
import token.TokenType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

public class LexerTest {

    @Test
    public void printTokens() {

        Stream<Character> input = "( )   let  : hola  number string = \"hola\" ; + - * /  123 if else { } import print \n".chars().mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        List<Token> tokens = lexer.lex(input);
        for (Token t : tokens) {
            System.out.println(t.getTokenType());
        }
    }

    @Test
    public void printUrlToken() {
        Stream<Character> input = "http://www.google.com".chars().mapToObj(i -> (char) i);
        Lexer lexer = new LexerImpl();
        List<Token> tokens = lexer.lex(input);
        for (Token t:tokens) {
            System.out.println(t.getTokenType());
        }
    }



}
