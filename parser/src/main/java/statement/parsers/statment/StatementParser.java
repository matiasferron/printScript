package statement.parsers.statment;

import statement.Statement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.expression.ExpressionParser;
import token.TokenWrapper;

public abstract class StatementParser {
    public StatementParser nextParser;

    public CommonExpressionParser expressionParser;

    public StatementParser(CommonExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    public abstract Statement parse(TokenWrapper tokens);

    void setNextParser(StatementParser statementParser) {
        nextParser = statementParser;
    }

}
