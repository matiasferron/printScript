package token;

public interface Token {

  TokenType getTokenType();

  String getTokenValue();

  Position getPosition();

  void updatePosition(int line, int col);
}
