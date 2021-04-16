package visitor;

import exception.InterpretException;

import interpreter.Interpreter;
import statement.impl.ExpressionStatement;
import statement.impl.PrintStatement;
import statement.impl.VariableStatement;

import static token.TokenType.*;

public class StatementVisitorImpl implements  StatementVisitor{
    private final Interpreter interpreter;
    private final ExpressionVisitor expressionVisitor;

    public StatementVisitorImpl(Interpreter interpreter, ExpressionVisitor expressionVisitor) {
        this.interpreter = interpreter;
        this.expressionVisitor = expressionVisitor;
    }


    // Statments
    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        statement.getExpression().accept(expressionVisitor);
    }

    @Override
    public void visitPrintStatement(PrintStatement statement) {
        Object value = statement.getExpression().accept(expressionVisitor);
        System.out.println(value);
    }

    @Override
    public void visitVariableStatement(VariableStatement statement) {
        Object value = null;
        if (statement.getExpression() != null){
            value = statement.getExpression().accept(expressionVisitor);
        }
        if (value == null){
            interpreter.addVariableDefinition(statement.getName().getTokenValue(), statement.getKeyWord().getTokenType(), statement.getType(), null);
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

        interpreter.addVariableDefinition(statement.getName().getTokenValue(), statement.getKeyWord().getTokenType(), statement.getType(), value);
    }

}
