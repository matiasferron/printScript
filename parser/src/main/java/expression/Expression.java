package expression;

import token.Token;
import visitor.ExpressionVisitor;

public interface Expression {
  Object accept(ExpressionVisitor expressionVisitor);

  Token getToken();

  Expression getExpression();
}
