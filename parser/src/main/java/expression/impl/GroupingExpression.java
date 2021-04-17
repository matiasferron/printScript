package expression.impl;

import expression.Expression;
import token.Token;
import visitor.ExpressionVisitor;

public class GroupingExpression implements Expression {

    private Expression expression;

    public GroupingExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object accept(ExpressionVisitor expressionVisitor) {
        return expressionVisitor.visitGrouping(this);
    }

    @Override
    public Expression getExpression() {
        return expression;
    }

    @Override
    public Token getToken(){ return null;}
}
