package statement.parsers;

import statement.Statement;
import token.TokenWrapper;

public interface StatementParser {

    Statement parse(TokenWrapper tokensWrapper);

}
