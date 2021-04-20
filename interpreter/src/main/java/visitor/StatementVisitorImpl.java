package visitor;

import exception.InterpretException;

import interpreter.Interpreter;
import interpreter.InterpreterMemory;
import statement.impl.ExpressionStatement;
import statement.impl.IfStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;

import static token.TokenType.*;

public class StatementVisitorImpl implements  StatementVisitor{
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
        Object value = null;
        if (statement.getExpression() != null){
            value = statement.getExpression().accept(expressionVisitor);
        }
        if (value == null){
            interpreterMemory.addVariableDefinition(statement.getName().getTokenValue(), statement.getKeyWord().getTokenType(), statement.getType(), null);
            return;
        }
        if (statement.getType() == NUMBERTYPE){
            if (!(value instanceof Number)){ // lo mismo, aca si toto decide usar strings siempre tende que usar el token y no el instaceOf
                throw new InterpretException(statement.getName(), "Expected a Number");
            }
        }
        if (statement.getType() == STRINGTYPE){
            if (!(value instanceof String)){
                throw new InterpretException(statement.getName(), "Expected a String");
            }
        }

        interpreterMemory.addVariableDefinition(statement.getName().getTokenValue(), statement.getKeyWord().getTokenType(), statement.getType(), value);
    }

    @Override
    public void visitIfStatement(IfStatement statement) {
        // todo. Esto es con un If de una unico statement .
            // - Falta: que la variable que se decalre dentro del if no exista mas fuera de este
            // - multi statments dentro del if
        if (isBoolean(statement.getCondition().accept(expressionVisitor))) {
            //true branch
            statement.getConditionStatement().accept(this);
        } else if (statement.getElseStatement() != null) {
            // false branch
            statement.getElseStatement().accept(this);
        }
    }

    private boolean isBoolean(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }

}
