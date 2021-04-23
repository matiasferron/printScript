package parser;

import java.util.List;
import statement.Statement;

public interface Parser {
  public List<Statement> parse();
}
