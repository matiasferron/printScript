package statement.impl;

import expression.Expression;
import statement.Statement;
import visitor.StatementVisitor;

public class IfStatement implements Statement {

    private final Expression condition;
    private final Statement conditionStatement;
    private final Statement elseStatement;

    public IfStatement(Expression condition, Statement conditionStatement, Statement elseStatement) {
        this.condition = condition;
        this.conditionStatement = conditionStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public Expression getExpression() {
        return condition;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visitIfStatement(this);
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getConditionStatement() {
        return conditionStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }
}
