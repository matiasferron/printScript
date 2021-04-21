package visitor;

import exception.InterpretException;

import interpreter.InterpreterMemory;
import statement.impl.ExpressionStatement;
import statement.impl.IfStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;
import visitor.StatementVisitorHelpers.VisitVariableStatementHelper;

import static token.TokenType.*;

public class StatementVisitorImpl implements StatementVisitor {
    private final ExpressionVisitor expressionVisitor;
    private final InterpreterMemory interpreterMemory;

    public StatementVisitorImpl(ExpressionVisitor expressionVisitor, InterpreterMemory interpreterMemory) {
        this.expressionVisitor = expressionVisitor;
        this.interpreterMemory = interpreterMemory;
    }


    // Statments
    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        statement.getExpression().accept(expressionVisitor);
    }

    @Override
    public void visitPrintStatement(PrintStatement statement) {
        Object value = statement.getExpression().accept(expressionVisitor);
        interpreterMemory.addPrintedValues(value.toString());
        System.out.println(value);
    }

    @Override
    public void visitVariableStatement(VariableStatement statement) {
        VisitVariableStatementHelper.visitVariableStatementHelper(statement, expressionVisitor, interpreterMemory);
    }

    @Override
    public void visitIfStatement(IfStatement statement) {
        // todo.
        // - Falta: que la variable que se decalre dentro del if no exista mas fuera de este
        if (isBoolean(statement.getCondition().accept(expressionVisitor))) {
            interpreterMemory.turnOnTemporalSpace();
            //true branch
            statement.getConditionStatement().forEach(statement1 ->
                    statement1.accept(this)
            );
        } else if (statement.getElseStatement().size() != 0) {
            interpreterMemory.turnOnTemporalSpace();
            // false branch
            statement.getElseStatement().forEach(statement1 ->
                    statement1.accept(this)
            );
        }

        interpreterMemory.turnOffTemporalSpace();
    }

    private boolean isBoolean(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean) object;
        return true;
    }

}
