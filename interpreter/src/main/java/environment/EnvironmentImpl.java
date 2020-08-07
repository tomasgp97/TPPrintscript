package environment;

import declaration.Declaration;
import declaration.DeclarationImpl;
import token.Token;
import token.TokenType;

import java.util.HashMap;
import java.util.Map;

public class EnvironmentImpl implements Environment {


    private Map<String, Declaration> values;
    private Environment enclosing;

    public EnvironmentImpl(Environment enclosing) {
        this.values = new HashMap<>();
        this.enclosing = enclosing;
    }

    public EnvironmentImpl() {
        this.values = new HashMap<>();
        enclosing = null;
    }

    @Override
    public Map<String, Declaration> getValues() {
        return values;
    }

    @Override
    public void addValue(String name, TokenType keyword, TokenType type, Object value) {
        values.put(name, new DeclarationImpl(keyword, type, value));
    }

    @Override
    public void assign(Token name, Object value) {
        if (values.containsKey(name.getTokenValue())) {
            Declaration declaration = values.get(name.getTokenValue());
            if(declaration.getKeyword() == TokenType.LET){
                if (declaration.getType() == TokenType.BOOLEAN){
                    if (!(value instanceof Boolean)){
                        throw new RuntimeException("Expected a boolean");
                    }
                }
                else if (declaration.getType() == TokenType.NUMBER){
                    if (!(value instanceof Number)) {
                        throw new RuntimeException("Expected a number");
                    }
                }
                else if (declaration.getType() == TokenType.STRING){
                    if (!(value instanceof String)){
                        throw new RuntimeException("Expected a string");
                    }
                }
                declaration.setValue(value);
                values.put(name.getTokenValue(), declaration);
                return;
            } else {
                throw new RuntimeException("Constant cannot be changed");
            }
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeException("Undefined variable '" + name.getTokenType() + "'.");
    }

    @Override
    public Object getValue(Token name) {
        if (values.containsKey(name.getTokenValue())) {
            return values.get(name.getTokenValue()).getValue();
        }
        if (enclosing != null) return enclosing.getValue(name);

        throw new RuntimeException(name + "Undefined variable '" + name.getTokenValue() + "'.");
    }

    @Override
    public Environment getEnclosing() {
        return enclosing;
    }
}
