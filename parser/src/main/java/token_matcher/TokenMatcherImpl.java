package token_matcher;

import token.Token;
import token.TokenType;

import java.util.List;

public class TokenMatcherImpl implements TokenMatcher {

    private final List<Token> tokens;
    private int current = 0;

    public TokenMatcherImpl(List<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                next();
                return true;
            }
        }

        return false;
    }

    public Token next() {
        if (!isAtEnd()) current++;
        return previous();
    }

    public boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return current().getTokenType() == type;
    }

    public Token current() {
        return tokens.get(current);
    }

    public boolean isAtEnd() {
        return current().getTokenType() == TokenType.EOF;
    }

    public Token previous() {
        return tokens.get(current - 1);
    }

    public List<Token> getTokens() {
        return tokens;
    }
}
