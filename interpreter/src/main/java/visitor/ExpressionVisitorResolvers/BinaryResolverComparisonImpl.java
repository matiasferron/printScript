package visitor.ExpressionVisitorResolvers;

import static token.TokenType.*;

import exception.InterpretException;
import expression.impl.BinaryExpression;
import token.Token;
import visitor.ExpressionVisitor;

public class BinaryResolverComparisonImpl implements BinaryExpressionResolver {

  public Object visit(BinaryExpression binaryExpression, ExpressionVisitor expressionVisitor) {
    Object left = binaryExpression.getLeft().accept(expressionVisitor);
    Object right = binaryExpression.getRight().accept(expressionVisitor);

    Number convertedLeft = 0;
    Number convertedRight = 0;

    if (binaryExpression.getOperator().getTokenType() != PLUS) {
      checkNumbersType(binaryExpression.getOperator(), left, right);
      convertedLeft = convertTypeNumberOperation(left);
      convertedRight = convertTypeNumberOperation(right);
    }

    switch (binaryExpression.getOperator().getTokenType()) {
      case MINUS:
        return minusOperation(convertedLeft, convertedRight);
      case PLUS:
        if (left instanceof Number && right instanceof Number) {
          return this.convertNumberOperation(left, right);
        }
        if (left instanceof String
            && (((String) left).charAt(0) == '\"' || ((String) left).charAt(0) == '\'')) {
          left = ((String) left).substring(1, ((String) left).length() - 1);
        }
        if (right instanceof String
            && (((String) right).charAt(0) == '\"' || ((String) right).charAt(0) == '\'')) {
          right = ((String) right).substring(1, ((String) right).length() - 1);
        }
        return left.toString() + right.toString();
      case DIVISION:
        return divisionOperation(convertedLeft, convertedRight);
      case MULTIPLICATION:
        return multiplyOperation(convertedLeft, convertedRight);
      case GREATER:
        return convertedLeft.doubleValue() > convertedRight.doubleValue();
      case GREATEREQ:
        return convertedLeft.doubleValue() >= convertedRight.doubleValue();
      case LESS:
        return convertedLeft.doubleValue() < convertedRight.doubleValue();
      case LESSEQ:
        return convertedLeft.doubleValue() <= convertedRight.doubleValue();
    }
    return -1;
  }

  private Object minusOperation(Number left, Number right) {
    if (left instanceof Integer && right instanceof Integer)
      return left.intValue() - right.intValue();

    return left.doubleValue() - right.doubleValue();
  }

  private Object divisionOperation(Number left, Number right) {
    if (left instanceof Integer && right instanceof Integer)
      return left.intValue() / right.intValue();

    return left.doubleValue() / right.doubleValue();
  }

  private Object multiplyOperation(Number left, Number right) {
    if (left instanceof Integer && right instanceof Integer)
      return left.intValue() * right.intValue();

    return left.doubleValue() * right.doubleValue();
  }

  private Object convertNumberOperation(Object left, Object right) {
    if (left instanceof Integer && right instanceof Integer) return (int) left + (int) right;

    if (left instanceof Integer) return (int) left + (double) right;

    if (right instanceof Integer) return (double) left + (int) right;

    return (double) left + (double) right;
  }

  private Number convertTypeNumberOperation(Object value) {
    if (value instanceof Integer) return (int) value;

    return (double) value;
  }

  private static void isNumberType(Token operator, Object leaf) {
    if (leaf instanceof Number) return;
    throw new InterpretException(operator, "Operand must be a number.");
  }

  private static void checkNumbersType(Token operator, Object... leafs) {
    for (Object leaf : leafs) {
      isNumberType(operator, leaf);
    }
  }
}
