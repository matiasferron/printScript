package interpreter;

import token.TokenType;

public class VariableDefinitionDataImplementation implements VariableDefinitionData {

    private final TokenType keyword;
    private final TokenType type;
    private Object value;

    public VariableDefinitionDataImplementation(TokenType keyword, TokenType type, Object value) {
        this.keyword = keyword;
        this.type = type;
        this.value = value;
    }

    public TokenType getVarDefinitionKey() {
        return keyword;
    }

    public TokenType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
