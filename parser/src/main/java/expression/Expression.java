package expression;

import token.Token;
import visitor.ExpressionVisitor;

public interface Expression {
    Object accept(ExpressionVisitor expressionVisitor); // todo cambiar a String
//    Token getToken();
}
