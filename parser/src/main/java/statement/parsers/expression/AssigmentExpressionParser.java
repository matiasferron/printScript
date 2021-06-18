package statement.parsers.expression;

import static token.TokenType.EQUALS;
import static utils.ParserUtils.tokenMatchTokenType;

import exception.ParseException;
import expression.Expression;
import expression.impl.AssigmentExpression;
import expression.impl.VariableExpression;
import token.Token;
import token.TokenWrapper;

public class AssigmentExpressionParser extends CommonExpressionParser {
  @Override
  public Expression parse(TokenWrapper tokens) {
    Expression expression = this.nextParser.parse(tokens);

    if (tokenMatchTokenType(tokens, EQUALS)) {
      Token equals = tokens.getCurrentAndAdvance();
      Expression value = parse(tokens);

      // vale la pena hacer un get de los tokens en todas las expressiones solo para evitar este
      // instace of?
      if (expression instanceof VariableExpression) {
        Token name = ((VariableExpression) expression).getName();
        return new AssigmentExpression(name, value);
      }

      throw new ParseException("Invalid assignment target.", equals);
    }

    return expression;
  }
}
