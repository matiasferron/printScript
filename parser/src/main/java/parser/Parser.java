package parser;

import statement.Statement;

import java.util.List;

public interface Parser {
  public List<Statement> parse();
}
