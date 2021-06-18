package statement.parsers.expression;

import static token.TokenType.*;
import static utils.ParserUtils.tokenMatchTokenType;
import static utils.ParserUtils.validateCurrentToken;

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
      String value = tokens.getCurrentAndAdvance().getTokenValue();
      Number valueConverted = checkNumberTypeConversion(value);
      return new LiteralExpression(valueConverted);
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

  private Number checkNumberTypeConversion(String value) {
    if (value.contains(".")) return Double.parseDouble(value);

    return Integer.parseInt(value);
  }
}
