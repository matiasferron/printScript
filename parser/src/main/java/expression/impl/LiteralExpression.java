package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class LiteralExpression implements Expression {

    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    @Override
    public Object accept(ExpressionVisitor expressionVisitor) {
        return expressionVisitor.visitLiteral(this);
    }

    @Override
    public Token getToken() {
        return null;
    }

    @Override
    public Expression getExpression() {
        return null;
    }

    public Object getValue() {
        return value;
    }
}
