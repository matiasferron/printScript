package statement.parsers.statment.impl;

import static token.TokenType.*;
import static utils.ParserUtils.tokenMatchTokenType;
import static utils.ParserUtils.validateCurrentToken;

import expression.Expression;
import statement.Statement;
import statement.impl.PrintStatement;
import statement.parsers.expression.CommonExpressionParser;
import statement.parsers.statment.StatementParser;
import token.TokenWrapper;

public class PrintParser extends StatementParser {
  public PrintParser() {
    super();
  }

  public PrintParser(CommonExpressionParser expressionParser) {
    super(expressionParser);
  }

  @Override
  public Statement parse(TokenWrapper tokens) {
    if (tokenMatchTokenType(tokens, PRINTLN)) {
      tokens.advance();
      Expression value = expressionParser.parse(tokens);
      validateCurrentToken(tokens, SEMICOLON, "Expect ';' after value.");
      return new PrintStatement(value);
    }
    return nextParser.parse(tokens);
  }
}
