package utils;

import exception.ParseException;
import token.Token;
import token.TokenType;
import token.TokenWrapper;

public class parserUtils {

    public static Token consume(TokenWrapper tokens, TokenType type, String message) {
        if (tokens.check(type)) return tokens.advance();
        throw new ParseException(message, tokens.getCurrent());
    }

    public static boolean match(TokenWrapper tokens, TokenType... types) {
        for (TokenType type : types) {
            if (tokens.check(type)) {
                //tokens.advance();
                return true;
            }
        }

        return false;
    }
}
