package statement.parsers.expression;

import exception.ParseException;
import expression.Expression;
import expression.impl.GroupingExpression;
import expression.impl.LiteralExpression;
import expression.impl.VariableExpression;
import token.TokenWrapper;

import static token.TokenType.*;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class TypeParser extends CommonExpressionParser {
  @Override
  public Expression parse(TokenWrapper tokens) {
    if (match(tokens, STRING)) {
      return new LiteralExpression(tokens.getCurrentAndAdvance().getTokenValue());
    }

    if (match(tokens, NUMBER)) {
      return new LiteralExpression(
          Double.parseDouble(tokens.getCurrentAndAdvance().getTokenValue()));
    }

    if (match(tokens, FALSE)) {
      tokens.advance();
      return new LiteralExpression(false);
    }
    if (match(tokens, TRUE)) {
      tokens.advance();
      return new LiteralExpression(true);
    }

    if (match(tokens, IDENTIFIER)) {
      return new VariableExpression(tokens.getCurrentAndAdvance());
    }

    if (match(tokens, LPAREN)) {
      tokens.advance();
      Expression expr = nextParser.parse(tokens);
      consume(tokens, RPAREN, "Expect ')' after expression.");
      return new GroupingExpression(expr);
    }

    throw new ParseException("Expect expression.", tokens.getCurrent());
  }
}
