package token;


public class TokenFactory {

    public static TokenFactory newTokenFactory() { return new TokenFactory(); }

    public Token create(TokenType tokenType,String value, Position position) {
        return new TokenImpl(tokenType, value, position);
    }

}
