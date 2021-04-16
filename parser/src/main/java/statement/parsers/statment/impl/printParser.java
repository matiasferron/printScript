package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.PrintStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static token.TokenType.PRINTLN;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class printParser extends StatementParser {
    public printParser(CommonExpressionParser expressionParser) {
        super(expressionParser);
    }

    @Override
    public Statement parse(TokenWrapper tokens) {
        if (match(tokens, PRINTLN)) {
            Expression value = expressionParser.parse(tokens);
            consume(tokens, SEMICOLON, "Expect ';' after value.");
            return new PrintStatement(value);
        }
        return nextParser.parse(tokens);
    }

}
