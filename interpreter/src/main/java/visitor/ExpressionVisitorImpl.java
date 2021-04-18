package visitor;

import exception.InterpretException;
import expression.impl.*;
import interpreter.Interpreter;
import token.Token;

import static token.TokenType.MINUS;
import static token.TokenType.PLUS;

public class ExpressionVisitorImpl implements ExpressionVisitor{

    // todo auxiliary class instead of interpreter
    private final Interpreter interpreter;

    public ExpressionVisitorImpl(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public Object visitBinary(BinaryExpression expression) {
        Object left = expression.getLeft().accept(this);
        Object right = expression.getRight().accept(this);

        switch (expression.getOperator().getTokenType()) {
            case MINUS:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left - (double)right;
            case PLUS:
                if(left instanceof Number && right instanceof Number){
                    return (double)left + (double)right;
                }
                return left.toString() + right.toString();
            case DIVISION:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left / (double)right;
            case MULTIPLICATION:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left * (double)right;
            case GREATER:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left > (double)right;
            case GREATEREQ:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left >= (double)right;
            case LESS:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left < (double)right;
            case LESSEQ:
                checkNumbersType(expression.getOperator(), left, right);
                return (double)left <= (double)right;
        }
        return null;
    }

    @Override
    public Object visitGrouping(GroupingExpression expression) {
        return expression.getExpression().accept(this);
    }

    @Override
    public Object visitLiteral(LiteralExpression expression) {
        return expression.getValue();
    }

    @Override
    public Object visitUnary(UnaryExpression expression) {
        Object right = expression.getRight().accept(this);

        if (expression.getOperator().getTokenType() == MINUS) {
            checkNumbersType(expression.getOperator(), right);
            return -(double) right;
        }
        if (expression.getOperator().getTokenType() == PLUS) {
            checkNumbersType(expression.getOperator(), right);
            return +(double) right;
        }

        return null;
    }

    private void isNumberType(Token operator, Object leaf) {
        // todo esto tambien cambia si son strings
        if (leaf instanceof Double) return;
        throw new InterpretException(operator, "Operand must be a number.");
    }

    private void checkNumbersType(Token operator, Object ... leafs) {
        for (Object leaf: leafs) {
            isNumberType(operator, leaf);
        }
    }

    @Override
    public Object visitVariable(VariableExpression expression) {
        return interpreter.get(expression.getName());
    }

    @Override
    public Object visitAssignment(AssigmentExpression expression) {
        Object value = expression.getExpression().accept(this);

        interpreter.setVariableValue(expression.getToken(), value);
        return value;
    }
}
