package statement;

import expression.Expression;
import visitor.StatementVisitor;

public interface Statement {
    void accept(StatementVisitor statementVisitor);
    Expression getExpression();
}
