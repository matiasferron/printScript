package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.ExpressionStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.expression.ExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static statement.parsers.expression.ExpressionParser.generateExpression;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.consume;

public class ExpressionStatementParser extends StatementParser {

    public ExpressionStatementParser(CommonExpressionParser expressionParser) {
        super(expressionParser);
    }

    @Override
    public Statement parse(TokenWrapper tokens) {
        // todo. Aca no hago nada con el next. es como el ultima etapa siempre (?
        Expression expr = expressionParser.parse(tokens);
        consume(tokens, SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStatement(expr);
    }

}
