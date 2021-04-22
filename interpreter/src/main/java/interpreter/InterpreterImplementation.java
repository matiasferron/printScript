package interpreter;

import statement.Statement;
import visitor.StatementVisitor;

import java.util.List;

import static token.TokenType.*;

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
