package statement.parsers.expression;

import expression.Expression;
import expression.impl.BinaryExpression;
import token.Token;
import token.TokenWrapper;

import static token.TokenType.*;
import static token.TokenType.LESSEQ;
import static utils.parserUtils.match;

public class ComparisonParser extends CommonExpressionParser{
    @Override
    public Expression parse(TokenWrapper tokens) {
        Expression initialExpression = nextParser.parse(tokens);

        if (match(tokens, GREATER, GREATEREQ, LESS, LESSEQ)) {
            Token operator = tokens.getCurrentAndAdvance();
            Expression right = nextParser.parse(tokens);
            initialExpression = new BinaryExpression(initialExpression, right, operator);
        }

        return initialExpression;
    }
}
