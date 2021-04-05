package statement.parsers.statment;

import statement.Statement;
import token.TokenWrapper;

public abstract class StatementParser {
    public StatementParser nextParser;

    public abstract Statement parse(TokenWrapper tokens);

    void setNextParser(StatementParser statementParser) {
        nextParser = statementParser;
    }

}
