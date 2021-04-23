package visitor;

import interpreter.helper.InterpreterHelper;
import statement.impl.ExpressionStatement;
import statement.impl.IfStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;
import visitor.StatementVisitorHelpers.VisitorStatementHelper;

public class StatementVisitorImpl implements StatementVisitor {
  private final ExpressionVisitor expressionVisitor;
  private final InterpreterHelper interpreterMemory;
  private final VisitorStatementHelper visitStatementHelper;

  public StatementVisitorImpl(
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
    if (isBoolean(statement.getCondition().accept(expressionVisitor))) {
      interpreterMemory.turnOnTemporalSpace();
      // true branch
      statement.getConditionStatement().forEach(statement1 -> statement1.accept(this));
    } else if (statement.getElseStatement().size() != 0) {
      interpreterMemory.turnOnTemporalSpace();
      // false branch
      statement.getElseStatement().forEach(statement1 -> statement1.accept(this));
    }

    interpreterMemory.turnOffTemporalSpace();
  }

  private boolean isBoolean(Object object) {
    if (object == null) return false;
    if (object instanceof Boolean) return (boolean) object;
    return true;
  }
}
