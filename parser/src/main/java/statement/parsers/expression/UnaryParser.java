package statement.parsers.expression;

import expression.Expression;
import expression.impl.UnaryExpression;
import token.Token;
import token.TokenWrapper;

import static token.TokenType.MINUS;
import static utils.parserUtils.match;

public class UnaryParser extends CommonExpressionParser {
    @Override
    public Expression parse(TokenWrapper tokens) {
        if (match(tokens, MINUS)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = parse(tokens);
            return new UnaryExpression(operator, right);
        }

        return nextParser.parse(tokens);
    }
}
