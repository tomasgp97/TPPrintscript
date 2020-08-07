package declaration;

import token.TokenType;

public class DeclarationImpl implements Declaration {

    private final TokenType keyword;
    private final TokenType type;
    private Object value;

    public DeclarationImpl(TokenType keyword, TokenType type, Object value) {
        this.keyword = keyword;
        this.type = type;
        this.value = value;
    }

    @Override
    public TokenType getKeyword() {
        return keyword;
    }

    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
