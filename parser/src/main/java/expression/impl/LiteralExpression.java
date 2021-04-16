package expression.impl;

import expression.Expression;
import visitor.ExpressionVisitor;

public class LiteralExpression implements Expression {

    // TODO esto lo hable con toto, Si el parsea siempre a String esto es String y no object. tengo que tener un token por cada expression
    // y ahi pregunto por el tokentype y despues parsear de acuerdo a eso
    private final String value;

    public LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public Object accept(ExpressionVisitor expressionVisitor) {
        return expressionVisitor.visitLiteral(this);
    }

    public String getValue() {
        return value;
    }
}
