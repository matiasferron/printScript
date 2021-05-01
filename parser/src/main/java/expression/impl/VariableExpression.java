package expression.impl;

import expression.Expression;
import lombok.Data;
import lombok.ToString;
import token.Token;
import visitor.ExpressionVisitor;

@ToString()
public class VariableExpression implements Expression {

  private Token name;

  public VariableExpression(Token name) {
    this.name = name;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {

    return expressionVisitor.visitVariable(this);
  }

  @Override
  public Token getToken() {
    return name;
  }

  @Override
  public Expression getExpression() {
    return null;
  }

  public Token getName() {
    return name;
  }
}
