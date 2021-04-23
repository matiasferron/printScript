package visitor.ExpressionVisitorHelpers;

import expression.impl.BinaryExpression;
import visitor.ExpressionVisitor;

public interface VisitorExpressionHelper {
    public Object visit(BinaryExpression binaryExpression, ExpressionVisitor expressionVisitor);
}
