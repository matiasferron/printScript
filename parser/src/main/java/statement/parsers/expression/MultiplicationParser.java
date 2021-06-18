package statement.parsers.expression;

import static token.TokenType.DIVISION;
import static token.TokenType.MULTIPLICATION;
import static utils.ParserUtils.tokenMatchTokenType;

import expression.Expression;
import expression.impl.BinaryExpression;
import token.Token;
import token.TokenWrapper;

public class MultiplicationParser extends CommonExpressionParser {
  @Override
  public Expression parse(TokenWrapper tokens) {
    Expression expr = nextParser.parse(tokens);

    if (tokenMatchTokenType(tokens, DIVISION, MULTIPLICATION)) {
      Token operator = tokens.getCurrentAndAdvance();
      Expression right = nextParser.parse(tokens);
      expr = new BinaryExpression(expr, right, operator);
    }

    return expr;
  }
}
