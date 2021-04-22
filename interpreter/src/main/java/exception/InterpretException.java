package exception;

import token.Token;

public class InterpretException extends RuntimeException {
  private final Token failedToken;
  private final String errorMessage;

  public InterpretException(Token token, String message) {
    this.failedToken = token;
    this.errorMessage = message;
  }

  @Override
  public String getMessage() {
    return errorMessage
        + " at line: "
        + failedToken.getPosition().getLine()
        + " in column: "
        + failedToken.getPosition().getColumn();
  }
}
