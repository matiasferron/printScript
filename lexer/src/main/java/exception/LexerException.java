package exception;

import token.Token;

public class LexerException {

    private String message;
    private Token token;

    public LexerException(String message, Token token) {
        this.message = message;
        this.token = token;
    }
}
