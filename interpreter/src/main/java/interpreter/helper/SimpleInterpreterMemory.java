package interpreter.helper;

import static token.TokenType.LET;

import exception.InterpretException;
import interpreter.VariableDefinitionDataImplementation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import token.Token;
import token.TokenType;

public class SimpleInterpreterMemory implements InterpreterHelper {
  private final Map<String, VariableDefinitionDataImplementation> values;
  private final List<String> printedValues;

  public SimpleInterpreterMemory() {
    this.values = new HashMap<>();
    this.printedValues = new ArrayList<>();
  }

  public Object get(Token name) {

    if (values.containsKey(name.getTokenValue()))
      return values.get(name.getTokenValue()).getValue();

    throw new InterpretException(name, "Variable " + name.getTokenValue() + " not found");
  }

  public void turnOnTemporalSpace() {}

  public void turnOffTemporalSpace() {}

  public List<String> getPrintedValues() {
    return printedValues;
  }

  public void addPrintedValues(String value) {
    printedValues.add(value);
  }

  public void addVariableDefinition(
      String varName, TokenType keyword, TokenType type, Object value, Token name) {
    values.put(varName, new VariableDefinitionDataImplementation(keyword, type, value));
  }

  public void setVariableValue(Token varName, Object value) {
    if (values.containsKey(varName.getTokenValue())) {
      checkIfContainAndUpdate(varName, value);
      return;
    }

    throw new InterpretException(varName, "Undefined variable '" + varName.getTokenValue() + "'.");
  }

  // Private methods

  private void checkIfContainAndUpdate(Token varName, Object value) {

    VariableDefinitionDataImplementation variable = values.get(varName.getTokenValue());

    updateValue(variable, varName, value, values);
  }

  private void checkType(TokenType type, Object value, Token varName) {
    if (type == null) return;
    switch (type) {
      case NUMBERTYPE:
        {
          if (!(value instanceof Number)) {
            throw new InterpretException(varName, "Expected a number");
          }
          break;
        }

      case STRINGTYPE:
        {
          if (!(value instanceof String)) {
            throw new InterpretException(varName, "Expected a string");
          }
          break;
        }

        // todo test
      case BOOLEAN:
        {
          if (!(value instanceof Boolean)) {
            throw new InterpretException(varName, "Expected a boolean");
          }
          break;
        }
    }
  }

  private void updateValue(
      VariableDefinitionDataImplementation oldValue,
      Token varName,
      Object value,
      Map<String, VariableDefinitionDataImplementation> values) {
    if (oldValue.getVarDefinitionKey() == LET) {
      checkType(oldValue.getType(), value, varName);
      oldValue.setValue(value);
      values.put(varName.getTokenValue(), oldValue);
    } else {
      throw new InterpretException(varName, "Constant cannot be changed");
    }
  }
}
