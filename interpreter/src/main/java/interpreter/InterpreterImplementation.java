package interpreter;

import exception.InterpretException;
import statement.Statement;
import token.Token;
import token.TokenType;
import visitor.ExpressionVisitor;
import visitor.StatementVisitorImpl;
import visitor.ExpressionVisitorImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static token.TokenType.*;

public class InterpreterImplementation implements Interpreter{

    private final Map<String, VariableDefinitionDataImplementation> values = new HashMap<>();

    // todo el expressionVisitor no tiene que recibir el interpete
    // pasarle el expressionVisitor como parametro al statmentvisitor?
    private final ExpressionVisitor expressionVisitor = new ExpressionVisitorImpl(this);
    private final StatementVisitorImpl visitor = new StatementVisitorImpl(this, expressionVisitor);

    @Override
    public void interpret(List<Statement> statements) {
        statements.forEach(s ->
                s.accept(visitor)
        );
    }

    //
    // in other class?
    //
    public Object get(Token name) {
        if (values.containsKey(name.getValue())){
            return values.get(name.getValue()).getValue();
        }

        throw new InterpretException(name, "Variable not found");
    }

    public void addVariableDefinition(String varName, TokenType keyword, TokenType type, Object value) {
        values.put(varName, new VariableDefinitionDataImplementation(keyword, type, value));
    }

    public void setVariableValue(Token varName, Object value) {
        if (values.containsKey(varName.getValue())) {
            VariableDefinitionDataImplementation variable = values.get(varName.getValue());
            if(variable.getVarDefinitionKey() == LET){
                switch (variable.getType()){
                    case NUMBER: {
                        if (!(value instanceof Number)) { // queda definir lo del Objeto o string con tokens
                            throw new InterpretException(varName, "Expected a number");
                        }
                    }
                    case STRING: {
                        if (!(value instanceof String)){
                            throw new InterpretException(varName, "Expected a string");
                        }
                    }
                }

                variable.setValue(value);
                values.put(varName.getValue(), variable);
                return;
            } else {
                throw new InterpretException(varName, "Constant cannot be changed");
            }
        }
        throw new InterpretException(varName, "Undefined variable '" + varName.getValue() + "'.");
    }
}
