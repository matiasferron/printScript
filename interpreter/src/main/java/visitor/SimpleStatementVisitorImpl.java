package visitor;

import interpreter.helper.InterpreterHelper;
import statement.impl.ExpressionStatement;
import statement.impl.IfStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;
import visitor.StatementVisitorHelpers.VisitorStatementHelper;

public class SimpleStatementVisitorImpl implements StatementVisitor {
  private final ExpressionVisitor expressionVisitor;
  private final InterpreterHelper interpreterMemory;
  private final VisitorStatementHelper visitStatementHelper;

  public SimpleStatementVisitorImpl(
      ExpressionVisitor expressionVisitor,
      InterpreterHelper interpreterMemory,
      VisitorStatementHelper visitStatementHelper) {
    this.expressionVisitor = expressionVisitor;
    this.interpreterMemory = interpreterMemory;
    this.visitStatementHelper = visitStatementHelper;
  }

  @Override
  public void visitExpressionStatement(ExpressionStatement statement) {
    statement.getExpression().accept(expressionVisitor);
  }

  @Override
  public void visitPrintStatement(PrintStatement statement) {
    Object value = statement.getExpression().accept(expressionVisitor);
    interpreterMemory.addPrintedValues(value.toString());
    System.out.println(value);
  }

  @Override
  public void visitVariableStatement(VariableStatement statement) {
    visitStatementHelper.visitVariableStatementHelper(
        statement, expressionVisitor, interpreterMemory);
  }

  @Override
  public void visitIfStatement(IfStatement statement) {
    throw new RuntimeException("Esta version no es compatible con if statements");
  }
}
