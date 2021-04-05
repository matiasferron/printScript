package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.PrintStatement;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static statement.parsers.expression.ExpressionParser.generateExpression;
import static token.TokenType.PRINTLN;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class printParser extends StatementParser {
    @Override
    public Statement parse(TokenWrapper tokens) {
        if (match(tokens, PRINTLN)) {
            Expression value = generateExpression(tokens);
            consume(tokens, SEMICOLON, "Expect ';' after value.");
            return new PrintStatement(value);
        }
        return nextParser.parse(tokens);
    }

}
