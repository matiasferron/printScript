package visitor;

import expression.impl.*;
import interpreter.helper.InterpreterHelper;
import visitor.ExpressionVisitorResolvers.BinaryExpressionResolver;

public class ExpressionVisitorImpl implements ExpressionVisitor {
  private final InterpreterHelper interpreterMemory;
  private final BinaryExpressionResolver binaryExpressionResolver;

  public ExpressionVisitorImpl(
      InterpreterHelper interpreterMemory, BinaryExpressionResolver expressionHelper) {
    this.interpreterMemory = interpreterMemory;
    this.binaryExpressionResolver = expressionHelper;
  }

  @Override
  public Object visitBinary(BinaryExpression expression) {
    return binaryExpressionResolver.visit(expression, this);
  }

  @Override
  public Object visitGrouping(GroupingExpression expression) {
    return expression.getExpression().accept(this);
  }

  @Override
  public Object visitLiteral(LiteralExpression expression) {
    if (expression.getValue() instanceof String
            && (((String) expression.getValue()).charAt(0) == ('\"') || ((String) expression.getValue()).charAt(0) == ('\'') )){
      return ((String) expression.getValue()).substring(1, ((String) expression.getValue()).length() - 1);
    }
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
