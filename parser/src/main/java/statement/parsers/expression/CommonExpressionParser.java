package statement.parsers.expression;

import expression.Expression;
import token.TokenWrapper;

public abstract class CommonExpressionParser {
  CommonExpressionParser nextParser;

  public abstract Expression parse(TokenWrapper tokens);

  public void setNextParser(CommonExpressionParser nextParser) {
    this.nextParser = nextParser;
  }
}
