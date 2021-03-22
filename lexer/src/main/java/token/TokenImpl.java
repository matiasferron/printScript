package token;

public class TokenImpl implements Token {

    private TokenType tokenType;
    private String value;
    private Position position;

    public TokenImpl(TokenType tokenType, String value, Position position) {
        this.tokenType = tokenType;
        this.value = value;
        this.position = position;
    }


    @Override
    public TokenType getTokenType() {
        return this.tokenType;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
