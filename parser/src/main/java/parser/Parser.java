package parser;

import java.util.List;
import statement.Statement;
import token.Token;

public interface Parser {
  public List<Statement> parse(List<Token> tokens);
}
