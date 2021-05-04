package expression.impl;

import expression.Expression;
import lombok.ToString;
import token.Token;
import visitor.ExpressionVisitor;

@ToString()
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

  public Expression getExpression() {
    return expression;
  }

  public Token getToken() {
    return name;
  }
}
