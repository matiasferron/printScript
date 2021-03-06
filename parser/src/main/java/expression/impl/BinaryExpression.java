package expression.impl;

import expression.Expression;
import lombok.ToString;
import token.Token;
import visitor.ExpressionVisitor;

@ToString()
public class BinaryExpression implements Expression {

  private final Expression left;
  private final Expression right;
  private final Token operator;

  public BinaryExpression(Expression left, Expression right, Token operator) {
    this.left = left;
    this.right = right;
    this.operator = operator;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {
    return expressionVisitor.visitBinary(this);
  }

  public Expression getLeft() {
    return left;
  }

  public Expression getRight() {
    return right;
  }

  public Token getOperator() {
    return operator;
  }
}
