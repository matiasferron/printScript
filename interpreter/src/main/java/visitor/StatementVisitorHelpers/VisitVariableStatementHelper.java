package visitor.StatementVisitorHelpers;

import static token.TokenType.*;

import exception.InterpretException;
import interpreter.helper.InterpreterHelper;
import statement.impl.VariableStatement;
import visitor.ExpressionVisitor;

public class VisitVariableStatementHelper implements VisitorStatementHelper {

  public void visitVariableStatementHelper(
      VariableStatement variableStatement,
      ExpressionVisitor expressionVisitor,
      InterpreterHelper interpreterMemory) {
    Object value = null;
    if (variableStatement.getExpression() != null) {
      value = variableStatement.getExpression().accept(expressionVisitor);
    }
    if (value == null) {
      interpreterMemory.addVariableDefinition(
          variableStatement.getName().getTokenValue(),
          variableStatement.getKeyWord().getTokenType(),
          variableStatement.getType(),
          null,
          variableStatement.getName());
      return;
    }
    if (variableStatement.getType() == NUMBERTYPE) {
      if (!(value instanceof Number)) {
        throw new InterpretException(variableStatement.getName(), "Expected a Number");
      }
    }
    if (variableStatement.getType() == STRINGTYPE) {
      if (!(value instanceof String)) {
        throw new InterpretException(variableStatement.getName(), "Expected a String");
      }
    }

    if (variableStatement.getType() == BOOLEAN) {
      if (!(value instanceof Boolean)) {
        throw new InterpretException(variableStatement.getName(), "Expected a boolean");
      }
    }

    interpreterMemory.addVariableDefinition(
        variableStatement.getName().getTokenValue(),
        variableStatement.getKeyWord().getTokenType(),
        variableStatement.getType(),
        value,
        variableStatement.getName());
  }
}
