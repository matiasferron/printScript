package visitor.ExpressionVisitorResolvers;

import expression.impl.BinaryExpression;
import visitor.ExpressionVisitor;

public interface BinaryExpressionResolver {
  public Object visit(BinaryExpression binaryExpression, ExpressionVisitor expressionVisitor);
}
