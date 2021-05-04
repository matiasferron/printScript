package visitor.StatementVisitorResolvers;

import interpreter.helper.InterpreterHelper;
import statement.impl.VariableStatement;
import visitor.ExpressionVisitor;

public interface VariableStatementResolver {
  public void visitVariableStatementHelper(
      VariableStatement variableStatement,
      ExpressionVisitor expressionVisitor,
      InterpreterHelper interpreterMemory);
}
