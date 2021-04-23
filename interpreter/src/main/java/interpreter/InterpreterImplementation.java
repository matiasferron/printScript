package interpreter;

import static token.TokenType.*;

import java.util.List;
import statement.Statement;
import visitor.StatementVisitor;

public class InterpreterImplementation implements Interpreter {

  private final StatementVisitor visitor;

  public InterpreterImplementation(StatementVisitor statementVisitor) {
    this.visitor = statementVisitor;
  }

  @Override
  public void interpret(List<Statement> statements) {
    statements.forEach(s -> s.accept(visitor));
  }
}
