package visitor.ExpressionVisitorHelpers;

import exception.InterpretException;
import expression.impl.BinaryExpression;
import token.Token;
import token.TokenType;
import visitor.ExpressionVisitor;


import static token.TokenType.*;

public class VisitBinaryHelper {

    public static Object visitBinary(BinaryExpression binaryExpression, ExpressionVisitor expressionVisitor){
        Object left = binaryExpression.getLeft().accept(expressionVisitor);
        Object right = binaryExpression.getRight().accept(expressionVisitor );

        if(binaryExpression.getOperator().getTokenType() != PLUS)
            checkNumbersType(binaryExpression.getOperator(), left, right);

        // si quiero qu tambien sea un int. puedo parsear el string del value del token y fijarme si tiene un '.'
        switch (binaryExpression.getOperator().getTokenType()) {
            case MINUS:
                return (double)left - (double)right;
            case PLUS:
                if(left instanceof Number && right instanceof Number){
                    return (double)left + (double)right;
                }
                return left.toString() + right.toString();
            case DIVISION:
                return (double)left / (double)right;
            case MULTIPLICATION:
                return (double)left * (double)right;
            case GREATER:
                return (double)left > (double)right;
            case GREATEREQ:
                return (double)left >= (double)right;
            case LESS:
                return (double)left < (double)right;
            case LESSEQ:
                return (double)left <= (double)right;
        }
        return -1;
    }

    private static void isNumberType(Token operator, Object leaf) {
        if (leaf instanceof Double) return;
        throw new InterpretException(operator, "Operand must be a number.");
    }

    private static void checkNumbersType(Token operator, Object ... leafs) {
        for (Object leaf: leafs) {
            isNumberType(operator, leaf);
        }
    }
}
