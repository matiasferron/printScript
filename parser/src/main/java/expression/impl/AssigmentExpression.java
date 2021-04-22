package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class AssigmentExpression implements Expression {

  private final Expression expression;
  private final Token name;

  public AssigmentExpression(Token name, Expression expression) {
    this.expression = expression;
    this.name = name;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {
    return expressionVisitor.visitAssignment(this);
  }

  @Override
  public Expression getExpression() {
    return expression;
  }

  @Override
  public Token getToken() {
    return name;
  }
}
