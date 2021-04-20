package visitor.StatementVisitorHelpers;

import exception.InterpretException;
import interpreter.InterpreterMemory;
import statement.impl.VariableStatement;
import visitor.ExpressionVisitor;

import static token.TokenType.NUMBERTYPE;
import static token.TokenType.STRINGTYPE;

public class VisitVariableStatementHelper {

    public static void visitVariableStatementHelper(VariableStatement variableStatement, ExpressionVisitor expressionVisitor, InterpreterMemory interpreterMemory){
        Object value = null;
        if (variableStatement.getExpression() != null){
            value = variableStatement.getExpression().accept(expressionVisitor);
        }
        if (value == null){
            interpreterMemory.addVariableDefinition(variableStatement.getName().getTokenValue(), variableStatement.getKeyWord().getTokenType(), variableStatement.getType(), null);
            return;
        }
        if (variableStatement.getType() == NUMBERTYPE){
            if (!(value instanceof Number)){
                throw new InterpretException(variableStatement.getName(), "Expected a Number");
            }
        }
        if (variableStatement.getType() == STRINGTYPE){
            if (!(value instanceof String)){
                throw new InterpretException(variableStatement.getName(), "Expected a String");
            }
        }

        interpreterMemory.addVariableDefinition(variableStatement.getName().getTokenValue(), variableStatement.getKeyWord().getTokenType(), variableStatement.getType(), value);
    }
}
