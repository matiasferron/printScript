package statement.parsers.expression;

import static token.TokenType.*;
import static utils.parserUtils.tokenMatchTokenType;
import static utils.parserUtils.validateCurrentToken;

import exception.ParseException;
import expression.Expression;
import expression.impl.GroupingExpression;
import expression.impl.LiteralExpression;
import expression.impl.VariableExpression;
import token.TokenWrapper;

public class TypeParser extends CommonExpressionParser {
  @Override
  public Expression parse(TokenWrapper tokens) {
    if (tokenMatchTokenType(tokens, STRING)) {
      return new LiteralExpression(tokens.getCurrentAndAdvance().getTokenValue());
    }

    if (tokenMatchTokenType(tokens, NUMBER)) {
      return new LiteralExpression(
          Double.parseDouble(tokens.getCurrentAndAdvance().getTokenValue()));
    }

    if (tokenMatchTokenType(tokens, FALSE)) {
      tokens.advance();
      return new LiteralExpression(false);
    }
    if (tokenMatchTokenType(tokens, TRUE)) {
      tokens.advance();
      return new LiteralExpression(true);
    }

    if (tokenMatchTokenType(tokens, IDENTIFIER)) {
      return new VariableExpression(tokens.getCurrentAndAdvance());
    }

    if (tokenMatchTokenType(tokens, LPAREN)) {
      tokens.advance();
      Expression expr = nextParser.parse(tokens);
      validateCurrentToken(tokens, RPAREN, "Expect ')' after expression.");
      return new GroupingExpression(expr);
    }

    throw new ParseException("Expect expression.", tokens.getCurrent());
  }
}
