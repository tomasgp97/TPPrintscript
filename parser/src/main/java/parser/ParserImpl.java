package parser;

import expression.*;
import statement.*;
import token.Token;
import static token.TokenType.*;

import token.TokenType;
import token_matcher.TokenMatcher;
import token_matcher.TokenMatcherImpl;



import java.util.ArrayList;
import java.util.List;

public class ParserImpl  implements Parser {

    private final TokenMatcher tokenMatcher;
    
    public ParserImpl(List<Token> tokens) {
        this.tokenMatcher = new TokenMatcherImpl(tokens);
    }

    @Override
    public List<Statement> parse() {
        List<Statement> statements = new ArrayList<>();
        while (!tokenMatcher.isAtEnd()) {
            statements.add(declaration());
        }

        return statements;
    }

    private Statement declaration() {

        if (tokenMatcher.match(LET, CONST)) {
            return variable(tokenMatcher.previous());
        }
        return statement();
    }

    private Statement statement() {
        if (tokenMatcher.match(IF)) return ifStatement();
        if (tokenMatcher.match(PRINT)) return printStatement();
        if (tokenMatcher.match(LBRACKET)) return new BlockStatement(block());

        return expressionStatement();
    }

    private Statement variable(Token keyword) {
        Token name = consume(IDENTIFIER, "Expected variable name.");

        TokenType type = null;
        Expression initializer = null;

        if (tokenMatcher.match(COLON)){
            if (tokenMatcher.match(STRINGTYPE)){
                type = STRINGTYPE;
            }
            else if (tokenMatcher.match(NUMBERTYPE)){
                type = NUMBERTYPE;
            }
            else if (tokenMatcher.match(BOOLEAN)){
                type = BOOLEAN;
            }
        }
       
        if (tokenMatcher.match(EQUALS)) {
            initializer = expression();
        }

        consume(SEMICOLON, "Expected ';' after variable declaration.");
        return new VariableStatement(keyword, name, type,  initializer);
    }

    private Expression expression() {
        return assignment();
    }

    private Expression assignment() {
        Expression expr = comparison();

        if (tokenMatcher.match(EQUALS)) {
            Token equals = tokenMatcher.previous();
            Expression value = assignment();

            if (expr instanceof VariableExpression) {
                Token name = ((VariableExpression) expr).getVariable();
                return new AssignationExpression(name, value);
            }

        }

        return expr;
    }

    private Expression comparison() {
        Expression expr = addition();

        while (tokenMatcher.match(GREATER, GREATEREQ, LESS, LESSEQ)) {
            Token operator = tokenMatcher.previous();
            Expression right = addition();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression addition() {
        Expression expr = multiplication();

        while (tokenMatcher.match(MINUS, PLUS)) {
            Token operator = tokenMatcher.previous();
            Expression right = multiplication();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression multiplication() {
        Expression expr = unary();

        while (tokenMatcher.match(SLASH, STAR)) {
            Token operator = tokenMatcher.previous();
            Expression right = unary();
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression unary() {
        if (tokenMatcher.match(MINUS)) {
            Token operator = tokenMatcher.previous();
            Expression right = unary();
            return new UnaryExpression(operator, right);
        }

        return primary();
    }

    private Expression primary() {

        if (tokenMatcher.match(FALSE)) {
            return new LiteralExpression(false);
        }

        if (tokenMatcher.match(TRUE)) {
            return new LiteralExpression(true);
        }

        if (tokenMatcher.match(NUMBER, STRING)) {
            return new LiteralExpression(tokenMatcher.previous().getTokenValue());
        }

        if (tokenMatcher.match(IDENTIFIER)) {
            return new VariableExpression(tokenMatcher.previous());
        }

        if (tokenMatcher.match(LPAREN)) {
            Expression expr = expression();
            consume(RPAREN, "Expected ')' after expression.");
            return new GroupedExpression(expr);
        }
        throw new RuntimeException("Expression not matching");
    }
    

    private Token consume(TokenType type, String message) {
        if (tokenMatcher.check(type)) {
            return tokenMatcher.next();
        } else {
            throw new RuntimeException(message);
        }

    }

    private List<Statement> block() {
        List<Statement> statements = new ArrayList<>();

        while (!tokenMatcher.check(RBRACKET) && !tokenMatcher.isAtEnd()) {
            statements.add(declaration());
        }

        consume(RBRACKET, "Expected '}' after block.");
        return statements;
    }

    private Statement ifStatement() {
        consume(LPAREN, "Expected '(' after 'if'.");
        Expression condition = expression();
        consume(RPAREN, "Expected ')' after if condition.");

        Statement thenBranch = statement();
        Statement elseBranch = null;

        if (tokenMatcher.match(ELSE)) {
            elseBranch = statement();
        }

        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Statement printStatement() {
        Expression value = expression();
        consume(SEMICOLON, "Expected ';' after value.");
        return new PrintStatement(value);
    }

    private Statement expressionStatement() {
        Expression expr = expression();
        consume(SEMICOLON, "Expected ';' after expression.");
        return new ExpressionStatement(expr);
    }
}
