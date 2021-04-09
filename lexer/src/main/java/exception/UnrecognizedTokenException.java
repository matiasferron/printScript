package exception;

import token.Token;

public class UnrecognizedTokenException extends LexerException {

    public UnrecognizedTokenException(String message, Token token) {
        super(message, token);
    }
}
