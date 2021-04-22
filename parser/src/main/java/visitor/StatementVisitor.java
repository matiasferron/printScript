package visitor;

import statement.impl.*;

public interface StatementVisitor {
  void visitExpressionStatement(ExpressionStatement statement);

  void visitPrintStatement(PrintStatement statement);

  void visitVariableStatement(VariableStatement statement);

  void visitIfStatement(IfStatement statement);
}
