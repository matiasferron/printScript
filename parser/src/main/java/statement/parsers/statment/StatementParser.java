package statement.parsers.statment;

import statement.Statement;
import statement.parsers.expression.CommonExpressionParser;
import token.TokenWrapper;

public abstract class StatementParser {
  public StatementParser nextParser;

  public CommonExpressionParser expressionParser;

  public StatementParser(CommonExpressionParser expressionParser) {
    this.expressionParser = expressionParser;
  }

  public StatementParser() {
    this.expressionParser = null;
  }

  public abstract Statement parse(TokenWrapper tokens);

  public void setNextParser(StatementParser statementParser) {
    nextParser = statementParser;
  }
}
