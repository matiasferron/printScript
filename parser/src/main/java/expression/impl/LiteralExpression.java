package expression.impl;

import expression.Expression;
import lombok.ToString;
import visitor.ExpressionVisitor;

@ToString()
public class LiteralExpression implements Expression {

  private final Object value;

  public LiteralExpression(Object value) {
    this.value = value;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {
    return expressionVisitor.visitLiteral(this);
  }

  public Object getValue() {
    return value;
  }
}
