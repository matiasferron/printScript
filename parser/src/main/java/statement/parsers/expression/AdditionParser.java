package statement.parsers.expression;

import static token.TokenType.MINUS;
import static token.TokenType.PLUS;
import static utils.ParserUtils.tokenMatchTokenType;

import expression.Expression;
import expression.impl.BinaryExpression;
import token.Token;
import token.TokenWrapper;

public class AdditionParser extends CommonExpressionParser {
  @Override
  public Expression parse(TokenWrapper tokens) {
    Expression expr = nextParser.parse(tokens);

    if (tokenMatchTokenType(tokens, MINUS, PLUS)) {
      Token operator = tokens.getCurrentAndAdvance();
      Expression right = nextParser.parse(tokens);
      expr = new BinaryExpression(expr, right, operator);
    }

    return expr;
  }
}
