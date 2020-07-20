package statement;

import expression.Expression;
import token.Token;
import token.TokenType;
import visitor.StatementVisitor;

public class VariableStatement implements Statement{
    private final Token keyword;
    private final Token name;
    private final TokenType type;
    private final Expression initializer;

    public VariableStatement(Token keyword, Token name, TokenType type, Expression initializer) {
        this.keyword = keyword;
        this.name = name;
        this.type = type;
        this.initializer = initializer;
    }

    public Token getKeyword() {
        return keyword;
    }

    public Token getName() {
        return name;
    }

    public TokenType getType() {
        return type;
    }

    public Expression getInitializer() {
        return initializer;
    }

    @Override
    public Expression getExpression() {
        return initializer;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }
}
