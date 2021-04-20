package interpreter;

import exception.InterpretException;
import statement.Statement;
import token.Token;
import token.TokenType;
import visitor.StatementVisitor;
import visitor.StatementVisitorImpl;
import visitor.ExpressionVisitorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static token.TokenType.*;

public class InterpreterImplementation implements Interpreter{

    private final StatementVisitor visitor;

    public InterpreterImplementation(StatementVisitor statementVisitor) {
        this.visitor = statementVisitor;
    }

    @Override
    public void interpret(List<Statement> statements) {
        statements.forEach(s ->
                s.accept(visitor)
        );
    }

}
