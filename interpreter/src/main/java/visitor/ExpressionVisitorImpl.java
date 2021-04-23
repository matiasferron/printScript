package visitor;

import expression.impl.*;
import interpreter.helper.InterpreterHelper;
import visitor.ExpressionVisitorHelpers.VisitorExpressionHelper;

public class ExpressionVisitorImpl implements ExpressionVisitor {
  private final InterpreterHelper interpreterMemory;
  private final VisitorExpressionHelper visitorExpressionHelper;

  public ExpressionVisitorImpl(
      InterpreterHelper interpreterMemory, VisitorExpressionHelper expressionHelper) {
    this.interpreterMemory = interpreterMemory;
    this.visitorExpressionHelper = expressionHelper;
  }

  @Override
  public Object visitBinary(BinaryExpression expression) {
    return visitorExpressionHelper.visit(expression, this);
  }

  @Override
  public Object visitGrouping(GroupingExpression expression) {
    return expression.getExpression().accept(this);
  }

  @Override
  public Object visitLiteral(LiteralExpression expression) {
    return expression.getValue();
  }

  @Override
  public Object visitVariable(VariableExpression expression) {
    return interpreterMemory.get(expression.getName());
  }

  @Override
  public Object visitAssignment(AssigmentExpression expression) {
    Object value = expression.getExpression().accept(this);

    interpreterMemory.setVariableValue(expression.getToken(), value);
    return value;
  }
}
