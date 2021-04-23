package visitor.StatementVisitorHelpers;

import interpreter.helper.InterpreterHelper;
import statement.impl.VariableStatement;
import visitor.ExpressionVisitor;

public interface VisitorStatementHelper {
  public void visitVariableStatementHelper(
      VariableStatement variableStatement,
      ExpressionVisitor expressionVisitor,
      InterpreterHelper interpreterMemory);
}
