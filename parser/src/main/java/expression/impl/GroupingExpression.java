package expression.impl;

import expression.Expression;
import lombok.ToString;
import visitor.ExpressionVisitor;

@ToString()
public class GroupingExpression implements Expression {

  private final Expression expression;

  public GroupingExpression(Expression expression) {
    this.expression = expression;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {
    return expressionVisitor.visitGrouping(this);
  }

  public Expression getExpression() {
    return expression;
  }
}
