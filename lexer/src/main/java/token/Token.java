package token;

public interface Token {

    TokenType getTokenType();
    String getValue();
    Position getPosition();

}
