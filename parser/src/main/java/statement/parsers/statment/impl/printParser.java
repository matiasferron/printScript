package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.PrintStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.expression._ExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static token.TokenType.*;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class printParser extends StatementParser {
    public printParser( ) {
        super();
    }

    @Override
    public Statement parse(TokenWrapper tokens) {
        if (match(tokens, PRINT)) {
            tokens.advance();
            Expression value = _ExpressionParser.generateExpression(tokens);
            consume(tokens, SEMICOLON, "Expect ';' after value.");
            return new PrintStatement(value);
        }
        return nextParser.parse(tokens);
    }

}
