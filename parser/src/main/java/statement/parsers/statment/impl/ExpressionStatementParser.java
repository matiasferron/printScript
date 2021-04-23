package statement.parsers.statment.impl;

import static token.TokenType.SEMICOLON;
import static utils.parserUtils.validateCurrentToken;

import expression.Expression;
import statement.Statement;
import statement.impl.ExpressionStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

public class ExpressionStatementParser extends StatementParser {

  public ExpressionStatementParser() {
    super();
  }

  public ExpressionStatementParser(CommonExpressionParser expressionParser) {
    super(expressionParser);
  }

  @Override
  public Statement parse(TokenWrapper tokens) {
    // todo. Aca no hago nada con el next. es como el ultima etapa siempre (?
    Expression expr = expressionParser.parse(tokens);
    validateCurrentToken(tokens, SEMICOLON, "Expect ';' after expression.");
    return new ExpressionStatement(expr);
  }
}
