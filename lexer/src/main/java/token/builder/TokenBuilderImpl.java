package token.builder;

import token.Position;
import token.Token;
import token.TokenImpl;
import token.TokenType;

public class TokenBuilderImpl implements TokenBuilder {

    private TokenType tokenType;
    private Position position;
    private String value;

    public static TokenBuilder createBuilder() {
        return new TokenBuilderImpl();
    }

    @Override
    public Token build() {
        return new TokenImpl(tokenType, value, position);
    }

    @Override
    public TokenBuilder withType(TokenType type) {
        this.tokenType = type;
        return this;
    }

    @Override
    public TokenBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public TokenBuilder withPosition(Position position) {
        this.position = position;
        return this;
    }
}
