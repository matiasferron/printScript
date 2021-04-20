package visitor;

import exception.InterpretException;
import expression.impl.*;
import interpreter.Interpreter;
import interpreter.InterpreterMemory;
import token.Token;
import visitor.ExpressionVisitorHelpers.VisitBinaryHelper;

import static token.TokenType.MINUS;
import static token.TokenType.PLUS;

public class ExpressionVisitorImpl implements ExpressionVisitor{
    private final InterpreterMemory interpreterMemory;

    public ExpressionVisitorImpl(InterpreterMemory interpreterMemory) {
        this.interpreterMemory = interpreterMemory;
    }

    @Override
    public Object visitBinary(BinaryExpression expression) {
        return VisitBinaryHelper.visitBinary(expression, this);
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
    public Object visitVariable(VariableExpression expression) {
        return interpreterMemory.get(expression.getName());
    }

    @Override
    public Object visitAssignment(AssigmentExpression expression) {
        Object value = expression.getExpression().accept(this);

        interpreterMemory.setVariableValue(expression.getToken(), value);
        return value;
    }
}
