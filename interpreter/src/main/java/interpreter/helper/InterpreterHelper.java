package interpreter.helper;

import token.Token;
import token.TokenType;

import java.util.List;

public interface InterpreterHelper {

  Object get(Token name);

  void turnOnTemporalSpace();

  void turnOffTemporalSpace();

  // Only to save printed values in console.
  List<String> getPrintedValues();

  void addPrintedValues(String value);

  void addVariableDefinition(
      String varName, TokenType keyword, TokenType type, Object value, Token name);

  void setVariableValue(Token varName, Object value);
}
