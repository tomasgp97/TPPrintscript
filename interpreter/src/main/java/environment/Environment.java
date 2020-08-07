package environment;

import declaration.Declaration;
import token.Token;
import token.TokenType;

import java.util.Map;

public interface Environment {

    Map<String, Declaration> getValues();
    void addValue(String name, TokenType keyword, TokenType type, Object value);
    void assign(Token name, Object value);
    Object getValue(Token name);
    Environment getEnclosing();
}
