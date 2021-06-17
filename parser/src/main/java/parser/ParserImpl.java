package parser;

import java.util.ArrayList;
import java.util.List;
import statement.Statement;
import statement.parsers.statment.StatementParser;
import token.Token;
import token.TokenWrapperImp;

public class ParserImpl implements Parser {
  private final StatementParser statementParser;

  public ParserImpl(StatementParser statementParser) {
    this.statementParser = statementParser;
  }

  @Override
  public List<Statement> parse(List<Token> tokens) {
    TokenWrapperImp wrappedTokens =
            new TokenWrapperImp(tokens);
    List<Statement> statements = new ArrayList<>();
    while (wrappedTokens.hasMoreTokens()) {
      statements.add(statementParser.parse(wrappedTokens));
    }

    return statements;
  }
}
