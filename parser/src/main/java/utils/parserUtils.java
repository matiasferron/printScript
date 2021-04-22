package utils;

import exception.ParseException;
import token.Token;
import token.TokenType;
import token.TokenWrapper;

public class parserUtils {

  public static Token validateCurrentToken(TokenWrapper tokens, TokenType type, String message) {
    if (tokens.check(type)) return tokens.getCurrentAndAdvance();
    throw new ParseException(message, tokens.getCurrent());
  }

  public static boolean tokenMatchTokenType(TokenWrapper tokens, TokenType... types) {
    for (TokenType type : types) {
      if (tokens.check(type)) {
        return true;
      }
    }

    return false;
  }
}
