package parser;

import java.util.ArrayList;
import java.util.List;
import statement.Statement;
import statement.parsers.statment.StatementParser;
import token.Token;
import token.TokenWrapperImp;

public class ParserImpl implements Parser {
  private final TokenWrapperImp tokens;
  private final StatementParser statementParser;

  public ParserImpl(List<Token> tokens, StatementParser statementParser) {
    this.tokens = new TokenWrapperImp(tokens);
    this.statementParser = statementParser;
  }

  @Override
  public List<Statement> parse() {
    List<Statement> statements = new ArrayList<>();
    while (tokens.hasMoreTokens()) {
      statements.add(statementParser.parse(tokens));
    }

    return statements;
  }
}
