package interpreter.helper;

import exception.InterpretException;
import interpreter.VariableDefinitionDataImplementation;
import token.Token;
import token.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static token.TokenType.LET;

public class InterpreterMemory implements InterpreterHelper {

    private final Map<String, VariableDefinitionDataImplementation> values;
    private final List<String> printedValues;
    private boolean isTemporal;
    private Map<String, VariableDefinitionDataImplementation> temporalValues;


    public InterpreterMemory() {
        this.values = new HashMap<>();
        this.printedValues = new ArrayList<>();
        this.isTemporal = false;
        this.temporalValues = new HashMap<>();
    }

    public Object get(Token name) {
        if (isTemporal)
            return getTempValues(name);


        if (values.containsKey(name.getTokenValue()))
            return values.get(name.getTokenValue()).getValue();


        throw new InterpretException(name, "Variable " + name.getTokenValue() + " not found");
    }

    public void turnOnTemporalSpace(){
        this.isTemporal = true;
    }

    public void turnOffTemporalSpace(){
        this.isTemporal = false;
        this.temporalValues = new HashMap<>();
    }

    public List<String> getPrintedValues() {
        return printedValues;
    }

    public void addPrintedValues(String value) {
        printedValues.add(value);
    }

    public void addVariableDefinition(String varName, TokenType keyword, TokenType type, Object value, Token name) {
        if (isTemporal) {
            if (values.containsKey(varName)){
                throw new InterpretException(name, "Variable " + varName + " already defined. Error");
            }
            temporalValues.put(varName, new VariableDefinitionDataImplementation(keyword, type, value));
            return;
        }

        values.put(varName, new VariableDefinitionDataImplementation(keyword, type, value));
    }

    public void setVariableValue(Token varName, Object value) {
        if (isTemporal) {
            setTemporalVariableValue(varName, value);
            return;
        }

        if (values.containsKey(varName.getTokenValue())) {
            checkIfContainAndUpdate(varName, value);
            return;
        }

        throw new InterpretException(varName, "Undefined variable '" + varName.getTokenValue() + "'.");
    }


    // Private methods

    private Object getTempValues(Token name) {
        if (values.containsKey(name.getTokenValue())) {
            return values.get(name.getTokenValue()).getValue();
        }

        if (temporalValues.containsKey(name.getTokenValue())) {
            return temporalValues.get(name.getTokenValue()).getValue();
        }

        throw new InterpretException(name, "Variable " + name.getTokenValue() + " not found");
    }

    private void checkIfContainAndUpdate(Token varName, Object value) {

        VariableDefinitionDataImplementation variable = values.get(varName.getTokenValue());

        updateValue(variable, varName, value, values);

    }

    private void checkType(TokenType type, Object value, Token varName) {
        if (type == null)
            return;
        switch (type) {
            case NUMBERTYPE: {
                if (!(value instanceof Number)) {
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

            //todo test
            case BOOLEAN: {
                if (!(value instanceof Boolean)) {
                    throw new InterpretException(varName, "Expected a boolean");
                }
                break;
            }
        }
    }

    private void setTemporalVariableValue(Token varName, Object value) {
        if (values.containsKey(varName.getTokenValue())) {
            checkIfContainAndUpdate(varName, value);
            return;
        }

        if (temporalValues.containsKey(varName.getTokenValue())) {
            checkIfContainTemporalValueAndUpdate(varName, value);
            return;
        }

        throw new InterpretException(varName, "Undefined variable '" + varName.getTokenValue() + "'.");
    }

    private void checkIfContainTemporalValueAndUpdate(Token varName, Object value) {

        VariableDefinitionDataImplementation tempValue = temporalValues.get(varName.getTokenValue());

        updateValue(tempValue, varName, value, temporalValues);
    }

    private void updateValue(VariableDefinitionDataImplementation oldValue, Token varName, Object value, Map<String, VariableDefinitionDataImplementation> mapValues) {
        if (oldValue.getVarDefinitionKey() == LET) {
            checkType(oldValue.getType(), value, varName);
            oldValue.setValue(value);
            mapValues.put(varName.getTokenValue(), oldValue);
        } else {
            throw new InterpretException(varName, "Constant cannot be changed");
        }
    }
}
