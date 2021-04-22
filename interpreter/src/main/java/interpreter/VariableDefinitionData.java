package interpreter;

import token.TokenType;

public interface VariableDefinitionData {

  TokenType getVarDefinitionKey();

  TokenType getType();

  Object getValue();

  void setValue(Object value);
}
