package expression.impl;

import expression.Expression;
import lombok.ToString;
import token.Token;
import visitor.ExpressionVisitor;

@ToString()
public class VariableExpression implements Expression {

  private final Token name;

  public VariableExpression(Token name) {
    this.name = name;
  }

  @Override
  public Object accept(ExpressionVisitor expressionVisitor) {

    return expressionVisitor.visitVariable(this);
  }

  public Token getToken() {
    return name;
  }

  public Token getName() {
    return name;
  }
}
