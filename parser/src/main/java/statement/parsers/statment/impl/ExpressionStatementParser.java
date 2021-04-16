package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.ExpressionStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.expression._ExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static token.TokenType.EOF;
import static token.TokenType.SEMICOLON;
import static utils.parserUtils.consume;

public class ExpressionStatementParser extends StatementParser {

    public ExpressionStatementParser() {
        super();
    }

    @Override
    public Statement parse(TokenWrapper tokens) {
        // todo. Aca no hago nada con el next. es como el ultima etapa siempre (?
        Expression expr = _ExpressionParser.generateExpression(tokens);
        consume(tokens, SEMICOLON, "Expect ';' after expression.");
        return new ExpressionStatement(expr);
    }

}
