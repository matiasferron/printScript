package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class UnaryExpression implements Expression {

    private Token operator;
    private Expression right;

    public UnaryExpression(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object accept(ExpressionVisitor expressionVisitor) {

        return expressionVisitor.visitUnary(this);
    }

    @Override
    public Token getToken() {
        return operator;
    }

    @Override
    public Expression getExpression() {
        return right;
    }

    public Expression getRight() {
        return right;
    }

    public Token getOperator() {
        return operator;
    }
}
