package token;

class TokenImpl implements Token {

  private final TokenType tokenType;
  private final String value;
  private Position position;

  TokenImpl(TokenType tokenType, String value, Position position) {
    this.tokenType = tokenType;
    this.value = value;
    this.position = position;
  }

  @Override
  public TokenType getTokenType() {
    return this.tokenType;
  }

  @Override
  public String getTokenValue() {
    return this.value;
  }

  @Override
  public Position getPosition() {
    return this.position;
  }

  @Override
  public void updatePosition(int line, int col) {
    this.position = new Position(line, col);
  }

  @Override
  public String toString() {
    return String.format("(%s %s)", tokenType.name(), value);
  }
}
