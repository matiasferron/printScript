package interpreter;

import exception.InterpretException;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static token.TokenType.LET;

public class InterpreterMemory {

    private final Map<String, VariableDefinitionDataImplementation> values = new HashMap<>();
    private final List<String> printedValues = new ArrayList<>();

    public InterpreterMemory() {
    }

    public Object get(Token name) {
        if (values.containsKey(name.getTokenValue())) {
            return values.get(name.getTokenValue()).getValue();
        }

        throw new InterpretException(name, "Variable not found");
    }

    public List<String> getPrintedValues() {
        return printedValues;
    }

    public void addPrintedValues(String value) {
        printedValues.add(value);
    }

    public void addVariableDefinition(String varName, TokenType keyword, TokenType type, Object value) {
        values.put(varName, new VariableDefinitionDataImplementation(keyword, type, value));
    }

    public void setVariableValue(Token varName, Object value) {
        if (values.containsKey(varName.getTokenValue())) {
            VariableDefinitionDataImplementation variable = values.get(varName.getTokenValue());
            if (variable.getVarDefinitionKey() == LET) {
                switch (variable.getType()) {
                    case NUMBERTYPE: {
                        if (!(value instanceof Number)) { // queda definir lo del Objeto o string con tokens
                            throw new InterpretException(varName, "Expected a number");
                        }
                        break;
                    }

                    case STRINGTYPE: {
                        if (!(value instanceof String)) {
                            throw new InterpretException(varName, "Expected a string");
                        }
                        break;
                    }
                }

                variable.setValue(value);
                values.put(varName.getTokenValue(), variable);
                return;
            } else {
                throw new InterpretException(varName, "Constant cannot be changed");
            }
        }
        throw new InterpretException(varName, "Undefined variable '" + varName.getTokenValue() + "'.");
    }
}
