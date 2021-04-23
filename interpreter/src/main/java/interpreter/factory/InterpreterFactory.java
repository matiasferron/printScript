package interpreter.factory;

import interpreter.Interpreter;
import interpreter.InterpreterImplementation;
import visitor.StatementVisitor;

import java.util.List;

public interface InterpreterFactory {

    static InterpreterFactory newParserFactory() {
        return new InterpreterFactoryImpl();
    }
    Interpreter createInterpreter(StatementVisitor statementVisitor);
}
