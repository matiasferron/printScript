package interpreter;


import statement.Statement;
import token.Token;
import token.TokenType;

import java.util.List;


public interface Interpreter{
    void interpret(List<Statement> statements);

    Object get(Token name); //

    List<String> getPrintedValues();
    void addPrintedValues(String value);

    void addVariableDefinition(String name, TokenType keyword, TokenType type, Object value);

    void setVariableValue(Token name, Object value);
}
