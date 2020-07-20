package token_matcher;

import token.Token;
import token.TokenType;

public interface TokenMatcher {

    boolean match(TokenType... tokenTypes);
    Token next();
    boolean check(TokenType type);
    Token current();
    Token previous();
    boolean isAtEnd();

}
