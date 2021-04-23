package interpreter;

import java.util.List;
import statement.Statement;

public interface Interpreter {
  void interpret(List<Statement> statements);
}
