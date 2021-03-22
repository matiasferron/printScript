package token.builder;

import token.Position;
import token.Token;
import token.TokenType;

public interface TokenBuilder {

    Token build();
    TokenBuilder withType(TokenType type);
    TokenBuilder withValue(String value);
    TokenBuilder withPosition(Position position);
}
