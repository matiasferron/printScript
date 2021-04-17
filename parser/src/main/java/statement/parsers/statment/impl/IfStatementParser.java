package statement.parsers.statment.impl;

import expression.Expression;
import statement.Statement;
import statement.impl.IfStatement;
import statement.impl.PrintStatement;
import statement.parsers.expression._ExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

import static token.TokenType.*;
import static utils.parserUtils.consume;
import static utils.parserUtils.match;

public class IfStatementParser extends StatementParser {
    @Override
    public Statement parse(TokenWrapper tokens) {
        if (match(tokens, IF)) {
            tokens.advance();

            consume(tokens, LPAREN,"Expect '(' after 'if'");

            Expression condition = _ExpressionParser.generateExpression(tokens);

            consume(tokens, RPAREN,"Expect ')' after if condition");

            consume(tokens, LBRACKET,"Expect '{' after if condition");

            Statement conditionBranch = nextParser.parse(tokens);

            consume(tokens, RBRACKET,"Expect '}' after if condition");


            Statement elseBranch = null;

            if (tokens.getCurrent().getTokenType() == ELSE) {
                tokens.advance();
                elseBranch = nextParser.parse(tokens);
            }
            consume(tokens, SEMICOLON, "Expect ';' after expression.");
            return new IfStatement(condition, conditionBranch, elseBranch);
        }
        return nextParser.parse(tokens);
    }
}
