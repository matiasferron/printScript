package token;

public interface TokenWrapper {

  Token getCurrent();

  Token getCurrentAndAdvance();

  Token advance();

  boolean hasMoreTokens();

  boolean check(TokenType type);
}
