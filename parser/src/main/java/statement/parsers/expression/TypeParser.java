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

public class TypeParser extends CommonExpressionParser{
    @Override
    public Expression parse(TokenWrapper tokens) {
        if (match(tokens, NUMBER, STRING)) {
            return new LiteralExpression(tokens.getCurrentAndAdvance().getValue());
        }

        if (match(tokens, IDENTIFIER)) {
            return new VariableExpression(tokens.getCurrentAndAdvance());
        }

        if (match(tokens, LPAREN)) {
            Expression expr = nextParser.parse(tokens);
            consume(tokens, RPAREN, "Expect ')' after expression.");
            return new GroupingExpression(expr);
        }

        throw new ParseException("Expect expression.", tokens.getCurrent());
    }
}
